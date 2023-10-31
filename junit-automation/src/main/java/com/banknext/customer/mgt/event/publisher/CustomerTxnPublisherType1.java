package com.banknext.customer.mgt.event.publisher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.banknext.customer.mgt.repo.CustomerRepository;
import com.banknext.customer.mgt.repo.model.Customer;

@Component
public class CustomerTxnPublisherType1 {
	
	@Autowired
	CustomerRepository custRepo;
	
	@Value("${new-customer-created-topic}")
	String custTxnTopic;
	//String custTxnTopic="new_customer_created_topic";
	
	@Autowired
	private KafkaTemplate<String, Customer> customerKafkaTemplate;
	
	Log LOGGER = LogFactory.getLog(CustomerTxnPublisherType1.class);
		
	public void publish(Customer customer) 
	{
		custRepo.save(customer);
		customerKafkaTemplate.send("new_customer_created_topic", customer);		
		LOGGER.info("---- Message published - Customer created : " +  customer.toString());
    }	
}