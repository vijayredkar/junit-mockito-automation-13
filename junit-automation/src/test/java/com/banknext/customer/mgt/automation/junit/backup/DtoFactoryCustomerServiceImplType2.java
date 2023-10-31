package com.banknext.customer.mgt.automation.junit.backup;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.List;
import java.util.*;
import java.util.Optional;
import java.util.*;
import java.util.ArrayList;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.*;
import com.banknext.customer.mgt.repo.AccountRepository;
import com.banknext.customer.mgt.repo.*;
import com.banknext.customer.mgt.repo.CustomerRepository;
import com.banknext.customer.mgt.repo.*;
import com.banknext.customer.mgt.repo.LoanRepository;
import com.banknext.customer.mgt.repo.*;
import com.banknext.customer.mgt.repo.model.Customer;
import com.banknext.customer.mgt.repo.model.*;
import com.banknext.customer.mgt.service.CustomerServiceImplType2;;

public class DtoFactoryCustomerServiceImplType2
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

public Customer getCustomer() {
Customer _CustomerObj  = new Customer();
_CustomerObj.setCustomerNum(1);

_CustomerObj.setEmail("testEmail");

_CustomerObj.setFirstName("testFirstName");

_CustomerObj.setLastName("testLastName");

_CustomerObj.setOccupation("testOccupation");

_CustomerObj.setCitizenship("testCitizenship");

_CustomerObj.setCreatedDate(new java.sql.Timestamp(1l));

_CustomerObj.setModifiedDate(new java.sql.Timestamp(1l));

_CustomerObj.setEnabled(true);

_CustomerObj.setTxnRemarks("testTxnRemarks");

return _CustomerObj;}

public Optional< Customer> getOptCustomer() {
Customer _CustomerObj  = new Customer();
_CustomerObj.setCustomerNum(1);

_CustomerObj.setEmail("testEmail");

_CustomerObj.setFirstName("testFirstName");

_CustomerObj.setLastName("testLastName");

_CustomerObj.setOccupation("testOccupation");

_CustomerObj.setCitizenship("testCitizenship");

_CustomerObj.setCreatedDate(new java.sql.Timestamp(1l));

_CustomerObj.setModifiedDate(new java.sql.Timestamp(1l));

_CustomerObj.setEnabled(true);

_CustomerObj.setTxnRemarks("testTxnRemarks");

return Optional.of(_CustomerObj);}
 
} //finish class 
