package com.panpages.bow.dao;

import java.util.List;

import com.panpages.bow.model.Customer;
import com.panpages.bow.model.CustomerSurveys;

public interface CustomerDao {

	List<Customer> findAllCustomers();

	List<CustomerSurveys> findSurveysByCustomerId(int customerId);

	CustomerSurveys findSurveyByCusSurId(int customerSurveyId);

    public Customer findCustomerById(int customerId);
	
}
