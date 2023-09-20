package com.banner.search.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.banner.apis.book.IBookClient;
import com.banner.common.exception.CRCException;
import com.banner.common.utils.ThreadLocalUtil;
import com.banner.model.common.dtos.PageDto;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.banner.model.search.dtos.CrcBookAuthorSearchDto;
import com.banner.model.search.dtos.CrcBookSearchDto;
import com.banner.model.search.pojos.SearchHistory;
import com.banner.search.mapper.SearchHistoryMapper;
import com.banner.search.service.SearchService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.banner.search.utils.SearchConstant.*;

/**
 * @author rjj
 * @date 2023/8/9 - 20:22
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private SearchHistoryMapper searchHistoryMapper;

    @Override
    public ResponseResult searchBookByName(PageDto getBookSearchDto) {

        //设置查询条件
        SearchRequest searchRequest = new SearchRequest(ES_BOOK_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //关键字的分词之后查询
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(getBookSearchDto.getCondition()).field(ES_BOOK_KEY_WORD);

        boolQueryBuilder.must(queryStringQueryBuilder);

        //分页查询
        searchSourceBuilder.from((getBookSearchDto.getPage() - 1) * getBookSearchDto.getPageSize());
        searchSourceBuilder.size(getBookSearchDto.getPageSize());

        //设置高亮搜索词
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(ES_BOOK_KEY_WORD);
        highlightBuilder.preTags(ES_HIGH_PRE);
        highlightBuilder.postTags(ES_HIGH_POST);
        searchSourceBuilder.highlighter(highlightBuilder);

        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new CRCException(500, e.getMessage());
        }
        //结果封装返回
        List<Map> list = new ArrayList<>();

        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            Map map = JSONUtil.toBean(json, Map.class);
            //处理高亮
            if (hit.getHighlightFields() != null && hit.getHighlightFields().size() > 0) {
                Text[] bookNames = hit.getHighlightFields().get(ES_BOOK_KEY_WORD).getFragments();
                String bookName = StringUtils.join(bookNames);
                //高亮标题
                map.put("highBookName", bookName);
            } else {
                //原始标题
                map.put("highBookName", map.get(ES_BOOK_KEY_WORD));
            }
            list.add(map);
        }

        if (ThreadLocalUtil.getId() != null)
            searchHistoryMapper.saveSearchHistory(getBookSearchDto.getCondition(), ThreadLocalUtil.getId()); //保存搜索记录



        return ResponseResult.okResult(list);
    }

    @Override
    public ResponseResult searchBookAuthorByName(String authorName) {

        //设置查询条件
        SearchRequest searchRequest = new SearchRequest(ES_BOOK_AUTHOR_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //关键字查询
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(authorName).field(ES_BOOK_AUTHOR_KEY_WORD);

        boolQueryBuilder.must(queryStringQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse;

        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new CRCException(500, e.getMessage());
        }

        SearchHit[] hits = searchResponse.getHits().getHits();
        List<Map> r = new ArrayList<>();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            Map map = JSONUtil.toBean(sourceAsString, Map.class);
            r.add(map);
        }

        return ResponseResult.okResult(r);
    }

    @Override
    public ResponseResult getSearchHistory(String userId) {

        if (StrUtil.isBlank(userId)) userId = ThreadLocalUtil.getId().toString();
        List<SearchHistory> searchHistories = searchHistoryMapper.get(userId);

        return ResponseResult.okResult(searchHistories);
    }

    @Override
    public ResponseResult delSearchHistory(String userId) {

        if (StrUtil.isBlank(userId)) userId = ThreadLocalUtil.getId().toString();


        return searchHistoryMapper.del(userId) ? ResponseResult.okResult(200, "删除成功") :
                ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
    }


    @Resource
    private IBookClient bookClient;

    @XxlJob("crcFlushEs")
    public void crcFlushEs() {
        log.info("es更新了");
        //获取书籍列表
        List<CrcBookSearchDto> bookList = bookClient.getBookSearchList();
        List<CrcBookAuthorSearchDto> bookAuthorList = bookClient.getBookAuthorSearchList();

        //条件删除
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest();
        deleteByQueryRequest.indices(ES_BOOK_INDEX, ES_BOOK_AUTHOR_INDEX);
        //匹配所有
        deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());

        BulkRequest bookBulkRequest = new BulkRequest(ES_BOOK_INDEX);
        bookList.forEach(i -> {
            bookBulkRequest.add(new IndexRequest().id(i.getId()).source(JSONUtil.toJsonStr(i), XContentType.JSON));
        });

        BulkRequest bookAuthorBulkRequest = new BulkRequest(ES_BOOK_AUTHOR_INDEX);
        bookAuthorList.forEach(i -> {
            bookAuthorBulkRequest.add(new IndexRequest().id(i.getId().toString()).source(JSONUtil.toJsonStr(i), XContentType.JSON));
        });

        try {
            //删除所有内容
            BulkByScrollResponse bulkByScrollResponse = restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
            log.info("删除失败结果：" + bulkByScrollResponse.getBulkFailures());
            //添加新的
            BulkResponse bulk1 = restHighLevelClient.bulk(bookBulkRequest, RequestOptions.DEFAULT);
            BulkResponse bulk2 = restHighLevelClient.bulk(bookAuthorBulkRequest, RequestOptions.DEFAULT);
            log.info("是否添加失败: " + bulk1.hasFailures() + "," + bulk2.hasFailures());
        } catch (Exception e) {
            throw new CRCException(500, e.getMessage());
        }

    }


}
