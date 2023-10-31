package com.banknext.customer.mgt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banknext.customer.mgt.repo.model.Customer;

@Service
public interface CustomerService {
	
	
	public List<Customer> findAll();
	public char findAllChar(String test2, int num2, char a);
	
	public  Character findAllCharacter(String test2, int num2, Character a);
	
	public  boolean findAllBooleanPrim(String test2, int num2, boolean a);
	
	public  Boolean findAllBooleanWrpr(String test2, int num2, Boolean a);
	
	public  void createCustomerWithVoidReturnTypeAndExtrMtdWithVoidReturnType(Customer cust);
	
	public  void createCustomerWithVoidReturnTypeAndExtrMtdWithNonVoidReturnType(Customer cust);
	
	public  Customer createCustomerWithNonVoidReturnTypeAndExtrMtdWithNonVoidReturnType(Customer cust);

	public  void createCustomerWithVoidReturnTypeAndExtrMtdWithNonVoidAndVoidReturnType(Customer cust);
	
	public  Customer createCustomerWithNonVoidReturnTypeAndExtrMtdWithNonVoidAndVoidReturnTypeA(Customer cust);

	public  Customer createCustomerWithNonVoidReturnTypeAndExtrMtdWithNonVoidAndVoidReturnTypeB(Customer cust);
	
	public  void Functional1(List<Integer> numbers) ;
	
	public  List<Customer> getAllCustomersFunctional2(String title) ;

	public  Customer getCustomerById(int id) ;
	public  Customer getCustomerById(Customer customer) ;
	public  Optional<Customer> getOptCustomerById(int id) ;	
	
	public  Customer createCustomer(Customer customer) ;
	
	public  Customer updateCustomer(int id, Customer customer) ;
	public  Optional<Customer> updateCustomerOpt(Customer existingCustomer);
	public  Customer updateCustomer(Customer existingCustomer);
	public  void deleteCustomer(int customerId);

}
