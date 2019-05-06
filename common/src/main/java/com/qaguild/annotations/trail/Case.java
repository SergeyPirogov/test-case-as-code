package com.qaguild.annotations.trail;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Case {
    String title();

    int id() default 0;

    int type() default 0;

    String ac() default "";
}
