package com.infy.ekart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.ekart.dao.CustomerDAO;
import com.infy.ekart.model.Address;
import com.infy.ekart.model.Customer;
import com.infy.ekart.validator.CustomerValidator;

@Service( value = "customerService" )
@Transactional
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Override
	public Customer authenticateCustomer(String emailId, String password)
			throws Exception{

		Customer customer = null;
		String customerEmailIdFromDAO = customerDAO.authenticateCustomer(emailId.toLowerCase(), password);
		if(customerEmailIdFromDAO!=null){
			
				customer  = customerDAO.getCustomerByEmailId(customerEmailIdFromDAO);
		}
		else
			throw new Exception ("CustomerService.INVALID_CREDENTIALS");
		
		return customer;
		
	}
	
	@Override
	public Integer addShippingAddress(String customerEmailId, Address address)
			throws Exception {
		
		CustomerValidator.validateAddress(address);
		Integer newAddressID = customerDAO.addShippingAddress(customerEmailId, address);
		
		return newAddressID; 
	}
	
	 @Override
	   public Address getShippingAddress(Integer addressId) throws Exception {
	       
	       return customerDAO.getShippingAddress(addressId);
	       
	   }
}
