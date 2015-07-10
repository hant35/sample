package com.panpages.bow.service.survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panpages.bow.dao.CustomerDao;
import com.panpages.bow.model.Customer;
import com.panpages.bow.model.CustomerSurveys;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public List<Customer> findAllCustomer() {
		return customerDao.findAllCustomers();
	}

	@Override
	public List<CustomerSurveys> findSurveysByCustomerId(int customerId) {
		return customerDao.findSurveysByCustomerId(customerId);
	}

	@Override
	public CustomerSurveys findSurveyByCusSurId(int customerSurveyId) {
		return customerDao.findSurveyByCusSurId(customerSurveyId);
	}

    @Override
    public Customer findCustomerById(int customerId) {
        return customerDao.findCustomerById(customerId);
    }

}
