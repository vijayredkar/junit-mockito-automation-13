import java.util.List;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.FileVisitResult;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.io.BufferedReader;
import java.io.FileReader;


public class AutomationPrep
{
	public static void main(String[] args) throws Exception
	{
		System.out.println("\n-- APP_SETUP_DIR      : "+args[0]); // C:\Vijay\Java\AutomationJunitMockito\appsetup
		System.out.println("-- APP_BASE_PATH	    : "+args[1]); // C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation
		System.out.println("-- APP_MAIN_CLASS_DIR   : "+args[2]); // C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\main\java\com\banknext\customer\mgt
		System.out.println("-- TARGET_CLASS	        : "+args[3]); // com.banknext.customer.mgt.controller.CustomerMgtControllerA


		String  appMainClassDir = args[2];    // C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\main\java\com\banknext\customer\mgt
		String  targetClassWithPath = args[3]; //com.banknext.customer.mgt.controller.CustomerMgtControllerA

		//String automationRawDir =	"C:\\Vijay\\Java\\AutomationJunitMockito\\appsetup\\automation\\junit\\";
		String automationRawDir =	args[0] + "\\automation\\junit\\"; // C:\Vijay\Java\AutomationJunitMockito\appsetup\automation\junit\

		//String targetClassNamePath = "C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\main\\java\\com\\banknext\\customer\\mgt\\controller";
		String targetClassNamePath = args[1] + "\\src\\main\\java\\" +  args[3]; //C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\main\\java\\com.banknext.customer.mgt.controller.CustomerMgtControllerA
		//System.out.println("\ntargetClassNamePath 1 : "+targetClassNamePath); 
		targetClassNamePath        = targetClassNamePath.substring(0, targetClassNamePath.lastIndexOf(".")); // C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\main\\java\\com.banknext.customer.mgt.controller
		//System.out.println("targetClassNamePath 2 : "+targetClassNamePath); 		
		targetClassNamePath        = targetClassNamePath.replaceAll("\\.", "\\\\"); // C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\main\\java\\com\\banknext\\customer\\mgt\\controller
		//System.out.println("targetClassNamePath 3 : "+targetClassNamePath); 


		//Thread.sleep(30000);



		//String applicationBasePath = "C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation";
		String applicationBasePath = args[1];
		applicationBasePath = applicationBasePath + "\\src\\main\\java\\"; 	
		
		String fileNamePortion = "Application";

	
	  
      //String automationBaseDir  =  directoryParse(targetClassNamePath, applicationBasePath, fileNamePortion); // no need to compute. user provides 
	  String automationBaseDir  = args[2];
      System.out.println("\n-- automation base dir location : "+automationBaseDir);







	  //--determine SpringBoot/automation files version	
	  String userAppBasePath = args[1];  // C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation
	  String appSpringBootVersion = detectVersion(userAppBasePath, "org.springframework.boot:spring-boot-starter:jar");

	  if(appSpringBootVersion.contains("2"))
		{
		 automationRawDir = automationRawDir.replace("junit","junit-2x");             // \appsetup\automation\junit-2x
		 System.out.println("\nSpringBoot application version detected : 2.x"); 
		}
		else
		{
		 automationRawDir = automationRawDir.replace("junit","junit-3x");             // \appsetup\automation\junit-3x
		 System.out.println("\nSpringBoot application version detected : 3.x"); 
		}

	 /*
		Copy From
		Start Dir  C:\Vijay\Java\AutomationJunitMockito\appsetup\automation\junit\        -> automationRawDir
		End   Dir  C:\Vijay\Java\AutomationJunitMockito\appsetup\automation\junit-2x\     
																			junit-3x\			
		Copy To  
		User's Dir C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\main\java\com\banknext\customer\mgt   -> automationBaseDir		
	 */
	   copyDir(automationRawDir,automationBaseDir); // copy all files from appsetup\automation\junit-2x~3x to user's application base automation dir [ automationBaseDir ]


	  // -- copy templates 
	  String automationTemplatesRawDir =	args[0] + "\\templates\\"; // C:\Vijay\Java\AutomationJunitMockito\appsetup\templates
	  String automationTemplatesBaseDir =	args[2] + "\\automation\\" + "\\templates\\"; // C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\main\java\com\banknext\customer\mgt\automation\templates
	  copyDirSimple(automationTemplatesRawDir,automationTemplatesBaseDir);	







//---replacements

		String automationPckgPath = appMainClassDir.substring(appMainClassDir.indexOf("\\src\\main\\java\\")); // com\banknext\customer\mgt
		automationPckgPath = automationPckgPath.replaceAll("\\\\", ".");
		automationPckgPath = automationPckgPath.replaceAll(".src.main.java.", "");
		automationPckgPath = automationPckgPath.concat(".automation.junit");

		/*
		iterate thr all files under 
		C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\main\java\com\banknext\customer\mgt -> com.banknext.customer.mgt
		for every automation prep class file
		replace PLCHLDR-AUTOMATION-PCKG with com.banknext.customer.mgt
		*/

		//System.out.println("-- file replace start: ");
		Path automationPath = Paths.get(appMainClassDir + "\\" + "automation\\junit\\"); // C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\main\java\com\banknext\customer\mgt\automation\junit\

		String temp = automationPath.toString();
		//System.out.println("-- file temp "+ temp);

        File automationDir = new File(automationPath.toString());
		String[] fileList =  automationDir.list() ;
					
		for(String fileName : fileList)
		{
            String fullFileName="";
			StringBuilder contentBldr = new StringBuilder();
			fullFileName = automationPath.toString() + "\\"+fileName;

			try (BufferedReader br = new BufferedReader(new FileReader(fullFileName))) 
			{
				String line;
				while ((line = br.readLine()) != null) 
				{
					if(line.contains("PLCHLDR-AUTOMATION-PCKG"))
					{
						line = line.replace("PLCHLDR-AUTOMATION-PCKG", automationPckgPath);
					}

					contentBldr.append(line);
					contentBldr.append("\n");
				}//end while

				Path path = Paths.get(fullFileName);
				Files.writeString(path, contentBldr.toString(), StandardCharsets.UTF_8);
			}
			catch (Exception e) 
			{
				System.err.format("Exception: %s%n", e);
			}
		} //end for
	}

