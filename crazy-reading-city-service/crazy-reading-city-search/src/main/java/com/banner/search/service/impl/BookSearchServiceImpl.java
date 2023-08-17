package com.banner.search.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.banner.apis.book.IBookClient;
import com.banner.common.exception.CRCException;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.banner.model.search.dtos.CrcBookSearchDto;
import com.banner.model.search.dtos.GetBookSearchDto;
import com.banner.search.service.BookSearchService;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
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
public class BookSearchServiceImpl implements BookSearchService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Override
    public ResponseResult searchByName(GetBookSearchDto getBookSearchDto) {

        //设置查询条件
        SearchRequest searchRequest = new SearchRequest(ES_BOOK_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //关键字的分词之后查询
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders
                .queryStringQuery(getBookSearchDto.getBookName()).field(ES_KEY_WORD);

        boolQueryBuilder.must(queryStringQueryBuilder);

        //分页查询
        searchSourceBuilder.from((getBookSearchDto.getPage() - 1) * getBookSearchDto.getPageSize());
        searchSourceBuilder.size(getBookSearchDto.getPageSize());

        //设置高亮搜索词
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(ES_KEY_WORD);
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
                Text[] bookNames = hit.getHighlightFields().get(ES_KEY_WORD).getFragments();
                String bookName = StringUtils.join(bookNames);
                //高亮标题
                map.put("h_book_name", bookName);
            } else {
                //原始标题
                map.put("h_book_name", map.get(ES_KEY_WORD));
            }
            list.add(map);
        }

        return ResponseResult.okResult(list);
    }

    @Resource
    private IBookClient bookClient;

    @XxlJob("crcBookEs")
    public void flushBook(){
        //获取书籍列表
        List<CrcBookSearchDto> bookList = bookClient.getBookSearchList();

        //条件删除
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest();
        deleteByQueryRequest.indices(ES_BOOK_INDEX);
        //匹配所有
        deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());

        BulkRequest bulkRequest = new BulkRequest(ES_BOOK_INDEX);
        bookList.forEach(i->{
            bulkRequest.add(new IndexRequest().id(i.getId())
                    .source(JSONUtil.toJsonStr(i), XContentType.JSON));
            log.info(i.toString());
        });

        try {
            //删除所有内容
            BulkByScrollResponse bulkByScrollResponse = restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
            log.info("删除失败结果："+ bulkByScrollResponse.getBulkFailures());
            //添加新的
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("是否添加失败: "+bulk.hasFailures());
        } catch (Exception e) {
            throw new CRCException(500, e.getMessage());
        }

    }

}
