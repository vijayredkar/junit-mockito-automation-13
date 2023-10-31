package PLCHLDR-AUTOMATION-PCKG ;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MetaDataDeclaredMethods 
{
	private String fullSignature;
	private String 	methodName; //findall
	private String 	mockBeanInstancename; //custrepo
	private String 	inputParamInstanceName;//test2	
	private int 	numberOfInputParams; //2
	private Class<?> 	returnTypeAsRaw;    //int
	private String 	returnTypeAsString; //int
	private List<String>  stubEligibleLine = new ArrayList<String>();
	private String formattedMethodSignature;
	private List<String> assertionsList;
	List<String>  whenThenList = new ArrayList<String>();
	String constructedJunitMethod = new String();
	private String targetMethodInvocation;
	private boolean returnTypePrimitive = false;
	private Annotation[] declaredAnnotationsForMtd;
	private boolean isStereoTypeController=false;
	private boolean isTypeKafkaProducer=false;//vjNew5
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMockBeanInstancename() {
		return mockBeanInstancename;
	}
	public void setMockBeanInstancename(String mockBeanInstancename) {
		this.mockBeanInstancename = mockBeanInstancename;
	}
	public String getInputParamInstanceName() {
		return inputParamInstanceName;
	}
	public void setInputParamInstanceName(String inputParamInstanceName) {
		this.inputParamInstanceName = inputParamInstanceName;
	}
	public int getNumberOfInputParams() {
		return numberOfInputParams;
	}
	public void setNumberOfInputParams(int numberOfInputParams) {
		this.numberOfInputParams = numberOfInputParams;
	}
	public Class<?> getReturnTypeAsRaw() {
		return returnTypeAsRaw;
	}
	public void setReturnTypeAsRaw(Class<?> returnTypeAsRaw) {
		this.returnTypeAsRaw = returnTypeAsRaw;
	}
	public String getReturnTypeAsString() {
		return returnTypeAsString;
	}
	public void setReturnTypeAsString(String returnTypeAsString) {
		this.returnTypeAsString = returnTypeAsString;
	}
	public List<String> getStubEligibleLine() {
		return stubEligibleLine;
	}
	public void setStubEligibleLine(List<String> stubEligibleLine) {
		this.stubEligibleLine = stubEligibleLine;
	}
	public String getFullSignature() {
		return fullSignature;
	}
	public void setFullSignature(String fullSignature) {
		this.fullSignature = fullSignature;
	}
	public String getFormattedMethodSignature() {
		return formattedMethodSignature;
	}
	public void setFormattedMethodSignature(String formattedMethodSignature) {
		this.formattedMethodSignature = formattedMethodSignature;
	}
	public List<String> getAssertionsList() {
		return assertionsList;
	}
	public void setAssertionsList(List<String> assertionsList) {
		this.assertionsList = assertionsList;
	}
	public List<String> getWhenThenStubsList() {
		return whenThenList;
	}
	public void setWhenThenStubsList(List<String> whenThenList) {
		this.whenThenList = whenThenList;
	}
	public List<String> getWhenThenList() {
		return whenThenList;
	}
	public void setWhenThenList(List<String> whenThenList) {
		this.whenThenList = whenThenList;
	}
	public String getConstructedJunitMethod() {
		return constructedJunitMethod;
	}
	public void setConstructedJunitMethod(String constructedJunitMethods) {
		this.constructedJunitMethod = constructedJunitMethods;
	}
	public String getTargetMethodInvocation() {
		return targetMethodInvocation;
	}	
	public boolean isStereoTypeController() {
		return isStereoTypeController;
	}
	public Annotation[] getDeclaredAnnotationsForMtd() {
		return declaredAnnotationsForMtd;
	}
	public void setDeclaredAnnotationsForMtd(Annotation[] declaredAnnotationsForMtd) {
		this.declaredAnnotationsForMtd = declaredAnnotationsForMtd;
	}
	public void setStereoTypeController(boolean isStereoTypeController) {
		this.isStereoTypeController = isStereoTypeController;
	}
	public void setTargetMethodInvocation(String targetMethodInvocation) {
		this.targetMethodInvocation = targetMethodInvocation;
	}
	public boolean isReturnTypePrimitive() {
		return returnTypePrimitive;
	}
	public void setReturnTypePrimitive(boolean returnTypePrimitive) {
		this.returnTypePrimitive = returnTypePrimitive;
	}
	public boolean isTypeKafkaProducer() {
		return isTypeKafkaProducer;
	}
	public void setTypeKafkaProducer(boolean isTypeKafkaProducer) {
		this.isTypeKafkaProducer = isTypeKafkaProducer;
	}
	
}
