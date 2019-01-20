package com.sans.axis.service.impl;

import com.sans.axis.domain.Loan;
import com.sans.axis.service.ICommonValidationService;
import java.lang.reflect.Field;

import org.springframework.stereotype.Service;

@Service
public class CommonValidationService implements ICommonValidationService {

	@Override
	public boolean loanRequestValidation(Loan loan) {
		
		//validate input
		this.inputsValidation(loan);
		
		//user history good standing?
		
		//user have outstanding balance?
		
		return false;
	}

	@Override
	public boolean loanLenderValidation() {
		
		return false;
	}
	
	public <E> boolean inputsValidation(E objToValidate) {
		Field[] fields =  objToValidate.getClass().getFields();
		
		System.out.println("Fields size " + fields.length);
		
		for(int i = 0; i < fields.length; i++) {
			System.out.println("field: " + i + " " + fields[i]);
		}
		
		return false;
	}

}
