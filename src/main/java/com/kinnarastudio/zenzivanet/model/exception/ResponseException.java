package com.kinnarastudio.zenzivanet.model.exception;

public class ResponseException extends Exception {
    public ResponseException(String message) {
        super(message);
    }
    public ResponseException(Throwable cause) {
        super(cause);
    }
}
