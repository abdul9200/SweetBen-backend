package com.SweetBen.SweetBen.web;

import com.SweetBen.SweetBen.dao.CategoryRepository;
import com.SweetBen.SweetBen.entities.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);

    }

    @Override
    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }
}
