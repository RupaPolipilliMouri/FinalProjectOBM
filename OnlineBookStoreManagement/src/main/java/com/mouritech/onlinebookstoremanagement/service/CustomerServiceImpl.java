package com.mouritech.onlinebookstoremanagement.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouritech.onlinebookstoremanagement.entity.Customer;
import com.mouritech.onlinebookstoremanagement.exception.CustomerNotFoundException;
import com.mouritech.onlinebookstoremanagement.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Customer insertCustomer(Customer newCustomer) {
		return customerRepository.save(newCustomer);
	}
	
//	public String generateCustomerId() {
//		Random rand = new Random(); // instance of random class
//		int upperbound = 255;
//		// generate random values from 0-254
//		Long pId = (long) rand.nextInt(upperbound);
//		return "C00" + pId;
//
//	}

	@Override
	public Customer showCustomerById(Long customerId) throws CustomerNotFoundException {

		return customerRepository.findByCustomerId(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("The following customer id is not found"));

	}

	@Override
	public List<Customer> showAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Customer updateCustomerById(Long customerId, Customer customer) throws CustomerNotFoundException {
		Customer existingCustomer = customerRepository.findByCustomerId(customerId).orElseThrow(() -> new CustomerNotFoundException("The following customer id is not found"));
		existingCustomer.setCustomerEmailId(customer.getCustomerEmailId());
		existingCustomer.setCustomerContactNumber(customer.getCustomerContactNumber());
		existingCustomer.setAddress(customer.getAddress());
		existingCustomer.setCustomerName(customer.getCustomerName());
		return existingCustomer;
	}

	@Override
	public void deleteCustomerById(Long customerId) throws CustomerNotFoundException {
		Customer exisitingCustomer = customerRepository.findByCustomerId(customerId).orElseThrow(() -> new CustomerNotFoundException("The folowing customer id is not found"));
		customerRepository.delete(exisitingCustomer);

	}
//
//	@Override
//	public ResponseEntity<List<Customer>> getAllCustomersBySalesId(int SalesId) throws SalesRecordNotFoundException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseEntity<Customer> createCustomer(int SalesId, Customer newCustomer)
//			throws SalesRecordNotFoundException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Customer getCustomerNameBySalesId(int SalesId, String customername)
//			throws CustomerNameAlreadyExistsException {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
