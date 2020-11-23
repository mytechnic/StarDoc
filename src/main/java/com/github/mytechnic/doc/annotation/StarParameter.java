package com.github.mytechnic.doc.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StarParameter {

    @AliasFor("description")
    String value() default "";

    @AliasFor("value")
    String description() default "";

    String example() default "";

    String notes() default "";

    boolean required() default false;
}
