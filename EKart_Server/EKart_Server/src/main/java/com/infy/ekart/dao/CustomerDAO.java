package com.infy.ekart.dao;

import com.infy.ekart.model.Address;
import com.infy.ekart.model.Customer;

public interface CustomerDAO {
	
	public String authenticateCustomer(String emailId, String password);
	public Customer getCustomerByEmailId(String emailId);
	public String getPasswordOfCustomer(String emailId);
	
	public Integer addShippingAddress(String customerEmailId, Address address);
	public Address getShippingAddress(Integer addressId) throws Exception;
}
