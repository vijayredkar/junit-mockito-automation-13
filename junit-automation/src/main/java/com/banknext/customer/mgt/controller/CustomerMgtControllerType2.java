package com.banknext.customer.mgt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banknext.customer.mgt.repo.model.Customer;
import com.banknext.customer.mgt.service.AccountService;
import com.banknext.customer.mgt.service.CustomerService;

@RestController
@RequestMapping("/api/customers/type2/")
public class CustomerMgtControllerType2 {

    private CustomerService customerService;
    private AccountService accountService;

    public CustomerMgtControllerType2(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") int customerId){
        return customerService.getOptCustomerById(customerId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }   


    @PostMapping("/{id}/loan/{loanId}")
    public ResponseEntity<Object> createCustomerWithResponseEntityNoContent(@RequestBody Customer 
    		customer, 
    		@PathVariable("id") int customerId,@PathVariable("loanId") int loanId)
     {        
        		 customerService.createCustomer(customer);
        		 //return ResponseEntity.status(HttpStatus.CREATED).build();
        		 return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") int customerId, @RequestBody Customer customer){
        return customerService.getOptCustomerById(customerId)
                .map(savedCustomer -> {

                    savedCustomer.setFirstName(customer.getFirstName());
                    savedCustomer.setLastName(customer.getLastName());
                    savedCustomer.setEmail(customer.getEmail());

                    Customer updatedCustomer = customerService.updateCustomer(savedCustomer);
                    return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }    
    
    @DeleteMapping("/{id1}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id1") int customerId){

        customerService.deleteCustomer(customerId);

        return new ResponseEntity<String>("Customer deleted successfully!.", HttpStatus.OK);

    }
}