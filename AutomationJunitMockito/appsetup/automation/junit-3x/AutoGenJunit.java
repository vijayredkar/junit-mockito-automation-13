package PLCHLDR-AUTOMATION-PCKG ;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/*  *******************  Junit 4.x *******************
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
*/

//* ******************  Junit 5.x *******************
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

//* ******************  Junit 4.x *******************
//@RunWith(SpringRunner.class)
//* ******************  Junit 5.x *******************
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest

//******************************** Junit-Mockito  Ground Rules ********************************//  
/*
1- target mtd to be tested createCustomer is a void returnType   
2- external mtd invoked .delete     is a void returnType
3- when-then	  :  doNothing whenInvoke .delete
4- assertion    :  verify .delete called 1 times  


1- target mtd to be tested createCustomer is a void returnType   
2- external mtd invoked .save          is a Customer returnType
3- when-then	  :  when  save then return new Customer
4- assertion    :  verify .save called 1 times



1- target mtd to be tested createCustomer is a Customer returnType   
2- external mtd invoked .save          is a Customer returnType
3- when-then	  :  when  save then return new Customer
4- assertion    :  assert result instanceof Customer / Customer is not null

1- target mtd to be tested createCustomer is a Customer returnType   
2- external mtd invoked .findAll          is a List returnType
3- when-then	  :  when findAll then return new ArrayList
4- assertion    :  assert result instanceof List / List.size >0
*/
//******************************** Junit-Mockito  Ground Rules ********************************//


