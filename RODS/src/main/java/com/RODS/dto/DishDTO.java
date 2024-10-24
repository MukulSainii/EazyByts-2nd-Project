package com.RODS.dto;



import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

// no validation, internal use for orders service

public class DishDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private CategoryDTO category;
}
