package com.banknext.customer.mgt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banknext.customer.mgt.repo.model.Account;
import com.banknext.customer.mgt.repo.model.Customer;

@Service
public interface AccountService {
	
	
	public List<Account> findAll();
	public Account createAccount(Account account) ;
	public Optional<Account> getOptAccountById(int id) ;
}
