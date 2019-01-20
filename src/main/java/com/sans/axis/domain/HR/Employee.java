package com.sans.axis.domain.HR;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee")
public class Employee {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name= "employee_id")
	private long employeeID;
	
	@Column(name= "title")
	private String title;
	
	@Column(name="company_name")
	private String companyName;
	
	
	@Column(name= "start_date")
	private Date date;
	
	@Column(name= "work_phone")
	private String workPhone;
	
	@Column(name= "mobile_phone")
	private String mobilePhone;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(long employeeID) {
		this.employeeID = employeeID;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeID=" + employeeID + ", title=" + title + ", companyName=" + companyName
				+ ", date=" + date + ", workPhone=" + workPhone + ", mobilePhone=" + mobilePhone + "]";
	}

}
