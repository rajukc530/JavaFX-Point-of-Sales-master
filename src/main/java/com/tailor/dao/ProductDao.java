package com.tailor.dao;

import com.tailor.entity.Product;

import javafx.collections.ObservableList;

public interface ProductDao {
    
    public ObservableList<Product> getProducts();
    public Product getProduct(long id);
    public Product getProductByName(String productName);
    public void saveProduct(Product product);
    public void updateProduct(Product product);
    public void decreaseProduct(Product product);
    public void deleteProduct(Product product);
    public ObservableList<String> getProductNames();
    public void increaseProduct(Product product);
}
