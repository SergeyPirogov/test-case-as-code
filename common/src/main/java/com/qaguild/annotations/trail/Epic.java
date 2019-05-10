package com.qaguild.annotations.trail;

import io.qameta.allure.LabelAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@LabelAnnotation(name = "epic")
public @interface Epic {
    String value();
    Story[] stories() default {};
}
