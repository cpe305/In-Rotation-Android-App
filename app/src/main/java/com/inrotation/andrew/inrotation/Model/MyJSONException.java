package com.inrotation.andrew.inrotation.model;

/**
 * Created by andrewcofano on 11/18/16.
 */
public class MyJSONException extends Exception {


    public MyJSONException() {
        super();
    }

    public MyJSONException(String message) {
        super(message);
    }

    public MyJSONException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyJSONException(Throwable cause) {
        super(cause);
    }
}
