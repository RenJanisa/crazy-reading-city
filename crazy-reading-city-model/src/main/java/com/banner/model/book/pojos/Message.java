package com.banner.model.book.pojos;

import com.sun.corba.se.spi.ior.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "crc-book-talk")
public class Message {
    @Id
    private String id;
    @NotEmpty(message = "信息不能为空")
    private String msg;
    @Field("send_date")
    private String sendDate;
    @Indexed
    private String bookId;
    private String userId;
    private String avatar;
    private String userName;
}