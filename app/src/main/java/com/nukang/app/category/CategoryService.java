package com.nukang.app.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository repository;

    public List<Category> getAll(){
        return repository.findAll();
    }

    public Category create(Category category) {
        return repository.save(category);
    }
}
