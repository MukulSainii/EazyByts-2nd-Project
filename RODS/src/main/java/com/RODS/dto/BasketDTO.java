package com.RODS.dto;


import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

// no validation, send to basket page

public class BasketDTO {
    private List<DishDTO> dishes;
    private BigDecimal totalPrice;
}
