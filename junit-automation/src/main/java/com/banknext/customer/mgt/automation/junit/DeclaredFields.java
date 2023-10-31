package com.banknext.customer.mgt.automation.junit;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class DeclaredFields 
{
	private String instanceType;

	public String getInstanceType() {
		return instanceType;
	}

	public void setInstanceType(String instanceType) {
		this.instanceType = instanceType;
	}

}
