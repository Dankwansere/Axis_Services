package com.sans.axis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sans.axis.commons.AxisException;
import com.sans.axis.domain.AxisResponse;
import com.sans.axis.domain.Loan;
import com.sans.axis.service.ICommonValidationService;

@RestController
@RequestMapping(value = "api/v1/transac/")
@CrossOrigin
public class TransactionController {
	
	@Autowired
	private ICommonValidationService commonValidation;
	
	private AxisResponse axisResponse;
	private AxisException axisException;
	
	@PostMapping(value="requestFunds")
	public ResponseEntity<AxisResponse> requestFunds(@RequestBody Loan loan) {
		this.axisResponse = new AxisResponse();
		
		//perform validations
		this.commonValidation.loanRequestValidation(loan);
		
		System.out.println("Validations complete");
		
		
		return new ResponseEntity<AxisResponse>(this.axisResponse, HttpStatus.OK) ;
	}

}
