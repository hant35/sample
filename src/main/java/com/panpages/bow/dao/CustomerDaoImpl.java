package com.panpages.bow.dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.panpages.bow.model.Customer;
import com.panpages.bow.model.CustomerSurveys;

@Repository("customerDao")
public class CustomerDaoImpl extends AbstractDao implements CustomerDao {

    @Autowired
    ApplicationContext ctx;

    private static final Logger logger = Logger.getLogger(CustomerDaoImpl.class);

    @Override

    public List<Customer> findAllCustomers() {
        Query query = getSession().createQuery("from Customer as s where s.enable = 1");
        List<Customer> result = new LinkedList<Customer>();
        for (Object obj : query.list()) {
            result.add((Customer) obj);
        }
        return result;
    }

    @Override
    public List<CustomerSurveys> findSurveysByCustomerId(int customerId) {
        Query query = getSession().createQuery("from CustomerSurveys as s where s.customerId = :customerId");
        query.setInteger("customerId", customerId);
        List<CustomerSurveys> result = new LinkedList<CustomerSurveys>();
        for (Object obj : query.list()) {
            result.add((CustomerSurveys) obj);
        }
        return result;
    }

    @Override
    public CustomerSurveys findSurveyByCusSurId(int customerSurveyId) {
        Query query = getSession().createQuery("from CustomerSurveys as s where s.customerSurveyId = :customerSurveyId");
        query.setInteger("customerSurveyId", customerSurveyId);
        for (Object obj : query.list()) {
            return (CustomerSurveys) obj;
        }
        return null;
    }

    @Override
    public Customer findCustomerById(int customerId) {
        Query query = getSession().createQuery("from Customer as s where s.enable = 1 and s.customerId = :customerId");
        query.setInteger("customerId", customerId);
        for (Object obj : query.list()) {
            return (Customer) obj;
        }
        return null;
    }

}
