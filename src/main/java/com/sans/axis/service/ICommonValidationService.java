package com.sans.axis.service;

import com.sans.axis.domain.Loan;

/***
 * 
 * @author erics
 * Common validation service class to be used
 * by controllers
 *
 */
public interface ICommonValidationService {
	
	public boolean loanRequestValidation(Loan loan);
	
	public boolean loanLenderValidation();
	
	

}
