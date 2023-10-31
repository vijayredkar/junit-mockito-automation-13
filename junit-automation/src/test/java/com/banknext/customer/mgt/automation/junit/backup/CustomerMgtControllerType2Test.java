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


import org.springframework.http.HttpStatus;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.banknext.customer.mgt.repo.model.Customer;
import com.banknext.customer.mgt.repo.model.*;
import com.banknext.customer.mgt.service.AccountService;
import com.banknext.customer.mgt.service.*;
import com.banknext.customer.mgt.service.CustomerService;
import com.banknext.customer.mgt.service.*;
import com.banknext.customer.mgt.controller.CustomerMgtControllerType2;







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



public class CustomerMgtControllerType2Test 
{

	
 @MockBean 
CustomerServiceImpl  customerService ;
 @MockBean 
AccountServiceImpl  accountService ;
	/*
	@MockBean
	CustomerRepository custRepo;
	*/
	
	@Autowired 
CustomerMgtControllerType2 customermgtcontrollertype2 ;	
	/*
	@Autowired
	CustomerServiceImpl custSvc;
	*/

    @Autowired
    private MockMvc mockMvc;
	
    @Autowired
    private ObjectMapper objectMapper;

    DtoFactoryCustomerMgtControllerType2 dtoFactory ;	
    @BeforeEach
    public void init()
    {
    	dtoFactory = new DtoFactoryCustomerMgtControllerType2();
    }	
	        
