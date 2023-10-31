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


import java.util.List;
import java.util.*;
import java.util.Optional;
import java.util.*;
import java.util.ArrayList;
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
import com.banknext.customer.mgt.service.CustomerServiceImplType2;







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



public class CustomerServiceImplType2Test 
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
CustomerServiceImplType2 customerserviceimpltype2 ;	
	/*
	@Autowired
	CustomerServiceImpl custSvc;
	*/

    @Autowired
    private MockMvc mockMvc;
	
    @Autowired
    private ObjectMapper objectMapper;

    DtoFactoryCustomerServiceImplType2 dtoFactory ;	
    @BeforeEach
    public void init()
    {
    	dtoFactory = new DtoFactoryCustomerServiceImplType2();
    }	
	        
    ////@Test 
public void updateCustomer_int_com_banknext_customer_mgt_repo_model_Customer_Test_2() throws Exception
{


Customer result = customerserviceimpltype2.updateCustomer( 1 ,new Customer() ) ;

assertThat(result).isInstanceOf(Customer.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void deleteCustomer_int_Test_3() throws Exception
{


customerserviceimpltype2.deleteCustomer( 1 ) ;


/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void getCustomerById_int_Test_4() throws Exception
{
when(custRepo.findById(Mockito.any())).thenReturn(dtoFactory.getOptCustomer());


Customer result = customerserviceimpltype2.getCustomerById( 1 ) ;

verify(custRepo, times(1)).findById(ArgumentMatchers.any(int.class));assertThat(result).isInstanceOf(Customer.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void findAllChar_java_lang_String_int_char_Test_5() throws Exception
{


char result = customerserviceimpltype2.findAllChar( new String() ,1 ,new Character((char) 1) ) ;

assertThat(result).isInstanceOf(Character.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void findAllBooleanWrpr_java_lang_String_int_java_lang_Boolean_Test_6() throws Exception
{


Boolean result = customerserviceimpltype2.findAllBooleanWrpr( new String() ,1 ,new Boolean(true) ) ;

assertThat(result).isInstanceOf(Boolean.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void createCustomer_com_banknext_customer_mgt_repo_model_Customer_Test_7() throws Exception
{


Customer result = customerserviceimpltype2.createCustomer( new Customer() ) ;

assertThat(result).isInstanceOf(Customer.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void createCustomerWithVoidReturnTypeAndExtrMtdWithVoidReturnType_com_banknext_customer_mgt_repo_model_Customer_Test_8() throws Exception
{
doNothing().when(custRepo).delete(isA(com.banknext.customer.mgt.repo.model.Customer.class));


customerserviceimpltype2.createCustomerWithVoidReturnTypeAndExtrMtdWithVoidReturnType( new Customer() ) ;

verify(custRepo, times(1)).delete(ArgumentMatchers.any(Customer.class));
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void findAll_Test_9() throws Exception
{
doNothing().when(custRepo).deleteById(isA(int.class));
when(custRepo.findAll()).thenReturn(dtoFactory.getList_WithStubbedValues());


List result = customerserviceimpltype2.findAll( ) ;

verify(custRepo, times(1)).deleteById(ArgumentMatchers.any());assertThat(result).isInstanceOf(List.class);verify(custRepo, times(1)).findAll();assertThat(result).isInstanceOf(List.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void createCustomerWithNonVoidReturnTypeAndExtrMtdWithNonVoidAndVoidReturnTypeB_com_banknext_customer_mgt_repo_model_Customer_Test_10() throws Exception
{
doNothing().when(custRepo).delete(isA(com.banknext.customer.mgt.repo.model.Customer.class));
when(custRepo.save(Mockito.any())).thenReturn(dtoFactory.getCustomer());


Customer result = customerserviceimpltype2.createCustomerWithNonVoidReturnTypeAndExtrMtdWithNonVoidAndVoidReturnTypeB( new Customer() ) ;

verify(custRepo, times(1)).delete(ArgumentMatchers.any(Customer.class));assertThat(result).isInstanceOf(Customer.class);verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));assertThat(result).isInstanceOf(Customer.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void findAllBooleanPrim_java_lang_String_int_boolean_Test_11() throws Exception
{


boolean result = customerserviceimpltype2.findAllBooleanPrim( new String() ,1 ,true ) ;

assertThat(result).isInstanceOf(Boolean.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void findAllCharacter_java_lang_String_int_java_lang_Character_Test_12() throws Exception
{


Character result = customerserviceimpltype2.findAllCharacter( new String() ,1 ,new Character((char) 1) ) ;

assertThat(result).isInstanceOf(Character.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void createCustomerWithVoidReturnTypeAndExtrMtdWithNonVoidReturnType_com_banknext_customer_mgt_repo_model_Customer_Test_13() throws Exception
{
when(custRepo.save(Mockito.any())).thenReturn(dtoFactory.getCustomer());


customerserviceimpltype2.createCustomerWithVoidReturnTypeAndExtrMtdWithNonVoidReturnType( new Customer() ) ;

verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void createCustomerWithNonVoidReturnTypeAndExtrMtdWithNonVoidReturnType_com_banknext_customer_mgt_repo_model_Customer_Test_14() throws Exception
{
when(custRepo.save(Mockito.any())).thenReturn(dtoFactory.getCustomer());


Customer result = customerserviceimpltype2.createCustomerWithNonVoidReturnTypeAndExtrMtdWithNonVoidReturnType( new Customer() ) ;

verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));assertThat(result).isInstanceOf(Customer.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void updateCustomer_com_banknext_customer_mgt_repo_model_Customer_Test_15() throws Exception
{


Customer result = customerserviceimpltype2.updateCustomer( new Customer() ) ;

assertThat(result).isInstanceOf(Customer.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void getOptCustomerById_int_Test_16() throws Exception
{


Optional result = customerserviceimpltype2.getOptCustomerById( 1 ) ;

assertThat(result).isInstanceOf(Optional.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void createCustomerWithNonVoidReturnTypeAndExtrMtdWithNonVoidAndVoidReturnTypeA_com_banknext_customer_mgt_repo_model_Customer_Test_17() throws Exception
{
when(custRepo.save(Mockito.any())).thenReturn(dtoFactory.getCustomer());
doNothing().when(custRepo).delete(isA(com.banknext.customer.mgt.repo.model.Customer.class));


Customer result = customerserviceimpltype2.createCustomerWithNonVoidReturnTypeAndExtrMtdWithNonVoidAndVoidReturnTypeA( new Customer() ) ;

verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));assertThat(result).isInstanceOf(Customer.class);verify(custRepo, times(1)).delete(ArgumentMatchers.any(Customer.class));assertThat(result).isInstanceOf(Customer.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void createCustomerWithVoidReturnTypeAndExtrMtdWithNonVoidAndVoidReturnType_com_banknext_customer_mgt_repo_model_Customer_Test_18() throws Exception
{
when(custRepo.save(Mockito.any())).thenReturn(dtoFactory.getCustomer());
doNothing().when(custRepo).delete(isA(com.banknext.customer.mgt.repo.model.Customer.class));


customerserviceimpltype2.createCustomerWithVoidReturnTypeAndExtrMtdWithNonVoidAndVoidReturnType( new Customer() ) ;

verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));verify(custRepo, times(1)).delete(ArgumentMatchers.any(Customer.class));
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void getCustomerById_com_banknext_customer_mgt_repo_model_Customer_Test_19() throws Exception
{
when(custRepo.findById(Mockito.any())).thenReturn(dtoFactory.getOptCustomer());


Customer result = customerserviceimpltype2.getCustomerById( new Customer() ) ;

verify(custRepo, times(1)).findById(ArgumentMatchers.any());assertThat(result).isInstanceOf(Customer.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends






////@Test 
public void updateCustomerOpt_com_banknext_customer_mgt_repo_model_Customer_Test_20() throws Exception
{


Optional result = customerserviceimpltype2.updateCustomerOpt( new Customer() ) ;

assertThat(result).isInstanceOf(Optional.class);
/* additional assert templates
verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
assertThat(custSvc.findAll()).isInstanceOf(List.class);
assertThat(custSvc.findAll().size()).isGreaterThan(0);
assertThat(result).isEqualTo(3);
assertThat(result).isNotInstanceOf(List.class);
*/

} //mtd ends







}