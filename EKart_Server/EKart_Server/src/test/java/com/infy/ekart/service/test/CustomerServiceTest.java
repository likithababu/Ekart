package com.infy.ekart.service.test;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.infy.ekart.dao.CustomerDAO;
import com.infy.ekart.model.Address;
import com.infy.ekart.service.CustomerService;
import com.infy.ekart.service.CustomerServiceImpl;
import com.infy.ekart.utility.HashingUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {
	

	@Mock
	private CustomerDAO customerDAO;
	
	@InjectMocks
	private CustomerService customerService=new CustomerServiceImpl();
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	
	@Test
	public void authenticateCustomerInValidDetails() throws Exception {
		
	
		String password = "Tom@123";
		Mockito.when(customerDAO.getPasswordOfCustomer(Mockito.anyString())).thenReturn(null);
		expectedException.expect(Exception.class);
		expectedException.expectMessage("CustomerService.INVALID_CREDENTIALS");
		customerService.authenticateCustomer("Toinfosys.com", password);
		
	}
	
	
	@Test
	public void authenticateCustomerInValidDetails1() throws Exception {
		
		String password = "Tom23";
		String hashPassword = HashingUtility.getHashValue(password)+" ";
		Mockito.when(customerDAO.getPasswordOfCustomer(Mockito.anyString())).thenReturn(hashPassword);
		expectedException.expect(Exception.class);
		expectedException.expectMessage("CustomerService.INVALID_CREDENTIALS");
		customerService.authenticateCustomer("Tom@infosys.com", password);
		
	}
	
	@Test
	public void authenticateCustomerInValidDetails2() throws Exception {
		

		String password = "Tom23";
		Mockito.when(customerDAO.getPasswordOfCustomer(Mockito.anyString())).thenReturn(null);
		expectedException.expect(Exception.class);
		expectedException.expectMessage("CustomerService.INVALID_CREDENTIALS");
		customerService.authenticateCustomer("Tom@infosys.com", password);
		
	}
	
	@Test
	public void addShippingAddressValid() throws Exception{
		Address address=new Address();
		address.setAddressId(5000);
		address.setAddressLine1("Ist Main, Building No.3");
		address.setAddressLine2("Park Square");
		address.setCity("Los Angeles");
		address.setContactNumber("7886189066");
		address.setState("California");
		address.setPin("752110");
		Mockito.when(customerDAO.addShippingAddress("",address)).thenReturn(1);
		Assert.assertNotNull(customerService.addShippingAddress(" ", address));
	}
	
	@Test
	public void addShippingAddressInValidCity() throws Exception{
		Address address=new Address();
		address.setAddressId(5000);
		address.setAddressLine1("Ist Main, Building No.3");
		address.setAddressLine2("Park Square");
		address.setCity(" ");
		address.setContactNumber("7886189066");
		address.setState("California");
		address.setPin("752110");
		expectedException.expect(Exception.class);
		expectedException.expectMessage("CustomerValidator.INVALID_CITY");
		
		Mockito.when(customerDAO.addShippingAddress("",address)).thenReturn(null);
		customerService.addShippingAddress(" ", address);
	} 
	
	@Test
	public void addShippingAddressInValidAddress() throws Exception{
		Address address=new Address();
		address.setAddressId(5000);
		address.setAddressLine1("");
		address.setAddressLine2("Park Square");
		address.setCity("Vegas");
		address.setContactNumber("7886189066");
		address.setState("California");
		address.setPin("752110");
		expectedException.expect(Exception.class);
		expectedException.expectMessage("CustomerValidator.INVALID_ADDRESS_LINE_1");
		
		Mockito.when(customerDAO.addShippingAddress("",address)).thenReturn(null);
		customerService.addShippingAddress(" ", address);
	} 
	
	
	@Test
	public void addShippingAddressInValidContact() throws Exception{
		Address address=new Address();
		address.setAddressId(5000);
		address.setAddressLine1("Ist Main,Door no.333");
		address.setAddressLine2("Park Square");
		address.setCity("Vegas");
		address.setContactNumber("7886dd189066");
		address.setState("California");
		address.setPin("752110");
		expectedException.expect(Exception.class);
		expectedException.expectMessage("CustomerValidator.INVALID_CONTACT_NUMBER");
		
		Mockito.when(customerDAO.addShippingAddress("",address)).thenReturn(null);
		customerService.addShippingAddress(" ", address);
	} 
	
}
