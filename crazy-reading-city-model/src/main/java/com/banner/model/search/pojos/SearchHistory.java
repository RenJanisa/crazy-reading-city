package com.banner.model.search.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author rjj
 * @date 2023/8/29 - 16:30
 */
@Data
@Document(collection = "crc-book-search")
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistory {

    @Id
    private String id;

    @Indexed
    private String keyword;

    private String userId;

    private String saveTime;

}
