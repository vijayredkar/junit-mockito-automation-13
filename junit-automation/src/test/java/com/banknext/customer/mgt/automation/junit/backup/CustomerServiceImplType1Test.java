//package com.banknext.customer.mgt.service;
package com.banknext.customer.mgt.automation.junit.backup;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.isA;


import java.util.List;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


import java.util.Optional;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.*;
import com.banknext.customer.mgt.repo.AccountRepository;
import com.banknext.customer.mgt.repo.*;
import com.banknext.customer.mgt.repo.CustomerRepository;
import com.banknext.customer.mgt.repo.*;
import com.banknext.customer.mgt.repo.LoanRepository;
import com.banknext.customer.mgt.repo.*;
import com.banknext.customer.mgt.repo.model.Customer;
import com.banknext.customer.mgt.repo.model.*;
import com.banknext.customer.mgt.service.CustomerServiceImplType1;







//*******************  Junit 4.x *******************
/*
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
*/
//*******************  Junit 4.x *******************//



//*******************  Junit 5.x *******************
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
//*******************  Junit 5.x *******************
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.hamcrest.CoreMatchers.is;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



//*******************  Junit 4.x *******************
//@RunWith(SpringRunner.class)
//*******************  Junit 4.x *******************



//*******************  Junit 5.x *******************

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc   //required only for Controller Junits
// @TestPropertySource("classpath:application-test.properties")
//*******************  Junit 5.x *******************



public class CustomerServiceImplType1Test 
{

	
 @MockBean 
CustomerRepository  custRepo ;
 @MockBean 
AccountRepository  acctRepo ;
 @MockBean 
LoanRepository  loanRepo ;
 @MockBean 
Customer  test2 ;
	/*
	@MockBean
	CustomerRepository custRepo;
	*/
	
	@Autowired 
CustomerServiceImplType1 customerserviceimpltype1 ;	
	/*
	@Autowired
	CustomerServiceImpl custSvc;
	*/

    @Autowired
    private MockMvc mockMvc;
	
    @Autowired
    private ObjectMapper objectMapper;

    DtoFactoryCustomerServiceImplType1 dtoFactory ;	
    @BeforeEach
    public void init()
    {
    	dtoFactory = new DtoFactoryCustomerServiceImplType1();
    }	
	        
    ////@Test 
public void getCustomerById_int_Test_2() throws Exception
{
when(custRepo.findById(Mockito.any())).thenReturn(dtoFactory.getOptCustomer());


Customer result = customerserviceimpltype1.getCustomerById( 1 ) ;

verify(custRepo, times(1)).findById(ArgumentMatchers.any(int.class));assertThat(result).isInstanceOf(Customer.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends







}