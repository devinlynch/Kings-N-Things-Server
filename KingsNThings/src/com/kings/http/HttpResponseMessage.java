package com.kings.http;

import java.io.Serializable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kings.util.Utils;

public class HttpResponseMessage implements Serializable {
	private static final long serialVersionUID = -1112738164581552274L;
	
	private ResponseStatus responseStatus = ResponseStatus.OK_REQUEST;
	private HttpResponseData data;
	private String name;
	private String type;
	private HttpResponseError error;
	
	public HttpResponseMessage() {
		data = new HttpResponseData();
	}
	
	public HttpResponseMessage(HttpResponseError.ResponseError responseError) {
		setError(new HttpResponseError(responseError));
	}
	
	public enum ResponseStatus {
		OK_REQUEST,
		INVALID_REQUEST
	}
	
	public enum ResponseType{
		TEST
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HttpResponseError getError() {
		return error;
	}

	public void setError(HttpResponseError error) {
		this.error = error;
	}

	public HttpResponseData getData() {
		return data;
	}

	public void setData(HttpResponseData data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String toJson() {
		return Utils.toJson(this);
	}
}
