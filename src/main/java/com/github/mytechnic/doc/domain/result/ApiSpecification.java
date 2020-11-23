package com.github.mytechnic.doc.domain.result;

import com.github.mytechnic.doc.domain.result.body.ApiBody;
import com.github.mytechnic.doc.domain.result.body.ApiBodyType;
import com.github.mytechnic.doc.domain.result.parameter.ApiParameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpMethod;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class ApiSpecification {

    private String name;
    private boolean anyMethod;
    private List<HttpMethod> httpMethods;
    private List<String> urls;
    private List<ApiParameter> headers;
    private List<ApiParameter> params;
    private ApiBodyType bodyType;
    private ApiBody body;
}
