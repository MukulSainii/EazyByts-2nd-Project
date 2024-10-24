package com.RODS.repository;


import com.RODS.entity.Baskets;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface BasketRepository  extends JpaRepository<Baskets, Long> {
    List<Baskets> findAllByLogin_Id(Long id);
    List<Baskets> findBasketsByDishes_Id(Long id);
    void deleteByLogin_Id(Long id);

}
