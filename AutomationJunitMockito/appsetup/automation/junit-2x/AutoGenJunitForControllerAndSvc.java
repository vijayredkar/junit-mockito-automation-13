package com.banknext.customer.mgt.automation.junit;

import java.util.List;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.HashSet;

import org.hibernate.internal.build.AllowSysOut;

//*******************  Junit 4.x *******************
/*
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
*/
//*******************  Junit 4.x *******************//



//*******************  Junit 5.x *******************
import org.springframework.boot.SpringApplication;
//*******************  Junit 5.x *******************
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
//vjNew1
import java.io.BufferedWriter;
import java.io.FileWriter;


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



public class AutoGenJunitForControllerAndSvc	
	{
		MetaDataCollect metaDataCollect;
		String targetPackageFilePath ="";
		Class mainAppClaz;
		Class targetClaz;
		ConfigurableApplicationContext app;
		boolean isStereoTypeController= false; 
	    boolean isStereoTypeSevice= false;
	    String automationTemplatesPath ="";
	    boolean dtoFactoryBaseCreated = false; //vjNew1
	    Set<String> dtoFactoryTrackerSet = new HashSet<String>(); //vjNew1
		
		//user i/p
		String applicationBaseFilePath ="";
		static String targetClassNameFullyQualified ="";
		String mainApplicationClassName = "";
		String applicationMainClassPath = "";
		static String junitPackageScope = "";
		String extractedPackagePath ="";
		
		
		//static String targetClassNameFullyQualified =""; 
		static String targetClassName = "";              //CustomerServiceImpl	
		String targetMethodNameWithSignature = "public void createCustomer(Customer cust)";	
		String targetClassPostfix = ".java";
		//static String junitPackageScope = targetClassNameFullyQualified.substring(0, targetClassNameFullyQualified.indexOf("service"));  // com.banknext.customer.mgt.
		//static String junitPackageScope = "com.banknext.customer.mgt.";	
			
		String mainAppClassName = "com.banknext.customer.mgt.CustomerMgtApplication"; //hardcoded
		
		//List<Field>  beanDependencies = new ArrayList<Field>();
		String[] mtdLines;
		//List<String>  stubEligibleLine = new ArrayList<String>();	
		String whenThenTemplate = "when(STUB-CONDITION).thenReturn(STUB-ACTION);";
//		List<String>  whenThenList = new ArrayList<String>();
		StringBuilder mockBeansBuilder = new StringBuilder();
		StringBuilder whenThenBuilder = new StringBuilder();
		StringBuilder assertionBuilder = new StringBuilder();
		String executeMtdString = "";
		//List<String>  metaDataDeclaredMethodsList = new ArrayList<String>();
		//Map<String, String>  metaDataDeclaredMethodsMap = new HashMap<String, String>();
		Map<String, String>  metaDataDeclaredFieldsMap = new HashMap<String, String>();
		StringBuilder metaDataInfoBldr = new StringBuilder();
		
		
		StringBuilder contentBldr  =  new StringBuilder();
		String methodName = "";
		String[] tokens;
		String assertionTemplate;
		boolean skipProcessing = false;
		int mtdNameCounter = 1;
		int targetMtdNameCounter = 1;

		//String assertionTemplateAssert1 = "assertThat(PLCHOLDER-TARGET-BEAN-INSTANCE-NAME.PLCHOLDER-TARGET-BEAN-METHOD-NAME(PLCHOLDER-TARGET-BEAN-INPUT-ARGS)).isInstanceOf(PLCHOLDER-RETURN-TYPE-RAW-CLASS);"; //non void return type case
		String assertionTemplateAssert1 = "assertThat(result).isInstanceOf(PLCHOLDER-RETURN-TYPE-RAW-CLASS);"; //non void return type case
		String assertionTemplateVerify2 = "verify(PLCHOLDER-MOCK-BEAN-INSTANCE-NAME, times(1)).PLCHOLDER-MOCK-BEAN-INSTANCE-METHOD(ArgumentMatchers.any(PLCHOLDER-MOCK-BEAN-INSTANCE-TYPE));" ;
		String assertionTemplateVerify3 = "verify(PLCHOLDER-MOCK-BEAN-INSTANCE-NAME, times(1)).PLCHOLDER-MOCK-BEAN-INSTANCE-METHOD(PLCHOLDER-MOCK-BEAN-INSTANCE-TYPE) ;" ;
		Map<String, MetaDataDeclaredMethods>  metaDataDeclaredMethodsMap = new HashMap<String, MetaDataDeclaredMethods>();
		MetaDataDeclaredMethods metaDataDeclMtd = null;
		
		List<String>  stubEligibleLinesList = new ArrayList<String>();
		String returnTypeOfCurrentRecord="";
		int stubMtdNumOfInputParamsCount = 0;	
		
		
		List<MetaDataDeclaredMethods>  recordsWithFullMtdSignature = new ArrayList<MetaDataDeclaredMethods>();
		List<MetaDataDeclaredMethods>  recordsWithStubEligibleLinesList = new ArrayList<MetaDataDeclaredMethods>();
		
		String extractedTargetClassName="";
		String userResponseReturnType = "";
		String repositoryMtdInputArgsDataType = "";
		
		StringBuilder targetMtdInvokedArgsBldr = new StringBuilder();
		boolean voidCaseScenario = false;
		
		static Scanner sc = new Scanner(System.in);
		String formattedReturnType = "";
		String stubEligibleLIne1Formatted = "";
		boolean stubAssertionDone = false;
		
		static Map<String, String>  cacheUserInput = new HashMap<String, String>();
		String methodNameToMatch ="";
		StringBuilder importStmtsBldr = new StringBuilder();
		
		boolean blockCommentStartDetected = false;
		boolean blockCommentEndDetected = false;
		boolean blockCommentEndDetectedClsLvl = false;
		
	    String pathPackageModel = ""; 	
	    String pathOfInputDtos = "";
	    String pathOfOutputDtos = "";
	    String pathOfRepositoryDtos = "";
	    String pathOfInputEventDtos = "";
	    String pathOfOutputEventDtos = "";
	    String pathOfAllDtosConcatenated = "";
	    List<String> allEligibleDtosList = new ArrayList<String>();
	    
	    
	    StringBuilder dtoFactoryCreatedForTypeBldr = new StringBuilder();
	    String classNameOnly="";    
	    List<String> returnTypeDtoFieldNamesAll = new ArrayList<String>();
	    List<String> returnTypeDtoFieldNamesRawAll = new ArrayList<String>();
	    int finalNumOfInputArgs = 0;
	    Method exactMtdMatchFound = null;
	    String mtdSignatureCaptured ="";    
	    int signatureSpreadOverLinesCount = 0;   
	    

	    /*
	    public void initiate_1(String applicationBaseFilePath, String targetClassNameFullyQualified, 
				 			 ConfigurableApplicationContext app, MetaDataCollect metaDataCollect) throws Exception
		{	
			System.out.println("-- AutoGenJunitForControllerAndSvc started ..");		
			this.applicationBaseFilePath = applicationBaseFilePath;
			this.targetClassNameFullyQualified = targetClassNameFullyQualified;
			this.app = app;
			this.metaDataCollect = metaDataCollect;
			
			targetClassName = targetClassNameFullyQualified.substring(targetClassNameFullyQualified.lastIndexOf(".")+1); // CustomerServiceImpl  CustomerTxnPublisher
	    	String beanName = targetClassName;
	    	String firstLetter = ((Character)beanName.charAt(0)).toString(); //get the 1st letter
	    	beanName =  beanName.replaceFirst(firstLetter, firstLetter.toLowerCase()); // change 1st letter to lowercase  CustomerServiceImpl  to  customerServiceImpl
	    		    	    	
	    	getAllMockBeanDependencies(beanName);// get all injected dependencies  customerServiceImpl  customerTxnPublisher
	    	getDeclaredAnnotationsForClass();
	    	getAllDeclaredFields();						 //get all field attributes
	    	createMockitoWhenThen();
	    	autoGenJunitMockitoTest();
	    	
	    	sc.close();
		}
	    */
	    public void initiate(String applicationBaseFilePath, String targetClassNameFullyQualified, 
				 			 ConfigurableApplicationContext app, MetaDataCollect metaDataCollect,
				 			 boolean isStereoTypeController, boolean isStereoTypeSevice, 
				 			 String applicationMainClassPath, String automationTemplatesPath ) throws Exception
		 {	
			System.out.println("-- AutoGenJunitForControllerAndSvc started ..");		
			this.applicationBaseFilePath = applicationBaseFilePath;
			this.targetClassNameFullyQualified = targetClassNameFullyQualified;
			this.app = app;
			this.metaDataCollect = metaDataCollect;
			this.isStereoTypeController = isStereoTypeController;
			this.isStereoTypeSevice = isStereoTypeSevice;
			this.applicationMainClassPath = applicationMainClassPath;
			this.automationTemplatesPath = automationTemplatesPath;
			
			
			targetClassName = targetClassNameFullyQualified.substring(targetClassNameFullyQualified.lastIndexOf(".")+1); // CustomerServiceImpl  CustomerTxnPublisher
			
			// com.banknext.customer.mgt   classes under this location only will be in scope
			junitPackageScope = applicationMainClassPath;  // C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\main\java\com\banknext\customer\mgt
			junitPackageScope = junitPackageScope.replace(applicationBaseFilePath, ""); // \src\main\java\com\banknext\customer\mgt
			junitPackageScope = junitPackageScope.replace("src\\main\\java\\", ""); // com\banknext\customer\mgt
			junitPackageScope = junitPackageScope.replace("\\", "."); // com.banknext.customer.mgt 
			
			/*  -- below computing logic is no longer needed. Main class path is provided by the user
			junitPackageScope = targetClassNameFullyQualified;			
			if(junitPackageScope.contains("."))
			{
					if(junitPackageScope.split("\\.").length > 2)  // com.banknext.customer.mgt.service
					{
						List<String> pckgPortionList = Arrays.asList(junitPackageScope.split("\\."))
								  .stream()							    
								  .limit(2)								// com banknext
								  .skip(1)								// banknext
								  .toList()
								  ;
						String pckgPortion = pckgPortionList.get(0) + "."; // banknext.				
						junitPackageScope =  junitPackageScope.substring(0, junitPackageScope.indexOf(".") +1) +  pckgPortion; // com.banknext.
					}
				}				
				System.out.println("-- junitPackageScope  : "+junitPackageScope);
				*/
				
				targetPackageFilePath = "src\\main\\java\\" + targetClassNameFullyQualified.substring(0, targetClassNameFullyQualified.lastIndexOf("."));
				targetPackageFilePath = targetPackageFilePath.replaceAll("\\.", "\\\\") + "\\";
				System.out.println("-- targetPackageFilePath  : "+targetPackageFilePath);
						
				String beanName = targetClassName;
				String firstLetter = ((Character)beanName.charAt(0)).toString(); //get the 1st letter
				beanName =  beanName.replace(firstLetter, firstLetter.toLowerCase()); // change 1st letter to lowercase  CustomerServiceImpl  to  customerServiceImpl
				
				
				
				//vjNew1
				Path path = Paths.get(applicationMainClassPath + "\\" +"DtoFactory"+targetClassName + targetClassPostfix); // C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\main\java\com\banknext\customer\mgt\DtoFactory.java				
				if(Files.exists(path))
				{  //append to existing
					Files.delete(path);
					System.out.println("-- Deleted old DtoFactory: "+ path.toString());
				}

				
				
				
				getAllMockBeanDependencies(beanName);// get all injected dependencies  customerServiceImpl  customerTxnPublisher
				getAllDeclaredFields();						 //get all field attributes
				createMockitoWhenThen();
				//getAutomationTemplates();
				autoGenJunitMockitoTest();
				sc.close();    	
		}
	/*    
	   private void getAutomationTemplates() 
	   {
		   automationTemplatesDir = applicationMainClassPath + "\\src\\main\\java" + "\\automation\\templates";  // C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\main\\java\automation\templates			
	   }
*/
		/*
		private boolean isDtoModelDetected(String importStmt) 
		{
			return pathOfAllDtosConcatenated.trim().contains(importStmt.trim());
		}
		*/
		private List<String> collectAllDtos(String importStmt) // import com.banknext.customer.mgt.repo.model.Customer 
		{
			
			if(importStmt == null || "".equals(importStmt))
			{
				return allEligibleDtosList;
			}
			
			if(importStmt.contains("Customer"))
			{
			  //System.out.println("--test");	
			}
			
			String dtoClassName = "";
			
			importStmt =  importStmt.replace("import", "")  // com.banknext.customer.mgt.repo.model.Customer
				    .replace(";", "")
				    .trim();
			
			
			if(pathOfAllDtosConcatenated.trim().contains("."))
			{
				dtoClassName = importStmt.substring(importStmt.lastIndexOf(".") + 1); //Customer	
			}
			
			if(importStmt.contains("."))
			{
				importStmt =  importStmt.substring(0, importStmt.lastIndexOf(".")); //com.banknext.customer.mgt.repo.model	
			}		
			
			if(pathOfAllDtosConcatenated.trim().contains(importStmt.trim()))
			{
				allEligibleDtosList.add(importStmt + "." + dtoClassName); //com.banknext.customer.mgt.repo.model.Customer
			}
			return allEligibleDtosList; //  com.banknext.customer.mgt.repo.model.Customer
		}
		
		private void createDtoFactory(String dtoPackagePath) 
		{	
			if(dtoFactoryCreatedForTypeBldr.toString().contains(dtoPackagePath))
			{
				return; //already created dtoFactory mtds
			}	
			
			
			List<String> typeList = new ArrayList<String>();
			typeList.add("Regular");
			typeList.add("Optional");
			
			try 
			{   // // class com.banknext.customer.mgt.repo.model.Customer 
				dtoPackagePath = dtoPackagePath.replace("class", "")
											   .replace("interface", "")
											   .replace("java.util.Optional", "")
											   .replace("<", "")
											   .replace(">", "")										   
											   .trim(); // com.banknext.customer.mgt.repo.model.Customer
				
				StringBuilder dtoBuilderMethodBody =  new StringBuilder();
				
				// com.banknext.customer.mgt.repo.model.Customer
				classNameOnly =  dtoPackagePath.substring(dtoPackagePath.lastIndexOf(".") +1);   //Customer 
				
				
				
				
				
				
				StringBuilder dtoFactoryBldr = new StringBuilder();
				StringBuilder dtoFactoryBaseBldr = new StringBuilder();
				//vjNew1
				if(!dtoFactoryBaseCreated) 
				{   //create only once
					createDtoFactoryBase(dtoPackagePath, dtoBuilderMethodBody, classNameOnly, dtoFactoryBldr, dtoFactoryBaseBldr);
				}
				for(String type : typeList)
				{
				   buildByType(dtoPackagePath, dtoBuilderMethodBody, classNameOnly, type, dtoFactoryBldr);
				}
				
				dtoFactoryTrackerSet.add(dtoPackagePath); //mark as created    com.banknext.customer.mgt.repo.model.Customer


				dtoFactoryBldr.append("\n}//class ends"); //indicator
				dtoFactoryBldr.append("\n");
				
				StringBuilder dtoFactoryFull = dtoFactoryBaseBldr.append(dtoBuilderMethodBody)
																 //.append(dtoFactoryBldr) 
																 //.append("\n}//class ends")
																 //.append("\n}//class ends")
																 ;
				/*
				if(!dtoFactoryFull.toString().contains("\n}//class ends"))
				{
					dtoFactoryFull.append("\n}//class ends 123"); //to ensure that this is added only once and at the end of the class
				}
				*/
				
				/*
				//String formattedDtoFactoryFull = dtoFactoryFull.toString().replaceAll("}////class ends", "");
				String formattedDtoFactoryFull = dtoFactoryFull.toString().replace("}//class ends", "");
				dtoFactoryFull = new StringBuilder(formattedDtoFactoryFull);
				dtoFactoryFull.append("\n}//class ends");				
				*/
				
				
				if(dtoFactoryBldr.length() > 0)
				{
					//Path path = Paths.get("C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\test\\java\\com\\banknext\\customer\\mgt\\service\\DtoFactory.java");
					//Path path = Paths.get(applicationBaseFilePath + targetPackageFilePath + "DtoFactory" + targetClassPostfix); //C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\main\\java\\com\\banknext\\customer\\mgt\\service\\DtoFactory.java
					//Path path = Paths.get(applicationBaseFilePath + "src\\main\\java\\" + "com\\banknext\\customer\\mgt\\service\\"  +"DtoFactory" + targetClassPostfix);
					Path path = Paths.get(applicationMainClassPath + "\\" +"DtoFactory"+targetClassName + targetClassPostfix); // C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\main\java\com\banknext\customer\mgt\DtoFactory.java
					//vjNew1
					if(Files.exists(path))
					{  //append to existing
						//Files.writeString(path, dtoFactoryBldr.toString(), StandardCharsets.UTF_8, StandardOpenOption.APPEND); 	
						Files.writeString(path, dtoFactoryFull.toString(), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
					}
					else
					{   //create New 
						//dtoFactoryFull.append("\n}//class ends 123"); //to ensure that this is added only once and at the end of the class
						Files.writeString(path, dtoFactoryFull.toString(), StandardCharsets.UTF_8);
					}	
					System.out.println("--");
				}
				
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		//vjNew1
		private void createDtoFactoryBase(String dtoPackagePath, StringBuilder dtoBuilderMethodBody, String classNameOnly, StringBuilder dtoFactoryBldr, StringBuilder dtoFactoryBaseBldr)
				throws ClassNotFoundException, FileNotFoundException {
			
			/*
			if(!dtoPackagePath.contains(junitPackageScope)) //int , float 
			{	
				return;
			}
			*/
			Scanner sc = new Scanner(new File(automationTemplatesPath + "\\DtoFactoryBaseTemplate.txt"));
			String line ="";
			
			while(sc.hasNextLine())
			{
			 line = sc.nextLine();
			 line =  line.trim();
			 
			 
				 if(line.contains("PLCHOLDER-DTOFACTORY-PACKAGE-PATH"))
				 { 
					 line = line.replace("PLCHOLDER-DTOFACTORY-PACKAGE-PATH", junitPackageScope +".automation.junit;"); // com.banknext.customer.mgt.automation.junit;
				 }
				 else if(line.contains("PLCHOLDER-DTOFACTORY-IMPORT-STMTS"))
				 {
					 line = line.replace("PLCHOLDER-DTOFACTORY-IMPORT-STMTS",  importStmtsBldr + ";");
				 }
				 else if(line.contains("PLCHOLDER-DTOFACTORY-NAME"))
				 {
					 line = line.replace("PLCHOLDER-DTOFACTORY-NAME",  "DtoFactory" + targetClassName);  // DtoFactoryCustomerMgtControllerATest
				 }
			
			 dtoFactoryBldr.append(line);
			 dtoFactoryBldr.append("\n");
			 
			 dtoFactoryBaseBldr.append(line);
			 dtoFactoryBaseBldr.append("\n");
			 
			}
			
			sc.close();
			
			dtoFactoryCreatedForTypeBldr.append(dtoPackagePath);
			dtoFactoryCreatedForTypeBldr.append(",");
			
			dtoFactoryBaseCreated = true;
		}
		
		//vjNew1
		private void buildByType(String dtoPackagePath, StringBuilder dtoBuilderMethodBody, String classNameOnly, String type, StringBuilder dtoFactoryBldr)
				throws ClassNotFoundException, FileNotFoundException {
			
			if(!dtoPackagePath.contains(junitPackageScope)) //int , float 
			{	
				return;
			}
			//vjNew1
			if(dtoFactoryTrackerSet.contains(dtoPackagePath))  
			{	
				return; //don't create DtoFactory if already created
			}
			
			String constructedMtdName = constructedMtdName =  "public " + classNameOnly + " get"+classNameOnly+"() {";   // public Customer getCustomer() {;
			if("Optional".equals(type))
			{
				constructedMtdName =  "public Optional< " + classNameOnly + "> getOpt"+classNameOnly+"() {";   // public Optional<Customer> getCustomer() {	
			}
			
			dtoBuilderMethodBody.append("\n");
			dtoBuilderMethodBody.append(constructedMtdName);
			dtoBuilderMethodBody.append("\n");
			
			String instanceName = "_"+classNameOnly+"Obj";
			String newInstance = classNameOnly + " "+ instanceName + "  = new "+ classNameOnly  + "();"  ;  // Customer customer = new Customer();
			
			dtoBuilderMethodBody.append(newInstance);
			dtoBuilderMethodBody.append("\n");
			
			
			Class dtoClaz = Class.forName(dtoPackagePath);
			//Class dtoClaz = Class.forName("com.banknext.customer.mgt.repo.model.CustomerEmbed");			
			//Object dtoInstance  = dtoClaz.newInstance();
			//Class[] declaredClasses  = dtoClaz.getDeclaredClasses(); // nothing
			//Class[] nestMembers  = dtoClaz.getNestMembers();
			//Method[] declaredMethods  = dtoClaz.getDeclaredMethods(); //public void com.banknext.customer.mgt.repo.model.Customer.setEnabled(java.lang.Boolean)
			Field[] declaredFields  = dtoClaz.getDeclaredFields(); // private java.lang.Boolean com.banknext.customer.mgt.repo.model.Customer.enabled
			
			for(Field fld : declaredFields)
			{
				
				String fldNameFormatted = fld.getName();
				char firstChar  = fldNameFormatted.charAt(0);
				String firstCharStr  = new Character(firstChar).toString();
				String firstCharUprCase = new Character(firstChar).toString().toUpperCase();
				fldNameFormatted = fldNameFormatted.replaceFirst(firstCharStr, firstCharUprCase);  //firstName -> FirstName
				
				returnTypeDtoFieldNamesRawAll.add(fld.getName());
				returnTypeDtoFieldNamesAll.add(fldNameFormatted);
				
				
				//String value = "\"test\" + fldNameFormatted + \"";
				String value = getValueForType(fld, fldNameFormatted);
						
				//String setterStmt = instanceName + ".set" + fldNameFormatted + "(\"test" + fldNameFormatted + "\");\n" ; //customer.setFirstName("testFirstName"); 
				//String setterStmt = instanceName + ".set" + fldNameFormatted + "(\"test" + fldNameFormatted + "\");\n" ; //customer.setFirstName("testFirstName");
				String setterStmt = instanceName + ".set" + fldNameFormatted + "(" + value + ");\n" ; //customer.setFirstName("testFirstName");
				
				dtoBuilderMethodBody.append(setterStmt);
				dtoBuilderMethodBody.append("\n");
			}			
			
			if("Optional".equals(type))
			{
				instanceName = "Optional.of(" + instanceName +")" ; //Optional.of(_CustomerObj)
			}
			
			dtoBuilderMethodBody.append("return "+instanceName +";");
			
			dtoBuilderMethodBody.append("}");
			dtoBuilderMethodBody.append("\n");
			
			
			//sc = new Scanner(new File("C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\templates\\DtoFactoryTempate.txt"));
			//sc = new Scanner(new File(applicationBaseFilePath + "\\src\\main\\java" + "\\automation\\templates\\DtoFactoryTempate.txt")); // C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\main\\java\automation\templates\DtoFactoryTempate.txt
			//automationTemplatesDir = applicationBaseFilePath + "\\src\\main\\java" + "\\automation\\templates";  // C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\main\\java\automation\templates
			Scanner sc = new Scanner(new File(automationTemplatesPath + "\\DtoFactoryBodyTemplate.txt"));
			
			String line ="";
			
			/*
			while(sc.hasNextLine())
			{
				
			 line = sc.nextLine();
			 line =  line.trim();
			 
			 if(line.contains("}//class ends"))
				 {
					 line = line.replace("}//class ends", dtoBuilderMethodBody.toString() + "\n}//class ends");
					 dtoFactoryBldr.append(line);
					 dtoFactoryBldr.append("\n");
				 }
			}
			*/
			
			
			while(sc.hasNextLine())
			{
				
			 line = sc.nextLine();
			 line =  line.trim();
			 
			 if(line.contains("}//class ends"))
				 {
					 line = line.replace("}//class ends", dtoBuilderMethodBody.toString());
					 dtoFactoryBldr.append(line);
					 dtoFactoryBldr.append("\n");
				 }
			 else
			 {
				 dtoFactoryBldr.append(line);
				 dtoFactoryBldr.append("\n");
			 }
			}// end while			
			
			sc.close();
			
			dtoFactoryCreatedForTypeBldr.append(dtoPackagePath);
			dtoFactoryCreatedForTypeBldr.append(",");
		}

	 private String getValueForType(Field fld, String fldNameFormatted) 
	 {

		 String result = "\"test" + fldNameFormatted + "\"";
		 
		 String fldStr = fld.toString().toLowerCase();
		 
		   if(fldStr.contains("boolean"))
			{
				result =  "true";
			}
			else if(fldStr.contains("integer"))
			{
				result =  "1";
			}
			else if(fldStr.contains("float"))
			{
				result =  "1";
			}
			else if(fldStr.contains("long"))
			{
				result =  "1";
			}
			else if(fldStr.contains("byte"))
			{
				result =  "1";
			}
			else if(fldStr.contains("short"))
			{
				result =  "1";
			}
			else if(fldStr.contains("char"))
			{
				result =  "A";
			}
			else if(fldStr.contains("timestamp"))
			{
				result =  "new java.sql.Timestamp(1l)";
			}

			return result;
		}

	private static String getUserInput(String message) 
	    {
		  message = message.trim();	  
	      String result = "";	
	      
	      if(cacheUserInput.containsKey(message))
	      {
	    	  result = cacheUserInput.get(message);
	    	  //
	    	  System.out.println("\nUtilizing your earlier provided information : " );
	    	  System.out.println("  Question                : " + message);
	    	  System.out.println("  Your earlier response   : " + result );
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
	    
		private void getAllMockBeanDependencies(String beanName) throws Exception 
		{
			List<String> appNamesList = AutoConfigurationPackages.get(app.getAutowireCapableBeanFactory()); // com.banknext.customer.mgt		
			String mainApplicationClassPath = applicationBaseFilePath +  "src\\main\\java\\"   +appNamesList.get(0); //C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\main\java\com.banknext.customer.mgt
			mainApplicationClassPath = mainApplicationClassPath.replaceAll("\\.", "\\\\") + "\\"; //C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\main\java\com\banknext\customer\mgt			
			
			mainApplicationClassName  = findFile("Application", mainApplicationClassPath); //CustomerMgtApplication.java
			mainApplicationClassName = mainApplicationClassName.substring(0, mainApplicationClassName.indexOf(".")); // CustomerMgtApplication
			mainAppClaz = Class.forName(appNamesList.get(0)+ "." + mainApplicationClassName); // com.banknext.customer.mgt.CustomerMgtApplication
			
    	    ConfigurableListableBeanFactory beanFactory = app.getBeanFactory();
    	    String[] dependencies = beanFactory.getDependenciesForBean(beanName);
    	    metaDataCollect.setMockDependencies(Arrays.asList(dependencies)); // [accountRepository, loanRepository, customerRepository]	    
		}
		
		
		private void getAllDeclaredFields() throws Exception 
	    {
			// targetClassNameFullyQualified = "com.banknext.customer.mgt.service.CustomerServiceImpl"
			//String targetClass = "CustomerServiceImpl";
	    	//Field[] allFields = CustomerServiceImpl.class.getDeclaredFields();
			
			//Class claz = Class.forName("com.banknext.customer.mgt.service.CustomerServiceImpl");
			targetClaz = Class.forName(targetClassNameFullyQualified);
			Field[] allFields = targetClaz.getDeclaredFields(); // all private/public attributes in the class
			
			Annotation[] b = targetClaz.getDeclaredAnnotations();//@org.springframework.web.bind.annotation.RestController("")   @org.springframework.web.bind.annotation.RequestMapping(path={}, headers={}, method={}, name="", produces={}, params={}, value={"/api/customers"}, consumes={})
			
			
			/*
			Arrays.stream(allFields)
				  .forEach(field -> metaDataDeclaredFieldsMap.put(field.getName(), "INSTANCE-TYPE:"+extractOnlyClassName(field)+".class"));
			*/
			
			Arrays.stream(allFields)
			  	   .forEach(field -> 
					  {
						 String fieldType = field.getType().toString().toLowerCase(); 
					    if(!fieldType.contains("log") && !fieldType.contains("java"))//interface org.apache.commons.logging.Log     java.lang.String
					    {
					       DeclaredFields declaredFields  = new DeclaredFields ();
						   declaredFields.setInstanceType(constructClassName(field));
						   metaDataCollect.getDeclaredFieldsMap().put(field.getName(), declaredFields);
						   metaDataCollect.getAllBeanDependencies()
				   						  .add(field); //all fields declared in Autowired and Constructors  // [custRepo acctRepo loanRepo test2]
					    }
					    else
					    {  
					    	System.out.println("getAllDeclaredFields : skipped " + field.toString() );
					    }
					  });
		   /*	      
			metaDataCollect.getAllBeanDependencies()
						   .addAll(Arrays.asList(allFields)); //all fields declared in Autowired and Constructors  // [custRepo acctRepo loanRepo test2]
			
			*/
		}
		
		private void getDeclaredAnnotationsForClass() throws Exception 
	    {
			targetClaz = Class.forName(targetClassNameFullyQualified);
			
			//@org.springframework.web.bind.annotation.RestController("")   
			//@org.springframework.web.bind.annotation.RequestMapping(path={}, headers={}, method={}, name="", produces={}, params={}, value={"/api/customers"}, consumes={})
			Annotation[] declaredAnnotationsClass = targetClaz.getDeclaredAnnotations();			      
			metaDataCollect.setDeclaredAnnotationsForClass(declaredAnnotationsClass); 
		}
	    
		private void createMockitoWhenThen() throws Exception 
	    {
			Method[] methods = targetClaz.getDeclaredMethods();		
			constructMethodBodyMap();
			
			metaDataCollect.getMethodBodyMap()
			               .forEach((methodNameKey,methodLinesValue) -> 
			               {
			            	   extractLinesTobeStubbed(methodNameKey, methodLinesValue);
			            	   metaDataCollect.setMetaDataDeclaredMethodsMap(metaDataDeclaredMethodsMap);
			            	   metaDataDeclMtd = null;
			               
			               });
			
		}

		//private MetaDataDeclaredMethods extractLinesTobeStubbed(String methodName, String methodLines)
		private void extractLinesTobeStubbed(String methodName, String methodLines)
		{
			mtdLines = methodLines.split(";");
			
			//String test = "-->"+methodName+"<--";
			
			/*
			metaDataDeclMtd = metaDataCollect.getMetaDataDeclaredMethodsMap()																	      					
											 .get(methodName);
			if(metaDataDeclMtd == null)
			{
			 metaDataDeclMtd = new MetaDataDeclaredMethods();
			 //metaDataDeclaredMethodsMap.put(methodName, metaDataDeclMtd);
			}
			*/
			
			MetaDataDeclaredMethods metaDataDeclMtd1 = new MetaDataDeclaredMethods();
			
			metaDataCollect.getAllBeanDependencies()
	    					.stream()
	    					.forEach(field -> getLinesTobeStubbed(methodName, field, mtdLines, metaDataDeclMtd1));
			
			metaDataDeclaredMethodsMap.put(methodName, metaDataDeclMtd1);		
			//System.out.println("test");
			
		}

		
		private void constructMethodBodyMap() throws Exception 
		{	
			mtdSignatureCaptured="";
			int openCurlyBracketCount = 0;
			int closedCurlyBracketCount = 0;
			
			//Scanner sc = new Scanner(new File("C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\main\\java\\com\\banknext\\customer\\mgt\\service\\CustomerServiceImpl.java")); //hardcoded
			sc = new Scanner(new File(applicationBaseFilePath + targetPackageFilePath + targetClassName + targetClassPostfix)); //C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\main\\java\\com\\banknext\\customer\\mgt\\service\\CustomerServiceImpl.java
			
			
			//StringBuilder contentBldr  =  new StringBuilder();
			String line;
			String previousLine = new String();
			//String methodName = "";
			boolean methodStartLineReached = false;
			boolean methodEndLineReached = false;
			Stack<String> methodLinesStk = new Stack<String>();
			boolean methodParsingStarted = false;
			
			
			while(sc.hasNextLine())  // public int findAll(String test2, int num2)
			{
			 
			line = sc.nextLine();
			line =  line.trim();
			

			//methodLinesStk
			if("".equals(line.trim())) // empty lines ignore
			{
				continue; //ignore. don't add to the processing.
			}
			
			
			 if(line.trim().startsWith("@")) // valid Junit eligible mtd cannot start with @
				{
					continue; //ignore. don't add to the processing. @Override
				}
				 	
			if(skipCommentedLine(previousLine))
			{
				//line = sc.nextLine();
				continue;
			}
			
			 
			 //gather all import stmts from target class 
			 
			 if(line.contains("import"))
			 {   //vjNew1
				 importStmtsBldr.append(line+ "\n"); //  import com.banknext.customer.mgt.service.CustomerService;				 
				 String oneLevelUpPckg = line.substring(0, line.lastIndexOf(".") +1) + "*;"; // import com.banknext.customer.mgt.service.*;  
				 importStmtsBldr.append(oneLevelUpPckg+ "\n");
				 /*
				 if(line.contains(previousLine))
				 {
					 
				 }
				 */
			 }
			 
			 
			 
			 //if(line.contains("public") && line.endsWith(";")) //start parsing
			 
			 if(!methodParsingStarted)
			 {
			 if(!line.startsWith("public") && !methodStartLineReached ) // start line of a method will never start without public. Only public method are in scope for Junits	
			 {
				 continue;   //ignore. don't add to the processing
			 }
			 if("".equals(line) || line.endsWith(";") || line.contains("class") || line.startsWith("@")) // start line of a method will never end with ;	
			 {
				 if(!methodStartLineReached)
				 {
				  continue;   //ignore. don't add to the processing
				 }
			 }
			 
			 if(line.contains(targetClassName))
				{
					continue; //ignore. don't add to the processing. Almost always, valid method will not contain same class name
				}
			}
			 
			//-- now we are at the 1st line of a valid method
			     methodParsingStarted = true;
			     mtdSignatureCaptured = ""; //reset
			     
			     
			     
			     if("".equals(line))
			     {
			    	continue; 
			     }
			     
			     if(line.trim().startsWith("@GetMapping") || line.trim().startsWith("@PostMapping") || line.trim().startsWith("@PutMapping") || line.trim().startsWith("@PutMapping") || line.trim().startsWith("@DeleteMapping")  || line.trim().startsWith("@PatchMapping"))
			     {
			    	continue; 
			     }
			     
			     
				 if("".equals(methodName))
				 {
					 methodName = line;
					 methodStartLineReached=true;
					 
					 if("".equals(mtdSignatureCaptured))
					 {
						 mtdSignatureCaptured = captureMethodSignature(line);
						 line = mtdSignatureCaptured;
						 
						 int skipCounter = 0;
						 while(skipCounter < signatureSpreadOverLinesCount - 1)
						 {
							String tempLine =  sc.nextLine(); // mtd signature lines already captured. Hence skip over theat many lines
							//System.out.println("-- "+tempLine);
							 skipCounter++;
						 }
						 
					 }
					 
					 //System.out.println("test");
				 }
				 //line = sc.nextLine().trim();
				 //vjNew2   (@RequestParam(required = false) String title)
				 if(line.trim().contains("@PathVariable") || line.trim().contains("@RequestBody")  || line.trim().contains("@RequestParam")) //public ResponseEntity<Customer> getCustomerById(@PathVariable  ("id1") int     customerId, @PathVariable("id2") int loanId){
			     {
					 String originalLine = line;  // public ResponseEntity<Customer> getCustomerById(@PathVariable("id") int customerId, @PathVariable("id") int loanId){
					 int startIdx  =  line.indexOf("(");
					 int endIdx  =  line.lastIndexOf(")");
					 line  = line.substring(startIdx, endIdx+1); //(@PathVariable  ("id1") int     customerId, @PathVariable("id2") int loanId)
					 String portionToReplace = line.substring(1, line.length() - 1);             // @PathVariable  ("id1") int     customerId, @PathVariable("id2") int loanId
					 
					 while(line.contains("  "))
					 {
						 line = line.replace("  ", " "); // (@PathVariable ("id1") int customerId, @PathVariable("id2") int loanId)   make separation of only 1 space between tokens
					 }
					 
					 String[] paramsArr  = line.split(","); // [ (@PathVariable("id1") int customerId         @PathVariable("id2") int loanId) ]
					 
					 StringBuilder paramsBldr = new StringBuilder();				 
					 for(String param : paramsArr) // (@PathVariable("id1") int customerId
					 {
						 //param = param.replaceAll(")", "").trim(); // (@PathVariable("id1") int customerId
						 param = param.replaceAll("\\)", ""); // (@PathVariable("id1") int customerId
						 param = param.trim();
						 String[] tokenArr =  param.split(" "); // @PathVariable("id1")     int    customerId
						 
						 String dataType =  tokenArr[tokenArr.length -2]; //int 
						 String value = tokenArr[tokenArr.length-1];		// customerId
						 
						 paramsBldr.append(dataType + " " + value); // int customerId        int loanId  
						 paramsBldr.append(","); // int customerId,  int loanId,
					 }
					 
					 
					 String adjustmentLine = paramsBldr.toString();
					 adjustmentLine = adjustmentLine.substring(0, adjustmentLine.length()-1); //remove the last extra comma   int customerId,  int loanId
					 
					 line = originalLine.replace(portionToReplace, adjustmentLine); // // public ResponseEntity<Customer> getCustomerById(int customerId,  int loanId){				 
			     }
				 
				 methodLinesStk.add(line);
				 if(line.contains("{"))
				 {
					 openCurlyBracketCount ++;
				 }
				 
				 if(line.contains("}"))
				 {
					 closedCurlyBracketCount ++;
				 }
				 
				 if(openCurlyBracketCount > 0 && (openCurlyBracketCount - closedCurlyBracketCount == 0))
				 {
					 //methodEndLineReached =  true;
					 
					 
					 blockCommentStartDetected = false;
					 blockCommentEndDetected = false;
					 
					 //methodLinesStk.forEach(mtdLine -> { collectMethodLine(mtdLine, methodName); });
					 methodLinesStk.forEach(mtdLine -> { collectMethodLine(mtdLine, methodLinesStk.get(0)); }); //this is to get rid of extra tokens like @PathVariable
					 methodStartLineReached =false;
					 methodLinesStk.clear();
					 contentBldr = new StringBuilder();
					 methodName = "";
					 openCurlyBracketCount = 0;
					 closedCurlyBracketCount = 0;
					 
				 }
				 
				 //System.out.println("test");
			 
			 
			 
			} //end while
			
			//System.out.println("test");
			
		}
		
		
		private Scanner skipCommentedLines(Scanner sc2, String line) 
		{
			if(line.trim().startsWith("//")) //   //custRepo.save(constructCustomer(entity));
			{
				line  = sc.nextLine();  // skip this commented line
			}
			else if(line.contains("//")) //   public void processfailure(Entity entity) //SAGA to initiate rollback
			{
				line = sc.nextLine();  // skip this commented line // public void processfailure(Entity entity)
			}
			
			return sc2;
		}

		private Scanner skipCommentedCodeBlock_DISCARD(Scanner sc2) 
		{  
			String blockCommentLine = sc2.nextLine();
			while(!blockCommentLine.trim().contains("*/")) //until you reach the end of the block comment
			{
				blockCommentLine = sc2.nextLine(); //keep skipping lines
				System.out.println( "blockCommentLine : "+blockCommentLine);
			}		
			
			System.out.println("reached end of blockComment");
			return sc2;
		}
		
		private int howManyLinesToSkip(Scanner sc2) 
		{  
			int linesToSkip = 1;
			String blockCommentLine = sc2.nextLine();
			
			while(!blockCommentLine.trim().contains("*/")) //until you reach the end of the block comment
			{
				blockCommentLine = sc2.nextLine(); //keep skipping lines
				linesToSkip ++;
			}
			
			return linesToSkip;
		}
		
		private String captureMethodSignature(String mtdStartLine) throws FileNotFoundException 
		{	
			StringBuilder mtdSignatureBldr = new StringBuilder();
			
			if(!mtdStartLine.startsWith("public"))
			{
				signatureSpreadOverLinesCount = 0;
				return mtdSignatureBldr.toString();
			}
			
			signatureSpreadOverLinesCount = 0;
			Scanner sc1 = new Scanner(new File(applicationBaseFilePath + targetPackageFilePath + targetClassName + targetClassPostfix)); //C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\main\\java\\com\\banknext\\customer\\mgt\\service\\CustomerServiceImpl.java
			String line ="";
			
			boolean reachedMtdStart = false;
			while(sc1.hasNextLine())
			{	
			 line = sc1.nextLine();
			 line =  line.trim();
			 
			 if(!mtdStartLine.trim().equals(line) && !reachedMtdStart)
			 {
				 continue;
			 }
				 /*
				 public ResponseEntity<Object> createCustomerWithResponseEntityNoContent(@RequestBody Customer 
				    		customer, @PathVariable("id") int customerId,@PathVariable("loanId") int loanId)
				     {
				 */
			 
			 // now reached start of method signature
			     reachedMtdStart = true;
				 mtdSignatureBldr.append(line);
				 mtdSignatureBldr.append(" ");
				 signatureSpreadOverLinesCount ++;
				 if(line.endsWith("{")) //indicates the end of a standard mtd signature
				 {
					break; 
				 }			 
			 }//end while
			
			sc1.close();
			return mtdSignatureBldr.toString(); // public ResponseEntity<Object> createCustomerWithResponseEntityNoContent(@RequestBody Customer customer, @PathVariable("id") int customerId,@PathVariable("loanId") int loanId) {
		}

		private void collectMethodLine(String methodLine, String methodName) 
		{  
			
			//don't add commented code
			if(methodLine.trim().contains("/*")) //   /*  multi line comment block  custRepo.save(constructCustomer(entity));
			{
				blockCommentStartDetected = true;
				blockCommentEndDetected = false;
				return;
			}
			
			//if(blockCommentStartDetected && !blockCommentEndDetected && !methodLine.trim().contains("*/")) //   until you reach the end of the block comment.  */
			if(blockCommentStartDetected && !blockCommentEndDetected) // && !blockCommentEndDetected && !methodLine.trim().contains("*/")) //   until you reach the end of the block comment.  */
			{
				if(methodLine.trim().contains("*/")) //reached end of commented block
				{
					blockCommentEndDetected= true;	
					blockCommentStartDetected = false;
					return;
				}
				else
				{
					return; // not reached end of commented block. Still in the midst of the commented block lines  
				}
			}
			
			if(methodLine.trim().startsWith("//")) //   //custRepo.save(constructCustomer(entity));
			{
				return;
			}
			else if(methodLine.contains("//")) //   public void processfailure(Entity entity) //SAGA to initiate rollback
			{
				methodLine = methodLine.substring(0, methodLine.indexOf("//")); // public void processfailure(Entity entity)
			}
				
			
			
			contentBldr.append(methodLine + "\n");
			metaDataCollect.getMethodBodyMap().put(methodName, contentBldr.toString());
			//System.out.println("test");
			
		}
		
		
		private boolean skipCommentedLine(String line) 
		{	
			boolean skip = false;
			
			//don't add commented code
			if(line.trim().contains("/*")) //   /*  multi line comment block  custRepo.save(constructCustomer(entity));
			{
				blockCommentStartDetected = true;
				blockCommentEndDetected = false;
				skip = true;
				return skip;
			}
			
			//if(blockCommentStartDetected && !blockCommentEndDetected && !line.trim().contains("*/")) //   until you reach the end of the block comment.  */
			if(blockCommentStartDetected && !blockCommentEndDetected) // && !blockCommentEndDetected && !line.trim().contains("*/")) //   until you reach the end of the block comment.  */
			{
				if(line.trim().contains("*/")) //reached end of commented block
				{
					blockCommentEndDetected= true;	
					blockCommentStartDetected = false;
					skip = true;
					return skip;
				}
				else
				{
					skip = true;
					return skip;
					// not reached end of commented block. Still in the midst of the commented block lines  
				}
			}
			
			if(line.trim().startsWith("//")) //   //custRepo.save(constructCustomer(entity));
			{
				skip = true;
				return skip; 
			}
			else if(line.contains("//")) //   public void processfailure(Entity entity) //SAGA to initiate rollback
			{
				//line = line.substring(0, line.indexOf("//")); // public void processfailure(Entity entity)
			}
			
			return skip;
		}
		
		private void formatStub(MetaDataDeclaredMethods record, String stub)  //   custRepo.save(cust)    custRepo.findAll() 
		{		
			List<String>  whenThenList = record.getWhenThenList(); 
					
			if(whenThenList == null)
			{
				whenThenList =  new ArrayList<String>();
			}
			
			voidCaseScenario = false; 
			String returnType1="";
			String stubRaw = stub;
			//custRepo.save(cust)
			//loanRepo.save(cust)
			stub =  stub.trim();
			
			for(Field f : metaDataCollect.getAllBeanDependencies()) // stub = return custRepo.findAll()
			{
			  String t = f.getName();
			  if(stub.contains(t))
			  {
				  stub = stub.substring(stub.indexOf(f.getName())); // stub = custRepo.findAll()
				  break;
			  } 
			}		
			//custRepo.findById(customerNum).get()
			int startIdx = stub.indexOf("("); 						//(cust   (
			int endIdx = stub.lastIndexOf(")");				    	// cust)  )
			//String extractPortion = stub.substring(startIdx+1, endIdx+1); //cust  ()   cust, loan
			String extractPortion = stub.substring(startIdx+1,endIdx);     //cust   ""   customerNum).get(
			String extractPortion2 = stub.substring(startIdx,endIdx);      //(cust)   ""   (customerNum).get(
			if("".equals(extractPortion.trim()))  //findAll()
			{
				stubMtdNumOfInputParamsCount = 0;	
			}
			else if(!extractPortion.contains(",")) //deleteById(2)
			{
				stubMtdNumOfInputParamsCount = 1;	
			}
			else if(extractPortion.contains(","))
			{
				String[] stubMtdNumOfInputParams =  extractPortion.split(","); //findData(cust, num1, num2)		
				stubMtdNumOfInputParamsCount =  stubMtdNumOfInputParams.length;
			}
			//int stubMtdNumOfInputParamsCount =  Arrays.asList(extractPortion).spl.size();
			
//			int stubMtdNumOfInputParamsCount =  Arrays.asList(extractPortion.split(",")).size();
			String extractArgsPortion = stub.substring(startIdx,endIdx+1); //(cust) ""  (customerNum).get()
			//stub = stub.replace(extractPortion, "Mockito.any()");
			
			
			String formattedStub = "";   //if args NOT present custRepo.findAll() 
			//(customerNum).get()
			if(extractArgsPortion !=null && "()".equals(extractArgsPortion.strip()))
			{
				//formattedStub = stub.replace(extractArgsPortion, "()");	//if args present custRepo.findAll()
				formattedStub = stub;	//if args present custRepo.findAll()
			}		
			else if(extractArgsPortion !=null && !"".equals(extractArgsPortion))
			{
				String numOfArgs ="";
				String argReplacement = "";
				String[] extractArgsPortionArr = {};
//				int finalNumOfInputArgs = 0;
				//(customerNum, loanNum)   (customerNum, construct(customerNum, loanNum))
				
				extractArgsPortionArr = extractPortion2.split("\\(");
				if(extractArgsPortionArr.length > 2) //complex structure detected   (customerNum, construct(customerNum, loanNum))
				{	//vjNew3	
					numOfArgs =  getUserInput("5- "+ stubRaw + "\n\n  What is the NUMBER of INPUT args in the EXTERNAL class method invocation above?  If empty then enter 0" );	
				}
				else //simple structure   (customerNum, loanNum) 
				{
					 extractArgsPortionArr = extractArgsPortion.split(",");
				}
				
				if(!"".equals(numOfArgs))
				{
				 finalNumOfInputArgs =  Integer.parseInt(numOfArgs); //Numformat exception possible
				}
				else
				{	
					finalNumOfInputArgs = extractArgsPortionArr.length; 
				}
				
				int k = 0;
				 while(k < finalNumOfInputArgs)
				 {   
					 //when(customerKafkaTemplate.send(Mockito.any()), (Mockito.any())).thenReturn(responseFuture);  -> wrong
					 //when(customerKafkaTemplate.send(Mockito.any(), Mockito.any())).thenReturn(responseFuture);    -> correct
					 //argReplacement = argReplacement + "(Mockito.any())" + ", " ;
					 argReplacement = argReplacement + "Mockito.any()" + ", " ; // should really be dtoFactory.getCustomer(), dtoFactory.getLoan() 
					 k++;
				 }
				
				 if(argReplacement.contains(","))
				 {
					 argReplacement = argReplacement.substring(0, argReplacement.lastIndexOf(",")); //adjustment - remove extra last comma   Mockito.any(), Mockito.any()	 
				 }
				 argReplacement = "(" + argReplacement +")"; // (Mockito.any(), Mockito.any())
				 
				//formattedStub = stub.replace(extractArgsPortion, "(Mockito.any())");	//if args present custRepo.save(cust)
				 formattedStub = stub.replace(extractArgsPortion, argReplacement);	//if args present custRepo.save(cust)
				 
			}
			
			// custRepo.save(cust)
			String dependencyBeanName = stub.substring(0, stub.indexOf(".")); // custRepo
			String methodInvoked = stub.replace(dependencyBeanName, "");      // .save(cust)
			methodInvoked = methodInvoked.substring(1, methodInvoked.indexOf("("));  // save
			
			Field field = metaDataCollect.getAllBeanDependencies()
						   .stream()
						   .filter(f -> dependencyBeanName.equals(f.getName()))
						   .findFirst()
						   .get();
			
			String packagePath = field.getType().toString(); //interface com.banknext.customer.mgt.repo.CustomerRepository
			packagePath = packagePath.replace("interface", "").replace("class", "").trim(); //com.banknext.customer.mgt.repo.CustomerRepository

			Class cls;
			try 
			{
				cls = Class.forName(packagePath);
				Method[] methods =  cls.getMethods(); //cannot use declaredMethods because repository mtds come from the super classes
				
				StringBuilder methodInvokedBldr = new StringBuilder(methodInvoked);
				
				
				
				
				
				Method mtd = Arrays.asList(methods)
						  .stream()
						  .filter(m -> m.getName().equals(methodInvokedBldr.toString())) //match args list as well. this is reqd to granularly flter from mtds like public  updateCustomer(int id, Customer customer)  and   updateCustomer(Customer customer)
						  .findFirst()
						  .get();
				/*  try the below : //match args list as well. this is reqd to granularly flter from mtds like public  updateCustomer(int id, Customer customer)  and   updateCustomer(Customer customer)
				Method mtd = Arrays.asList(methods)
								  .stream() 
								  .filter(m -> isMethodMatchFound(methodInvokedBldr, m, stubRaw, finalNumOfInputArgs, methods))//match args list as well. this is reqd to granularly flter from mtds like public  updateCustomer(int id, Customer customer)  and   updateCustomer(Customer customer)
								  .findFirst()
								  .get();			
				mtd = exactMtdMatchFound;
				*/
				
				
				
				// create stb args string 
				Class<?>[] stubArgsAllParamsType = mtd.getParameterTypes(); // [ com.banknext.customer.mgt.repo.model.Customer	,  com.banknext.customer.mgt.repo.model.Loan  , int ]
				//generate str like this dtoFactory.getCustomer(), dtoFactory.getLoan()
				//formattedStub = formattedStub.replace("Mockito.any()", "dtoFactory.getCustomer()"); // may be this is not really reqd. Ony stub-action needs this refinement.
				formattedStub = getStubMockitoInputType(formattedStub, "Mockito.any()", stubArgsAllParamsType); // may be this is not really reqd. Ony stub-action needs this refinement.
				
				//hardcoded for now
				
				/*
				List<String> mtdReturnTypeList = Arrays.asList(methods)
													  .stream()
													  .filter(m -> m.getName().equals(methodInvokedBldr.toString())) //match args list as well
													  .map(m -> m.getReturnType().toString())
													  .toList();
				*/
				/*
				List<Method> methodsByInputParamCountList = Arrays.asList(methods)
													  .stream()
													  .filter(m -> m.getName().equals(methodInvokedBldr.toString())) //findAll
													  //.filter(m -> m.getParameters().length == 0)
													  .filter(m -> m.getParameterCount() == stubMtdNumOfInputParamsCount)
													  .filter(m -> m.toString().contains("abstract"))
													  //.map(m -> m.getReturnType().toString())
													  .toList();
				*/
				List<String> stubMtdReturnTypeList = Arrays.asList(methods)
																	  .stream()
																	  .filter(m -> m.getName().equals(methodInvokedBldr.toString())) //findAll
																	  .filter(m -> m.getParameterCount() == stubMtdNumOfInputParamsCount)
																	  .filter(m -> m.toString().contains("abstract")) //required to get single record for specific scenarios
																	  .map(m -> m.getReturnType().toString())
																	  .toList();			
				
				
				if(stubMtdReturnTypeList ==null || stubMtdReturnTypeList.isEmpty())
				{
					stubMtdReturnTypeList = Arrays.asList(methods)
							  .stream()
							  .filter(m -> m.getName().equals(methodInvokedBldr.toString())) //findAll
							  .filter(m -> m.getParameterCount() == stubMtdNumOfInputParamsCount)
							  //.filter(m -> m.toString().contains("abstract")) // needs to be removed for cases like   custTxnPub.publish(entityDTO);
							  .map(m -> m.getReturnType().toString())
							  .toList();		
				}
				
				//if still unable to figure out the return type  then ask the user
				if(stubMtdReturnTypeList ==null || stubMtdReturnTypeList.isEmpty())
				{   //vjNew3
					String response = getUserInput("4A- "+ stub + "\n\n  What is the RETURN TYPE of the EXTERNAL class method invocation?" );
					stubMtdReturnTypeList = new ArrayList<String>();
					stubMtdReturnTypeList.add(response);
				}			//vjNew2
				else if( stubMtdReturnTypeList.size() > 0 && stubMtdReturnTypeList.get(0).contains("Optional"))
				{   //vjNew3
					//String response = getUserInput("4B- This stub has an RETURN type of Optional<PARAMETERZED> - "+  stub+ "\n\n  What is the PARAMETERIZED type inside this Optional? \n  Please provide the fully qualified name eg. com.banknext.customer.mgt.repo.model.Customer ");
					String response = getUserInput("4B- " + stub +  "\n\n  This stub has an RETURN type of Optional<PARAMETERZED>. \n  What is the PARAMETERIZED type inside this Optional? \n  Please provide the fully qualified name eg. com.banknext.customer.mgt.repo.model.Customer ");
					response =  stubMtdReturnTypeList.get(0)                 // class java.util.Optional
														 .replace("class", "")
														 .trim()            // java.util.Optional
														 .concat("<")
														 .concat(response) // com.banknext.customer.mgt.repo.model.Customer
														 .concat(">");      // java.util.Optional<com.banknext.customer.mgt.repo.model.Customer>
					
					stubMtdReturnTypeList = new ArrayList<String>();
					stubMtdReturnTypeList.add(response);
					
					}
				
				
				/*
				List<Method> methodsList = Arrays.asList(methods)
												  .stream()
												  .filter(m -> m.getName().equals(methodInvokedBldr.toString())) //match args list as well
												  .toList();
				*/
				//public abstract java.util.List org.springframework.data.jpa.repository.JpaRepository.findAll()
				//public default java.util.Iterable org.springframework.data.jpa.repository.JpaRepository.findAll()
				//parameterTypes [interface org.springframework.data.domain.Example, class org.springframework.data.domain.Sort]
				//
				
				//Method mtd =  mtdList.get(0);
				//returnType1 = mtd.getReturnType().toString();// class java.lang.Object  interface java.util.List
				
				//expecting a record always to be present
				returnType1 = stubMtdReturnTypeList.get(0);// class java.lang.Object  interface java.util.List		
				returnType1 =  mapToConcreteClass(returnType1,  stub);
				returnType1 = returnType1.replace("interface", "").replace("class", "").trim(); // java.util.ArrayList   java.util.HashMap
				
				
				//List<Class<?>[]> stubMtdParameterTypeList = Arrays.asList(methods)
				List<Parameter[]> stubMtdParametersList = Arrays.asList(methods)
						  .stream()
						  .filter(m -> m.getName().equals(methodInvokedBldr.toString())) //findAll
						  //.filter(m -> m.getParameters().length == 0)
						  .filter(m -> m.getParameterCount() == stubMtdNumOfInputParamsCount)
						  .filter(m -> m.toString().contains("abstract"))
						  //.map(m -> m.getParameterTypes())
						  .map(m -> m.getParameters())
						  .toList();
				
				
				
				
				//System.out.println("test");	
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			//String metaDatadeclaredField = metaDataDeclaredFieldsMap.get(extractPortion);
					
			//String[] elements = metaDatadeclaredField.split(":");
			/*
			String instanceType="";
			for(String data : elements)
			{
				if("INSTANCE-TYPE".equals(data))
				{
					instanceType = elements[1]; //Customer.class
				}
				
			}
			
			String stubAction ="";  // returnType1 ??
			
			if(unableToDetermineReturnType)
			{
				stubAction =  "new " + instanceType + "()";	
			}
			else
			{  
				stubAction =  "new " + instanceType.substring(0, instanceType.indexOf(".")) + "()";	
			}
			*/
			String stubAction = "";
			String whenThenVoidCase = "";
			if(returnType1.contains(".") || !voidCaseScenario)
			{   //non void case
				createDtoFactory(returnType1);
				String classType =  returnType1.substring(returnType1.lastIndexOf(".")+1); //Customer   int   List
				classType = classType.replace("<", "");
				classType = classType.replace(">", "");
				classType = classType.replace("Optional", ""); //Customer
				String packageOfClassType =  returnType1.substring(0, returnType1.lastIndexOf(".")); //com.banknext.customer.mgt.repo.model
				
				
				
				// case-1 primitive type - if classType is primtv then replacementVals will get populated with legal value
				String replacementVals =  getReplacementValues(classType);			
				if(!"".equals(replacementVals))
				{
					stubAction = replacementVals;
				}
				else if(packageOfClassType.contains(junitPackageScope)) // case-2 custom model POJO - if classType is POJO then dtofactory.getCustomer()
				{
					if(returnType1.contains("Optional"))  // java.util.Optional<com.banknext.customer.mgt.repo.model.Customer>
					{   
						stubAction =  "dtoFactory.getOpt"+ classType + "()"  ; //dtoFactory.getOptCustomer()
					}
					else
					{
						stubAction =  "dtoFactory.get"+ classType + "()"  ; //dtoFactory.getCustomer()
					}
				}
				else //default case    List, Map other standard java class types 
				{   
					//vjNew3
					// to handle specific scenarios   java.util.Optional<Customer>   java.util.Optional<List>
					String response  = getUserInput("4C- " + classType + "\n\n  Is the above class/POJO specific to YOUR application (i.e  NOT internal to Java or Spring ).\n  Enter Y to confirm else N :");				
					
					if("Y".equalsIgnoreCase(response))         // java.util.Optional<Customer>
					{   //vjNew
						if(returnType1.contains("Optional"))
						{   
							stubAction =  "dtoFactory.getOpt"+ classType + "()"  ; //dtoFactory.getOptCustomer()
						}
						else
						{
							stubAction =  "dtoFactory.get"+ classType + "()"  ; //dtoFactory.getCustomer()
						}	
					}
					else   // java.util.Optional<List>
					{
						if(returnType1.contains("Optional"))
						{   
							stubAction =  "dtoFactory.getOpt"+ classType + "_WithStubbedValues()"  ; //dtoFactory.getOptListWithStubbedValues()
						}
						else
						{
							stubAction =  "dtoFactory.get"+ classType + "_WithStubbedValues()"  ; //dtoFactory.getListWithStubbedValues()
						}	
					}
				}
				
				//special handling cases 
				//Kafka calls
				//  when(customerKafkaTemplate.send(Mockito.any(), Mockito.any())).thenReturn(responseFuture);
				if(field.getType().toString().contains("KafkaTemplate") && stub.contains(".send"))
				{// customerKafkaTemplate.send(custTxnTopic, constructEntity(entity, customer))
					stubAction = "responseFuture";
				}
				
				
				
				
				voidCaseScenario = false;
			}
			else
			{   //void case
				
				//when(custRepo.deleteById(Mockito.any())).thenReturn(doNothing().when(custRepo).deleteById(isA(int.class)));
				//doNothing().when(custRepo).deleteById(isA(int.class))
				//stubAction =  "new " + returnType1 + "()";			
				/*
				stubAction = "doNothing().when(custRepo).deleteById(isA(int.class))";
				stubAction = "doNothing()." + whenThenTemplate     ;
				*/
				
				
				repositoryMtdInputArgsDataType = getRepoMtdInputArgsDataType(methodInvoked, extractPortion, "extracted custRepo from stub"); //hardcoded
				
				
				/*
				Scanner sc = new Scanner(System.in);
				System.out.println("What is the return type of "+ stub + "?");//provide list of all model/DTOs
				userResponseReturnType = sc.nextLine();			
				//repositoryMtdInputArgsDataType = userResponseReturnType;
				sc.close();
				*/
				
				/*
				String question = "What is the return type of "+ stub + "?";
				userResponseReturnType  = getUserInput(question);
				
				String targetMtdInvokedArgs = getTargetMtdInvokedArgs(record);
				*/
				
				//whenThenVoidCase = "doNothing().when("+ dependencyBeanName + ")."+ methodInvoked +"(isA(" + targetMtdInvokedArgs +".class));"; //doNothing().when(custRepo).deleteById(isA(int.class))    doNothing().when(custRepo).deleteById(isA(Customer.class, Loan.class, int.class))
				//whenThenVoidCase = "doNothing().when("+ dependencyBeanName + ")."+ methodInvoked +"(isA(" + targetMtdInvokedArgs +"));"; //doNothing().when(custRepo).deleteById(isA(int.class))    doNothing().when(custRepo).deleteById(isA(Customer.class, Loan.class, int.class))
				whenThenVoidCase = "doNothing().when("+ dependencyBeanName + ")."+ methodInvoked +"(isA(" + repositoryMtdInputArgsDataType +".class));"; //doNothing().when(custRepo).deleteById(isA(int.class))    doNothing().when(custRepo).deleteById(isA(Customer.class, Loan.class, int.class))
				
							
				
				
				//whenThenVoidCase = "doNothing().when("+ dependencyBeanName + ")."+ methodInvoked +"(isA(" + userResponseReturnType +".class));"; //doNothing().when(custRepo).deleteById(isA(int.class))

				//move this in to the assertion block logic for maintainability
				//String verifyStubbedVoidInvocation = "verify(custRepo, times(1)).deleteById(2);";
				//record.getAssertionsList().add(verifyStubbedVoidInvocation);
				
				voidCaseScenario = true;
			}
			
			//whenThenList.add("DtoFactory dtoFactory = new DtoFactory();\n");
			
			//STUB-ACTION
			if(voidCaseScenario)  //JPA/Repository void deleteById(..)
			{  
				whenThenList.add(whenThenVoidCase);
			}
			else				 //JPA/Repository List findById(..)
			{
				whenThenList.add(whenThenTemplate.replace("STUB-CONDITION", formattedStub)
						 //.replace("STUB-ACTION", "test")
						 //.replace("STUB-ACTION", "Mockito.RETURNS_DEFAULTS")
						 //.replace("STUB-ACTION", "Mockito.RETURNS_DEFAULTS")
						  .replace("STUB-ACTION", stubAction) //new Customer()
						);
			}
				
			
			
			record.setWhenThenStubsList(whenThenList);
			
		}

		
		private boolean isMethodMatchFound(StringBuilder methodInvokedBldr, Method m, String stubRaw, int finalNumOfInputArgs, Method[] methods) 
		{	
			if(m.getName().equals(methodInvokedBldr.toString()))//mtd names don't match
			{
			 return false;	
			}
			
			if(finalNumOfInputArgs != m.getParameterCount())//num of i/p args don't match. Hence mtds not the same
			{
			 return false;	
			}
			
			//at this point mtd names match + num of args also match. Now if all the arg types also match then the mtd is uniquely matched
			//scenario - more than one mtd with same name  updateCustomer + same numOfArgs but atleast 1type differs
			int countMtdWithSameName = 0;
			Method[] methodsPotentialMatched= {};
			
			for(Method mtd : methods)
			{
				if(m.getName().equals(mtd.getName()))
				{
					methodsPotentialMatched[countMtdWithSameName] = mtd;
					countMtdWithSameName ++;
				}
			}
			
			if(countMtdWithSameName == 1 )
			{
				return true; // match found
			}
			
			String mtd1ParamsListInSeq =""; 
			//countMtdWithSameName > 1
			for(Parameter ipParam : m.getParameters()) //mtds have the same name and same numOfArgs. Is the args types in sequence also same?  
			{
				mtd1ParamsListInSeq = mtd1ParamsListInSeq + ipParam;
			}
			
			for(Method potentialMatchMtd : methodsPotentialMatched)
			{
				for(Parameter ipParam : potentialMatchMtd.getParameters()) //mtds have the same name and same numOfArgs. Is the args types in sequence also same?  
				{
					mtd1ParamsListInSeq = mtd1ParamsListInSeq + ipParam;
				}	
				if(mtd1ParamsListInSeq.equals(mtd1ParamsListInSeq))
				{
					//exact mtd match found
					exactMtdMatchFound =  potentialMatchMtd;
				}
			}
			
			return false;
		}
		
		
		private void createDtoFactory(Class<?>[] stubArgsAllParamsType) 
		{
			createDtoFactory(stubArgsAllParamsType[0].toString()); //hardcoded
		}

		private String getTargetMtdInvokedArgs(MetaDataDeclaredMethods record) 
		{
			targetMtdInvokedArgsBldr= new StringBuilder();
			
			String args = record.getTargetMethodInvocation(); //customerserviceimpl.createCustomer( new Customer(), new Loan(), int ) ;
			args = args.substring(args.indexOf("(") +1 , args.lastIndexOf(")")); // new Customer(), new Loan(), int
			args = args.replace("new", "").replace("(", "").replace(")", "").trim(); // Customer, Loan, int
			String[] argsArr = args.split(","); // [Customer  Loan   int]
			
			Arrays.asList(argsArr)
				  .stream()
				  .map(d -> targetMtdInvokedArgsBldr.append(d.concat(".class, "))) //Customer.class,  Loan.class,   int.class,
				  //.close();
				  //.count();
				  .toList();
			
			//targetMtdInvokedArgsBldr = targetMtdInvokedArgsBldr.replace(targetMtdInvokedArgsBldr.lastIndexOf(","), targetMtdInvokedArgsBldr.lastIndexOf(","), "");  //Customer.class,  Loan.class,   int.class
			String targetMtdInvokedArgs = targetMtdInvokedArgsBldr.toString();
			int idxLastChar =  targetMtdInvokedArgs.lastIndexOf(",");
			targetMtdInvokedArgs = targetMtdInvokedArgs.replace(targetMtdInvokedArgs.substring(idxLastChar), ""); //Customer.class,  Loan.class,   int.class
			
			
			//		replace(targetMtdInvokedArgs.lastIndexOf(","), targetMtdInvokedArgsBldr.lastIndexOf(","), "");  //Customer.class,  Loan.class,   int.class
			
			return targetMtdInvokedArgs;
		}

		private String getRepoMtdInputArgsDataType(String methodInvoked, String argsList, String string) //2nd param should be of type custRepo of the Customer POJO and not String
		{   
		
			/*
			for @Repository methods that are inside JPA - to determine the datatype of input args use the mtd naming convention
			eg.  deleteById(..)  Id attribute in the Customer Entity  Mongo @Document and is marked as Integer. Hence arg type for this JPA mtd is also Integer
			eg.  findById(..)    same as above
			eg.  findByNameAndEnabled(..)  "Name" attribute Customer Entity  Mongo @Document and is marked as String and "Enabled" is boolean. Hence arg type for this JPA mtd is String, Boolean
			
			for custom written mtds inside the Customer repo, you will get input args datatype directly from the mtd fullSignature() of mtd.declaredMethods   
			*/
			
			/*
			//refine further to make it dynamic
			String result ="";
			if(methodInvoked.contains("deleteById"))
			{
				result = "int";
			}
			else if(methodInvoked.contains("delete"))
			{
				result = "Customer"; //hardcoded
			}
			else if(methodInvoked.contains("deleteByName"))
			{
				result = "String";
			}
			if(methodInvoked.contains("findByNameAndEnabled"))
			{
				result = "String, Boolean";
			}
			
			
			return result ;
			*/
			
			
			
			
			//for now simply ask the user the expected input arg type
			String result ="";
			String[] args = argsList.split(",");
			
			for(String arg : args)
			{   //vjNew3
				String question = "3- " + methodInvoked +" : "+ arg  + "\n\n  What is the DATATYPE of the INPUT arg in the above External class method invocation? If EMPTY, enter void";
				result  = result + getUserInput(question) + ", "; 
			}
			
			result = result.substring(0, result.lastIndexOf(","));//adjust remove the extra last comma
			
			return result;
		}

		private String mapToConcreteClass(String returnType, String mtdInvoked) 
		{
			String result = returnType;			
			voidCaseScenario = false;
			
			if(returnType !=null && "void".equals(returnType))
			{	
				voidCaseScenario = true;
			}
			
			
			if(!voidCaseScenario)
			{
				result = handleNonVoidCaseReturnType(returnType, mtdInvoked, result);
			}
			
			return result;
		}

		private String handleNonVoidCaseReturnType(String returnType, String mtdInvoked, String result) {
			if(returnType.contains("Object")) //java.lang.Object
			{
				/*
				if mtd is from repo/JPA and returnType is java.lang.Object then most likely concrete returntype will be Customer from below 
				@Repository
				public interface CustomerRepository extends JpaRepository<Customer, Integer>
				
				OR
				simply just ask the user
				
				*/
			  	
				/*
			    Scanner sc2 = new Scanner(System.in);
				System.out.println("What is the return type of "+ mtdInvoked + "?");//provide list of all model/DTOs
				//userResponseReturnType = sc2.nextLine();
				userResponseReturnType = sc2.nextLine();
				sc2.close();
				
				//userResponseReturnType = "Customer"; //hardcoded
				result =  userResponseReturnType; // Customer Loan
				*/
				
				//String question = "1- What is the RETURN TYPE of the method : "+ mtdInvoked + "?";
				result = getUserInput("1- " + mtdInvoked + "\n\n  What is the RETURN TYPE of the above method? \n  Please provide the fully qualified name eg. com.banknext.customer.mgt.repo.model.Customer");
				
			}
			/*
			else if(!returnType.contains("interface")) //   int   java.lang.String  - needs handling
			{
				
				result =  returnType;
			}
			*/
			/*
			else if(returnType.contains("List")) //interface java.util.List
			{
				result =  returnType.replace("List", "ArrayList");
			}
			else if(returnType.contains("Map")) //interface java.util.Map
			{
				result =  returnType.replace("Map", "HashMap");
			}
			*/
			// java.util.Optional<com.banknext.customer.mgt.repo.model.Customer>
			return result; // if nothing matches then type is class:  int   java.lang.String
		}

		private MetaDataDeclaredMethods getLinesTobeStubbed(String methodName, Field field, String[] mtdLines, MetaDataDeclaredMethods metaDataDeclMtd ) 
		{	//public List<Customer> findAll() public List<Customer> findAll()
			
			/*
			metaDataDeclMtd = metaDataCollect.getMetaDataDeclaredMethodsMap()																	      					
											.get(methodName);
			if(metaDataDeclMtd == null)
		      {
		    	  metaDataDeclMtd = new MetaDataDeclaredMethods();
		    	  metaDataDeclaredMethodsMap.put(methodName, metaDataDeclMtd);
		      }
			*/
			Arrays.asList(mtdLines)
			      .stream()
			      .filter(line -> line.contains(field.getName()+"."))
			      //.map(line -> metaDataDeclaredMethodsMap.get(methodName).getStubEligibleLine().add(line))
			      .map(line -> metaDataDeclMtd.getStubEligibleLine().add(line))		      
			      //.map(line -> metaDataDeclaredMethodsMap.get(methodName).getStubEligibleLine().add(line))
			      .toList();
			      
			//System.out.println("test : "+ metaDataDeclaredMethodsMap.get(methodName).getStubEligibleLine());
			//System.out.println("test");
			return metaDataDeclMtd;
		}
		
		private String constructClassName(Field field) 
	    {
			return extractOnlyClassName(field) + ".class"; //Customer.class
	    }
		
		private String extractOnlyClassName(Field field) 
	    {
			String fullClassName  = field.getType().toString(); // private com.banknext.customer.mgt.repo.model.Customer
	        int startIdx = fullClassName.lastIndexOf(".");
	        
			return fullClassName.substring(startIdx+1); //Customer
	    }
		


		/*
	    private void getAppMainClassName() 
	    {
	        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext( SpringBootApplication.class )) {
	            Map<String,Object> beans = applicationContext.getBeansWithAnnotation(SpringBootApplication.class);
	            System.out.println("-----beans : "+ beans.size());
	        }
	    }
		*/

		
		
		
		
		
		
		
		private void autoGenJunitMockitoTest() throws Exception 
	    {
		 /* 
		  1- Read the template file
		  2- user i/p 
		        class name     full     				com.banknext.customer.mgt.service.CustomerServiceImpl
		        method name w/ full signature           public void createCustomer(Customer cust)
		  3- generate @MockBeans code
		  4- generate @Autowired code
		  5- generate junit method code
		  6- create new file and add this generated content       
		  */
			
			
			
			
			//extractedPackagePath = targetClassNameFullyQualified.substring(0, targetClassNameFullyQualified.lastIndexOf("."));
			//System.out.println("extractedPackagePath  computed : "+extractedPackagePath);
			//extractedPackagePath = "com.banknext.customer.mgt.service" ; //for now hardcode. All generated test classes can be kept in same location/package
			extractedPackagePath = junitPackageScope + "." +"automation.junit" ; //com.banknext.customer.mgt.automation.junit
			//extractedPackagePath = "com.banknext.automation.junit" ; //for simplicity keep this static
			
			extractedTargetClassName = targetClassNameFullyQualified.substring(targetClassNameFullyQualified.lastIndexOf(".")+1);
			String postFixClassName = "Test";
			
			/*
			String methodJunitStub = "@Test \n"
					+ "	public void createCustomerTest() \n"
					+ "	{\n"
					+ "    	when(custRepo.save(new Customer())).thenReturn(new Customer());    	\n"
					+ "    	custSvc.createCustomer(new Customer()); \n"
					+ "    	verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class)); \n"
					+ "    	"
					+ "	}\n";
			*/
			
			
			//sc = new Scanner(new File("C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\templates\\JunitMockitoClassTempate.txt"));
			sc = new Scanner(new File(automationTemplatesPath + "\\JunitMockitoClassTempate.txt"));			
			
			StringBuilder contentBldr  =  new StringBuilder();
			
			while(sc.hasNextLine())
			{
			 String line = sc.nextLine();
			 
			 if(line.contains("PLCHOLDER-PACKAGE-PATH"))
			 {
				 line =  line.replace("PLCHOLDER-PACKAGE-PATH", extractedPackagePath);
			 }
			 else if(line.contains("PLCHOLDER-IMPORT-STMTS"))
			 {
				 importStmtsBldr.append("import " + targetClassNameFullyQualified + ";");
				 
				 
				 //vjNew2
				 /*
				 String a = metaDataCollect.getAllBeanDependencies().get(0).getName().toLowerCase();
				 String b = metaDataCollect.getDeclaredFieldsMap().get("").getInstanceType();
				 metaDataCollect.getAllBeanDependencies().get(0).getName().toLowerCase().contains("kafkatemplate");
				 metaDataCollect.getDeclaredFieldsMap().get("").getInstanceType().toLowerCase().contains("kafka");
				 */
				 
				 //check if Kafka dependency reqd
				 Field kafkaDep = metaDataCollect.getAllBeanDependencies()
				 				.stream()
				 				.filter(d -> d.getName().toLowerCase().contains("kafkatemplate"))
				 				.findFirst()				 				
				 				//.get()
				 				.orElse(null);
				 
				 //if(kafkaDep !=null || !"".equals(kafkaDep.toString()))
				 if(kafkaDep !=null)
				 {
					//add corresponding Kafka related imports as well - may or may not be needed in final Generated Junit class								
					importStmtsBldr.append("import org.springframework.util.concurrent.ListenableFuture;"+ "\n");
					importStmtsBldr.append("import org.springframework.kafka.support.SendResult;"+ "\n");						 	 
					importStmtsBldr.append("import org.springframework.kafka.KafkaException;"+ "\n");
				 }
				 
				
				 line =  line.replace("PLCHOLDER-IMPORT-STMTS", importStmtsBldr.toString());				 
			 }
			 
			 else if(line.contains("PLCHOLDER-CLASS-NAME"))
			 {
				 line =  line.replace("PLCHOLDER-CLASS-NAME", extractedTargetClassName+postFixClassName);
			 }
			 else if(line.contains("PLCHOLDER-MOCKBEAN-SECTION"))
			 {
				 //line =  line.replace("PLCHOLDER-MOCKBEAN-SECTION", "@MockBean Xyz");
				 line =  line.replace("PLCHOLDER-MOCKBEAN-SECTION", constructMockBeans());			 
			 }
			 else if(line.contains("PLCHOLDER-INJECT-TARGET-CLASS"))
			 {
				 line =  line.replace("PLCHOLDER-INJECT-TARGET-CLASS", "@Autowired \n"+ extractedTargetClassName +" "+ extractedTargetClassName.toLowerCase()  + " ;");
			 }
			 else if(line.contains("PLCHOLDER-TEST-METHOD"))
			 {
				 //line =  line.replace("PLCHOLDER-TEST-METHOD", methodJunitStub);
				 line =  line.replace("PLCHOLDER-TEST-METHOD", constructJunitTestMethods());//iterate thr all records in metaDataCollect.getMetaDataDeclaredMethodsMap() and replace with constructed Junit method string
			 }
			 else if(line.contains("PLCHOLDER-DTOFACTORY-NAME"))
			 {
				 line =  line.replace("PLCHOLDER-DTOFACTORY-NAME", "DtoFactory"+targetClassName); // DtoFactoryCustomerServiceImpl
			 }
			 
			 
			 
			 contentBldr.append(line);
			 contentBldr.append("\n");
			 
			}//end while
			 
			 String finalGenContent = contentBldr.toString();
			 finalGenContent = finalGenContent + "}"; //adjustment - close the class end brace
			
			 
			//copy gen content in to new Junit file
		   //Path path = Paths.get("C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\test\\java\\com\\banknext\\customer\\mgt\\service\\"+extractedTargetClassName+"Test1.java");
			 //Path path = Paths.get("C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\test\\java\\com\\banknext\\customer\\mgt\\service\\"+extractedTargetClassName+"Test.java");
			//Path path = Paths.get("C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\test\\java\\com\\banknext\\customer\\mgt\\service\\"+extractedTargetClassName+"Test.java");
			//Files.writeString(path, finalGenContent, StandardCharsets.UTF_8);
			
			// C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\  com.banknext.customer.mgt.service  CustomerMgtControllerA
			// applicationBaseFilePath extractedPackagePath src/test/java    /automation/junit  extractedTargetClassName  
			//Path path1 = Paths.get("C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\test\\java\\com\\banknext\\customer\\mgt\\service\\automation\\junit\\"+extractedTargetClassName+"Test.java");
			//Path path1 = Paths.get("C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\test\\java\\com\\banknext\\customer\\mgt\\service\\"+extractedTargetClassName+"Test.java");
			
			
			createNewResource(finalGenContent, extractedTargetClassName);			
		}

		private void createNewResource(String finalGenContent, String extractedTargetClassName) throws IOException 
		{
			
			String targetTestClassName = extractedTargetClassName+"Test.java";
			Path newEmptyDirPath2 = null;
			//String path2 = "C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\test\\java\\com\\banknext\\customer\\mgt\\service\\";
			//String path2 = "C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\test\\java\\com\\banknext\\";
			String applicationTestClassPath =  applicationMainClassPath.replace("\\src\\main\\java\\", "\\src\\test\\java\\"); //C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\test\java\com\banknext\customer\mgt
			
			String portionToReplace = applicationBaseFilePath + "src\\test\\java\\";
			String applTestClassPathsubset  = applicationTestClassPath.replace(portionToReplace, ""); // com\banknext\customer\mgt
			//String applTestClassPathsubset  = applicationTestClassPath.replace(applicationTestClassPath + "\\src\\test\\java\\", ""); // com\banknext\customer\mgt
			
			String newEmptyDir=applTestClassPathsubset + "\\automation" + "\\junit";
			String[] folders = newEmptyDir.split("\\\\");  // com\banknext\customer\mgt\service\automation\junit
			
			/*
			 created or verified that below structure is present
			 C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\test\\java\\com\\banknext\\customer\\mgt\\service\\automation\\junit\\";
			 */
			//vjNew2
			/*
			for(String folder : folders)				
			{	
				try
				{	
					newEmptyDir = newEmptyDir + "\\" + folder + "\\";    // com  banknext customer mgt  service  automation  junit
					Path newEmptyDirPath = Paths.get(newEmptyDir);
					System.out.println(folder +" dir create starts "+newEmptyDirPath );
					Files.createDirectory(newEmptyDirPath); //create new dir. if already present then swallow error
					System.out.println(folder + "  created "+newEmptyDirPath );
					System.out.println(folder +" new destinationPath is "+newEmptyDirPath );
				}
				catch(Exception e)
				{
					newEmptyDir = newEmptyDir.replace(0, 0)
					System.out.println(folder + " already present. Use the same dir \n ");
				}
			}
			*/
			String buildDirPath = portionToReplace;
			for(String folder : folders)				
			{	
				try
				{	
					//newEmptyDir = newEmptyDir + "\\" + folder + "\\";    // com  banknext customer mgt  service  automation  junit
					buildDirPath = buildDirPath + "\\" + folder + "\\"; // C:\Vijay\Java\projects\junit-mockito-mgt\test-projects\spring-boot-h2-database-crud\src\test\java\   com  bezcoder spring
					Path newEmptyDirPath = Paths.get(buildDirPath);
					System.out.println(folder +" dir create starts "+newEmptyDirPath );
					//Files.createDirectory(newEmptyDirPath); //create new dir. if already present then swallow error
					Files.createDirectory(newEmptyDirPath); //create new dir. if already present then swallow error
					
					
					System.out.println(folder + "  created "+newEmptyDirPath );
					System.out.println(folder +" new destinationPath is "+newEmptyDirPath );
				}
				catch(Exception e)
				{
					System.out.println(folder + " already present. Use the same dir \n ");
				}
			}
			
			/*
			String newEmptyDir="";
			try
			{
				newEmptyDir = applicationTestClassPath + "\\automation\\";
				Path newEmptyDirPath = Paths.get(newEmptyDir);
				System.out.println("\n-- dir create starts 1 "+newEmptyDirPath );
				Files.createDirectory(newEmptyDirPath);
				System.out.println("-- dir 1 created "+newEmptyDirPath );
				System.out.println("-- new destinationPath is "+newEmptyDirPath );
			}
			catch(Exception e)
			{
				System.out.println("**** dir 1 already present. Use the same dir \n " + e);
			}
			
			try
			{
				newEmptyDir = newEmptyDir + "\\junit\\";
				newEmptyDirPath2 = Paths.get(newEmptyDir);
				System.out.println("\n-- dir create starts 2 "+newEmptyDirPath2 );
				Files.createDirectory(newEmptyDirPath2);
				System.out.println("\n-- dir 2 created "+newEmptyDirPath2 );
			}
			catch(Exception e)
			{
				System.out.println("**** dir 2 already present. Use the same dir \n" + e);
			}
			*/
			
			//-- at this expecting the /test/.../automation/junit/ dir to be already present/created
			//Path path3 = Paths.get(newEmptyDirPath2 + "\\" +targetTestClassName);
			//Path path3 = Paths.get(applicationTestClassPath.toString() + "\\automation\\junit\\" +targetTestClassName);
			//Path path3 = Paths.get(applicationTestClassPath.toString() + "\\automation\\junit\\" +targetTestClassName);
			String finalAutomationDir = applicationTestClassPath  + "\\automation\\junit\\";
			//Path path3 = Paths.get(applicationTestClassPath  + "\\automation\\junit\\" + targetTestClassName);
			Path path3 = Paths.get(finalAutomationDir + targetTestClassName);			
			Files.deleteIfExists(path3);
			//Files.writeString(path1, finalGenContent, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
			Files.writeString(path3, finalGenContent, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
			
			//copy the DtoFactory.java from earlier saved location to this location
			Path pathTempDtoFctry = Paths.get(applicationMainClassPath + "\\" +"DtoFactory"+targetClassName + targetClassPostfix); // C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\main\java\com\banknext\customer\mgt\DtoFactory.java
			
			/*
			at the end of the file content 
			append }
			This is reqd to remove the compilation error in the dynamically generated DTO file
			*/
			adjustDtoFactoryFileContent(pathTempDtoFctry, " \n} //finish class ");
			
			
			Path pathFinalDtoFctry = Paths.get(finalAutomationDir + "\\" +"DtoFactory"+targetClassName + targetClassPostfix); // C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\test\java\com\banknext\customer\mgt\DtoFactory.java
			Files.copy(pathTempDtoFctry, pathFinalDtoFctry, StandardCopyOption.REPLACE_EXISTING);
			Files.deleteIfExists(pathTempDtoFctry); //cleanup - delete Dtofactory from earlier saved location
		}
		
		//vjNew1
		private void adjustDtoFactoryFileContent(Path pathTempDtoFctry, String lineToAppend) 
		{
			
			 try {
		            FileWriter fileWriter = new FileWriter(pathTempDtoFctry.toString(), true);
		            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		            bufferedWriter.write(lineToAppend);
		            bufferedWriter.newLine();
		            bufferedWriter.close();
		        } catch (IOException e) {
		            System.out.println("An error occurred while writing to the file.");
		            e.printStackTrace();
		        }
		}
		
		private String constructMockBeans() 
		{
			String mockBeanAnnotation = "\n @MockBean \n";
							
			metaDataCollect.getAllBeanDependencies()
							.stream()
							.forEach(bean ->  {
								
								if(bean.getType().toString().contains(junitPackageScope)) // create only for our application beans and not for core Java   com.banknext.customer.mgt.
								//if(!bean.getType().toString().contains("java")) // create only for our custom application beans, springframework KafkaTemplate and not for core Java String
									{
									
									/*
										public class CustomerServiceImpl implements CustomerService
										correct MockBean name is    customerServiceImpl because of the "implements"
									*/
									
									
									//isStereoTypeRestController
									 //mockBeansBuilder.append(mockBeanAnnotation + bean.getType().getSimpleName() + "  " + bean.getName() + " ;") ;								
																	
									if(isStereoTypeController)
									{   //vjNew2
										if(bean.getType().getSimpleName().toLowerCase().contains("repository"))
										{
											mockBeansBuilder.append(mockBeanAnnotation + bean.getType().getSimpleName() + "  " + bean.getName() + " ;") ; //assuming repository Interface will never have "Impl"   interface com.bezkoder.spring.jpa.h2.repository.TutorialRepository
										}
										else
										{
											mockBeansBuilder.append(mockBeanAnnotation + bean.getType().getSimpleName() + "Impl  " + bean.getName() + " ;") ; //assuming fo Controller "Impl" format is usually applied   Typically a Service class	
										}
										
									}
									else
									{
										mockBeansBuilder.append(mockBeanAnnotation + bean.getType().getSimpleName() + "  " + bean.getName() + " ;") ;	
									}
									 metaDataInfoBldr.append("MOCK-BEAN-INSTANCE-NAME:"+bean.getName());
									 metaDataInfoBldr.append(",");
									
									 
									//---- Alternate approaches require addtional mvn dep 
									//--testing
									/*
									Class targetClaz1 = null;
									try {
										targetClaz1 = Class.forName("com.banknext.customer.mgt.service.CustomerService");
									} catch (ClassNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Class[] classes1 = targetClaz1.getDeclaredClasses();
									*/
									
									// 2rd party Maven dep - https://stackoverflow.com/questions/44204851/java-how-to-import-reflections-class
									/*
									Reflections reflections = new Reflections("firstdeveloper.examples.reflections");
									Set<Class<? extends Pet>> classes = reflections.getSubTypesOf(Pet.class);
									*/
									//--testing
									
									
									
									
									
									}
								else if(bean.getType().toString().contains("KafkaTemplate"))
								{
									
									//KafkaTemplate  customerKafkaTemplate  = mock(KafkaTemplate.class);
									String mockKafkaTemplate = "\nKafkaTemplate  " + bean.getName() + " = Mockito.mock(KafkaTemplate.class);";
									mockBeansBuilder.append(mockKafkaTemplate);
									
									/*
									@MockBean
									 SendResult<String, Object> sendResult;
									*/
									String mockClass = "SendResult<String, Object> sendResult;";
									mockBeansBuilder.append(mockBeanAnnotation + mockClass) ;
									
									/*
									 @MockBean
									 ListenableFuture<SendResult<String, Object>> responseFuture;
									 */
									mockClass = "ListenableFuture<SendResult<String, Object>> responseFuture;";
									mockBeansBuilder.append(mockBeanAnnotation + mockClass) ;
								}
								
									
								}
								);
			
			return mockBeansBuilder.toString();
		}
		
		private String constructJunitTestMethods() throws Exception 
		{
			String extractedTargetClassName = targetClassNameFullyQualified.substring(targetClassNameFullyQualified.lastIndexOf(".")+1);
			Class cls = Class.forName(targetClassNameFullyQualified);
			//Method[] methods = cls.getMethods();
			Method[] methods = cls.getDeclaredMethods();
			
			/*
			Arrays.asList(methods)
			      //.forEach(mtd ->  System.out.println("parameters : " +mtd.getParameters() + " returnType : "+ mtd.getReturnType()  ));
					.forEach(mtd ->  gatherMethodMetaData(mtd));
			//.forEach(mtd ->  gatherMethodMetaData_1(mtd));
			*/
			
			/*
			List<Method> mtdsList =  Arrays.asList(methods);		
			//remove methods decared private - cannot Junit private mtds
			List<Method> onlyPublicMtdsList =  new ArrayList<Method>();
			
			mtdsList.stream()
					.filter(m -> m.toString().contains("public"))
					.map(m -> onlyPublicMtdsList.add(m)) //after removing all private declared mtds from processing
					.toList();
			*/
			
			
			
			
			
			
			
			/*
			Scanner sc1 = new Scanner(new File("C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\templates\\JunitMockitoMethodTempate_1.txt"));
			StringBuilder methodContentBldr  =  new StringBuilder();
			*/
			 
			//String assertionStatements = constructAssertionActions();
			constructAssertionActions();
			constructWhenThenStubActions();
			//below actions per method/record in metaDataCollect.getMetaDataDeclaredMethodsMap()
			Collection<MetaDataDeclaredMethods> metaDataDecMtdsColl = metaDataCollect.getMetaDataDeclaredMethodsMap()
		  				 															 .values();
			
			
			
			//remove methods decared private - cannot Junit private mtds
			/*
			metaDataDecMtdsColl.forEach( record -> 
								{ 
								    if(record.getFullSignature().contains("private"))
										{
											metaDataDecMtdsColl.remove(record); //after removing all private declared mtds from processing. retain only public mtds	
										}
								 });
			*/
			
			
			metaDataDecMtdsColl.forEach(record -> {
												try 
												{
													if(record.getFullSignature().contains("public"))
													{  
														assembleJunitComponents(record);
													}
													else
													{
														//don't process private declared mtds from processing. Junits only public mtds
													}												
												} catch (Exception e) 
												{
													System.out.println("discrepancy in assembleJunitComponents - "  + e);
												}
											});
			
			/*
			while(sc1.hasNextLine())
			{
			 String line = sc1.nextLine();
			 
			 if(line.contains("PLCHOLDER-JUNIT-METHOD-NAME"))
			 {
				 line =  line.replace("PLCHOLDER-JUNIT-METHOD-NAME", "targetMethodName");
			 }
			 else if(line.contains("PLCHOLDER-WHEN-THEN-SECTION"))
			 { 
				 line =  line.replace("PLCHOLDER-WHEN-THEN-SECTION", constructWhenThenActions());
			 }
			 else if(line.contains("PLCHOLDER-CALL-METHOD-TO-EXECUTE"))
			 {
				 line =  line.replace("PLCHOLDER-CALL-METHOD-TO-EXECUTE", constructTargetMethodActions());			 
			 }
			 else if(line.contains("PLCHOLDER-ASSERTION-SECTION"))
			 {
				 //line =  line.replace("PLCHOLDER-ASSERTION-SECTION", "assertionString");
				 //line =  line.replace("PLCHOLDER-ASSERTION-SECTION".trim(), constructAssertionActions());
				 line =  line.replace("PLCHOLDER-ASSERTION-SECTION".trim(), assertionStatements);
			 }
			 
			 methodContentBldr.append(line);
			 methodContentBldr.append("\n");
			 
			}//end while
			
			sc1.close();
			 
			String finalGenContent = methodContentBldr.toString();
			*/
			
			StringBuilder constructedJunitMethodsAll = new StringBuilder();
			
			metaDataDecMtdsColl.forEach(record -> constructedJunitMethodsAll.append(record.getConstructedJunitMethod())
																			.append("\n"));			
			return constructedJunitMethodsAll.toString();
		}

		private Object assembleJunitComponents(MetaDataDeclaredMethods record) throws Exception 
		{
			
			//constructAssertionActions();//populate every eligible Junit record with assertion stmts  
			
			//sc = new Scanner(new File("C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\templates\\JunitMockitoMethodTempate_1.txt"));
			sc = new Scanner(new File(automationTemplatesPath+ "\\JunitMockitoMethodTempate_1.txt"));
			
			
			StringBuilder methodContentBldr  =  new StringBuilder();
			
			while(sc.hasNextLine())
			{
			 String line = sc.nextLine();
			 
			 if(line.contains("PLCHOLDER-JUNIT-METHOD-NAME"))
			 {
				 //line =  line.replace("PLCHOLDER-JUNIT-METHOD-NAME", "targetMethodName");
				 //line =  line.replace("PLCHOLDER-JUNIT-METHOD-NAME", record.getFormattedMethodSignature())+"_Test";
				 String junitMethodName = record.getFullSignature(); //public int findAll(String test2, int num2)
				 junitMethodName = junitMethodName.substring(junitMethodName.indexOf("(")); //(String test2, int num2)
				 /*
				 junitMethodName = junitMethodName.replaceAll("(" , "_")
						 						  .replaceAll(")" , "_")
						 						  .replaceAll(" " , "_")
						 						  .replaceAll("." , "_")
						 						  .replaceAll("," , "_")
						 						  .replaceAll("@" , "")
						 						  .replaceAll("\"" , "")
						 						  ;
						 						  */
				 //vjNew2
				 junitMethodName = junitMethodName.replace("(" , "_")
												  .replace(")" , "_")
												  .replace(" " , "_")
												  .replace("." , "_")
												  .replace("," , "_")
												  .replace("@" , "")
												  .replace("\"" , "")
												  .replace("{" , "")
												  //.replace("RequestParam" , "")
												  //.replace("false" , "")
												  //.replace("required" , "") 	// @RequestParam(required = false)
												  .replace("=" , "");
				 
				 junitMethodName = junitMethodName.replaceAll("__" , "_");//adjustment
				 
				 mtdNameCounter = mtdNameCounter + 1;
				 junitMethodName =  record.getMethodName() + junitMethodName + "Test_"+mtdNameCounter; // findAll_String_test2_int_num2_Test_1			 
				 line =  line.replace("PLCHOLDER-JUNIT-METHOD-NAME", junitMethodName);
				 //System.out.println("test");
				 
			 }
			 else if(line.contains("PLCHOLDER-WHEN-THEN-SECTION"))
			 { 
				 //line =  line.replace("PLCHOLDER-WHEN-THEN-SECTION", constructWhenThenActions());
				 
				 List<String> whenThenList = record.getWhenThenStubsList();
				 if(whenThenList == null || whenThenList.isEmpty())
				 {
					 line =  line.replace("PLCHOLDER-WHEN-THEN-SECTION".trim(), "");//nothing to stub	 
				 }
				 else
				 {
					 StringBuilder whenThenListBldr =  new StringBuilder();
					 whenThenList.forEach(stmt -> whenThenListBldr.append(stmt + "\n"));				 
					 line =  line.replace("PLCHOLDER-WHEN-THEN-SECTION".trim(), whenThenListBldr.toString());	 
				 }
			 }
			 else if(line.contains("PLCHOLDER-CALL-METHOD-TO-EXECUTE"))
			 {
				 if(record.getTargetMethodInvocation() !=null && !"".equals(record.getTargetMethodInvocation()))
				 {   //vjNew4  
					 //boolean isBeanDepKafkaTemplate = isInvocationTypeKafka(record);
					 if(isInvocationTypeKafka(record))
					 {
						 System.out.println("isInvocationTypeKafka :  "+ true);//vjNew7
						 line =  line.replace("PLCHOLDER-CALL-METHOD-TO-EXECUTE", constructKafkaMethodInvocationBody(record, line));
					 }
					 else
					 {
						 line =  line.replace("PLCHOLDER-CALL-METHOD-TO-EXECUTE", record.getTargetMethodInvocation());
					 }				 
				 }
				 
			 }
			 else if(line.contains("PLCHOLDER-ASSERTION-SECTION"))
			 {
				 //line =  line.replace("PLCHOLDER-ASSERTION-SECTION", "assertionString");
				 //line =  line.replace("PLCHOLDER-ASSERTION-SECTION".trim(), constructAssertionActions());
				 
				 List<String> assertionsList = record.getAssertionsList();
				 if(assertionsList == null || assertionsList.isEmpty())
				 {
					 line =  line.replace("PLCHOLDER-ASSERTION-SECTION".trim(), "");//nothing to assert	 
				 }
				 /*
				 else
				 {
					 StringBuilder assertBldr =  new StringBuilder();
					 assertionsList.forEach(stmt -> assertBldr.append(stmt + "\n"));				 
					 line =  line.replace("PLCHOLDER-ASSERTION-SECTION".trim(), assertBldr.toString());	 
				 }	
				 */
				 else
				 {				 
					 StringBuilder assertBldr =  new StringBuilder();
					//vjNew4
					 /*
					 List<String> kafkaTemplateInstances = assertionsList.stream()       // verify(customerKafkaTemplate, times(1)).send(ArgumentMatchers.any());
													 			   .map(stmt -> stmt.substring(0, stmt.indexOf(",")).replace("verify(", ""))   // customerKafkaTemplate
													 			   .filter(inst -> "KafkaTemplate.class".equals(metaDataCollect.getDeclaredFieldsMap().get(inst).getInstanceType()))
													 			   .toList();
					 */
					 /*
					 assertionsList.stream()       // verify(customerKafkaTemplate, times(1)).send(ArgumentMatchers.any());
					 			   .map(stmt -> stmt.substring(0, stmt.indexOf(",")).replace("verify(", ""))   // customerKafkaTemplate
					 			   .filter(inst -> !"KafkaTemplate.class".equals(metaDataCollect.getDeclaredFieldsMap().get(inst).getInstanceType())) // for kafkaTemplate.send cases do not add verify becuase Mockito does not work as expected. A try catch kafkaException is utilized to detect kafka send invocations  
					 			   .map(stmt -> assertBldr.append(stmt + "\n"));
					 */
					 /*
					 assertionsList.stream()       // verify(customerKafkaTemplate, times(1)).send(ArgumentMatchers.any());
					 			   //.map(stmt -> stmt.substring(0, stmt.indexOf(",")).replace("verify(", ""))   // customerKafkaTemplate
					 			   //.filter(stmt -> !"KafkaTemplate.class".equals(metaDataCollect.getDeclaredFieldsMap().get(stmt.substring(0, stmt.indexOf(",")).replace("verify(", "")).getInstanceType())) // for kafkaTemplate.send cases do not add verify becuase Mockito does not work as expected. A try catch kafkaException is utilized to detect kafka send invocations  
					 			   .filter(stmt -> isInvocationTypeKafka(stmt)) // for kafkaTemplate.send cases do not add verify becuase Mockito does not work as expected. A try catch kafkaException is utilized to detect kafka send invocations
					 			   .map(stmt -> assertBldr.append(stmt + "\n"))
					 			   .toList()
					 			   ;
					 */
					 assertionsList.forEach(stmt  -> assertBldr.append(stmt));
					 line =  line.replace("PLCHOLDER-ASSERTION-SECTION".trim(), assertBldr.toString());	 
				 }
			 }
			 
			 methodContentBldr.append(line);
			 methodContentBldr.append("\n");
			 
			}//end while
			
			 
			//String finalGenContent = methodContentBldr.toString();
			record.setConstructedJunitMethod(methodContentBldr.toString());
			return null;
		}
		
		private String constructKafkaMethodInvocationBody(MetaDataDeclaredMethods record, String line) 
		{ //assertThat(result).isEqualTo("success");
			String invocationBody = "String result =  \"fail\";\n"
					+ "		try\n"
					+ "		{\n"
					+ "			PLCHOLDER-KAFKA-INVOCATION\n"
					+ "			result = \"success\";  //Scenario: Kafka is reachable during unit tests - kafka send/operations were invoked. \n"		
					+ "		}\n"
					+ "		catch (KafkaException e) \n"
					+ "		{ \n"				
					+ "		  /* "
					+ "        Scenario: Kafka is not reachable during unit tests - "
					+ "            providing regular Kafka connectivity OR embeddedKafka may not always be possible\n"
					+ "		       hence, alternate approach uses this error to ascertain that Kafka was invoked.\n"
					+ "		  */  \n"				
					+ "		  if(e.getCause().toString().contains(\"org.apache.kafka.common.errors.TimeoutException\"))\n"
					+ "		  {\n"
					+ "			  System.out.println(\"Test Pass - Confirmation received that Kafka send was invoked: \"+ e.getCause());\n"
					+ "			result = \"success\";  //kafka send/operations were invoked\n"	
					+ "		  }    \n"
					+ "		}\n"
					+ "		catch (Exception e) \n"
					+ "		{ \n"
					+ "		  System.out.println(\"Test Fail - Error occurred when invoking Kafka : \"+ e.getCause()); //kafka send/operations were (most likely) NOT invoked as expected  \n"
					+ "		}";
			
			invocationBody = invocationBody.replace("PLCHOLDER-KAFKA-INVOCATION", record.getTargetMethodInvocation());		
			return invocationBody;
		}
		
		private boolean isInvocationTypeKafka(String stmt, MetaDataDeclaredMethods record) 
		{
			boolean result = false;
			try
			{
				//result = !"KafkaTemplate.class".equals(metaDataCollect.getDeclaredFieldsMap().get(stmt.substring(0, stmt.indexOf(",")).replace("verify(", "")).getInstanceType());
				result = "KafkaTemplate.class".equals(metaDataCollect.getDeclaredFieldsMap().get(stmt.substring(0, stmt.indexOf(",")).replace("verify(", "")).getInstanceType());
				//System.out.println("isInvocationTypeKafka  "+ result);
			}
			catch(Exception e )
			{
				System.out.println("isInvocationTypeKafka - defaulting to false "+e);
			}
			
			//System.out.println("isInvocationTypeKafka :  "+ result);
			
			record.setTypeKafkaProducer(result);
			return result;
		}

		private boolean isInvocationTypeKafka(MetaDataDeclaredMethods record) 
		{
			 boolean isBeanDepKafkaTemplate = false;
			 String stubInstance ="";
			 long declFldKafkaCount = 0;
			 StringBuilder whenThenListBldr =  new StringBuilder();
			 List<String> whenThenList = record.getWhenThenStubsList();
			 
			 
			 //whenThenList.forEach(stmt -> whenThenListBldr.append(stmt + "\n"));
			//when(customerKafkaTemplate.send(Mockito.any(), Mockito.any())).thenReturn(responseFuture);
			 
			 // is the stubInstance of type Kafka ?
			 for(String whenThen : record.getWhenThenStubsList())
			 {
				 stubInstance = whenThen.substring(0,whenThen.indexOf("."));// when(customerKafkaTemplate
				 stubInstance = stubInstance.replace("when(", ""); // customerKafkaTemplate   custRepo
			
				 try 
				 {
					 if(metaDataCollect.getDeclaredFieldsMap()  // {custRepo=com.banknext.customer.mgt.automation.junit.DeclaredFields@66a8751a, customerKafkaTemplate=com.banknext.customer.mgt.automation.junit.DeclaredFields@4ec27c4c}
				 				.get(stubInstance)
				 				.getInstanceType()
				 				.contentEquals("KafkaTemplate.class"))
					 {
						 isBeanDepKafkaTemplate = true;
						 break;
					 }
				 }
				 catch(Exception e)
				 {
					 isBeanDepKafkaTemplate = false; //default in case of exceptions eg. KafkaConsumer scenario
				 }
			 }		 
			 
			 
			 /*
			 //KafkaTemplate.class
			 //{custRepo=com.banknext.customer.mgt.automation.junit.DeclaredFields@66a8751a, customerKafkaTemplate=com.banknext.customer.mgt.automation.junit.DeclaredFields@4ec27c4c}
			 Map<String, DeclaredFields> declaredFldsMap = metaDataCollect.getDeclaredFieldsMap();
			 declFldKafkaCount  = metaDataCollect.getDeclaredFieldsMap()
										 				.keySet()           //custRepo    customerKafkaTemplate
										 				.stream()
										 				.filter(k -> declaredFldsMap.get(k).getInstanceType().contentEquals("KafkaTemplate.class"))
										 				.count();
			 
			 for(Field beanDep : metaDataCollect.getAllBeanDependencies())
			 {
				 if(beanDep.getType().toString().contains("org.springframework.kafka.core.KafkaTemplate"))
				 {
					 isBeanDepKafkaTemplate = true;
				 }
			 }
			 */
			System.out.println("isBeanDepKafkaTemplate :  "+ isBeanDepKafkaTemplate);
			record.setTypeKafkaProducer(isBeanDepKafkaTemplate);
			return isBeanDepKafkaTemplate;
		}		
		
		private void gatherMethodMetaData_1(Method mtd) 
		{	
			
			if(mtd.getName().contains("lambda$")) //functional programming internal lambda mtds 
			{
				return;
			}
			
			
//			String fullSignature = mtd.toString();
			String methodName = mtd.getName();
			int attrB = mtd.getModifiers();     // represents positions of "public" "static"  "final"
			Class<?> returnTypeFull = mtd.getReturnType(); //return Type full Class name   interface java.util.List   void
			String returnTypeFullStr = returnTypeFull.toString(); // interface java.util.List    void
			Annotation[][] annotationsInArgsList = mtd.getParameterAnnotations(); //@org.springframework.web.bind.annotation.PathVariable(name="", value="id", required=true)]
			MetaDataDeclaredMethods metaDataDclMtds = new MetaDataDeclaredMethods();	
			//String inputParamInstanceName = "";
			//String parameterExtractedClassName = "";
			
			//[@org.springframework.web.bind.annotation.GetMapping(path={}, headers={}, name="", produces={}, params={}, value={"/{id}"}, consumes={})]
			Annotation[] annotations = mtd.getDeclaredAnnotations();
			metaDataDclMtds.setDeclaredAnnotationsForMtd(annotations);
					
			Parameter[] parameters = mtd.getParameters();
			
			int numOfInputParams = parameters.length;
			
			StringBuilder argsInput = new StringBuilder();
			
			if(parameters.length > 0)
			{
				//Parameter param = parameters[0];
				
			Arrays.asList(parameters) //list of input args  com.banknext.customer.mgt.repo.model.Customer customer, int customerId, int loanId
			
				  .forEach(param -> 
				  	{	
						int attr1 = param.getModifiers();       // modifiers for i/p params
						String inputParamInstanceName = param.getName();         // i/p param class instance name   cust
						
						//metaDataInfoBldr.append("INPUT-PARAM-INSTANCE-NAME:"+inputParamInstanceName);
						//metaDataInfoBldr.append(",");
						metaDataDclMtds.setInputParamInstanceName(inputParamInstanceName);
						
						Type attr3 = param.getParameterizedType(); //   class com.banknext.customer.mgt.repo.model.Customer
						String fullName =  attr3.toString();       //   class com.banknext.customer.mgt.repo.model.Customer
						
						Class<? extends Type> classType = param.getParameterizedType().getClass();   // class java.lang.Class
						String classTypeName = param.getParameterizedType().getTypeName(); //full name of i/p param   com.banknext.customer.mgt.repo.model.Customer
						Class<?> ipParamTypeClassNameFull2 = param.getType();		//  class com.banknext.customer.mgt.repo.model.Customer	
						String ipParamTypClassNameFull2Str = ipParamTypeClassNameFull2.toString(); //   class com.banknext.customer.mgt.repo.model.Customer
						
				/*		
						System.out.println("--" + param.getModifiers());
						System.out.println("--" + param.getName());
						System.out.println("--" + param.getParameterizedType());
						System.out.println("--" + param.getType());
				*/
						int startIdx = ipParamTypClassNameFull2Str.lastIndexOf(".");
						String parameterExtractedClassName =  ipParamTypClassNameFull2Str.substring(startIdx+1); //Customer
						
						//argsInput = "new " + parameterExtractedClassName + "()";
						//argsInput.append("new " + parameterExtractedClassName + "() ,");
						ipParamTypClassNameFull2Str = ipParamTypClassNameFull2Str.substring(startIdx+1);
						if(classTypeName.contains("java.") || classTypeName.contains(junitPackageScope))
						{
							//Object type arg eg. String BigDecimal Customer
							argsInput.append(inputArgsMapper(ipParamTypClassNameFull2Str));
							//executeMtdString = getConcreteOfSuper(executeMtdString); // List -> ArrayList
						}
						else
						{  //primitive type arg eg. int
							//argsInput.append(ipParamTypClassNameFull2Str + " ,");
							//vjNew
							String replacementVal  = getReplacementValues(classTypeName);
							if("".equals(replacementVal)) //still cannot determine the type. Neither primitive nor belongs to SB main package nor Java object
							{
	 						  //String response  = getUserInput("11- What is the RETURN TYPE of "+ stub +"\n If Return type is Optional then please specify alongwith full package name and the <PARAMETERIZED> type.\n for eg. java.util.Optional<Customer> ");
								replacementVal = "new " + classTypeName + "());" + " // ***Developer ALERT*** : Please replace the placeholder  " + classTypeName+"()"+ "  with your specific POJO populated with the right test values"; // new com.banknext.txn.Entity()  - most likely outside the component scan packages of SB
								argsInput.append(replacementVal + " ,");
							}
							else
							{
							  argsInput.append(replacementVal + " ,");
							}
						}
						
						//dynamic - no need for "INSTANCE-TYPE:" in the MAP value
						metaDataDeclaredFieldsMap.put(inputParamInstanceName, "INSTANCE-TYPE:"+parameterExtractedClassName+".class");
						
						//System.out.println("test");
						
				  	});
				  
			
				
				
				//dynamic - no need for "INSTANCE-TYPE:" in the MAP value
				//metaDataDeclaredFieldsMap.put(inputParamInstanceName, "INSTANCE-TYPE:"+parameterExtractedClassName+".class");
			}		
			
			metaDataDclMtds.setFullSignature(mtd.toString());// public int com.banknext.customer.mgt.service.CustomerServiceImpl.findAll(java.lang.String,int)
			
			//metaDataInfoBldr.append("METHOD_NAME:"+methodName);
			//metaDataInfoBldr.append(",");
			metaDataDclMtds.setMethodName(methodName);
			
			//metaDataInfoBldr.append("NUMBER-OF-INPUT-PARAMS:"+numOfInputParams);
			//metaDataInfoBldr.append(",");
			metaDataDclMtds.setNumberOfInputParams(numOfInputParams);
			
			//metaDataInfoBldr.append("RETURN-TYPE-AS-RAW:"+returnTypeFull);
			//metaDataInfoBldr.append(",");
			metaDataDclMtds.setReturnTypeAsRaw(returnTypeFull);
			
			//metaDataInfoBldr.append("RETURN-TYPE-AS-STRING:"+returnTypeFullStr);
			//metaDataInfoBldr.append(",");
			metaDataDclMtds.setReturnTypeAsString(returnTypeFullStr);
			
			
			
			String extractedTargetClassName = targetClassNameFullyQualified.substring(targetClassNameFullyQualified.lastIndexOf(".")+1); //class containing this method   :  CustomerServiceImpl
			//String executeCall = extractedTargetClassName.toLowerCase() +"."+mtd.getName() +"( new "+  mtd.getReturnType() +"())" ;
			String argsInputStr ="";
			if(argsInput !=null && !"".equals(argsInput.toString().trim()))
			{
				argsInputStr = argsInput.substring(0, argsInput.lastIndexOf(",")); //adjust the params stmt	
			}
			
			executeMtdString = extractedTargetClassName.toLowerCase() +"."+mtd.getName() +"( "+  argsInputStr +") ;" ; // customerserviceimpl.findAll( new String() , 1 ) ;		
			if(!"void".equals(returnTypeFullStr))
			{   
				//isStereoTypeRestController = isStereoTypeController();
				
				//if(returnTypeFullStr.contains("ResponseEntity"))//Controller detected
				if(isStereoTypeController)	//Controller detected
				{		
					
					//if(annotations[0] == null)
					if( mtd.getDeclaredAnnotations().length == 0)
					{
						return;
					}
					
					
					//executeMtdString = "ResultActions result = mockMvc.perform(get(\"/api/customers/1\"));" ;
					Class<? extends Annotation> annotationType  = annotations[0].annotationType();
					String annotationStr  = annotations[0].toString();//@org.springframework.web.bind.annotation.GetMapping(path={}, headers={}, name="", produces={}, params={}, value={"/{id}"}, consumes={})
					String httpMethodName = resolveHttpMtd(annotationStr);
					String uriPortion = resolveUriPortion(annotationStr);// /{id}
					
					uriPortion = getPathVariableValues(uriPortion); // /api/customers/{id}/loan/{itemId}				
					uriPortion = "\"" + uriPortion +"\""; 
					
					
					//  mockMvc.perform(get("/api/customers/1"));				
					if("post".equals(httpMethodName) || "put".equals(httpMethodName))
					{
						int idxOfRequestBody = 0;
						String typeOfRequestBody="";
						
						/*
						for(int i=0; i< annotationsInArgsList.length * 2 ; i++) // @RequestBody Customer customer, @PathVariable("id") int customerId, @PathVariable("loanId") int loanId
						{
						 //annotationsInArgsList.toString().contains("@RequestBody");
							
							//String annot = annotationsInArgsList[i].toString();
							Annotation[] annotArr1 = annotationsInArgsList[i];
							//Annotation annot1 = annotArr1[0];
							String annot1Str = annotArr1[i].toString();
							
							if(annot1Str.contains("@RequestBody"))
							{
								//idxOfRequestBody = i ;
								 typeOfRequestBody = parameters[i].getParameterizedType().toString(); // class com.banknext.customer.mgt.repo.model.Customer
								 
								 break;  //only 1 @RequestBody can be present in the argsList		
							}
						}
						*/
						
						/*
						Arrays.asList(annotationsInArgsList)
							  .forEach(l ->  System.out.println(l.get(0).toString()) )
							  .co
							  ;
						*/
						
						for(Annotation[] a : annotationsInArgsList)
						{
							//System.out.println("-- " + a[0].toString());
						}
						
						int i =0;
						//annotationsInArgsList  is a 2-dimension matrix/array
						for(Annotation[] annotStr : annotationsInArgsList) // @RequestBody Customer customer, @PathVariable("id") int customerId, @PathVariable("loanId") int loanId
						{
							
							if(annotStr[0].toString().contains("RequestBody"))
							{
								//idxOfRequestBody = i ;
								 typeOfRequestBody = parameters[i].getParameterizedType().toString(); // class com.banknext.customer.mgt.repo.model.Customer							 
								 break;  //only 1 @RequestBody can be present in the argsList		
							}	
							
							i++;
						}
						
						
						createDtoFactory(typeOfRequestBody);
						String reqBodyClassName = typeOfRequestBody.substring(typeOfRequestBody.lastIndexOf(".")+1); //Customer
						String requestBodyStubInvocation = "dtoFactory.get" + reqBodyClassName + "()" ;  //dtoFactory.getCustomer();
						
						
						
						executeMtdString = "ResultActions result = mockMvc.perform("+  httpMethodName + "(" + uriPortion +")" ;
						executeMtdString = executeMtdString + "\n";
						executeMtdString = executeMtdString + ".contentType(MediaType.APPLICATION_JSON)\n";
						//executeMtdString = executeMtdString + ".content(objectMapper.writeValueAsString(employee)));\n";
						executeMtdString = executeMtdString + ".content(objectMapper.writeValueAsString("+requestBodyStubInvocation+")));\n";
						
					}
					else
					{
						executeMtdString = "ResultActions result = mockMvc.perform("+  httpMethodName + "(" + uriPortion +"));" ;
					}
				}
				else
				{
					executeMtdString = extractOnlyTypeClassOfReturn(metaDataDclMtds).replace(".class", "") + " result = " + executeMtdString; //int result  = customerserviceimpl.findAll( new String() , 1 ) ;
				}			
			}
			
			
			metaDataDclMtds.setTargetMethodInvocation(executeMtdString);
			
			String returnType  = metaDataDclMtds.getReturnTypeAsString();
			if(returnType!=null)
			{   
				returnType = returnType.toLowerCase();
				if(!returnType.contains("class") && !returnType.contains("interface") ) // int
				{
					metaDataDclMtds.setReturnTypePrimitive(true);	
				}
			}
			
			//metaDataDeclaredMethodsList.add(metaDataInfoBldr.toString());
			//metaDataDeclaredMethodsMap.put(methodName+"_"+mtdNameCounter++, metaDataInfoBldr.toString());
			
			//public java.lang.String com.banknext.customer.mgt.service.CustomerServiceImpl.findAll(java.lang.String)
			//unqKey should be    public int findAll(String test2, int num2)
			
			//String unqKey = mtdNameCounter++ + "$"+methodName; // 2$findAll
			//metaDataCollect.getMetaDataDeclaredMethodsMap().put(unqKey, metaDataDclMtds);
			metaDataCollect.getMetaDataDeclaredMethodsMap().put(metaDataDclMtds.getFullSignature(), metaDataDclMtds);
			
			//metaDataInfoBldr = new StringBuilder();
		}
/*
		private boolean isStereoTypeController() 
		{ 
			return
			Arrays.asList(metaDataCollect.getDeclaredForAnnotationsClass())
				  .stream()
				  .anyMatch(a -> a.toString().contains("RestController"));		
			
		}
*/
		private String getPathVariableValues(String uriPortion) // /api/customers/{id}/loan/{itemId} 
		{
			//String uriPortionStr = uriPortion;
			//uriPortionStr = uriPortionStr.substring(uriPortionStr.indexOf("{"));
			//String[] elements = uriPortion.split("\\{"); // id}   , itemId}
			String[] elements = uriPortion.split("/"); // /api  customers  {id} loan  {itemId}  
			
			for(String token : elements)
			{
				if(token.startsWith("{")) // {id}     {itemId} 
				{
					String value = getUserInput(uriPortion + ": \n\n  Please provide a valid input value for :  "+ token); //{id}    {itemId}
					uriPortion = uriPortion.replace(token, value); //   /api/customers/{id}/loan/{itemId}   ->  /api/customers/1/loan/{itemId}	
				}
				 
			}
			
			return uriPortion;
		}

		private String resolveUriPortion(String annotationStr) 
		{
			// //@org.springframework.web.bind.annotation.GetMapping(path={}, headers={}, name="", produces={}, params={}, value={"/{id}"}, consumes={})
			String result = "";
		
			result = extractedAnnotationData(annotationStr);
			
			Annotation[]  annotationArrClassLvl  = metaDataCollect.getDeclaredForAnnotationsClass();
			Annotation annotationClassLvl = Arrays.asList(annotationArrClassLvl)
										  .stream()
										  .filter(a -> a.toString().contains("RequestMapping"))
										  .findFirst()
										  .get();
			String resultClassLvl = extractedAnnotationData(annotationClassLvl.toString()); //@org.springframework.web.bind.annotation.RequestMapping(path={}, headers={}, method={}, name="", produces={}, params={}, value={"/api/customers"}, consumes={})
			result = resultClassLvl + result; // /api/customers/{id}/loan/{itemId}
			
			//result = result.replaceAll("#\\{.*};", "1");	
			
			return result;
		}

		private String extractedAnnotationData(String annotationStr) {
			
			String result = "";
			String[] elements = annotationStr.split(",");
			for(String data : elements)
			{
				if(data.contains("value")) 
				{
					if(data.contains("\"")) // value={"/{id}"}  value={""}
					{
						result = data.substring(data.indexOf("\"") + 1 , data.lastIndexOf("\"")); // /{id}  ""
					}
				}
			}
			return result;
		}

		private String resolveHttpMtd(String annotationStr) 
		{
		  String result = "";
		  if(annotationStr.contains("GetMapping"))
		  {
			 result = "get";
		  }
		  else if(annotationStr.contains("PostMapping"))
		  {
			 result = "post";
		  }
		  else if(annotationStr.contains("PutMapping"))
		  {
			 result = "put";
		  }
		  else if(annotationStr.contains("DeleteMapping"))
		  {
			 result = "delete";
		  }
		  else if(annotationStr.contains("PatchMapping"))
		  {
			 result = "patch";
		  }
			
			return result;
		}

		private String inputArgsMapper(String arg) 
		{
			String template = "new PLCHOLDER-VALUE ,"; 
			
			if(arg.toLowerCase().contains("boolean"))
			{
				template = template.replace("PLCHOLDER-VALUE", "Boolean(true)"); //new Boolean(true) ,
			}		
			else if(arg.toLowerCase().contains("character"))
			{
				template = template.replace("PLCHOLDER-VALUE", "Character((char) 1)"); 
			}
			else if(arg.toLowerCase().contains("list"))
			{
				template = template.replace("PLCHOLDER-VALUE", "ArrayList()"); 
			}
			
			template = template.replace("PLCHOLDER-VALUE", arg + "()"); //new String() ,
				
			//return "new " + arg + "() , ";
			return template;
		}
		
		private String getReplacementValues(String classTypeName) 
		{
			//replacement values
			/*
			int i = 1;
			float f = 1;
			long l = 1;    	
			byte b = 1;
			short s = 1;
			char c = 1;
			boolean bl = false;
			 */
			String result ="";
			
			if("boolean".equals(classTypeName))
			{
				result =  "true";
			}
			else if("int".equals(classTypeName))
			{
				result =  "1";
			}
			else if("float".equals(classTypeName))
			{
				result =  "1f";
			}
			else if("long".equals(classTypeName))
			{
				result =  "1l";
			}
			else if("byte".equals(classTypeName))
			{
				result =  "new Byte((byte) 1)";
			}
			else if("short".equals(classTypeName))
			{
				result =  "new Short((short) 1)";
			}
			else if("char".equals(classTypeName))
			{
				result =  "new Character((char) 1)";
			}
			else if("Character".equals(classTypeName))
			{
				result =  "new Character((char) 1)";
			}
			
			return result;
		}
		/*
		private String getReplacementValuesForCollection(String classTypeName) 
		{
			//replacement values
			String result ="";
			
			if("List".equals(classTypeName))
			{
				result =  "new ArrayList();";
			}
			else if("int".equals(classTypeName))
			{
				result =  "1";
			}
			else if("float".equals(classTypeName))
			{
				result =  "1f";
			}
			else if("long".equals(classTypeName))
			{
				result =  "1l";
			}
			else if("byte".equals(classTypeName))
			{
				result =  "new Byte((byte) 1)";
			}
			else if("short".equals(classTypeName))
			{
				result =  "new Short((short) 1)";
			}
			else if("char".equals(classTypeName))
			{
				result =  "new Character((char) 1)";
			}
			else if("Character".equals(classTypeName))
			{
				result =  "new Character((char) 1)";
			}
			
			return result;
		}
		*/
		private String getWrapperOfPrimitive(MetaDataDeclaredMethods metaDataDclMtds, String dataType) 
		{
			String result ="";
			
			if(!metaDataDclMtds.isReturnTypePrimitive())
			{
				result = dataType;
				return result;
				
			}		
			
			if("boolean.class".equals(dataType))
			{
				result =  "Boolean.class";
			}
			else if("int.class".equals(dataType))
			{
				result =  "Integer.class";
			}
			else if("float.class".equals(dataType))
			{
				result =  "Float.class";
			}
			else if("long.class".equals(dataType))
			{
				result =  "Long.class";
			}
			else if("byte.class".equals(dataType))
			{
				result =  "Byte.class";
			}
			else if("short.class".equals(dataType))
			{
				result =  "Short.class";
			}
			else if("char.class".equals(dataType))
			{
				result =  "Character.class";
			}
			
			return result;
		}
		
		
		private String getStubMockitoInputType(String formattedStub, String mockitoType , Class<?>[] stubArgsAllParamsType) // [int, float, Customer] 
		{
			String result = "";
			//StringBuilder stubTypeBldr = new StringBuilder();
			
			for(Class<?> classType : stubArgsAllParamsType)
			{
				String dataType =  classType.toString();
				
				if("boolean".equals(dataType.toLowerCase()))
				{
					result =  "Mockito.anyBoolean()";
				}
				else if("int".equals(dataType.toLowerCase()))
				{
					result =  "Mockito.anyInt()";
				}
				else if("integer".equals(dataType.toLowerCase()))
				{
					result =  "Mockito.anyInt()";
				}
				else if("float.class".equals(dataType.toLowerCase()))
				{
					result =  "Mockito.anyFloat()";
				}
				else if("long".equals(dataType.toLowerCase()))
				{
					result =  "Mockito.anyLong()";
				}
				else if("byte".equals(dataType.toLowerCase()))
				{
					result =  "Mockito.anyByte()";
				}
				else if("short".equals(dataType.toLowerCase()))
				{
					result =  "Mockito.anyShort()";
				}
				else if("char".equals(dataType.toLowerCase()))
				{
					result =  "Mockito.anyChar()";
				}
				else
				{
					result =  mockitoType; //Mockito.any()
				}
				
				formattedStub =  formattedStub.replace(mockitoType, result.toString());   // Mockito.any() -> Mockito.anyInt()  Mockito.anyFloat  Mockito.any()
				
			}  
			  
			return formattedStub;
		}

		private String constructAssertionActions() throws ClassNotFoundException //throws ClassNotFoundException constructWhenThenStubActions 
		{
			/*
			1- get the method name            			  - createCustomer
			2- get the rturn type  						  - void
			3- get assertn template based off return type - assertTemplate2
			4- replace the place holders with real values from metaDataCollect/?
			5- repeat 1-4 for all declared methods
			
			*/
					
			// verify(PLCHOLDER-MOCK-BEAN-INSTANCE-NAME, times(1)).PLCHOLDER-MOCK-BEAN-INSTANCE-METHOD(ArgumentMatchers.any(PLCHOLDER-MOCK-BEAN-INSTANCE-TYPE));
			List<Field> a  = metaDataCollect.getAllBeanDependencies();
			
			String extractedTargetClassName = targetClassNameFullyQualified.substring(targetClassNameFullyQualified.lastIndexOf(".")+1);
			Class cls = Class.forName(targetClassNameFullyQualified);
			
			//0A- get all declared methods list 
			Method[] methods = cls.getDeclaredMethods();
			
			//0B- collect metatdata of all the declared mtds and create a metaDataDeclaredMethodsMap
			Arrays.asList(methods)
			      //.forEach(mtd ->  System.out.println("parameters : " +mtd.getParameters() + " returnType : "+ mtd.getReturnType()  ));
					.forEach(mtd ->  gatherMethodMetaData_1(mtd));

			/*
			1- for every declared method   - methodBodyMap
			2- if body contains any eligible stubline - metaDataCollect.getMetaDataDeclaredMethodsMap().getStubLine()
			3- then create assertion stmt for that stubline
			*/
			
			//metaDataCollect.getMethodBodyMap().forEach((mtdName, mtdBody) -> gatherAssertStmt(mtdName, mtdBody));		
			gatherAssertStmt();
			return "";
		}
		
		
		private void constructWhenThenStubActions() // throws Exception	
		{
			Collection<MetaDataDeclaredMethods> metaDataDecMtdsColl = metaDataCollect.getMetaDataDeclaredMethodsMap()
		  				 															 .values();
			
			metaDataDecMtdsColl.forEach(record -> record.getStubEligibleLine()
														.forEach(stubLine -> formatStub(record, stubLine)));
			
		}
		
		private Object gatherAssertStmt() 
		{
		
			Collection<MetaDataDeclaredMethods> metaDataDecMtdsColl = metaDataCollect.getMetaDataDeclaredMethodsMap()
						   											  				 .values();		
			collateMetaDataDeclMtdRecords(metaDataCollect.getMetaDataDeclaredMethodsMap());
			combineMetaDataDeclMtdRecords();
			
			/*
			stubEligibleLinesList.stream()
				                 .filter(stubLine -> mtdBody.contains(stubLine))
				                 .map(stubLine -> createAssertStmt(metaDataDecMtdsColl, metaDataDecMtdsColl.))
				                 .collect(Collectors.toList());
			*/
			
			metaDataDecMtdsColl.stream()
								.forEach(record -> createAssertStmt(record));
					
			return null;
		}
		
		private void collateMetaDataDeclMtdRecords(Map<String, MetaDataDeclaredMethods> metaDataDeclaredMethodsMap) 
		{	
			metaDataDeclaredMethodsMap.forEach((recKey, recordValue) -> 
											{ 
												
												if(recordValue.getFullSignature() != null)
												{
												    //implies that this record has the no stubEligibleLine
													recordValue.setFormattedMethodSignature(formatMethodSignature1(recKey, recordValue));
													recordsWithFullMtdSignature.add(recordValue);
												}
												else
												{
													//implies that this record has stubEligibleLine
													recordValue.setFullSignature(recKey);
													recordValue.setFormattedMethodSignature(formatMethodSignature2(recKey, recordValue));
													recordsWithStubEligibleLinesList.add(recordValue);
												}
											   });
			//System.out.println("test");
		}
		
		private String formatMethodSignature1(String recKey, MetaDataDeclaredMethods recordValue) 
		{
			//with full signature + mtd info - public void com.banknext.customer.mgt.service.CustomerServiceImpl.createCustomer(com.banknext.customer.mgt.repo.model.Customer)
			//								   public int com.banknext.customer.mgt.service.CustomerServiceImpl.findAll(java.lang.String,int)
			//							       public int com.banknext.customer.mgt.service.CustomerServiceImpl.findAll()
			//with stubEligLine              - public void createCustomer(Customer cust)
		 	String fullMethodSignature = recordValue.getFullSignature();
		 	
		 	// public int com.banknext.customer.mgt.service.CustomerServiceImpl.findAll(java.lang.String,int)
		 	//createCustomer(com.banknext.customer.mgt.repo.model.Customer,String)
		 	//public int com.banknext.customer.mgt.service.CustomerServiceImpl.findAll()
			String formattedSignature = fullMethodSignature.substring(fullMethodSignature.indexOf(recordValue.getMethodName()));// findAll(java.lang.String,int) 
			

			formattedSignature = formattedSignature.substring(formattedSignature.indexOf("(") , formattedSignature.lastIndexOf(")"));//(com.banknext.customer.mgt.repo.model.Customer   (   (java.lang.String,int 
			formattedSignature = formattedSignature.replace("(", ""); //com.banknext.customer.mgt.repo.model.Customer   ""   java.lang.String,int
			String[] formattedSignatureArr = formattedSignature.split(","); //[com.banknext.customer.mgt.repo.model.Customer]   [""]   [java.lang.String    int]
			
			StringBuilder formattedSignatureBldr =  new StringBuilder();
			
			Arrays.asList(formattedSignatureArr)
				  //.forEach(e -> formattedSignatureBldr.append(e))	
					.forEach(e -> { e = e.substring(e.lastIndexOf(".")+1); formattedSignatureBldr.append(e + ",");   }); //Customer,    String,   int,
			
			formattedSignature = formattedSignatureBldr.toString();
			formattedSignature = formattedSignature.substring(0, formattedSignature.length() -1); //remove the last comma   //Customer    String,   int
			
			/*
			formattedSignature = formattedSignature.substring(formattedSignature.lastIndexOf(".")+1); // createCustomer(Customer    (   String,int
			String[] inputArgs = formattedSignature.replace("(", "").split(",");    // [0]java.lang.String  [1]int
			String argsList ="";
			if(inputArgs !=null && inputArgs.length > 0)
			{
				for(int j = 0; j < inputArgs.length;  j++)
				{
					if(inputArgs[j].contains("."))
					{
					 inputArgs[j] = inputArgs[j].substring(inputArgs[j].lastIndexOf(".")); // java.lang.String -> String
					}
					
					//argsList = argsList + inputArgs[j] + "," ; // String,int,
					argsList = argsList + inputArgs[j] ; // String,int,
					if(j+1 != inputArgs.length)
					{
						argsList = argsList + "," ; // String,int	
					}
				}
				
				formattedSignature = argsList; // String,int
			}
			*/
			formattedSignature = recordValue.getMethodName() + "(" + formattedSignature;  //createCustomer(Customer  findAll(   findAll(String,int   
			
		 	//System.out.println("test");  
		 	
		 	return formattedSignature; //createCustomer(Customer  findAll(   findAll(String,int
			
		}
		
		
		private String formatMethodSignature2(String recKey, MetaDataDeclaredMethods recordValue) 
		{
			//with full signature + mtd info - public void com.banknext.customer.mgt.service.CustomerServiceImpl.createCustomer(com.banknext.customer.mgt.repo.model.Customer)
			//								   public int com.banknext.customer.mgt.service.CustomerServiceImpl.findAll(java.lang.String,int)
			//							       public int com.banknext.customer.mgt.service.CustomerServiceImpl.findAll()
			//with stubEligLine              - public void createCustomer(Customer cust)
		 	String fullMethodSignature = recordValue.getFullSignature();
		 	
		 	// public int com.banknext.customer.mgt.service.CustomerServiceImpl.findAll(java.lang.String,int)
		 	//createCustomer(com.banknext.customer.mgt.repo.model.Customer,String)
		 	//public int com.banknext.customer.mgt.service.CustomerServiceImpl.findAll()
		 	
		 	//public int findAll(String test2, int num2)
		 	String argumentList = recKey.substring(recKey.indexOf("("), recKey.lastIndexOf(")")+1); // (String test2, int num2)
		 	//methodNameToMatch = recKey.replace(argumentList, ""); // public int findAll      private void processMessage(Entity entityDTO) {    public Customer createCustomer(Customer customer) {
		 	String removeThisPortion = recKey.substring(recKey.indexOf(argumentList)) ; // (Customer customer) {
		 			//replace(argumentList, ""); // public int findAll      private void processMessage(Entity entityDTO) {    public Customer createCustomer(Customer customer) {
		 	methodNameToMatch = recKey.replace(removeThisPortion, ""); // public Customer createCustomer
		 	methodNameToMatch = methodNameToMatch.trim(); // public Boolean findAllBooleanWrpr  public Customer createCustomer(Customer customer) {
		 	methodNameToMatch = methodNameToMatch.substring(methodNameToMatch.lastIndexOf(" ") +1);// findAllBooleanWrpr
		 	/*
		 	String matchedMtdName = metaDataCollect.getMetaDataDeclaredMethodsMap()
								 				   .values()
								 				   .stream()
								 				   .filter(v -> methodNameToMatch.contains(v.getMethodName())) //match processMessage  in  private void processMessage(Entity entityDTO) { 
								 				   .map(v -> v.getMethodName())
								 				   .findAny()
								 				   .get();
	        */
		 	
		 	Collection<MetaDataDeclaredMethods> a1  = metaDataCollect.getMetaDataDeclaredMethodsMap()
			   .values();

		 	Set<String> matchedMtdNameSet =   metaDataCollect.getMetaDataDeclaredMethodsMap()
		 	//String[] matchedMtdNameArr =   (String[]) metaDataCollect.getMetaDataDeclaredMethodsMap()
									   				.values()
									   				.stream()
									   				.map(v -> v.getMethodName())
									   				.filter(v -> v!=null)
									   				.collect(Collectors.toSet())
									   				//.toArray()
									   				;
		 	
		 	String v1  = matchedMtdNameSet.stream()
		 			            //.anyMatch(v -> methodNameToMatch.contains(v))
		 						 //.filter(v -> methodNameToMatch.contains(v))
		 						 .filter(v -> methodNameToMatch.equals(v))
		 						 .findFirst()
		 						 .orElse("")
		 						 ;            
		 	
		 	methodName = v1;
		 	methodNameToMatch = methodName;
		 	
		 	if("".equals(methodName))
		 	{
		 		return "";
		 	}
		 	
		 	//String s2  = a1.forEach(v ->  );
		 	
		 	/*
		 	List<MetaDataDeclaredMethods> matchedMtdNameList = metaDataCollect.getMetaDataDeclaredMethodsMap()
									   .values()
									   .stream()
									   //.filter(v -> methodNameToMatch.contains(v.getMethodName())) //match processMessage  in  private void processMessage(Entity entityDTO) { 
									   //.filter(v -> v == v) //match processMessage  in  private void processMessage(Entity entityDTO) {
									   
									   .filter(v -> v.getFullSignature() !=null || !"".equals(v.getFullSignature())) //pair records in this HashMap. One side of pair has no fullSignature 
									   .filter(v -> methodNameToMatch.contains(v.getMethodName())) //match processMessage  in  private void processMessage(Entity entityDTO) {
									   //.map(v -> v.getMethodName())
									   //.toList()
									   .toList()
									   ;
		 	
		 	String matchedMtdName = matchedMtdNameList.get(0).getMethodName();
		 	//methodName = methodName.substring(methodName.lastIndexOf(" ")).trim(); // 
		 	//methodNameToMatch = methodNameToMatch.substring(methodNameToMatch.lastIndexOf(matchedMtdNameList.get(0))).trim(); //processMessage
		 	methodNameToMatch = methodNameToMatch.substring(methodNameToMatch.lastIndexOf(matchedMtdName)).trim(); //processMessage
		 	*/
		 	
		 	//methodNameToMatch = methodNameToMatch.substring(methodNameToMatch.indexOf(v1)).trim(); //processMessage
		 	
		 	recordValue.setMethodName(methodName); // findAll
		 	
			//String formattedSignature = fullMethodSignature.substring(fullMethodSignature.indexOf(recordValue.getMethodName()));//  findAll(String test2, int num2) 
		 	String formattedSignature = fullMethodSignature.substring(fullMethodSignature.indexOf(methodName));//  findAll(String test2, int num2)

			formattedSignature = formattedSignature.substring(formattedSignature.indexOf("(")+1 , formattedSignature.lastIndexOf(")"));// String test2, int num2
			String[] inputArgs = formattedSignature.replace("(", "").split(",");    // [0]String test2  [1]int num2
			String argsList ="";		
			
			
			//[@RequestBody Customer customer]
			
			if(inputArgs !=null && inputArgs.length > 0)
			{			
				for(int j = 0; j < inputArgs.length;  j++)
				{
					inputArgs[j] = inputArgs[j].trim();
					
					if("".equals(inputArgs[j]))
					{
						continue; //findAll()
					}
					
					/*
					//--				
					 StringBuilder paramsBldr = new StringBuilder();				 
					 for(String param : inputArgs) // (@PathVariable("id1") int customerId
					 {
						 //param = param.replaceAll(")", "").trim(); // (@PathVariable("id1") int customerId
						 param = param.replaceAll("\\)", ""); // (@PathVariable("id1") int customerId
						 param = param.trim();
						 String[] tokenArr =  param.split(" "); // @PathVariable("id1")     int    customerId
						 
						 String dataType =  tokenArr[tokenArr.length -2]; //int 
						 String value = tokenArr[tokenArr.length-1];		// customerId
						 
						 paramsBldr.append(dataType + " " + value); // int customerId        int loanId  
						 paramsBldr.append(","); // int customerId,  int loanId,
					 }
					 
					 
					 String adjustmentLine = paramsBldr.toString();
					 argsList = adjustmentLine.substring(0, adjustmentLine.length()-1); //remove the last extra comma   int customerId,  int loanId
					//--
					 */
					 
					
					inputArgs[j] = inputArgs[j].substring(0, inputArgs[j].indexOf(" ")); // String test2 -> String				 
					argsList = argsList + inputArgs[j] ; // String,int,
					if(j+1 != inputArgs.length)
					{
						argsList = argsList + "," ; // String,int	
					}
					
				}
				
				formattedSignature = argsList; // String,int   List<Integer>,List<Customer>
				while(formattedSignature.contains("<")) //adjustments - remove Generic syntax brackets
				{
					int startIdx = formattedSignature.indexOf("<");
					int endIdx = formattedSignature.indexOf(">") + 1;
					String portionToRemove = formattedSignature.substring(startIdx, endIdx); // <Integer>    <Customer>
					formattedSignature = formattedSignature.replace(portionToRemove, "");				
				}

			}
			formattedSignature = recordValue.getMethodName() + "(" + formattedSignature;  //createCustomer(Customer  findAll(   findAll(String,int   
			
		 	//System.out.println("test");  
		 	
		 	return formattedSignature; //createCustomer(Customer  findAll(   findAll(String,int
			
		}
		
		private void combineMetaDataDeclMtdRecords() 
		{	
			
			recordsWithFullMtdSignature.forEach(primaryRecord -> 
										recordsWithStubEligibleLinesList.forEach(secondaryRecord -> combine(primaryRecord, secondaryRecord)));
										//.anyMatch(d -> recordsWithStubEligibleLinesList.stream().anyMatch(k -> k.getFormattedMethodSignature().equals(d.getFormattedMethodSignature())))
			
			//System.out.println("test");
		}


		private void combine(MetaDataDeclaredMethods primaryRecord, MetaDataDeclaredMethods secondaryRecord) 
		{
		  if(primaryRecord.getFormattedMethodSignature().equals(secondaryRecord.getFormattedMethodSignature()))
		  {
			  primaryRecord.setStubEligibleLine(secondaryRecord.getStubEligibleLine());//add the info to primary record
			  
			  //metaDataCollect.getMetaDataDeclaredMethodsMap().remove(secondaryRecord);//no longer needed
			  String recordToRemoveKey = secondaryRecord.getFullSignature();
			  metaDataCollect.getMetaDataDeclaredMethodsMap().remove(recordToRemoveKey);//remove this record. it is no longer needed
			  
			  //adjust formattedMtdSig name to avoid gen name conflicts
			  //primaryRecord.setFormattedMethodSignature("_"+targetMtdNameCounter+"_" + primaryRecord.getFormattedMethodSignature()); //_1_
			  //primaryRecord.setFormattedMethodSignature("_"+targetMtdNameCounter+"_" + primaryRecord.getFormattedMethodSignature()); //_1_
			  
			  //System.out.println("test");
		  }  //findAllBooleanWrpr(Boolean        findAllBooleanWrpr(String,int,Boolean
		}		   
		private void createAssertStmt(MetaDataDeclaredMethods record) 
		{
			formattedReturnType = "";
			boolean isStubLineEligible = true;
			
			if(record.getStubEligibleLine().size() == 0)
			{
				isStubLineEligible = false;
			}		
			
			
			if(record.getReturnTypeAsString() == null || "".equals(record.getReturnTypeAsString()))
			{
				return;
				//record.setReturnTypeAsString("void");
			}
			
			/*
			//3- get assertn template		
			if("void".equalsIgnoreCase(record.getReturnTypeAsString()))
			{
				assertionTemplate = assertionTemplateVerify2;
			}
			else
			{	
				formattedReturnType = extractOnlyTypeClassOfReturn(record); .class int.class
				formattedReturnType = getWrapperOfPrimitive(record, formattedReturnType); //List.class int.class 
				
				assertionTemplate = assertionTemplateAssert1;
			}
			*/
			
			//4- replace the place holders with real values
			
			//hardcoded
			//String stubEligibleLIne1= "custRepo.save(cust)";
			String stubEligibleLIne1="";
			if(isStubLineEligible) //scenario -1 stub lines exists + targetMtd returnType may or many not be present
			{
			/*	
			  stubEligibleLIne1= record.getStubEligibleLine()
								 			.get(0); //make dynamic
			  */
			
			
				
				
				
				//List<String> assertionsList = new ArrayList<String>();
				 
				record.getStubEligibleLine()
				      .stream()
				      .map(line -> stubToAssertStmt(record, line, formattedReturnType))
				      .toList();
			
				//stubAssertionDone = record.getAssertionsList().isEmpty();
			}		
			
			//scenario -2 stub lines NOT exists + targetMtd returnType IS present 
			else
			{
				stubIneligibleButTargetMtdHasReturnTypeToAssertStmt(record, formattedReturnType);
			}
			
			boolean isGetMapping = false;
			//--
			if(record.getReturnTypeAsString().contains("ResponseEntity"))//Controller class detected
			{	
				for(Annotation annotation : record.getDeclaredAnnotationsForMtd())
				{
					if(annotation.toString().contains("GetMapping"))
					{
						isGetMapping = true;
						break;
					}
				}
					
				
				if(isGetMapping)
				{
					//vjNew3
					String response = getUserInput("7- " + record.getFullSignature() + "\n\n  What is the PARAMETERIZED type inside this ResponseEntity<PARAMETERIZED> ? \n  Please provide the fully qualified name \n  eg. com.banknext.customer.mgt.repo.model.Customer ");
					createDtoFactory(response);
					
					JunitTestConstants.mockMvcJunitTemplateForGet = JunitTestConstants.mockMvcJunitTemplateForGet.replace("PLCHOLDER-DTO-NAME", classNameOnly);
					JunitTestConstants.mockMvcJunitTemplateForGet = JunitTestConstants.mockMvcJunitTemplateForGet.replace("PLCHOLDER-FIELD-NAME", returnTypeDtoFieldNamesAll.get(0));
					JunitTestConstants.mockMvcJunitTemplateForGet = JunitTestConstants.mockMvcJunitTemplateForGet.replace("PLCHOLDER-FIELD-RAW", returnTypeDtoFieldNamesRawAll.get(0));
					record.getAssertionsList().add("\n");
					record.getAssertionsList().add(JunitTestConstants.mockMvcJunitTemplateForGet);
				}
				else  //Post/Put/Delete/Patch
				{
					record.getAssertionsList().add("\n");
					record.getAssertionsList().add(JunitTestConstants.mockMvcJunitTemplateForNonGet);
				}			
			}
					
					
					//--
			
			
			//System.out.println("test");
		}
		
		private MetaDataDeclaredMethods stubToAssertStmt(MetaDataDeclaredMethods record, String stubEligibleLIne1, String formattedReturnType) 
		{
			boolean complexInputArgs = false;
			String mockBeanInstanceName = "";
			String mockBeanInstanceMethod = "";
			String mockBeanInputArg =  "";
			String mockBeanInputArgType =  "";
			String mockBeanInputArgInstance ="";
			
			
			try
			{
				//Customer customer = custRepo.save(cust)
				/*
				metaDataCollect.getDeclaredFieldsMap()
				               .keySet()
				               .stream()
				               .filter(k -> stubEligibleLIne1.contains(k)) // Customer customer = custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
				               .map(k -> { stubEligibleLIne1Formatted = stubEligibleLIne1.substring(stubEligibleLIne1.indexOf(k)); return stubEligibleLIne1; } ) // custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));			               
				               .toString();
				*/
				
				//custRepo
				String matchingDeclaredField   = metaDataCollect.getDeclaredFieldsMap()
										            .keySet()
										            .stream()
										            .filter(k -> stubEligibleLIne1.contains(k)) // Customer customer = custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
										            .findFirst()
										            .get();	
					            
					            
				stubEligibleLIne1Formatted = stubEligibleLIne1.substring(stubEligibleLIne1.indexOf(matchingDeclaredField))  ; // custRepo.save(cust) 

				
				 mockBeanInstanceName = stubEligibleLIne1Formatted.substring(0,stubEligibleLIne1Formatted.indexOf(".")); //custRepo
				 mockBeanInstanceMethod = stubEligibleLIne1Formatted.substring(stubEligibleLIne1Formatted.indexOf(".")+1, stubEligibleLIne1Formatted.indexOf("(")); //save
				 mockBeanInputArg = stubEligibleLIne1Formatted.substring(stubEligibleLIne1Formatted.indexOf("(")+1, stubEligibleLIne1Formatted.indexOf(")")); //cust)		 
			     String mockBeanInputArg2 = stubEligibleLIne1Formatted.substring(stubEligibleLIne1Formatted.indexOf("("), stubEligibleLIne1Formatted.indexOf(")")); //(cust)

			     String numOfInputArgs="";
			     String[] mockBeanInputArg2Arr = mockBeanInputArg2.split("\\(");
			     String allInputArgsCommaSeparated = "";
			     String argReplacement ="";
				 if(mockBeanInputArg2Arr.length > 2) //complex structure detected   (customerNum, construct(customerNum, loanNum))   (custTxnTopic, constructEntity(entity, customer))
				 {   
					 //verify(customerKafkaTemplate, times(1)).send(ArgumentMatchers.any(), ArgumentMatchers.any());
					 /*
					 mockBeanInputArgType =  getUserInput("6- What is the INPUT args in "+ stubEligibleLIne1 + "\n Please provide comma separated list ");  //Event, Customer, String
					 mockBeanInputArgInstance = mockBeanInputArgType.replaceAll(",",".class, ");//Customer.class      Event.class, Customer.class, String.class
					 */
					 //vjNew3
					 numOfInputArgs =  getUserInput("6- " + mockBeanInstanceMethod +"(..) \n\n  For the above method , what is the NUMBER of INPUT args?"+ stubEligibleLIne1 );
					 
					 int finalNumOfInputArgs = Integer.parseInt(numOfInputArgs);
					 int k = 0;				 
					 while(k < finalNumOfInputArgs)
					 {   
						 //when(customerKafkaTemplate.send(Mockito.any()), (Mockito.any())).thenReturn(responseFuture);  -> wrong
						 //when(customerKafkaTemplate.send(Mockito.any(), Mockito.any())).thenReturn(responseFuture);    -> correct
						 argReplacement = argReplacement + "ArgumentMatchers.any()" + ", " ;
						 k++;
					 }
					
					 argReplacement = argReplacement.substring(0, argReplacement.lastIndexOf(",")); //adjustment - remove extra last comma
					 mockBeanInputArgInstance = argReplacement; //ArgumentMatchers.any(), ArgumentMatchers.any()
					 
					 complexInputArgs = true;
				 }
				 else
				 {
					 mockBeanInputArgType = metaDataDeclaredFieldsMap.get(mockBeanInputArg);//INSTANCE-TYPE:Customer.class
					 if(mockBeanInputArgType !=null) //findAll()
					 {
						mockBeanInputArgInstance = mockBeanInputArgType.split(":")[1];//Customer.class
					 }
				 }			 
				 
				
			}
			catch(Exception e)
			{
				System.out.println("--some discrepancy in assertion generation - Ignoring for now.");
				return record;
			}
			
			List<String> assertionsList = record.getAssertionsList(); 
			if(assertionsList ==  null)
			{
				assertionsList = new ArrayList<String>();;
			}
			
			//default - all cases will always have a verify times(1) assertion if a stubline exists
			assertionTemplate = assertionTemplateVerify2;
			if(complexInputArgs)
			{
				assertionTemplate = assertionTemplateVerify3; // more than mtd ip args 	
			}
				
			
			assertionTemplate = assertionTemplate.replace("PLCHOLDER-MOCK-BEAN-INSTANCE-NAME", mockBeanInstanceName);
			assertionTemplate = assertionTemplate.replace("PLCHOLDER-MOCK-BEAN-INSTANCE-METHOD", mockBeanInstanceMethod);
			assertionTemplate = assertionTemplate.replace("PLCHOLDER-MOCK-BEAN-INSTANCE-TYPE", mockBeanInputArgInstance);		
			//verify(custRepo, times(1)).deleteById(ArgumentMatchers.any());
			
			if(mockBeanInputArg == null || "".equals(mockBeanInputArg)) // findAll() - no i/p args
			{
				// adjustment remove ArgumentMatchers.any() 
				// verify(custRepo, times(1)).findAll(ArgumentMatchers.any());
				// verify(custRepo, times(1)).findAll();
				assertionTemplate  = assertionTemplate.replace("ArgumentMatchers.any()", "");
			}
			
			/*
			assertThat(customerserviceimpl.findAll()).isInstanceOf(List.class);
			assertThat(customerserviceimpl.findAll().size()).isGreaterThan(0);
			assertThat(customerserviceimpl.findAll(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).isInstanceOf(int.class);
			*/
			//case for non void return type
			/*
			String extractedTargetClassName = targetClassNameFullyQualified.substring(targetClassNameFullyQualified.lastIndexOf(".")+1); //class containing this method   :  CustomerServiceImpl
			assertionTemplate = assertionTemplate.replace("PLCHOLDER-TARGET-BEAN-INSTANCE-NAME", extractedTargetClassName.toLowerCase()); //customerserviceimpl
			assertionTemplate = assertionTemplate.replace("PLCHOLDER-TARGET-BEAN-METHOD-NAME", record.getMethodName());
			assertionTemplate = assertionTemplate.replace("PLCHOLDER-RETURN-TYPE-RAW-CLASS", formattedReturnType);		
			assertionTemplate = assertionTemplate.replace("PLCHOLDER-TARGET-BEAN-INPUT-ARGS", buildAssertionTargetBeanArgsList(record));
			*/
							
			if(isInvocationTypeKafka(assertionTemplate, record)) 
			{
				System.out.println("isInvocationTypeKafka :  "+ true); //vjNew7
				assertionTemplate  = "\nassertThat(result).isEqualTo(\"success\");"; //kafkaProducer specific scenario   
				assertionsList.add(assertionTemplate); // assertThat(result).isEqualTo("success");
			}
			else
			{
				assertionsList.add(assertionTemplate); //verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));	
			}
			
			
			//if target invoked mtd has a non void return type the an additional assertThat is required 
			if(!"void".equalsIgnoreCase(record.getReturnTypeAsString()))
			{	
				formattedReturnType = extractOnlyTypeClassOfReturn(record); //List.class int.class
				formattedReturnType = getWrapperOfPrimitive(record, formattedReturnType); //List.class int.class 
				//vjNew6
				if(!record.getReturnTypeAsString().contains("ResponseEntity"))//skip this assert for controller
				{
					assertionTemplate = assertionTemplateAssert1;
					assertionTemplate = assertionTemplate.replace("PLCHOLDER-RETURN-TYPE-RAW-CLASS", formattedReturnType);
					assertionsList.add(assertionTemplate); //assertThat(customerserviceimpl.findAll).isInstanceOf(interface java.util.List)					
					
				}
				
			}			
			
			record.setAssertionsList(assertionsList);
			//System.out.println("test");
					
			return record;
		}
		
		private MetaDataDeclaredMethods stubIneligibleButTargetMtdHasReturnTypeToAssertStmt(MetaDataDeclaredMethods record, String formattedReturnType) 
		{
			String mockBeanInstanceName = "";
			String mockBeanInstanceMethod = "";
			String mockBeanInputArg =  "";
			String mockBeanInputArgType =  "";
			String mockBeanInputArgInstance ="";
			
			
			try
			{
				//Customer customer = custRepo.save(cust)
				/*
				metaDataCollect.getDeclaredFieldsMap()
				               .keySet()
				               .stream()
				               .filter(k -> stubEligibleLIne1.contains(k)) // Customer customer = custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
				               .map(k -> { stubEligibleLIne1Formatted = stubEligibleLIne1.substring(stubEligibleLIne1.indexOf(k)); return stubEligibleLIne1; } ) // custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));			               
				               .toString();
				*/
				
				//custRepo
				/*
				String matchingDeclaredField   = metaDataCollect.getDeclaredFieldsMap()
										            .keySet()
										            .stream()
										            .filter(k -> stubEligibleLIne1.contains(k)) // Customer customer = custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
										            .findFirst()
										            .get();	
					            */
					            
				//stubEligibleLIne1Formatted = stubEligibleLIne1.substring(stubEligibleLIne1.indexOf(matchingDeclaredField))  ; // custRepo.save(cust) 

				/*
				 mockBeanInstanceName = stubEligibleLIne1Formatted.substring(0,stubEligibleLIne1Formatted.indexOf(".")); //custRepo
				 mockBeanInstanceMethod = stubEligibleLIne1Formatted.substring(stubEligibleLIne1Formatted.indexOf(".")+1, stubEligibleLIne1Formatted.indexOf("(")); //save
				 mockBeanInputArg = stubEligibleLIne1Formatted.substring(stubEligibleLIne1Formatted.indexOf("(")+1, stubEligibleLIne1Formatted.indexOf(")")); //(cust)		 
			 
				 mockBeanInputArgType = metaDataDeclaredFieldsMap.get(mockBeanInputArg);//INSTANCE-TYPE:Customer.class
			 
				if(mockBeanInputArgType !=null) //findAll()
				{
					mockBeanInputArgInstance = mockBeanInputArgType.split(":")[1];//Customer.class
				}
				*/
			}
			catch(Exception e)
			{
				System.out.println("--some discrepancy in assertion generation - Ignoring for now.");
				return record;
			}
			
			List<String> assertionsList = record.getAssertionsList(); 
			if(assertionsList ==  null)
			{
				assertionsList = new ArrayList<String>();;
			}
			
			/*
			//default - all cases will always have a verify times(1) assertion if a stubline exists
			assertionTemplate = assertionTemplateVerify2;
			
			assertionTemplate = assertionTemplate.replace("PLCHOLDER-MOCK-BEAN-INSTANCE-NAME", mockBeanInstanceName);
			assertionTemplate = assertionTemplate.replace("PLCHOLDER-MOCK-BEAN-INSTANCE-METHOD", mockBeanInstanceMethod);
			assertionTemplate = assertionTemplate.replace("PLCHOLDER-MOCK-BEAN-INSTANCE-TYPE", mockBeanInputArgInstance);
	//assertThat(PLCHOLDER-TARGET-BEAN-INSTANCE-NAME.PLCHOLDER-TARGET-BEAN-METHOD-NAME).isInstanceOf(PLCHOLDER-RETURN-TYPE-RAW-CLASS)
							
			assertionsList.add(assertionTemplate); //verify(custRepo, times(1)).save(ArgumentMatchers.any(Customer.class));
			*/
			
			
			
			//if target invoked mtd has a non void return type the an additional assertThat is required 
			if(!"void".equalsIgnoreCase(record.getReturnTypeAsString()))
			{	
				formattedReturnType = extractOnlyTypeClassOfReturn(record); //List.class int.class
				formattedReturnType = getWrapperOfPrimitive(record, formattedReturnType); //List.class int.class 
				
				if(!record.getReturnTypeAsString().contains("ResponseEntity"))//skip this assert for controller
				{
					assertionTemplate = assertionTemplateAssert1;
					assertionTemplate = assertionTemplate.replace("PLCHOLDER-RETURN-TYPE-RAW-CLASS", formattedReturnType);
					assertionsList.add(assertionTemplate); //assertThat(customerserviceimpl.findAll).isInstanceOf(interface java.util.List)
				}
			}
			
			record.setAssertionsList(assertionsList);
			//System.out.println("test");
					
			return record;
		}

		private String extractOnlyTypeClassOfReturn(MetaDataDeclaredMethods record) 
		{
			String formattedReturnType;
			formattedReturnType = record.getReturnTypeAsString(); 
			
			if(formattedReturnType.contains(".")) //interface java.util.List   int
			{
				formattedReturnType  = formattedReturnType.substring(formattedReturnType.lastIndexOf(".")+1); //List
				//formattedReturnType  = formattedReturnType + ".class"; //List.class	
			}
			formattedReturnType  = formattedReturnType + ".class"; //List.class   int .class
			return formattedReturnType;
		}

		private String buildAssertionTargetBeanArgsList_DISCARD(MetaDataDeclaredMethods record) 
		{
			StringBuilder targetBeanInputArgsBldr = new StringBuilder();
			IntStream.range(0, record.getNumberOfInputParams())
			         .forEach(param -> targetBeanInputArgsBldr.append("Mockito.any(), "));
			
			String targetBeanInputArgs  = targetBeanInputArgsBldr.toString();
			
			if(targetBeanInputArgs != null && !"".equals(targetBeanInputArgs)) //eg. findAll()
			{
				targetBeanInputArgs = targetBeanInputArgs.substring(0, targetBeanInputArgs.lastIndexOf(","));//adjustment - remove the last extra comma	
			}
			
			
			
			//targetBeanInputArgsBldr  = targetBeanInputArgsBldr.deleteCharAt(targetBeanInputArgsBldr.length()); //adjustment - remove the last extra comma
			//String targetBeanInputArgs  = targetBeanInputArgsBldr.deleteCharAt(targetBeanInputArgsBldr.length()-1).toString(); //adjustment - remove the last extra comma
			return targetBeanInputArgs;
		}
		
		private String findFile(String fileNamePortion, String dir) 
		{
			StringBuilder result = new StringBuilder();
			
			File files = new File(dir);
			files.list(new FilenameFilter() 
			{
				@Override
				public boolean accept(File file, String fileName) 
				{
					//System.out.println("name : "+fileName);
					if(fileName.contains(fileNamePortion))
					{
						File testFile =  new File(file.getPath() + "\\" +fileName);
						//boolean f = testFile.isFile();
						if(testFile.isFile())
						{
							result.append(fileName);
							return true;	
						}
					}				
					return false;
				}
			});
			 
			System.out.println("result.toString() " + result.toString());
			return result.toString();
		}
}
