package com.infy.ekart.dao.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.infy.ekart.dao.CustomerDAO;
import com.infy.ekart.model.Address;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
public class CustomerDAOTest {
	@Autowired
	private CustomerDAO customerDAO;
	
	
	
	@Test
	public void addShippingAddressValidDetails() throws Exception{
		Address address=new Address();
		address.setAddressId(5000);
		address.setAddressLine1("Ist Main, Building No.3");
		address.setAddressLine2("Park Square");
		address.setCity("Los Angeles");
		address.setContactNumber("7886189066");
		address.setState("California");
		address.setPin("752110");
		customerDAO.addShippingAddress("tom@infosys.com", address);
		
	}
				
	@Test
	public void getPasswordofCustomerValidDetails() {
		customerDAO.getPasswordOfCustomer("Tom@infosys.com");
		
	}
	
	
	
	@Test
	public void getCustomerbyEmailIdValidDetails() {
		customerDAO.getCustomerByEmailId("Tom@infosys.com");
	}
	@Test
	public void getCustomerbyEmailIdInValidDetails() {
		customerDAO.getCustomerByEmailId("T12");
	}

}
