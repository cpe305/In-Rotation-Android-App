package com.inrotation.andrew.inrotation.model;

/**
 * Created by andrewcofano on 11/18/16.
 */
public class MyJSONException extends Exception {

    /**
     * Constructor for the Personal JSONException
     */
    public MyJSONException() {
        super();
    }

    /**
     *
     * @param message Error message to report the exception
     */
    public MyJSONException(String message) {
        super(message);
    }

    /**
     *
     * @param message Error message to report the exception
     * @param cause Throwable exception passed to the exception
     */
    public MyJSONException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause Throwable exception passed to the exception
     */
    public MyJSONException(Throwable cause) {
        super(cause);
    }
}
