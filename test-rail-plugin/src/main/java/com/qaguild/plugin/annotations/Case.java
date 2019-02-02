package com.qaguild.plugin.annotations;


import com.qaguild.plugin.enums.State;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Case {
    String title();

    int automationType() default 0;

    State state() default State.READY_FOR_TESTING;

    int id() default 0;

    String ac();
}
