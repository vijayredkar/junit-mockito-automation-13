package com.banknext.customer.mgt.automation.junit.backup;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.*;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.*;
import com.banknext.customer.mgt.repo.CustomerRepository;
import com.banknext.customer.mgt.repo.*;
import com.banknext.customer.mgt.repo.model.Customer;
import com.banknext.customer.mgt.repo.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.*;
import com.banknext.customer.mgt.event.consumer.CustomerTxnConsumerType1;;

public class DtoFactoryCustomerTxnConsumerType1
{

public List getList_WithStubbedValues()
{
List _listObj  = new ArrayList();
_listObj.add("test1");
_listObj.add("test2");
_listObj.add("test3");
_listObj.add("test4");
_listObj.add("test5");
return _listObj;
}

public Optional<List> getOptList_WithStubbedValues()
{
return Optional.of(getList_WithStubbedValues());
}

public Map getMap_WithStubbedValues()
{
Map _mapObj  = new HashMap();
_mapObj.put(1,"test1");
_mapObj.put(2,"test2");
_mapObj.put(3,"test3");
_mapObj.put(4,"test4");
_mapObj.put(5,"test5");
return _mapObj;
}

public Optional<Map> getOptMap_WithStubbedValues()
{
return Optional.of(getMap_WithStubbedValues());
}
 
} //finish class 
