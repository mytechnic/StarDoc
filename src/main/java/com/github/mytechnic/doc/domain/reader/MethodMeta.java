package com.github.mytechnic.doc.domain.reader;

import com.github.mytechnic.doc.annotation.StarApiSpecification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MethodMeta {

    private StarApiSpecification starSpecification;
    private RequestMapping requestMapping;
    private GetMapping getMapping;
    private PostMapping postMapping;
    private PutMapping putMapping;
    private PatchMapping patchMapping;
    private DeleteMapping deleteMapping;

    private RequestBody requestBody;
    private ResponseBody responseBody;

    private ParameterMeta[] parameterMetas;
    private Class<?> returnType;
}
