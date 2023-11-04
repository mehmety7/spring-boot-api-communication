package com.example.application.component.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyEvent {

    public static final String TOPIC_1 = "topic1";

    private String key;
    // other variables state, based on your use case
}
