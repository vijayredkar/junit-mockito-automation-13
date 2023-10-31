package com.banknext.customer.mgt.automation.junit.backup;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.List;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.banknext.customer.mgt.repo.model.Account;
import com.banknext.customer.mgt.repo.model.*;
import com.banknext.customer.mgt.repo.model.Customer;
import com.banknext.customer.mgt.repo.model.*;
import com.banknext.customer.mgt.service.AccountService;
import com.banknext.customer.mgt.service.*;
import com.banknext.customer.mgt.service.CustomerService;
import com.banknext.customer.mgt.service.*;
import com.banknext.customer.mgt.controller.CustomerMgtControllerType3;;

public class DtoFactoryCustomerMgtControllerType3
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

public Account getAccount() {
Account _AccountObj  = new Account();
_AccountObj.setCustomerNum(1);

_AccountObj.setEmail("testEmail");

_AccountObj.setFirstName("testFirstName");

_AccountObj.setLastName("testLastName");

_AccountObj.setOccupation("testOccupation");

_AccountObj.setCitizenship("testCitizenship");

_AccountObj.setCreatedDate(new java.sql.Timestamp(1l));

_AccountObj.setModifiedDate(new java.sql.Timestamp(1l));

_AccountObj.setEnabled(true);

_AccountObj.setTxnRemarks("testTxnRemarks");

return _AccountObj;}

public Optional< Account> getOptAccount() {
Account _AccountObj  = new Account();
_AccountObj.setCustomerNum(1);

_AccountObj.setEmail("testEmail");

_AccountObj.setFirstName("testFirstName");

_AccountObj.setLastName("testLastName");

_AccountObj.setOccupation("testOccupation");

_AccountObj.setCitizenship("testCitizenship");

_AccountObj.setCreatedDate(new java.sql.Timestamp(1l));

_AccountObj.setModifiedDate(new java.sql.Timestamp(1l));

_AccountObj.setEnabled(true);

_AccountObj.setTxnRemarks("testTxnRemarks");

return Optional.of(_AccountObj);}
 
} //finish class 
