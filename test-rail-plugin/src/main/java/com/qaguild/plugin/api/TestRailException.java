package com.qaguild.plugin.api;


/**
 * Created by alpa on 2018-12-06
 */
public class TestRailException extends RuntimeException {

    public TestRailException(String message) {
        super(message);
    }

    public TestRailException(String message, Throwable e) {
        super(message, e);
    }


}
