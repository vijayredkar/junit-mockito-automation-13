package com.banknext.customer.mgt.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banknext.customer.mgt.repo.AccountRepository;
import com.banknext.customer.mgt.repo.CustomerRepository;
import com.banknext.customer.mgt.repo.model.Account;


@Service
public class AccountServiceImpl implements AccountService
{
	
	@Autowired
	AccountRepository accountRepo;

	public Account getAccountById(int id) {
		Optional<Account> accountData = accountRepo.findById(id);

		if (accountData.isPresent()) {
			return new Account();
		} else {
			return new Account();
		}
	}
	
	@Override
	public Optional<Account> getOptAccountById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty(); 
	}
		
	@Override
	public Account createAccount(Account account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
