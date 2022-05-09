package com.SweetBen.SweetBen.web;

import com.SweetBen.SweetBen.entities.Product;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
public class ProductRestController {
    ProductService productService;
    @PostMapping("/products")
    Product addProduct(Product product){
        return productService.addProduct(product);
    }
    @DeleteMapping("/deleteproduct")
    void deleteProduct(Product product){
        productService.deleteProduct(product);
    }
}
