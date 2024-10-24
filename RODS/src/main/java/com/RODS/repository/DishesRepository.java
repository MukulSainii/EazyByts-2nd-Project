package com.RODS.repository;


import com.RODS.entity.Dishes;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface DishesRepository extends JpaRepository<Dishes, Long> {
    Page<Dishes> findByCategories_Id(Long categoryId, Pageable pageable);
    @Nonnull
    Optional<Dishes> findById(@Nonnull Long id);
    void deleteById(@Nonnull Long id);

    Optional<Dishes> findByNameEn(@NonNull String name);
}
