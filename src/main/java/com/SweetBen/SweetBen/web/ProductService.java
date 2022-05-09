package com.SweetBen.SweetBen.web;


import com.SweetBen.SweetBen.entities.Product;

public interface ProductService {
    Product addProduct (Product product);
    void deleteProduct(Product product);
}
