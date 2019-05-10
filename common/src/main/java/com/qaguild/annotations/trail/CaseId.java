package com.qaguild.annotations.trail;

import io.qameta.allure.LabelAnnotation;
import io.qameta.allure.LabelAnnotations;
import io.qameta.allure.util.ResultsUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@LabelAnnotations({
        @LabelAnnotation(name = ResultsUtils.TAG_LABEL_NAME),
        @LabelAnnotation(name = ResultsUtils.TMS_LINK_TYPE)
})
public @interface CaseId {
    int value();
}
