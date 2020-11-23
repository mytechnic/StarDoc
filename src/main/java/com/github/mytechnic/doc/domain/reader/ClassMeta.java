package com.github.mytechnic.doc.domain.reader;

import com.github.mytechnic.doc.annotation.StarApiDocument;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClassMeta {

    private Class<?> classBean;
    private StarApiDocument starApiDocument;
    private RequestMapping requestMapping;
    private List<MethodMeta> methodMetas;
}
