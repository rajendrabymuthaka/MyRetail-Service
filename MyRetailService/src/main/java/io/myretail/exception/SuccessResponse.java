package io.myretail.exception;

public class SuccessResponse {
	
	private int value;
	private String message;
	
	public SuccessResponse() {
	}
	
	public SuccessResponse(int value, String message) {
		this.value = value;
		this.message = message;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



}
