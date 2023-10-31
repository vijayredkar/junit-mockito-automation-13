package com.banknext.customer.mgt.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banknext.customer.mgt.repo.AccountRepository;
import com.banknext.customer.mgt.repo.CustomerRepository;
import com.banknext.customer.mgt.repo.LoanRepository;
import com.banknext.customer.mgt.repo.model.Customer;

@Service
public class CustomerServiceImplType3 //implements CustomerService
{
	@Autowired
	CustomerRepository custRepo;
	
	private AccountRepository acctRepo;
	private LoanRepository loanRepo;
	//private String test1;
	private Customer test2;
	
	public CustomerServiceImplType3(AccountRepository acctRepo, LoanRepository loanRepo)
	{ 
		this.acctRepo = acctRepo;  
		this.loanRepo = loanRepo;
	}

	public void Functional1(List<Integer> numbers) {

		numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);
		numbers.stream() // Convert to Stream
				.filter(number -> number % 2 == 0) // Lamdba Expression
				//.forEach(System.out::println);// Method Reference
				.forEach(a -> custRepo.save(new Customer()));// Method Reference
	}
	
	public List<Customer> getAllCustomersFunctional2(String title) {
		try {
			List<Customer> customers = new ArrayList<Customer>();

			if (title == null)
				custRepo.findAll().forEach(customers::add);
			else
			    custRepo.findByFirstName(title).forEach(customers::add);

			if (customers.isEmpty()) {
				return null;
			}

			return customers;
		} catch (Exception e) {
			throw e;
		}
	}



}
