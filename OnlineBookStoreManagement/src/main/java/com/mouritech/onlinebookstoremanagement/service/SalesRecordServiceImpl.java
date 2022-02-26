package com.mouritech.onlinebookstoremanagement.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mouritech.onlinebookstoremanagement.entity.SalesRecord;
import com.mouritech.onlinebookstoremanagement.exception.CustomerNotFoundException;
import com.mouritech.onlinebookstoremanagement.exception.SalesRecordNotFoundException;
import com.mouritech.onlinebookstoremanagement.repository.CustomerRepository;
import com.mouritech.onlinebookstoremanagement.repository.SalesRecordRepository;


@Service
public class SalesRecordServiceImpl implements SalesRecordService {

	@Autowired
	private SalesRecordRepository salesRecordRepository;
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public SalesRecord insertSalesRecord(SalesRecord newSalesRecord) {
		newSalesRecord.setSalesId(generateSalesId());
		return salesRecordRepository.save(newSalesRecord);
	}

	public String generateSalesId() {
		Random rand = new Random(); // instance of random class
		int upperbound = 255;
		// generate random values from 0-254
		Long pId = (long) rand.nextInt(upperbound);
		return "SAL00" + pId;

	}
	@Override
	public SalesRecord showSalesRecordById(String SalesId) throws SalesRecordNotFoundException {
		return salesRecordRepository.findBySalesId(SalesId)
				.orElseThrow(() -> new SalesRecordNotFoundException("salesRecord not found with salesId "));
	}

	@Override
	public List<SalesRecord> showAllSalesRecords() {
		return salesRecordRepository.findAll();
	}

	@Override
	public SalesRecord updateSalesRecordById(String SalesId, SalesRecord salesRecord) throws SalesRecordNotFoundException {
		SalesRecord newSalesRecord = salesRecordRepository.findBySalesId(SalesId)
				.orElseThrow(() -> new SalesRecordNotFoundException("SalesRecord not found with salesId"));
		newSalesRecord.setAmountToPay(salesRecord.getAmountToPay());
		newSalesRecord.setAmountPaid(salesRecord.getAmountPaid());
		newSalesRecord.setBalance(salesRecord.getBalance());
		newSalesRecord.setNoOfCopies(salesRecord.getNoOfCopies());
		return newSalesRecord;

	}

	@Override
	public void deleteSalesRecordById(String SalesId) throws SalesRecordNotFoundException {
		SalesRecord salesRecord = salesRecordRepository.findBySalesId(SalesId)
				.orElseThrow(() -> new SalesRecordNotFoundException("sales id not found"));
		salesRecordRepository.delete(salesRecord);

	}

	@Override
	public ResponseEntity<List<SalesRecord>> getAllSalesRecordsByCustomerId(Long customerId) throws CustomerNotFoundException {
		if(!customerRepository.existsCustomerByCustomerId(customerId)) {
			throw new CustomerNotFoundException("customer not found with id = " + customerId);
			}
			List<SalesRecord> sales = salesRecordRepository.findByCustomer_CustomerId(customerId);
			return new ResponseEntity<List<SalesRecord>>(sales,HttpStatus.OK);
			}

	@Override
	public ResponseEntity<SalesRecord> createSalesRecord(Long customerId, SalesRecord salesRecord) throws CustomerNotFoundException {
		SalesRecord salesRecord1 = customerRepository.findByCustomerId(customerId).map(
				customer ->{
					salesRecord.setCustomer(customer);
					salesRecord.setSalesId(generateSalesId());
					return salesRecordRepository.save(salesRecord);
				}).orElseThrow(()-> new CustomerNotFoundException("customer not found with id = "  + customerId));
		return new ResponseEntity<SalesRecord>(salesRecord,HttpStatus.CREATED);
	}

	@Override
	public SalesRecord updateSalesById(String salesId, SalesRecord salesRecord) throws SalesRecordNotFoundException {
		SalesRecord existingSalesRecord = salesRecordRepository.findBySalesId(salesId).orElseThrow(() -> new SalesRecordNotFoundException("Sales record not found with the id "+salesId));
		existingSalesRecord.setBookISBN(salesRecord.getBookISBN());
		existingSalesRecord.setInvoiceNo(salesRecord.getInvoiceNo());
		existingSalesRecord.setAmountPaid(salesRecord.getAmountPaid());
		existingSalesRecord.setAmountToPay(salesRecord.getAmountToPay());
		existingSalesRecord.setPrice(salesRecord.getPrice());
		existingSalesRecord.setNoOfCopies(salesRecord.getNoOfCopies());
		existingSalesRecord.setBalance(salesRecord.getBalance());
		existingSalesRecord.setCustomer(salesRecord.getCustomer());
		salesRecordRepository.save(existingSalesRecord);
		return existingSalesRecord;
	}
	
//	public Booking updateBookingById(String bookingId, Booking booking) throws BookingNotFoundException {
//		Booking existingBooking = bookingRepository.findByBookingId(bookingId).orElseThrow(() -> new BookingNotFoundException("booking not found with id " + bookingId));
//		existingBooking.setBookingDate(booking.getBookingDate());
//		existingBooking.setTotalCost(booking.getTotalCost());
//		existingBooking.setFightBooked(booking.getFightBooked());
//		existingBooking.setPassengerId(booking.getPassengerId());
//		existingBooking.setBookingId(booking.getBookingId());
//		existingBooking.setUser(booking.getUser());
//		bookingRepository.save(existingBooking);
//		return existingBooking;
//		}



}
