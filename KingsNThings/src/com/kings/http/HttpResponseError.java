package com.kings.http;

public class HttpResponseError {
	private ResponseError responseError;
	
	public HttpResponseError() {
		
	}
	
	public HttpResponseError(ResponseError responseError) {
		setResponseError(responseError);
	}
	
	public enum ResponseError {
		BAD_USERNAME_AND_PASSWORD,
		ALREADY_LOGGED_IN,
		ALREADY_REGISTERED,
		GENERIC_ERROR,
		NOT_LOGGED_IN
	}

	public ResponseError getResponseError() {
		return responseError;
	}

	public void setResponseError(ResponseError responseError) {
		this.responseError = responseError;
	}
	
	
}
