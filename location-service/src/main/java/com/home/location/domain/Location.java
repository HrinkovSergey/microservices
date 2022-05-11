package com.home.location.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "location")
public class Location {

    @Id
    private String id;
    @Field(name = "country")
    private String locationCountry;
    @Field(name = "city")
    private String locationCity;
}
