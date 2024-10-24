package com.RODS.service;


import com.RODS.dto.CategoryDTO;
import com.RODS.repository.CategoriesRepository;
import com.RODS.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;
    @Autowired
    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    /**
     * Get categories for creating dish
     * @return List<CategoryDTO>
     */
    public List<CategoryDTO> findAllCategories() {
        return Mapper.categoriesToCategoriesDTO(categoriesRepository.findAll());
    }


}

