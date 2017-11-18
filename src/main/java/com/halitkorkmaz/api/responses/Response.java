package com.halitkorkmaz.api.responses;

import java.util.List;

import com.halitkorkmaz.api.util.validation.ValidationData;

public class Response<T> {
	
	private T data;
	private List<ValidationData> errors;
	
	public Response(T data) {
		this.data = data;
	}
	
	public Response(List<ValidationData> errors) {
		this.errors = errors;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<ValidationData> getErrors() {
		return errors;
	}

	public void setErrors(List<ValidationData> errors) {
		this.errors = errors;
	}

}
