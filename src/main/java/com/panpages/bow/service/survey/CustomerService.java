package com.panpages.bow.service.survey;

import java.util.List;

import com.panpages.bow.model.Customer;
import com.panpages.bow.model.CustomerSurveys;

public interface CustomerService {
	
	List<Customer> findAllCustomer(); 
	
	List<CustomerSurveys> findSurveysByCustomerId(int customerId);

	CustomerSurveys findSurveyByCusSurId(int customerSurveyId);

    public Customer findCustomerById(int customerId);
	
}
