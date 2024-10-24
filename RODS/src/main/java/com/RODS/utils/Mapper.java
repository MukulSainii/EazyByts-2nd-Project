package com.RODS.utils;


import com.RODS.dto.CategoryDTO;
import com.RODS.dto.DishDTO;
import com.RODS.entity.Baskets;
import com.RODS.entity.Categories;
import com.RODS.entity.Dishes;


import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Converts entities to DTO objects
 */
public class Mapper {

    private static DishDTO dishDTOMapper(Dishes d) {
        return DishDTO.builder()
                .id(d.getId())
                .price(d.getPrice())
                .name(ContextHelpers.isLocaleEnglish()
                        ? d.getNameEn()
                        : d.getNameUa())
                .category(categoryDTOMapper(d.getCategories()))
                .build();
    }

    private static CategoryDTO categoryDTOMapper(Categories c) {
        return CategoryDTO.builder()
                .id(c.getId())
                .category(ContextHelpers.isLocaleEnglish()
                        ? c.getCategoryEn()
                        : c.getCategoryUa())
                .build();
    }

    public static List<DishDTO> dishesToDishesDTO(List<Dishes> list) {
        return list.stream()
                .map(Mapper::dishDTOMapper)
                .collect(Collectors.toList());
    }

    public static List<DishDTO> basketToDishesDTO(List<Baskets> list) {
        return list.stream()
                .map(Baskets::getDishes)
                .map(Mapper::dishDTOMapper)
                .collect(Collectors.toList());
    }

    public static BigDecimal getTotalPrice(List<DishDTO> dishes) {
        return dishes.stream()
                .map(DishDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static List<CategoryDTO> categoriesToCategoriesDTO(List<Categories> list) {
        return list.stream()
                .map(Mapper::categoryDTOMapper)
                .collect(Collectors.toList());
    }

    public static List<CategoryDTO> dishesToCategoriesDTO(List<Dishes> list) {
        return list.stream()
                .filter(distinctByKey(Dishes::getCategories))
                .map(d -> categoryDTOMapper(d.getCategories()))
                .collect(Collectors.toList());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
