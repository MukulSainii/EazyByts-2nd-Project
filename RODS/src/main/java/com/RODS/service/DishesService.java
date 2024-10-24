package com.RODS.service;


import com.RODS.configuration.Bundler;
import com.RODS.dto.DishDTO;
import com.RODS.dto.PageableDishesDTO;
import com.RODS.entity.Categories;
import com.RODS.entity.Dishes;
import com.RODS.repository.CategoriesRepository;
import com.RODS.repository.DishesRepository;
import com.RODS.utils.Constants;
import com.RODS.utils.Mapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DishesService {
    // todo remove this or move to controller
    // todo make validation on page -> if -1 or 100 get first or last page
    @Value( "${page.size}" )
    private int pageSize;
    @Value( "${page.sortDefault}" )
    private String sortDefault;
    @Value( "${page.sortDirectionDefault}" )
    private String sortDirectionDefault;
    @Value( "${page.category}" )
    private int categoryIdDefault;

    private final DishesRepository dishesRepository;
    private final CategoriesRepository categoriesRepository;
    private final Bundler bundler;
    @Autowired
    public DishesService(DishesRepository dishesRepository,
                         CategoriesRepository categoriesRepository,
                         Bundler bundler) {
        this.dishesRepository = dishesRepository;
        this.categoriesRepository = categoriesRepository;
        this.bundler = bundler;
    }

    /**
     * Gets paginated dto for main page
     * @param pageNo page number
     * @param sortField field for sort
     * @param sortDirection asc or desc sort
     * @param categoryId if there is filter by categories
     * @return dto for front
     */
    @Transactional
    public PageableDishesDTO findAllDishesPaginated(Integer pageNo, String sortField,
                                                    String sortDirection, Long categoryId) {
        categoryId = categoryId == null || categoryId < 0 ? categoryIdDefault : categoryId;
        Sort sort = validationSetDefault(sortField, sortDirection);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Dishes> page = (categoryId == 0)
                ? dishesRepository.findAll(pageable)
                : dishesRepository.findByCategories_Id(categoryId, pageable);

        List<Categories> categories = categoriesRepository.findAll();

        return PageableDishesDTO.builder()
                .dishes(Mapper.dishesToDishesDTO(page.getContent()))
                .categories(Mapper.categoriesToCategoriesDTO(categories))
                .currentPage(pageNo)
                .totalPages(page.getTotalPages())
                .sortField(sortField)
                .sortDirection(sortDirection)
                .categoryId(categoryId)
                .build();
    }
    /**
     * Set default sort if failed get from front
     * @param sortField sort field
     * @param sortDirection asc or desc
     * @return Sort
     */
    private Sort validationSetDefault(String sortField, String sortDirection) {
        sortField = sortField == null || sortField.equals(Constants.NULL)
                ? sortDefault
                : (sortField.equals(Constants.NAME)) ? bundler.getMsg("db.name") : sortField;
        sortDirection = sortDirection != null &&
                (sortDirection.equalsIgnoreCase("asc") ||
                        sortDirection.equalsIgnoreCase("desc"))
                ? sortDirection
                : sortDirectionDefault;
        return sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
    }

    /**
     * Get all dishes for manager
     * @return List<DishDTO>
     */
    public List<DishDTO> findAllDishes() {
        Sort sort = Sort.by("id").ascending();
        return Mapper.dishesToDishesDTO(dishesRepository.findAll(sort));
    }

    /**
     * Create new dish
     * @param dish get dish and validate
     * @return Dishes
     * @throws NoSuchElementException if category not found
     */
    @Transactional
    public Dishes saveNewDish (@NonNull Dishes dish) throws NoSuchElementException {
        dish.setTime(LocalDateTime.now());
        dish.setCategories(categoriesRepository.findById(dish.getCategories().getId())
                .orElseThrow(() -> new NoSuchElementException(bundler.getLogMsg(Constants.DISHES_ONE_DBE))));
        return dishesRepository.save(dish);
    }

    /**
     * for get mapping for update dish page
     * @param id dish id
     * @return Dishes
     */
    public Dishes findById(Long id) {
        return dishesRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        bundler.getLogMsg(Constants.DISHES_ONE_DBE)));
    }

    /**
     * Update dish
     * @param dish dish and validation
     */
    public void update(@NonNull Dishes dish) {
        dish.setTime(LocalDateTime.now());
        dishesRepository.save(dish);
    }
    /**
     * Delete dish
     * @param id dish id
     */
    public void delete(@NonNull Long id) {
        dishesRepository.deleteById(id);
    }
}
