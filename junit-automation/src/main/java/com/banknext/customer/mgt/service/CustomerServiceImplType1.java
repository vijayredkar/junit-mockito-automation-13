package com.banknext.customer.mgt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banknext.customer.mgt.repo.AccountRepository;
import com.banknext.customer.mgt.repo.CustomerRepository;
import com.banknext.customer.mgt.repo.LoanRepository;
import com.banknext.customer.mgt.repo.model.Customer;

@Service
//public abstract class CustomerServiceImplType1 implements CustomerService
public class CustomerServiceImplType1
{
	@Autowired
	CustomerRepository custRepo;
	
	private AccountRepository acctRepo;
	private LoanRepository loanRepo;
	//private String test1;
	private Customer test2;
	
	public CustomerServiceImplType1(AccountRepository acctRepo, LoanRepository loanRepo)
	{ 
		this.acctRepo = acctRepo;  
		this.loanRepo = loanRepo;
	}
	
	public Customer getCustomerById(int id) {
		Optional<Customer> customerData = custRepo.findById(id);

		if (customerData.isPresent()) {
			return customerData.get();
		} else {
			return new Customer();
		}
	}
}
