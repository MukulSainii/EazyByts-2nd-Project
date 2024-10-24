package com.RODS.controller;

import com.RODS.configuration.Bundler;
import com.RODS.dto.PageableDishesDTO;
import com.RODS.service.DishesService;
import com.RODS.utils.Constants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@RestController
@RequestMapping(value = "api/")
public class MainController {
    private final DishesService dishesService;
    private final Bundler bundler;
    @Autowired
    public MainController(DishesService dishesService,
                          Bundler bundler) {
        this.dishesService = dishesService;
        this.bundler = bundler;
    }

    /**
     * Returns page of dishes
     * @param pageNo current page
     * @param sortField sort field
     * @param sortDirection asc or desc
     * @param categoryId filter by category
     * @return PageableDishesDTO
     */

    // todo page -1 return 1, page 100 returns last

    @GetMapping("/get/{page}")
    public ResponseEntity<PageableDishesDTO>
    findPaginated(@Valid @PathVariable (value = "page") Integer pageNo,
                  @RequestParam (value = "sort", required = false) String sortField,
                  @RequestParam (value = "direct", required = false) String sortDirection,
                  @RequestParam (value = "category", required = false) Long categoryId)  {

        log.info(bundler.getLogMsg(Constants.DISHES_ALL_PAGE) + pageNo);

        try {
            return ResponseEntity.ok(
                    dishesService.findAllDishesPaginated(
                            pageNo, sortField, sortDirection, categoryId));
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

}
