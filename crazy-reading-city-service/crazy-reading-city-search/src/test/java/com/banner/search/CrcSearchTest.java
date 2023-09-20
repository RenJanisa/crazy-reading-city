package com.banner.search;

import cn.hutool.json.JSONUtil;
import com.banner.model.book.pojos.CrcBook;
import com.banner.model.search.dtos.CrcBookSearchDto;
import com.banner.model.search.pojos.SearchHistory;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.mongodb.client.result.UpdateResult;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author rjj
 * @date 2023/8/8 - 16:20
 */

@SpringBootTest(classes = CRCSearchApplication.class)
@RunWith(SpringRunner.class)
public class CrcSearchTest {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    public void mongo(){
        SearchHistory newSearchHistory = new SearchHistory(null
                , "认知"
                , "1634389816206467074"
                , LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        //存在则更新
        UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(new Criteria().andOperator(
                Criteria.where("userId").is("1634389816206467074"),
                Criteria.where("keyword").is("认知")
        )), new Update().set("saveTime", newSearchHistory.getSaveTime()), SearchHistory.class);

        System.out.println(updateResult.getMatchedCount());
        System.out.println(updateResult.getModifiedCount());
    }


    @Test
    public void add() throws IOException {
        BulkRequest bulkRequest = new BulkRequest("crc_book_name");
        CrcBookSearchDto crcBookSearchDto = new CrcBookSearchDto();
        crcBookSearchDto.setId(IdWorker.getId()+"");
        crcBookSearchDto.setBookName("java程序设计");
        crcBookSearchDto.setImg("abc.jpg");
        System.out.println(crcBookSearchDto.toString());
        boolean crc_book_name = restHighLevelClient.indices().exists(new GetIndexRequest("crc_book_name"), RequestOptions.DEFAULT);
        System.out.println(crc_book_name);
        bulkRequest.add(new IndexRequest().id(crcBookSearchDto.getId())
                .source(JSONUtil.toJsonStr(crcBookSearchDto), XContentType.JSON));
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    @Test
    public void delete() throws IOException {
        DeleteResponse crc_book_name = restHighLevelClient.delete(new DeleteRequest("crc_book_name", "1634389816206467075"), RequestOptions.DEFAULT);
        System.out.println(crc_book_name.toString());
    }

}
