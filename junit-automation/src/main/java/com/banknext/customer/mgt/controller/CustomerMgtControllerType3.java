    package com.banknext.customer.mgt.controller;

    import java.util.List;

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

    import com.banknext.customer.mgt.repo.model.Account;
    import com.banknext.customer.mgt.repo.model.Customer;
    import com.banknext.customer.mgt.service.AccountService;
    import com.banknext.customer.mgt.service.CustomerService;

    @RestController
    @RequestMapping("/api/customers/type2/")
    public class CustomerMgtControllerType3 {

        private CustomerService customerService;
        private AccountService accountService;

        public CustomerMgtControllerType3(CustomerService customerService, AccountService accountService) {
            this.customerService = customerService;
            this.accountService = accountService;
        }
        
        @GetMapping("/all/accounts")
        public ResponseEntity<List<Account>> getAllAccountsWithResponseEntity(){
            List<Account> accounts = accountService.findAll();
            return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
        }
        
    }