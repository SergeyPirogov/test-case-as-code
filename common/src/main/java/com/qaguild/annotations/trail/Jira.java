package com.qaguild.annotations.trail;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(Jiras.class)
public @interface Jira {
    String id();
    String title() default "";
    Case[] manual() default {};
}
