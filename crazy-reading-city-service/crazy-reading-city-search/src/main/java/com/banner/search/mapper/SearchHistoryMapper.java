package com.banner.search.mapper;

import com.banner.model.search.pojos.SearchHistory;
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
 * @date 2023/8/29 - 17:47
 */
public interface SearchHistoryMapper {


    void saveSearchHistory(String condition, Long id);

    List<SearchHistory> get(String userId);

    boolean del(String userId);
}
