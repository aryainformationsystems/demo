package com.example.demo.dto;

public class GenericResponse {
	private Object data;
	private String message;
	private Exception error;
	private long count;
	
	public GenericResponse(Object data, String message, Exception err, long count) {
		this.data = data;
		this.message = message;
		this.error = err;
		this.count = count;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getError() {
		return error;
	}

	public void setError(Exception error) {
		this.error = error;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
