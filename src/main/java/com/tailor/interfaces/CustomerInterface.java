package com.tailor.interfaces;

import com.tailor.entity.Customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface CustomerInterface {
    
    public ObservableList<Customer> CUSTOMERLIST = FXCollections.observableArrayList();
}
