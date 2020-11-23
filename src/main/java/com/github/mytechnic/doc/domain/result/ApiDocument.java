package com.github.mytechnic.doc.domain.result;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class ApiDocument {

    private final String name;
    private final List<ApiSpecification> apiSpecifications;
}
