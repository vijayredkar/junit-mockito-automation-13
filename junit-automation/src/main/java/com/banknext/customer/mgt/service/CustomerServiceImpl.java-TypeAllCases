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
public class CustomerServiceImpl implements CustomerService
{
	@Autowired
	CustomerRepository custRepo;
	
	private AccountRepository acctRepo;
	private LoanRepository loanRepo;
	//private String test1;
	private Customer test2;
	
	public CustomerServiceImpl(AccountRepository acctRepo, LoanRepository loanRepo)
	{ 
		this.acctRepo = acctRepo;  
		this.loanRepo = loanRepo;
	}

	public List<Customer> findAll()
	{	
		System.out.println("stmt 1");
		System.out.println("stmt 2");
		System.out.println("stmt 3");
		System.out.println("stmt 4");
		custRepo.deleteById(2);
		return custRepo.findAll();
	}

	public char findAllChar(String test2, int num2, char a)
	{	
		System.out.println("stmt 9");
		System.out.println("stmt 10");
		System.out.println("stmt 11");
		System.out.println("stmt 12");
		return 'k';
	}
	
	public Character findAllCharacter(String test2, int num2, Character a)
	{	
		System.out.println("stmt 9");
		System.out.println("stmt 10");
		System.out.println("stmt 11");
		System.out.println("stmt 12");
		return 'k';
	}
	
	public boolean findAllBooleanPrim(String test2, int num2, boolean a)
	{	
		System.out.println("stmt 9");
		System.out.println("stmt 10");
		System.out.println("stmt 11");
		System.out.println("stmt 12");
		return false;
	}
	
	public Boolean findAllBooleanWrpr(String test2, int num2, Boolean a)
	{	
		System.out.println("stmt 9");
		System.out.println("stmt 10");
		System.out.println("stmt 11");
		System.out.println("stmt 12");
		return false;
	}
	
	public void createCustomerWithVoidReturnTypeAndExtrMtdWithVoidReturnType(Customer cust)
	{	
		System.out.println("stmt 5");
		System.out.println("stmt 6");
		System.out.println("stmt 7");
		System.out.println("stmt 8");
		custRepo.delete(cust);
	}	
	
	public void createCustomerWithVoidReturnTypeAndExtrMtdWithNonVoidReturnType(Customer cust)
	{	
		System.out.println("stmt 5");
		System.out.println("stmt 6");
		System.out.println("stmt 7");
		System.out.println("stmt 8");
		custRepo.save(cust);
	}
	
	public Customer createCustomerWithNonVoidReturnTypeAndExtrMtdWithNonVoidReturnType(Customer cust)
	{	
		System.out.println("stmt 5");
		System.out.println("stmt 6");
		System.out.println("stmt 7");
		System.out.println("stmt 8");
		return custRepo.save(cust);
	}

	public void createCustomerWithVoidReturnTypeAndExtrMtdWithNonVoidAndVoidReturnType(Customer cust)
	{	
		System.out.println("stmt 5");
		System.out.println("stmt 6");
		System.out.println("stmt 7");
		System.out.println("stmt 8");
		custRepo.save(cust);
		custRepo.delete(cust);
	}

	public Customer createCustomerWithNonVoidReturnTypeAndExtrMtdWithNonVoidAndVoidReturnTypeA(Customer cust)
	{	
		System.out.println("stmt 5");
		System.out.println("stmt 6");
		System.out.println("stmt 7");
		System.out.println("stmt 8");
		Customer customer = custRepo.save(cust);
		custRepo.delete(cust);
		
		return customer;
	}

	public Customer createCustomerWithNonVoidReturnTypeAndExtrMtdWithNonVoidAndVoidReturnTypeB(Customer cust)
	{	
		System.out.println("stmt 5");
		System.out.println("stmt 6");
		System.out.println("stmt 7");
		System.out.println("stmt 8");	
		custRepo.delete(cust);
		
		return custRepo.save(cust);
	}	

	public Customer getCustomerById(int id) {
		Optional<Customer> customerData = custRepo.findById(id);

		if (customerData.isPresent()) {
			return new Customer();
		} else {
			return new Customer();
		}
	}
	
	public Customer getCustomerById(Customer customer) {
		Optional<Customer> customerData = custRepo.findById(customer.getCustomerNum());

		if (customerData.isPresent()) {
			return new Customer();
		} else {
			return new Customer();
		}
	}

	@Override
	public Optional<Customer> getOptCustomerById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Customer createCustomer(Customer customer) {
		return new Customer();
	}

	@Override
	public Customer updateCustomer(int id, Customer customer) {
		return new Customer();
	}

	@Override
	public Optional<Customer> updateCustomerOpt(Customer existingCustomer) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Customer updateCustomer(Customer existingCustomer) {
		return new Customer();
	}

	@Override
	public void deleteCustomer(int customerId) {
		// TODO Auto-generated method stub
		
	}

	
	//----------------------
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
