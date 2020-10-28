package com.zkdlu.elasticsearch;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "user")
public class User {
    @Id
    private int Id;
    private String name;
}
