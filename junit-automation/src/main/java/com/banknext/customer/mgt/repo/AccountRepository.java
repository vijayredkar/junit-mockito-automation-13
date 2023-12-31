package com.banknext.customer.mgt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banknext.customer.mgt.repo.model.Account;
import com.banknext.customer.mgt.repo.model.Customer;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}