package com.tailor.interfaces;

import com.tailor.entity.Supplier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface SupplierInterface {
    
    public ObservableList<Supplier> SUPPLIERLIST = FXCollections.observableArrayList();
}
