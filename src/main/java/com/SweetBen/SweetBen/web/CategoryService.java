package com.SweetBen.SweetBen.web;

import com.SweetBen.SweetBen.entities.Category;

import java.util.List;

public interface CategoryService {
    Category addCategory(Category category);
    void deleteCategory(Category category);
    List<Category> listCategory();

}