	private static String directoryParse(String targetClassNamePath, String applicationBasePath, String fileNamePortion)  //C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\main\\java\\com\\banknext\\customer\\mgt\\controller
	{

	 System.out.println("-- targetClassNamePath : "+targetClassNamePath);
	 System.out.println("-- applicationBasePath : "+applicationBasePath);
	 System.out.println("-- fileNamePortion : "+fileNamePortion);


	  String matchedFileName = "";
	  String subPath = targetClassNamePath.replace(applicationBasePath, ""); //com\\banknext\\customer\\mgt\\controller
	  String constructedPath = applicationBasePath;	 
	  String[] folders = subPath.split("\\\\");


	  for(String folder : folders)
		{
		    constructedPath = constructedPath + folder; // C:\\Vijay\\Java\\projects\\junit-mockito-mgt\\junit-automation\\src\\main\\java\\com
			matchedFileName = findFile(fileNamePortion, constructedPath);

			if(!"".equals(matchedFileName))
			{
				System.out.println("-- matchedFileName : "+matchedFileName);
				break;
			}

			constructedPath = constructedPath + "\\";
		}

		return constructedPath;
	}

	private static String findFile(String fileNamePortion, String dir) 
	{
		StringBuilder result = new StringBuilder();
		
		File files = new File(dir);
		files.list(new FilenameFilter() 
		{
			@Override
			public boolean accept(File file, String fileName) 
			{
				if(fileName.contains(fileNamePortion))
				{
					File testFile =  new File(file.getPath() + "\\" +fileName);
					if(testFile.isFile())
					{
						result.append(fileName);
						return true;	
					}
				}				
				return false;
			}
		});
		 
		return result.toString();
	}

