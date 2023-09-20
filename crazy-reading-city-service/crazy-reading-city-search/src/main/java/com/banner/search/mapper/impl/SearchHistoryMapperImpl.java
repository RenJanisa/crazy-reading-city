package com.banner.search.mapper.impl;

import com.banner.model.search.pojos.SearchHistory;
import com.banner.search.mapper.SearchHistoryMapper;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author rjj
 * @date 2023/9/15 - 15:59
 */
@Component
public class SearchHistoryMapperImpl implements SearchHistoryMapper {
    @Resource
    private MongoTemplate mongoTemplate;

    @Async
    public void saveSearchHistory(String condition, Long userId) {

        SearchHistory newSearchHistory = new SearchHistory(null
                , condition
                , userId.toString()
                , LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        //存在则更新
        UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(new Criteria().andOperator(
                Criteria.where("userId").is(userId.toString()),
                Criteria.where("keyword").is(condition)
        )), new Update().set("saveTime", newSearchHistory.getSaveTime()), SearchHistory.class);

        if (updateResult.getMatchedCount() > 0) {
            //更新成功则返回
            return;
        }

        //不存在该关键词,查看已存储个数
        List<SearchHistory> searchHistories = mongoTemplate.find(Query
                        .query(Criteria.where("userId").is(userId.toString()))
                        .with(Sort.by(Sort.Direction.DESC, "saveTime"))
                , SearchHistory.class);

        //如果超过10个搜索词替换最早一个
        if (searchHistories.size() >= 10) {
            SearchHistory searchHistory = searchHistories.get((searchHistories.size() - 1));
            mongoTemplate.findAndReplace(Query.query(Criteria.where("id").is(searchHistory.getId())), newSearchHistory);
            return;
        }

        mongoTemplate.save(newSearchHistory);

    }

    public List<SearchHistory> get(String userId) {

        return mongoTemplate.find(Query
                        .query(Criteria.where("userId").is(userId))
                        .with(Sort.by(Sort.Direction.DESC, "saveTime"))
                , SearchHistory.class);

    }

    @Override
    public boolean del(String userId) {

        DeleteResult remove = mongoTemplate.remove(Query
                .query(Criteria.where("userId").is(userId))
                , SearchHistory.class);

        return remove.getDeletedCount() > 0;
    }
}
