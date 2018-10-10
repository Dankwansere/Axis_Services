package com.sans.axis.domain;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

public class AxisResponse {
	
	private String status;
	private Object data;
	private String exception;
	
	
	
	public AxisResponse() {
		super();
	}

	public AxisResponse(String status, Object data, String exception) {
		super();
		this.status = status;
		this.data = data;
		this.exception = exception;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	
	public static AxisResponse setAxisResponse(String status) {
		return setAxisResponse(status, null);
	}
	
	public static AxisResponse setAxisResponse(String status, Object data) {
		return setAxisResponse(status, data, "");
	}
	
	public static AxisResponse setErrorAxisResponse(String status, String exception) {
		return setAxisResponse(status, null, exception);
	}
	
	public static AxisResponse setAxisResponse(String status, Object data, String exception) {
		return new AxisResponse(status, data, exception);
	}

}
