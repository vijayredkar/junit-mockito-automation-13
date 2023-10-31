package com.banknext.customer.mgt.event.consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.banknext.customer.mgt.repo.CustomerRepository;
import com.banknext.customer.mgt.repo.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;


@Component
public class CustomerTxnConsumerType1 {
	
	Log LOGGER = LogFactory.getLog(CustomerTxnConsumerType1.class);
	
	@Autowired
	CustomerRepository custRepo;

		@KafkaListener(groupId = "grp1", topics = "topic1")
		public void receivedMessage(Customer customer) throws JsonProcessingException 
		{
			System.out.println("msg recieved : " + customer);
			System.out.println("Risk level high ");
			custRepo.delete(customer);			
		}
}