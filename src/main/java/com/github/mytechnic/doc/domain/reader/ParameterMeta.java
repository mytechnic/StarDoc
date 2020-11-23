package com.github.mytechnic.doc.domain.reader;

import com.github.mytechnic.doc.annotation.StarParameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ParameterMeta {

    private Class<?> parameterType;
    private String name;
    private StarParameter starParameter;
    private PathVariable pathVariable;
    private RequestParam requestParam;
    private RequestBody requestBody;
    private RequestHeader requestHeader;
    private RequestPart requestPart;
    private ModelAttribute modelAttribute;
    private boolean isRequestModel;
}
