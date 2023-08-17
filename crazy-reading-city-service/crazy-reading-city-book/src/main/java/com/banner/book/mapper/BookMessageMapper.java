package com.banner.book.mapper;

import cn.hutool.core.bean.BeanUtil;
import com.banner.apis.user.IUserClient;
import com.banner.model.book.dtos.GetBookMessageDto;
import com.banner.model.book.pojos.Message;
import com.banner.model.user.dtos.SimpleUserDto;
import com.sun.prism.impl.BaseContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rjj
 * @date 2023/8/7 - 15:33
 */
@Component
public class BookMessageMapper {

    @Resource
    private MongoTemplate mongoTemplate;


    public Message saveToMongo(Message message) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        message.setSendDate(date);
        return mongoTemplate.save(message);
    }

    public List<Message> getMessages(GetBookMessageDto getBookMessageDto) {

        //条件一:用户A发送给用户B的条件
        Criteria criteriaFrom = new Criteria().andOperator(
                Criteria.where("bookId").is(getBookMessageDto.getBookId())
        );

        PageRequest pageRequest = PageRequest.of(getBookMessageDto.getPage() - 1
                , getBookMessageDto.getRows()
                , Sort.by(Sort.Direction.ASC, "send_date"));
        //设置查询条件,分页
        Query query = Query.query(criteriaFrom).with(pageRequest);

        return mongoTemplate.find(query, Message.class);

    }
}
