package com.tailor.dao;

import com.tailor.entity.Customer;
import javafx.collections.ObservableList;

public interface CustomerDao {
    public ObservableList<Customer> getCustomers();
    public Customer getCustomer(long id);
    public void saveCustomer(Customer customer);
    public void updateCustomer(Customer customer);
    public void deleteCustomer(Customer customer);
    public boolean checkCustomer(String username);
}