    //@Test 
public void deleteCustomer_int_Test_2() throws Exception
{
doNothing().when(customerService).deleteCustomer(isA(int.class));


ResultActions result = mockMvc.perform(delete("/api/customers/type2//4"));

verify(customerService, times(1)).deleteCustomer(ArgumentMatchers.any(int.class));
//--------------------------- MockMvc tests
    //assert-verify results
    result.andDo(print())                 
    		.andExpect(status().is2xxSuccessful())
             /*
             .andExpect(status().is1xxInformational())
             .andExpect(status().is2xxSuccessful())
             .andExpect(status().is3xxRedirection())
             .andExpect(status().is4xxClientError())
             .andExpect(status().is5xxServerError())
             .andExpect(status().isAccepted())
             .andExpect(status().isBadGateway())
             .andExpect(status().isCreated())
             .andExpect(status().isForbidden())
             .andExpect(status().isMethodNotAllowed())
             .andExpect(status().isBadRequest())
             .andExpect(status().isNoContent())
             .andExpect(status().isNotFound())
             .andExpect(status().isOk())
             .andExpect(status().isRequestTimeout())
             .andExpect(status().is
             .andExpect(status().isRequestTimeout())
             .andExpect(status().isTooManyRequests())
             .andExpect(status().isUnauthorized()))
             */                 
            ;

//--only for Controller tests

/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






//@Test 
public void updateCustomer_int_com_banknext_customer_mgt_repo_model_Customer_Test_3() throws Exception
{
when(customerService.getOptCustomerById(Mockito.anyInt())).thenReturn(dtoFactory.getOptCustomer());
when(customerService.updateCustomer(Mockito.any())).thenReturn(dtoFactory.getCustomer());


ResultActions result = mockMvc.perform(put("/api/customers/type2//3")
.contentType(MediaType.APPLICATION_JSON)
.content(objectMapper.writeValueAsString(dtoFactory.getCustomer())));


verify(customerService, times(1)).getOptCustomerById(ArgumentMatchers.any(int.class));verify(customerService, times(1)).updateCustomer(ArgumentMatchers.any());
//--------------------------- MockMvc tests
    //assert-verify results
    result.andDo(print())                 
    		.andExpect(status().is2xxSuccessful())
             /*
             .andExpect(status().is1xxInformational())
             .andExpect(status().is2xxSuccessful())
             .andExpect(status().is3xxRedirection())
             .andExpect(status().is4xxClientError())
             .andExpect(status().is5xxServerError())
             .andExpect(status().isAccepted())
             .andExpect(status().isBadGateway())
             .andExpect(status().isCreated())
             .andExpect(status().isForbidden())
             .andExpect(status().isMethodNotAllowed())
             .andExpect(status().isBadRequest())
             .andExpect(status().isNoContent())
             .andExpect(status().isNotFound())
             .andExpect(status().isOk())
             .andExpect(status().isRequestTimeout())
             .andExpect(status().is
             .andExpect(status().isRequestTimeout())
             .andExpect(status().isTooManyRequests())
             .andExpect(status().isUnauthorized()))
             */                 
            ;

//--only for Controller tests

/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






//@Test 
public void createCustomerWithResponseEntityNoContent_com_banknext_customer_mgt_repo_model_Customer_int_int_Test_4() throws Exception
{
when(customerService.createCustomer(Mockito.any())).thenReturn(dtoFactory.getCustomer());


ResultActions result = mockMvc.perform(post("/api/customers/type2//1/loan/2")
.contentType(MediaType.APPLICATION_JSON)
.content(objectMapper.writeValueAsString(dtoFactory.getCustomer())));


verify(customerService, times(1)).createCustomer(ArgumentMatchers.any(Customer.class));
//--------------------------- MockMvc tests
    //assert-verify results
    result.andDo(print())                 
    		.andExpect(status().is2xxSuccessful())
             /*
             .andExpect(status().is1xxInformational())
             .andExpect(status().is2xxSuccessful())
             .andExpect(status().is3xxRedirection())
             .andExpect(status().is4xxClientError())
             .andExpect(status().is5xxServerError())
             .andExpect(status().isAccepted())
             .andExpect(status().isBadGateway())
             .andExpect(status().isCreated())
             .andExpect(status().isForbidden())
             .andExpect(status().isMethodNotAllowed())
             .andExpect(status().isBadRequest())
             .andExpect(status().isNoContent())
             .andExpect(status().isNotFound())
             .andExpect(status().isOk())
             .andExpect(status().isRequestTimeout())
             .andExpect(status().is
             .andExpect(status().isRequestTimeout())
             .andExpect(status().isTooManyRequests())
             .andExpect(status().isUnauthorized()))
             */                 
            ;

//--only for Controller tests

/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






//@Test 
public void getCustomerById_int_Test_5() throws Exception
{
when(customerService.getOptCustomerById(Mockito.anyInt())).thenReturn(dtoFactory.getOptCustomer());


ResultActions result = mockMvc.perform(get("/api/customers/type2//5"));

verify(customerService, times(1)).getOptCustomerById(ArgumentMatchers.any(int.class));
//--------------------------- MockMvc tests
    //assert-verify results
    result.andDo(print())                 
    		.andExpect(status().is2xxSuccessful())
             /*
             .andExpect(status().is1xxInformational())
             .andExpect(status().is2xxSuccessful())
             .andExpect(status().is3xxRedirection())
             .andExpect(status().is4xxClientError())
             .andExpect(status().is5xxServerError())
             .andExpect(status().isAccepted())
             .andExpect(status().isBadGateway())
             .andExpect(status().isCreated())
             .andExpect(status().isForbidden())
             .andExpect(status().isMethodNotAllowed())
             .andExpect(status().isBadRequest())
             .andExpect(status().isNoContent())
             .andExpect(status().isNotFound())
             .andExpect(status().isOk())
             .andExpect(status().isRequestTimeout())
             .andExpect(status().is
             .andExpect(status().isRequestTimeout())
             .andExpect(status().isTooManyRequests())
             .andExpect(status().isUnauthorized()))
             */                 
             /*
             .andExpect(content().string(containsString(""title":"testTitle"")))
             */
             .andExpect(jsonPath("$.customerNum", is(dtoFactory.getCustomer().getCustomerNum())))            ;

//--only for Controller tests

/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends







}