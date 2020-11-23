package com.github.mytechnic.doc.domain.result.parameter;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ApiParameter {

    private final String type;
    private final String name;
    private final String description;
    private final String example;
    private final String notes;
    private final boolean required;
}
