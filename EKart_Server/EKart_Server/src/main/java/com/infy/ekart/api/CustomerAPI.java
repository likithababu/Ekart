package com.infy.ekart.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.infy.ekart.model.Address;
import com.infy.ekart.model.Customer;
import com.infy.ekart.service.CustomerService;

@CrossOrigin
@RestController
@RequestMapping("CustomerAPI")
public class CustomerAPI {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private Environment environment;
	
	static Logger logger = LogManager.getLogger(CustomerAPI.class.getName());
	
	@PostMapping(value = "customerLogin")
	public ResponseEntity<Customer> authenticateCustomer(@RequestBody Customer customer) throws Exception {
		try {
			logger.info("CUSTOMER TRYING TO LOGIN, VALIDATING CREDENTIALS. CUSTOMER EMAIL ID: "+customer.getEmailId());
			
			Customer customerfromDB = customerService.authenticateCustomer(customer.getEmailId(), customer.getPassword());
			
			logger.info("CUSTOMER LOGIN SUCCESS, CUSTOMER EMAIL : "+customerfromDB.getEmailId());
			
			return new ResponseEntity<Customer>(customerfromDB, HttpStatus.OK);
		} 
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, environment.getProperty(e.getMessage()));
		}
	}
		
	@PostMapping(value = "addNewShippingAddress/{customerEmailId:.+}")
	public ResponseEntity<String> addNewShippingAddress(@RequestBody Address address, @PathVariable("customerEmailId") String customerEmailId) throws Exception {
		int addressId;
		
		try
		{
			addressId = customerService.addShippingAddress(customerEmailId,address);
			String message=environment.getProperty("CustomerAPI.NEW_SHIPPING_ADDRESS_ADDED_SUCCESS");
			String toReturn = message+addressId;
			
			toReturn = toReturn.trim();
			return new ResponseEntity<String>(toReturn, HttpStatus.OK);
		}
		catch (Exception e) {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}	}
	
	
	 @GetMapping(value = "getShippingAddress/{addressId}")
	   public ResponseEntity<Address> getShippingAddress(@PathVariable("addressId") Integer addressId) throws Exception {
	 
	       try
	       {
	           Address address = customerService.getShippingAddress(addressId);
	           return new ResponseEntity<Address>(address, HttpStatus.OK);
	           
	       }
	       catch (Exception e) {
	           
	           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
	       }
	   }
}
