package com.cg.tms.exception;

public class TaskNotFoundException  extends RuntimeException {
	    private String code; 
	    private String message; 
	    
	    public TaskNotFoundException(String code, String message) {
	       
	        this.code = code;
	        this.message = message;
	    }

	    public String getCode() {
	        return code;
	    }

	    @Override
	    public String getMessage() {
	        return message;
	    }
}