    public static void copyDir(String source, String destination) throws Exception 
	{
        Path sourcePath = Paths.get(source + "\\");
        Path destinationPath = Paths.get(destination);
	
		System.out.println("-- sourcePath "+sourcePath );
		System.out.println("-- destinationPath "+destinationPath );

		/*
		String  existingAutomationDirToDelete = destination + "\\automation\\junit\\";
		deleteDir(existingAutomationDirToDelete);
		*/

		String newEmptyDir = destinationPath + "\\automation\\";
		Path newEmptyDirPath = Paths.get(newEmptyDir);
		//System.out.println("\n-- dir create starts 1 "+newEmptyDirPath );
		Files.createDirectory(newEmptyDirPath);
		//System.out.println("-- dir created "+newEmptyDirPath );
		//System.out.println("-- new destinationPath is "+newEmptyDirPath );
	
		newEmptyDir = newEmptyDir + "\\junit\\";
		Path newEmptyDirPath2 = Paths.get(newEmptyDir);
		//System.out.println("\n-- dir create starts 2 "+newEmptyDirPath2 );
		Files.createDirectory(newEmptyDirPath2);

        Files.walk(sourcePath)
             .forEach(path -> {
                 try 
				{
					Files.copy(path, newEmptyDirPath2.resolve(sourcePath.relativize(path)), REPLACE_EXISTING);
                } 
				catch (Exception e) 
				{
                    System.err.println("** dir copy failed. Exiting ... " +e);
					System.exit(-1);
                }
             });

             System.out.println("-- Success : copied automation dir to : " +destination);
    }

	public static void copyDirSimple(String source, String destination) throws Exception 
	{
        Path sourcePath = Paths.get(source + "\\");
        Path destinationPath = Paths.get(destination);
	
		System.out.println("-- sourcePath "+sourcePath );
		System.out.println("-- destinationPath "+destinationPath );
		
		Files.createDirectory(destinationPath);
		System.out.println(destinationPath + " dir created ");

        Files.walk(sourcePath)
             .forEach(path -> {
                 try 
				{
					Files.copy(path, destinationPath.resolve(sourcePath.relativize(path)), REPLACE_EXISTING);
                } 
				catch (Exception e) 
				{
                    System.err.println("** templates dir copy failed. Exiting ... " +e);
					System.exit(-1);
                }
             });

             System.out.println("-- Success : templates copied automation dir  : " +destination);
    }

/*   delete dir recursively  - too complex in Java - utilize command line to delete 
	public static void deleteDir(String dirToDelete) throws Exception 
	{
		System.out.println("-- dirToDelete : " +dirToDelete);

		Thread.sleep(10000);
		
        Path dirToDeletePath = Paths.get(dirToDelete);
		try 
		{
			

		 Files.walkFileTree(dirToDeletePath, new SimpleFileVisitor<Path>() {
            //@Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)  {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            //@Override
            public FileVisitResult postVisitDirectory(Path dir, Exception exc) {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
	    }
		catch (Exception e) 
		{
			System.err.println("** dir delete failed. Exiting ... " +e);
			System.exit(-1);
		}
    }
*/

  public static String detectVersion(String applicationBasePath, String depInfo) throws Exception   //org.springframework.boot:spring-boot-starter:jar
	{
		String versionDetected = "";
		Scanner sc = new Scanner(new File(applicationBasePath + "\\app-dependencies-output.txt")); //C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\app-dependencies-output.txt
		String line ="";
		
		while(sc.hasNextLine())
		{
		  line = sc.nextLine();
		  line =  line.trim();

		  if(line.contains(depInfo))    //[INFO] \- org.springframework.boot:spring-boot-starter:jar:2.5.0:compile
		  { 
             System.out.println("-- reached line : " +line);
			 line = line.replace("[INFO] \\-", ""); // org.springframework.boot:spring-boot-starter:jar:2.5.0:compile
			 line = line.replace("org.springframework.boot:spring-boot-starter:jar:", ""); // 2.5.0:compile
			 line = line.replace(":compile", ""); // 2.5.0
			 line = line.substring(0, line.indexOf(".")); // 2

			 versionDetected = line.trim();
			 break;
		  } 		 
		} //end while
		
		sc.close();

		if("".equals(versionDetected)) //version still not detected
		{	
			sc = new Scanner(System.in);	//ask the user
			System.out.println("\n\n What is your SpringBoot application's version [org.springframework.boot:spring-boot-starter] ?\nEnter the major version number only [2/3] : ");
			versionDetected = sc.nextLine();      
			sc.close();
		}

		System.out.println("\n-- SpringBoot version detected: " +versionDetected);
		return line;
	}
}
