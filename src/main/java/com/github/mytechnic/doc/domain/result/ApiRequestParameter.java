package com.github.mytechnic.doc.domain.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApiRequestParameter {

    private String requestType;
    private String name;
    private String description;
    private String example;
    private String notes;
    private boolean required;
    private int order;
}
