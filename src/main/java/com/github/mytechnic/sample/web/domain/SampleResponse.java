package com.github.mytechnic.sample.web.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SampleResponse<T> {

    private String responseName;
    private T responseBody;
}
