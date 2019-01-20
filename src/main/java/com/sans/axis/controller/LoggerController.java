package com.sans.axis.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/log/")
@CrossOrigin
public class LoggerController {
	
	@RequestMapping(value = "debug", method = RequestMethod.POST)
	public void debug(@RequestBody String msg) {
		System.out.println("debug called: " + msg);
	}
	
	@RequestMapping(value = "warning", method = RequestMethod.POST)
	public void warning(@RequestBody String msg) {
		System.out.println("warning called: " + msg);
	}
	
	@RequestMapping(value = "error", method = RequestMethod.POST)
	public void error(@RequestBody String msg) {
		System.out.println("error called: " + msg);
	}

}
