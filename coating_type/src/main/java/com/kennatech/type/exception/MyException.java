package com.kennatech.type.exception;

public class MyException extends Exception {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
            */
    public MyException() {
    }

    public MyException(String msg) {
        super(msg);
    }

}
