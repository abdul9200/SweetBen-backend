package com.SweetBen.SweetBen.web;

import com.SweetBen.SweetBen.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CategoryRestController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/category")
    List<Category> listCategories(){
        return categoryService.listCategory();
    }
    @DeleteMapping("/deletecategory")
    void deleteCategory(Category category){
        categoryService.deleteCategory(category);
    }
    @PostMapping("/addcategory")
    Category addCategory(Category category){
        return categoryService.addCategory(category);
    }
}
