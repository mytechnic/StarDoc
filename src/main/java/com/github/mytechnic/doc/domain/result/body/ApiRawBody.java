package com.github.mytechnic.doc.domain.result.body;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ApiRawBody implements ApiBody {

    private String raw;
}
