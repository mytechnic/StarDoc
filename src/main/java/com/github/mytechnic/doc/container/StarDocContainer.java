package com.github.mytechnic.doc.container;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.mytechnic.doc.annotation.StarApiDocument;
import com.github.mytechnic.doc.annotation.StarApiSpecification;
import com.github.mytechnic.doc.annotation.StarParameter;
import com.github.mytechnic.doc.domain.reader.ApiDocumentMeta;
import com.github.mytechnic.doc.domain.reader.ClassMeta;
import com.github.mytechnic.doc.domain.reader.MethodMeta;
import com.github.mytechnic.doc.domain.reader.ParameterMeta;
import com.github.mytechnic.doc.domain.result.ApiDocument;
import com.github.mytechnic.doc.domain.result.ApiSpecification;
import com.github.mytechnic.doc.domain.result.body.ApiBody;
import com.github.mytechnic.doc.domain.result.body.ApiBodyType;
import com.github.mytechnic.doc.domain.result.parameter.ApiParameter;
import com.thoughtworks.paranamer.AdaptiveParanamer;
import com.thoughtworks.paranamer.Paranamer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class StarDocContainer {
    private final ApplicationContext applicationContext;
    private final ApiDocumentMeta apiDocumentMeta = new ApiDocumentMeta();
    private final Paranamer paranamer = new AdaptiveParanamer();

    public void apiDocumentMetaScan() {

        String[] beanNameList = applicationContext.getBeanNamesForAnnotation(StarApiDocument.class);
        Arrays.stream(beanNameList).forEach(beanName -> {
            Object bean = applicationContext.getBean(beanName);

            List<MethodMeta> methodMappers = Arrays.stream(bean.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(StarApiSpecification.class))
                    .map(method -> {
                        MethodMeta methodMapper = new MethodMeta();
                        methodMapper.setStarSpecification(method.getAnnotation(StarApiSpecification.class));
                        methodMapper.setRequestMapping(method.getAnnotation(RequestMapping.class));
                        methodMapper.setGetMapping(method.getAnnotation(GetMapping.class));
                        methodMapper.setPostMapping(method.getAnnotation(PostMapping.class));
                        methodMapper.setPutMapping(method.getAnnotation(PutMapping.class));
                        methodMapper.setPatchMapping(method.getAnnotation(PatchMapping.class));
                        methodMapper.setDeleteMapping(method.getAnnotation(DeleteMapping.class));
                        methodMapper.setRequestBody(method.getAnnotation(RequestBody.class));
                        methodMapper.setResponseBody(method.getAnnotation(ResponseBody.class));

                        String[] parameterNames = paranamer.lookupParameterNames(method);
                        ParameterMeta[] parameterMappers = new ParameterMeta[method.getParameters().length];
                        for (int i = 0; i < method.getParameters().length; i++) {
                            Parameter parameter = method.getParameters()[i];

                            parameterMappers[i] = new ParameterMeta();
                            parameterMappers[i].setName(parameterNames[i]);
                            parameterMappers[i].setParameterType(parameter.getType());
                            parameterMappers[i].setStarParameter(parameter.getAnnotation(StarParameter.class));
                            parameterMappers[i].setPathVariable(parameter.getAnnotation(PathVariable.class));
                            parameterMappers[i].setRequestParam(parameter.getAnnotation(RequestParam.class));
                            parameterMappers[i].setRequestBody(parameter.getAnnotation(RequestBody.class));
                            parameterMappers[i].setRequestHeader(parameter.getAnnotation(RequestHeader.class));
                            parameterMappers[i].setRequestPart(parameter.getAnnotation(RequestPart.class));
                            parameterMappers[i].setModelAttribute(parameter.getAnnotation(ModelAttribute.class));

                            if (parameterMappers[i].getStarParameter() != null
                                    && parameterMappers[i].getPathVariable() == null
                                    && parameterMappers[i].getRequestParam() == null
                                    && parameterMappers[i].getRequestHeader() == null
                                    && parameterMappers[i].getRequestPart() == null) {
                                parameterMappers[i].setRequestModel(true);
                            }
                        }
                        methodMapper.setParameterMetas(parameterMappers);

                        methodMapper.setReturnType(method.getReturnType());
                        return methodMapper;
                    })
                    .collect(Collectors.toList());

            apiDocumentMeta.getClassMetas().add(getNewClassMapper(bean, methodMappers));
        });
    }

    private ClassMeta getNewClassMapper(Object bean, List<MethodMeta> methodMappers) {

        ClassMeta classMapper = new ClassMeta();
        classMapper.setClassBean(bean.getClass());
        classMapper.setStarApiDocument(bean.getClass().getAnnotation(StarApiDocument.class));
        classMapper.setRequestMapping(bean.getClass().getAnnotation(RequestMapping.class));
        classMapper.setMethodMetas(methodMappers);

        return classMapper;
    }

    public void aggregation() {

        List<ApiDocument> apiDocuments = apiDocumentMeta.getClassMetas().stream()
                .sorted(Comparator.comparing(c -> c.getStarApiDocument().order()))
                .map(c -> ApiDocument.builder()
                        .name(c.getClassBean().getCanonicalName())
                        .apiSpecifications(c.getMethodMetas().stream()
                                .sorted(Comparator.comparing(m -> m.getStarSpecification().order()))
                                .map(m -> getApiSpecification(c, m))
                                .collect(Collectors.toList())
                        )
                        .build())
                .collect(Collectors.toList());

        print(apiDocuments);
    }

    private ApiSpecification getApiSpecification(ClassMeta c, MethodMeta m) {

        List<HttpMethod> httpMethods = getHttpMethods(c, m);

        ApiSpecification apiSpecification = new ApiSpecification();
        apiSpecification.setName(m.getStarSpecification().name());
        apiSpecification.setAnyMethod(httpMethods == null);
        apiSpecification.setHttpMethods(httpMethods);
        apiSpecification.setUrls(getUrls(c, m));
        apiSpecification.setParams(getParams(m));
        apiSpecification.setHeaders(getHeaders(m));
        apiSpecification.setBodyType(ApiBodyType.RAW);
        apiSpecification.setBody(getBody(m));

        return apiSpecification;
    }

    private List<HttpMethod> getHttpMethods(ClassMeta c, MethodMeta m) {

        List<HttpMethod> httpMethods = new ArrayList<>();

        if (c.getRequestMapping() != null) {
            if (c.getRequestMapping().method().length == 0) {
                return null;
            } else {
                httpMethods.addAll(getHttpMethods(c.getRequestMapping()));
            }
        }

        if (m.getRequestMapping() != null) {
            if (m.getRequestMapping().method().length == 0) {
                return null;
            } else {
                httpMethods.addAll(getHttpMethods(m.getRequestMapping()));
            }
        } else if (m.getGetMapping() != null) {
            httpMethods.add(HttpMethod.GET);
        } else if (m.getPostMapping() != null) {
            httpMethods.add(HttpMethod.POST);
        } else if (m.getPutMapping() != null) {
            httpMethods.add(HttpMethod.PUT);
        } else if (m.getPatchMapping() != null) {
            httpMethods.add(HttpMethod.PATCH);
        } else if (m.getDeleteMapping() != null) {
            httpMethods.add(HttpMethod.DELETE);
        }

        return httpMethods;
    }

    private List<HttpMethod> getHttpMethods(RequestMapping requestMapping) {
        return Arrays.stream(requestMapping.method()).map(p -> HttpMethod.valueOf(p.name())).collect(Collectors.toList());
    }

    private List<String> getUrls(ClassMeta c, MethodMeta m) {

        if (c.getRequestMapping() == null) {
            if (m.getRequestMapping() != null) {
                return getValue(m.getRequestMapping().value(), m.getRequestMapping().path());
            } else if (m.getGetMapping() != null) {
                return getValue(m.getGetMapping().value(), m.getGetMapping().path());
            } else if (m.getPostMapping() != null) {
                return getValue(m.getPostMapping().value(), m.getPostMapping().path());
            } else if (m.getPutMapping() != null) {
                return getValue(m.getPutMapping().value(), m.getPutMapping().path());
            } else if (m.getPatchMapping() != null) {
                return getValue(m.getPatchMapping().value(), m.getPatchMapping().path());
            } else if (m.getDeleteMapping() != null) {
                return getValue(m.getDeleteMapping().value(), m.getDeleteMapping().path());
            } else {
                return null;
            }
        }

        List<String> urls = new ArrayList<>();
        for (String prefixPath : getValue(c.getRequestMapping().value(), c.getRequestMapping().path())) {
            List<String> pathList = new ArrayList<>();
            if (m.getRequestMapping() != null) {
                pathList = getValue(m.getRequestMapping().value(), m.getRequestMapping().path());
            } else if (m.getGetMapping() != null) {
                pathList = getValue(m.getGetMapping().value(), m.getGetMapping().path());
            } else if (m.getPostMapping() != null) {
                pathList = getValue(m.getPostMapping().value(), m.getPostMapping().path());
            } else if (m.getPutMapping() != null) {
                pathList = getValue(m.getPutMapping().value(), m.getPutMapping().path());
            } else if (m.getPatchMapping() != null) {
                pathList = getValue(m.getPatchMapping().value(), m.getPatchMapping().path());
            } else if (m.getDeleteMapping() != null) {
                pathList = getValue(m.getDeleteMapping().value(), m.getDeleteMapping().path());
            }

            for (String path : pathList) {
                urls.add(prefixPath + path);
            }
        }

        return urls;
    }

    private List<ApiParameter> getHeaders(MethodMeta m) {

        List<ApiParameter> parameters = new ArrayList<>();

        if (m.getParameterMetas().length == 0) {
            return parameters;
        }

        return Arrays.stream(m.getParameterMetas())
                .filter(p -> p.getRequestHeader() != null)
                .map(p -> ApiParameter.builder()
                        .type("String")
                        .name(getNotEmptyValue(p.getRequestHeader().value(), p.getRequestHeader().name(), p.getName()))
                        .description(p.getStarParameter().description())
                        .example(p.getStarParameter().example())
                        .notes(p.getStarParameter().notes())
                        .required(p.getRequestHeader().required())
                        .build())
                .collect(Collectors.toList());
    }

    private List<ApiParameter> getParams(MethodMeta m) {

        List<ApiParameter> parameters = new ArrayList<>();

        if (m.getParameterMetas().length == 0) {
            return parameters;
        }

        return Arrays.stream(m.getParameterMetas())
                .filter(p -> p.getRequestParam() != null)
                .map(p -> ApiParameter.builder()
                        .type((p.getParameterType().isInstance(Number.class)) ? "Number" : "String")
                        .name(getNotEmptyValue(p.getRequestParam().value(), p.getRequestParam().name(), p.getName()))
                        .description(p.getStarParameter().description())
                        .example(p.getStarParameter().example())
                        .notes(p.getStarParameter().notes())
                        .required(p.getRequestParam().required())
                        .build())
                .collect(Collectors.toList());
    }

    private ApiBody getBody(MethodMeta m) {

        // TODO
        return null;
    }

    private List<String> getValue(String[] value, String[] spec) {

        if (value.length == 0 && spec.length == 0) {
            return Collections.singletonList("");
        } else if (value.length > 0) {
            return Arrays.asList(value);
        } else {
            return Arrays.asList(spec);
        }
    }

    private String getNotEmptyValue(String... values) {

        for (String value : values) {
            if (!ObjectUtils.isEmpty(value)) {
                return value;
            }
        }

        return null;
    }

    public void print(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter();
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Value.construct(JsonInclude.Include.ALWAYS, JsonInclude.Include.NON_NULL));
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            log.debug("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
        } catch (JsonProcessingException ignored) {
        }
    }
}
