package com.sans.axis.service;
import java.util.List;

import com.sans.axis.domain.HR.UserTimesheet;
public interface ITimesheetService {
	
	public boolean createTimeWeek(UserTimesheet[] userTimesheet);
	
	public List<UserTimesheet> retrievePendingTimesheets(String userName);

}
