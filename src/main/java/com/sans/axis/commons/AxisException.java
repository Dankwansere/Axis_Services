package com.sans.axis.commons;

public class AxisException extends Exception {
	
	public AxisException(String customExection) {
		super(customExection);
	}
	
	public String getException() {
		return super.getMessage();
	}
	
}
