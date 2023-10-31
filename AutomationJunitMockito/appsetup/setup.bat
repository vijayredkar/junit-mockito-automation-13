@echo off

cls
echo[
echo[
echo --------- Automation: Junits + Mocks for your Maven based SpringBoot application --------
echo[
echo Pre-requisites:
echo   1- Springboot application that builds and starts up normally.
echo   2- Working local jdk 8+ setup.
echo   3- Working local maven  setup.
echo   4- BACKUP your application so that you can reinstate the original state.
echo      Automation will add/change your project structure.
echo[
echo   5- Maven pom.xml with Junit + Mockito + Starter dependencies.
echo      Reference POM for you: \AutomationJunitMockito\appsetup\templates\pom-junit-mockito-template.xml
echo				   Junit Mockito Mandatory section lists all mandatory POM portions.
echo[
echo If you are NOT READY to proceed then TERMINATE this console OTHERWISE press any key to proceed further
pause


REM set environment variables
set MODE=autogen

echo[
echo[
echo Step-1 ----Gather application information
set APP_SETUP_DIR=%cd%
echo            Please provide the local path of your Maven based SpringBoot application:
echo            This path contains your pom.xml.
echo            for eg. 
echo              C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation
set /p "APP_BASE_PATH="


echo[
echo[
echo Step-2 ----Gather target class information
echo            Please provide the fully qualified Class name for which you wish to create the Junits:
echo            for eg. 
echo              com.banknext.customer.mgt.event.publisher.CustomerTxnPublisher
echo		  com.banknext.customer.mgt.event.consumer.EntityTxnConsumerA
echo		  com.banknext.customer.mgt.controller.CustomerMgtControllerA
echo		  com.banknext.customer.mgt.service.CustomerServiceImpl
set /p "TARGET_CLASS="


echo[
echo[
echo Step-3 ----Gather Springboot application information.
echo            Please provide the full directory path of your Springboot Application Main class:
echo            for eg. 
echo              C:\Vijay\Java\projects\junit-mockito-mgt\junit-automation\src\main\java\com\banknext\customer\mgt
set /p "APP_MAIN_CLASS_DIR="


echo[
echo[
echo Step-4 ----Preparing automation env
set AUTOMATION_PREP_PATH=%APP_MAIN_CLASS_DIR%\automation\
echo        DELETING remnants of older automation run from -
echo        %AUTOMATION_PREP_PATH%
echo        If you DO NOT WISH to proceed then TERMINATE this console OTHERWISE press any key to CONFIRM deletion
pause
rmdir /s /q %AUTOMATION_PREP_PATH%
timeout /t 5 /nobreak
echo Step-4 complete


echo[
echo[
echo Step-5A ----Detecting SpringBoot version
cd %APP_BASE_PATH%
call mvn dependency:tree -Dincludes=org.springframework.boot:spring-boot-starter > app-dependencies-output.txt
echo Dependency info saved at %APP_BASE_PATH%\app-dependencies-output.txt


echo[
echo[
echo Step-5B ----Creating automation env
REM echo[
cd %APP_SETUP_DIR%
REM echo Current dir : %APP_SETUP_DIR%
javac AutomationPrep.java
java AutomationPrep %APP_SETUP_DIR% %APP_BASE_PATH% %APP_MAIN_CLASS_DIR% %TARGET_CLASS%
REM echo[
set AUTOMATION_TEMPLATES_PATH=%APP_MAIN_CLASS_DIR%\automation\templates
echo        Automation templates path set to:
echo        %AUTOMATION_TEMPLATES_PATH%
echo Step-5 Complete


echo[
echo[
echo Step-6 ----Core processing will launch in new window. Once it completes press any key to continue ..
for /r %APP_BASE_PATH% %%i in (*.jar) do set "APP_JAR_NAME=%%~ni"
start cmd /k "cd %APP_BASE_PATH% & mvn clean install -DskipTests=true -Pkafka,mongodb,redis,avro,hive & java -jar %APP_BASE_PATH%\target\%APP_JAR_NAME%.jar"
set /p "CHILD_PROCESS_COMPLETE="
set AUTO_GEN_JUNITS_PATH=%APP_MAIN_CLASS_DIR%\automation\junit 
set AUTO_GEN_JUNITS_PATH=%AUTO_GEN_JUNITS_PATH:main=test%
REM echo Step-6 complete


REM echo[
echo[
echo Step-7 ----Review
echo            Your auto generated Junits are saved at the below location. Please review/adjust/correct as you see fit.
echo            %AUTO_GEN_JUNITS_PATH%


REM echo[
echo[
REM echo Step-3 ----clean up automation remnants       %AUTOMATION_PREP_PATH%
rmdir /s /q %AUTOMATION_PREP_PATH%


echo[
echo[
echo Step-8 ----Launch auto-generated Junits? [Y/N]:
set /p "LAUNCH_JUNITS="
IF %LAUNCH_JUNITS%==Y (
   echo Launching auto-generated Junits
   set MODE=autorun
   cd %APP_BASE_PATH% 
   mvn test -Djacoco.haltOnFailure=false

   echo[
   echo launching Jacoco code coverage report
   timeout /t 5 /nobreak
   start file:///%APP_BASE_PATH%/target/site/jacoco/index.html
)

echo Process complete. Exiting ..