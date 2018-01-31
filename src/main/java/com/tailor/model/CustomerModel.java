package com.tailor.model;

import com.tailor.HibernateUtil;
import com.tailor.dao.CustomerDao;
import com.tailor.entity.Customer;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Query;
import org.hibernate.Session;

public class CustomerModel implements CustomerDao {

    private static Session session;

    @Override
    public ObservableList<Customer> getCustomers() {

        ObservableList<Customer> list = FXCollections.observableArrayList();

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Customer> customers = session.createQuery("from Customer").list();
        session.beginTransaction().commit();
        customers.stream().forEach(list::add);

        return list;
    }

    @Override
    public Customer getCustomer(long id) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Customer customer = session.get(Customer.class, id);
        session.getTransaction().commit();
        return customer;
    }
    
    
    @Override
    public void saveCustomer(Customer customer) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(customer);
        session.getTransaction().commit();
    }

    @Override
    public void updateCustomer(Customer customer) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Customer c = session.get(Customer.class, customer.getId());
        c.setFirstName(customer.getFirstName());
        c.setLastName(customer.getLastName());
        c.setPhone(customer.getPhone());
        c.setAddress(customer.getAddress());
        c.setEmail(customer.getEmail());
        session.getTransaction().commit();
    }

    @Override
    public void deleteCustomer(Customer customer) {
        
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Customer c = session.get(Customer.class, customer.getId());
        session.delete(c);
        session.getTransaction().commit();
    }
    
    @Override
    public boolean checkCustomer(String email) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from Customer where email = :email");
        query.setParameter("email", email);
        Customer customer = (Customer) query.uniqueResult();
        session.getTransaction().commit();

        return customer != null;
    }
 
}
