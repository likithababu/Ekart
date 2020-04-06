package com.infy.ekart.service;

import com.infy.ekart.model.Address;
import com.infy.ekart.model.Customer;

public interface CustomerService {

	public Customer authenticateCustomer(String emailId, String password) throws Exception;
	public Integer addShippingAddress(String customerEmailId, Address address) throws Exception;
	public Address getShippingAddress(Integer addressId) throws Exception;
}
