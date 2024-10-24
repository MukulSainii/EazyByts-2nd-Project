package com.RODS.controller;



import com.RODS.configuration.Bundler;
import com.RODS.dto.DishDTO;
import com.RODS.dto.ItemDTO;
import com.RODS.entity.Dishes;
import com.RODS.service.DishesService;
import com.RODS.utils.Constants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/manager/dishes")
public class DishesController {
    private final DishesService dishesService;
    private final Bundler bundler;
    @Autowired
    public DishesController(DishesService dishesService,
                            Bundler bundler) {
        this.dishesService = dishesService;
        this.bundler = bundler;
    }

    /**
     * Get all dishes for manager
     * @return List<DishDTO>
     */
    @GetMapping("/get_all")
    public ResponseEntity<List<DishDTO>> getAllDishes() {
        log.info(bundler.getLogMsg(Constants.DISHES_ALL));
        return ResponseEntity.ok(dishesService.findAllDishes());
    }

    /**
     * Create new dish
     * @param dish get dish and validate
     * @return Dishes
     */
    @PostMapping("/create")
    public ResponseEntity<Dishes> create (@Valid @RequestBody Dishes dish) {
        log.info(bundler.getLogMsg(Constants.DISHES_CREATE));
        try {
            return ResponseEntity.ok(dishesService.saveNewDish(dish));
        } catch (Exception e){
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    bundler.getLogMsg(Constants.DISHES_CREATE_DBE) + dish.getNameEn());
        }
    }

    /**
     * Get mapping for update dish page
     * @param id dish id
     * @return Dishes
     */
    @GetMapping("/update")
    public ResponseEntity<Dishes> getDish(@Valid @RequestParam (value = "id") Long id) {
        log.info(bundler.getLogMsg(Constants.DISHES_ONE) + id);
        try {
            return ResponseEntity.ok(dishesService.findById(id));
        } catch (Exception e){
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    bundler.getLogMsg(Constants.DISHES_ONE_DBE) + id);
        }
    }

    /**
     * Update dish
     * @param dish dish and validation
     */
    @PutMapping("/update")
    public void update (@Valid @RequestBody Dishes dish) {
        log.info(bundler.getLogMsg(Constants.DISHES_UPDATE) + dish.getId());
        try {
            dishesService.update(dish);
        } catch (Exception e){
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    bundler.getLogMsg(Constants.DISHES_UPDATE_DBE));
        }
    }

    /**
     * Delete dish
     * @param itemDTO dish id
     */
    @DeleteMapping("/delete")
    public void delete (@Valid @RequestBody ItemDTO itemDTO) {
        log.info(bundler.getLogMsg(Constants.DISHES_DELETE) + itemDTO.getItemId());
        try {
            dishesService.delete(itemDTO.getItemId());
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    bundler.getLogMsg(Constants.DISHES_DELETE_DBE));
        }
    }
}