@Component
public class AutoGenJunit	
	{
	
		String applicationBaseFilePath = "";
		String targetClassNameFullyQualified = "";		
		String applicationSetupDirPath = "";
		String applicationMainClassPath = "";
		String automationTemplatesPath = "";		
		Class targetClaz;
		boolean isStereoTypeController= false; 
	    boolean isStereoTypeSevice= false;
	    static Map<String, String>  cacheUserInput = new HashMap<String, String>();
	    static Scanner sc = new Scanner(System.in);
	
		@Autowired
		Environment env;
		
		@Autowired
		ConfigurableApplicationContext app;
		
		@Autowired
		MetaDataCollect metaDataCollect;
		
	    @PostConstruct
		public void initiate() throws Exception
		{
			
			 if(!isOperateModeGenerate()) { return; } 
			
			captureEnv();						
			generateJunits();			
			cleanup();
		}

		private void cleanup() 
		{
			try 
	    	{  //shutdown all the components/application. Junits are generated. No need launch the application.
	    		app.close();
	    		app.stop();        	
	    	}
	    	catch(Exception e)
	    	{
	    		System.out.println("\n\nProcess complete - Junits generated");
	        	System.out.println("Close this console and proceed to the next step on the parent console.");
	    		System.exit(0);
	    	}
		}

		private void captureEnv() 
		{
			applicationSetupDirPath = env.getProperty("APP_SETUP_DIR");       // C:\Vijay\Java\AutomationJunitMockito\appsetup
			applicationBaseFilePath = env.getProperty("APP_BASE_PATH") + "\\"; // C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation			
			targetClassNameFullyQualified = env.getProperty("TARGET_CLASS"); // com.banknext.customer.mgt.controller.CustomerMgtControllerA			
			applicationMainClassPath = env.getProperty("APP_MAIN_CLASS_DIR"); // C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\main\java\com\banknext\customer\mgt
			automationTemplatesPath = env.getProperty("AUTOMATION_TEMPLATES_PATH"); //C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\main\java\com\banknext\customer\mgt\automation\templates
			
			
			
			
			//-- hardcoded for tests
			/*
			System.out.println("\n*************** HARDCODED FOR TESTS ********************\n");
			System.out.println("\n*************** HARDCODED FOR TESTS ********************\n");
			System.out.println("\n*************** HARDCODED FOR TESTS ********************\n");
			System.out.println("\n*************** HARDCODED FOR TESTS ********************\n");
			System.out.println("\n*************** HARDCODED FOR TESTS ********************\n");
			applicationSetupDirPath =  "C:\\Vijay\\Java\\AutomationJunitMockito\\appsetup";
			applicationBaseFilePath =  "C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\";
			applicationMainClassPath =  "C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\main\\java\\com\\banknext\\customer\\mgt";
			automationTemplatesPath  = "C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\main\\java\\com\\banknext\\customer\\mgt\\automation\\templates";
			//targetClassNameFullyQualified = "com.banknext.customer.mgt.event.publisher.CustomerTxnPublisher";
			//targetClassNameFullyQualified = "com.banknext.customer.mgt.event.consumer.EntityTxnConsumerA";
			//targetClassNameFullyQualified = "com.banknext.customer.mgt.controller.CustomerMgtControllerA";
			targetClassNameFullyQualified = "com.banknext.customer.mgt.service.CustomerServiceImpl";			
			//-- hardcoded for tests
			*/
			
			//Controller scenario     
			/*
			String targetPackageFilePath = "src\\main\\java\\com\\banknext\\customer\\mgt\\controller\\";
			String targetClassNameFullyQualified = "com.banknext.customer.mgt.controller.CustomerMgtControllerA";	
			*/
			
			//Service scenario
			/*
			String targetPackageFilePath = "src\\main\\java\\com\\banknext\\customer\\mgt\\service\\";
			String targetClassNameFullyQualified = "com.banknext.customer.mgt.service.CustomerServiceImpl"; 
			*/
			
			//KafkaConsumer Producer scenario
			/*
			String targetPackageFilePath = "src\\main\\java\\com\\banknext\\customer\\mgt\\event\\consumer\\";
			String targetClassNameFullyQualified = "com.banknext.customer.mgt.event.consumer.EntityTxnConsumerA";
			*/
			
			//KafkaPubslisher Consumer scenario
			/*
			String targetPackageFilePath = "src\\main\\java\\com\\banknext\\customer\\mgt\\event\\publisher\\";
			String targetClassNameFullyQualified = "com.banknext.customer.mgt.event.publisher.CustomerTxnPublisher";
			*/

		}

		private boolean isOperateModeGenerate() 
		{
			String modeOfOperation = env.getProperty("MODE", "autorun");
			
			if(!"autogen".equals(modeOfOperation))
			{
				System.out.println("-- modeOfOperation : "+modeOfOperation);
				System.out.println("-- skipping auto generation ");
				return false;
			}			
			System.out.println("-- modeOfOperation : "+modeOfOperation);
			System.out.println("-- starting auto generation ");
			return true;
		}

		private void generateJunits() throws Exception 
		{
			getDeclaredAnnotationsForClass();
			
			isStereoTypeController = isStereoTypeMatch("RestController");
			isStereoTypeSevice = isStereoTypeMatch("Service");
			
			confirmTargetType();
			
			if(isStereoTypeController || isStereoTypeSevice)
			{
				new AutoGenJunitForControllerAndSvc().initiate(applicationBaseFilePath, targetClassNameFullyQualified, app, metaDataCollect, isStereoTypeController, isStereoTypeSevice, applicationMainClassPath, automationTemplatesPath); 
			}
			else
			{
				new AutoGenJunitGeneric().initiate(applicationBaseFilePath, targetClassNameFullyQualified, app, metaDataCollect, applicationMainClassPath, automationTemplatesPath);
			}
		}

		private void confirmTargetType()  throws Exception
		{
			String targetType = setTargetType();			
			finalizeTargetType(targetType);
		}

		private void finalizeTargetType(String targetType) throws Exception 
		{
			String result  = getUserInput("10-  Is your target class of type : "+ targetType + "\n  Please confirm: [Y/N]");
			
			if(!"Y".equalsIgnoreCase(result))
			{
				result  = getUserInput("11-  Please specify the type# \n 1: RestController \n 2: Service \n 3: Other");	
				if("1".equals(result))
				{
					targetType = "RestController";
					isStereoTypeController = true;
					isStereoTypeSevice = false;
				}
				else if("2".equals(result))
				{
					targetType = "Service";
					isStereoTypeController = false;
					isStereoTypeSevice = true;
				}
				else
				{
					targetType = "Component Generic";
					isStereoTypeController = false;
					isStereoTypeController = false;
				}
			}
			
			System.out.println("Target type confirmed as "+ targetType + " \nProceeding to produce Junits .." );
			Thread.sleep(5000);
		}

		private String setTargetType() {
			String targetType = "Neither RestController nor Service";
			if(isStereoTypeController)
			{
				targetType = "RestController";
			}
			else if(isStereoTypeSevice)
			{
				targetType = "Service";
			}
			return targetType;
		}
		
		/*
		private boolean isStereoTypeMatch(String type) 
		{ 
			return
			Arrays.asList(metaDataCollect.getDeclaredForAnnotationsClass())
				  .stream()
				  .anyMatch(a -> a.toString().contains(type));
		}
		*/
		private boolean isStereoTypeMatch(String type) 
		{ 
			return
			Arrays.asList(metaDataCollect.getDeclaredForAnnotationsClass())
				  .stream()
				  .anyMatch(a -> a.toString().contains(type));
		}
		
	    private void getDeclaredAnnotationsForClass() throws Exception 
	    {
			targetClaz = Class.forName(targetClassNameFullyQualified);
			
			//@org.springframework.web.bind.annotation.RestController("")   
			//@org.springframework.web.bind.annotation.RequestMapping(path={}, headers={}, method={}, name="", produces={}, params={}, value={"/api/customers"}, consumes={})
			Annotation[] declaredAnnotationsClass = targetClaz.getDeclaredAnnotations();			      
			metaDataCollect.setDeclaredAnnotationsForClass(declaredAnnotationsClass); 
		}
	    
	    private static String getUserInput(String message) 
	    { 
	      String result = "";	
	      if(cacheUserInput.containsKey(message.trim()))
	      {
	    	  result = cacheUserInput.get(message);
	    	  
	    	  System.out.println("\nUtilizing your earlier provided information : " );
	    	  System.out.println("Question                : " + message);
	    	  System.out.println("Your earlier response   : " + result );
	      }
	      else
	      {
	    	  sc = new Scanner(System.in);	
	          System.out.println("\n" + message);	
	          result = sc.nextLine();      
	          //cache user provide i/p
	          cacheUserInput.put(message.trim(), result);  
	      }     
	      
		  return  result;
		}
 }
