package com.qaguild.annotations.trail;

import io.qameta.allure.LinkAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@LinkAnnotation(type = "tms")
public @interface CaseId {
    int value();
}
