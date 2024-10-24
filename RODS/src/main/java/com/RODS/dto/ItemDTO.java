package com.RODS.dto;



import jakarta.validation.constraints.Min;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

// income:
// - for confirm,
// - for payment,
// - for add dish to basket,
// - for delete dish from basket

public class ItemDTO {
    @Min(value = 1, message = "{error.itemDTO}")
    Long itemId;
}

