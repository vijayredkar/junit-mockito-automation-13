package PLCHLDR-AUTOMATION-PCKG ;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.springframework.stereotype.Component;

@Component
public class MetaDataCollect 
{
	private List<String> mockDependencies =  new ArrayList<String>();
	private Map<String, DeclaredFields>  declaredFieldsMap = new HashMap<String, DeclaredFields>();
	//private DeclaredFields declaredFields;
	private List<Field>  allBeanDependencies = new ArrayList<Field>();
//	private List<String>  stubEligibleLine = new ArrayList<String>();
	private Map<String, String> methodBodyMap = new HashMap<String, String>();
	//private Map<String, MetaDataDeclaredMethods>  metaDataDeclaredMethodsMap = new HashMap<String, MetaDataDeclaredMethods>();
	private Map<String, MetaDataDeclaredMethods>  metaDataDeclaredMethodsMap = new ConcurrentHashMap<String, MetaDataDeclaredMethods>();
	private Annotation[] declaredAnnotationsForClass;

	public List<String> getMockDependencies() {
		return mockDependencies;
	}

	public void setMockDependencies(List<String> mockDependencies) {
		this.mockDependencies = mockDependencies;
	}

	public Map<String, DeclaredFields> getDeclaredFieldsMap() {
		return declaredFieldsMap;
	}

	public void setDeclaredFieldsMap(Map<String, DeclaredFields> declaredFieldsMap) {
		this.declaredFieldsMap = declaredFieldsMap;
	}

	public List<Field> getAllBeanDependencies() {
		return allBeanDependencies;
	}

	public void setAllBeanDependencies(List<Field> allBeanDependencies) {
		this.allBeanDependencies = allBeanDependencies;
	}
	/*
	public List<String> getStubEligibleLine() {
		return stubEligibleLine;
	}

	public void setStubEligibleLine(List<String> stubEligibleLine) {
		this.stubEligibleLine = stubEligibleLine;
	}
	*/

	public Map<String, String> getMethodBodyMap() {
		return methodBodyMap;
	}

	public void setMethodBodyMap(Map<String, String> methodBodyMap) {
		this.methodBodyMap = methodBodyMap;
	}

	public Map<String, MetaDataDeclaredMethods> getMetaDataDeclaredMethodsMap() {
		return metaDataDeclaredMethodsMap;
	}

	public void setMetaDataDeclaredMethodsMap(Map<String, MetaDataDeclaredMethods> metaDataDeclaredMethodsMap) {
		this.metaDataDeclaredMethodsMap = metaDataDeclaredMethodsMap;
	}

	public Annotation[] getDeclaredForAnnotationsClass() {
		return declaredAnnotationsForClass;
	}

	public void setDeclaredAnnotationsForClass(Annotation[] declaredAnnotationsForClass) {
		this.declaredAnnotationsForClass = declaredAnnotationsForClass;
	}
	
	
}
