//package com.banknext.customer.mgt.service;
package com.bezkoder.spring.jpa.h2.automation.junit.backup;


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


import java.util.ArrayList;
import java.util.*;
import java.util.List;
import java.util.*;
import java.util.Optional;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.bezkoder.spring.jpa.h2.model.Tutorial;
import com.bezkoder.spring.jpa.h2.model.*;
import com.bezkoder.spring.jpa.h2.repository.TutorialRepository;
import com.bezkoder.spring.jpa.h2.repository.*;
import com.bezkoder.spring.jpa.h2.controller.TutorialController;







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



public class TutorialControllerTest 
{

	
 @MockBean 
TutorialRepository  tutorialRepository ;
	/*
	@MockBean
	CustomerRepository custRepo;
	*/
	
	@Autowired 
TutorialController tutorialcontroller ;	
	/*
	@Autowired
	CustomerServiceImpl custSvc;
	*/

    @Autowired
    private MockMvc mockMvc;
	
    @Autowired
    private ObjectMapper objectMapper;

    DtoFactoryTutorialController dtoFactory ;	
    @BeforeEach
    public void init()
    {
    	dtoFactory = new DtoFactoryTutorialController();
    }	
	        
    //@Test 
public void createTutorial_com_bezkoder_spring_jpa_h2_model_Tutorial_Test_2() throws Exception
{
when(tutorialRepository.save(Mockito.any())).thenReturn(dtoFactory.getTutorial());


ResultActions result = mockMvc.perform(post("/api/tutorials")
.contentType(MediaType.APPLICATION_JSON)
.content(objectMapper.writeValueAsString(dtoFactory.getTutorial())));


verify(tutorialRepository, times(1)).save(ArgumentMatchers.any()) ;
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
public void deleteAllTutorials_Test_3() throws Exception
{
doNothing().when(tutorialRepository).deleteAll();


ResultActions result = mockMvc.perform(delete("/api/tutorials"));

verify(tutorialRepository, times(1)).deleteAll();
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
public void getAllTutorials_java_lang_String_Test_4() throws Exception
{
	List<Tutorial> tutorials =  new ArrayList<Tutorial>();
	tutorials.add(dtoFactory.getTutorial());
	
	
when(tutorialRepository.findAll()).thenReturn(tutorials);
when(tutorialRepository.findByTitleContainingIgnoreCase(Mockito.any())).thenReturn(tutorials);


ResultActions result = mockMvc.perform(get("/api/tutorials"));

verify(tutorialRepository, times(1)).findAll();
//verify(tutorialRepository, times(1)).findByTitleContainingIgnoreCase(ArgumentMatchers.any(String.class));
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
             
             //.andExpect(content().   string(containsString("")))
             
             //.andExpect(jsonPath("$.id", is(dtoFactory.getTutorial().getId())))            
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
public void updateTutorial_long_com_bezkoder_spring_jpa_h2_model_Tutorial_Test_5() throws Exception
{
when(tutorialRepository.findById(Mockito.any())).thenReturn(dtoFactory.getOptTutorial());
when(tutorialRepository.save(Mockito.any())).thenReturn(dtoFactory.getTutorial());


ResultActions result = mockMvc.perform(put("/api/tutorials/1")
.contentType(MediaType.APPLICATION_JSON)
.content(objectMapper.writeValueAsString(dtoFactory.getTutorial())));


verify(tutorialRepository, times(1)).findById(ArgumentMatchers.any(long.class));
verify(tutorialRepository, times(1)).save(ArgumentMatchers.any());
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
public void deleteTutorial_long_Test_6() throws Exception
{
doNothing().when(tutorialRepository).deleteById(isA(long.class));


ResultActions result = mockMvc.perform(delete("/api/tutorials/1"));

verify(tutorialRepository, times(1)).deleteById(ArgumentMatchers.any(long.class));
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
public void findByPublished_Test_7() throws Exception
{
	List<Tutorial> tutorials =  new ArrayList<Tutorial>();
	tutorials.add(dtoFactory.getTutorial());
when(tutorialRepository.findByPublished(Mockito.anyBoolean())).thenReturn(tutorials);


ResultActions result = mockMvc.perform(get("/api/tutorials/published"));

verify(tutorialRepository, times(1)).findByPublished(ArgumentMatchers.anyBoolean());
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
             
             .andExpect(content().string(containsString("testTitle")))
             
             //.andExpect(jsonPath("$.id", is(dtoFactory.getTutorial().getId())))            
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
public void getTutorialById_long_Test_8() throws Exception
{
when(tutorialRepository.findById(Mockito.any())).thenReturn(dtoFactory.getOptTutorial());


ResultActions result = mockMvc.perform(get("/api/tutorials/1"));

verify(tutorialRepository, times(1)).findById(ArgumentMatchers.any(long.class));
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
             
             .andExpect(content().string(containsString("test")))
             
             //.andExpect(jsonPath("$.id", is(dtoFactory.getTutorial().getId())))            
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







}