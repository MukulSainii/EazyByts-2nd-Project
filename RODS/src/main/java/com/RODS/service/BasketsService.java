package com.RODS.service;


import com.RODS.configuration.Bundler;
import com.RODS.dto.BasketDTO;
import com.RODS.dto.DishDTO;
import com.RODS.dto.ItemDTO;
import com.RODS.entity.Baskets;
import com.RODS.entity.Dishes;
import com.RODS.entity.Logins;
import com.RODS.repository.BasketRepository;
import com.RODS.repository.DishesRepository;
import com.RODS.utils.Constants;
import com.RODS.utils.ContextHelpers;
import com.RODS.utils.Mapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BasketsService {
    private final BasketRepository basketRepository;
    private final DishesRepository dishesRepository;
    private final Bundler bundler;
    @Autowired
    public BasketsService(BasketRepository basketRepository,
                          DishesRepository dishesRepository,
                          Bundler bundler) {
        this.basketRepository = basketRepository;
        this.dishesRepository = dishesRepository;
        this.bundler = bundler;
    }

    /**
     * Get all dishes, that are in authorized user basket
     * @return list of baskets
     */
    public BasketDTO findAllDishes() {
        List<DishDTO> dishes = Mapper.basketToDishesDTO(
                basketRepository.findAllByLogin_Id(
                        ContextHelpers.getAuthorizedLogin().getId()));
        return BasketDTO.builder()
                .dishes(dishes)
                .totalPrice(Mapper.getTotalPrice(dishes))
                .build();
    }

    /**
     * Save new dish to users basket
     * @param itemDTO dto request from user
     * @return saved basket entity
     * @throws NoSuchElementException
     *      - if dish not found
     *      - if cannot save
     */
    @Transactional
    public Baskets saveNewItem (@NonNull ItemDTO itemDTO) {
        Logins user = ContextHelpers.getAuthorizedLogin();

        Dishes dish = dishesRepository.findById(itemDTO.getItemId())
                .orElseThrow(() -> new NoSuchElementException(
                        bundler.getLogMsg(Constants.BASKET_CREATE_DBE) + itemDTO.getItemId()));

        return basketRepository.save(Baskets.builder()
                .login(user)
                .dishes(dish)
                .build());
    }

    /**
     * delete one item from users basket
     * (there can be more than one identical dishes)
     * @param id dish id
     */
    @Transactional
    public void delete(@NonNull Long id) {
        List<Baskets> list = basketRepository.findBasketsByDishes_Id(id);
        if (list.isEmpty()) {
            throw new NoSuchElementException(bundler.getLogMsg(Constants.BASKET_DELETE_DBE));
        }
        basketRepository.delete(list.get(0));
    }

    /**
     * delete all items from users basket
     * @param id authorized user id
     */
    @Transactional
    public void deleteByLogin(@NonNull Long id) {
        basketRepository.deleteByLogin_Id(id);
    }

}
