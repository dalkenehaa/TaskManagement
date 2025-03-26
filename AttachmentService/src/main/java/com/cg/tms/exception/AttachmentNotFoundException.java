package com.cg.tms.exception;

public class AttachmentNotFoundException extends RuntimeException {

    private String message;
    private String details;

    public AttachmentNotFoundException(String message, String details) {
        super(message);
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
