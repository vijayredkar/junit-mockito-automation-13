package com.banknext.customer.mgt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banknext.customer.mgt.repo.model.Customer;
import com.banknext.customer.mgt.service.AccountService;
import com.banknext.customer.mgt.service.CustomerService;

@RestController
@RequestMapping("/api/customers/type1/")
public class CustomerMgtControllerType1 {

    private CustomerService customerService;
    private AccountService accountService;

    public CustomerMgtControllerType1(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }  
  
    @GetMapping("/{id}/loan/{loanId}")
    public ResponseEntity<Customer> getCustomerByIdWithContent(@PathVariable("id") int id){
    	Customer customer = customerService.getOptCustomerById(id).get();
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }  
}