package com.drawproject.dev.service;

import com.drawproject.dev.model.Category;
import com.drawproject.dev.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getCategory() {
      List<Category> list = new ArrayList<>();
      categoryRepository.findAll().forEach(category -> {
          list.add(category);
      });
      return list;
    }

}
