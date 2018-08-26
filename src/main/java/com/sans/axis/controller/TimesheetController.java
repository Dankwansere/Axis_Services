package com.sans.axis.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sans.axis.domain.UserTimesheet;
import com.sans.axis.service.ITimesheetService;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping(value = "/timesheet/")
@CrossOrigin
public class TimesheetController {
	
	@Autowired
	private ITimesheetService timesheetService;
	
	
	@RequestMapping(value = "submitTimesheetForm", method = RequestMethod.POST)
	public Map<String, Boolean> submitTimeSheet(@RequestBody UserTimesheet[] userTimesheet) {
		boolean success;
		try {
			success = this.timesheetService.createTimeWeek(userTimesheet);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return Collections.singletonMap("success", true);
	}
	
	@RequestMapping(value = "retrievePending/{userName}", method = RequestMethod.GET)
	public List<UserTimesheet> retrievePendingTimesheets(@PathVariable(value = "userName") String userName) {
		
		return this.timesheetService.retrievePendingTimesheets(userName);
		
		//return Collections.singletonMap("success", true); //testing purpose
	}

}
