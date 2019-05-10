package com.qaguild.annotations.trail;

import io.qameta.allure.LabelAnnotation;
import io.qameta.allure.util.ResultsUtils;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(Jiras.class)
@LabelAnnotation(name = ResultsUtils.STORY_LABEL_NAME)
public @interface Jira {
    String value();
    String title() default "";
    Case[] manual() default {};
}
