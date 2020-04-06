package com.infy.ekart.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.infy.ekart.entity.AddressEntity;
import com.infy.ekart.entity.CustomerCartEntity;
import com.infy.ekart.entity.CustomerEntity;
import com.infy.ekart.model.Address;
import com.infy.ekart.model.Customer;
import com.infy.ekart.model.CustomerCart;
import com.infy.ekart.model.Product;

@Repository(value = "customerDAO")
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public String authenticateCustomer(String emailId, String password){
		
		Query query = entityManager.createQuery("select c from CustomerEntity c where c.emailId = '"+emailId+"' and c.password = '"+password+"'");
		
		List<CustomerEntity> customerEntities = query.getResultList();
		if(customerEntities.isEmpty())
			return null;

		return customerEntities.get(0).getEmailId();
	}
	
	@Override
	public String getPasswordOfCustomer(String emailId) {
		
		String password = null;
		emailId = emailId.toLowerCase();
		CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, emailId);
		if (customerEntity!=null){
			password = customerEntity.getPassword();
		}
		
		return password;
	}


	@Override
	public Customer getCustomerByEmailId(String emailId) {

		Customer customer = null;
		emailId = emailId.toLowerCase();
		List<Address> customerAddresses = new ArrayList<>();
		
		CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, emailId);
		if (customerEntity!=null){
			customer = new Customer();
			customer.setEmailId(customerEntity.getEmailId());
			customer.setName(customerEntity.getName());
			customer.setPhoneNumber(customerEntity.getPhoneNumber());
			for (AddressEntity i : customerEntity.getAddressEntities()) {
				Address address = new Address();
				address.setAddressId(i.getAddressId());
				address.setAddressLine1(i.getAddressLine1());
				address.setAddressLine2(i.getAddressLine2());
				address.setCity(i.getCity());
				address.setContactNumber(i.getContactNumber());
				address.setPin(i.getPin());
				address.setState(i.getState());
				
				customerAddresses.add(address);
			}
			customer.setAddresses(customerAddresses);
			
			List<CustomerCart> customerCarts = new ArrayList<>();
			for (CustomerCartEntity customerCartEntity : customerEntity.getCustomerCarts()) {	
				CustomerCart cart = new CustomerCart();
				cart.setCartId(customerCartEntity.getCartId());
				cart.setQuantity(customerCartEntity.getQuantity());
					Product product = new Product();
					product.setBrand(customerCartEntity.getProductEntity().getBrand());
					product.setCategory(customerCartEntity.getProductEntity().getCategory());
					product.setDescription(customerCartEntity.getProductEntity().getDescription());
					product.setDiscount(customerCartEntity.getProductEntity().getDiscount());
					product.setName(customerCartEntity.getProductEntity().getName());
					product.setPrice(customerCartEntity.getProductEntity().getPrice());
					product.setProductId(customerCartEntity.getProductEntity().getProductId());
					product.setQuantity(customerCartEntity.getProductEntity().getQuantity());
				
				cart.setProduct(product);
				
				customerCarts.add(cart);
			}
			customer.setCustomerCarts(customerCarts);
			
		}
		
		return customer;
	}
	
	@Override
	public Integer addShippingAddress(String customerEmailId, Address address) {
		
		CustomerEntity customerEntity = null;
		
		Integer newAddressId = null;
		
		customerEntity = entityManager.find(CustomerEntity.class, customerEmailId);
		
		List<AddressEntity> customerAddressEntities = customerEntity.getAddressEntities();
		
		AddressEntity newShippingAddress = new AddressEntity();
		newShippingAddress.setAddressLine1(address.getAddressLine1());
		newShippingAddress.setAddressLine2(address.getAddressLine2());
		newShippingAddress.setCity(address.getCity());
		newShippingAddress.setContactNumber(address.getContactNumber());
		newShippingAddress.setPin(address.getPin());
		newShippingAddress.setState(address.getState());
		
		customerAddressEntities.add(newShippingAddress);
		customerEntity.setAddressEntities(customerAddressEntities);
		
		entityManager.persist(customerEntity);
		
		
		List<AddressEntity> customerAddressEntitiesAfterAddition = customerEntity.getAddressEntities();
		
		AddressEntity newAddress = customerAddressEntitiesAfterAddition.get(customerAddressEntitiesAfterAddition.size()-1);
		newAddressId = newAddress.getAddressId();
		return newAddressId;
		
	}
	
	@Override
	   public Address getShippingAddress(Integer addressId) throws Exception {
	       AddressEntity addressEntity = entityManager.find(AddressEntity.class, addressId);
	       Address address = new Address();
	       address.setAddressId(addressEntity.getAddressId());
	       address.setAddressLine1(addressEntity.getAddressLine1());
	       address.setAddressLine2(addressEntity.getAddressLine2());
	       address.setCity(addressEntity.getCity());
	       address.setContactNumber(addressEntity.getContactNumber());
	       address.setPin(addressEntity.getPin());
	       address.setState(addressEntity.getState());
	       
	       return address;
	       
	   }
}
