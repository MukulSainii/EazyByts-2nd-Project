package com.RODS.entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity
public class Dishes {
    @Id
    @GeneratedValue(generator = "sequence-dishes-id")
    @GenericGenerator(
            name = "sequence-dishes-id",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "dishes_id_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            })
    @Column(name = "id")
    private Long id;

    @Pattern(regexp = "^[a-zA-z0-9 -]+$",
            message = "{error.dish.name_en}")
    @Column(name = "name_en")
    private String nameEn;

    @Pattern(regexp = "^[А-ЩЬЮЯҐЄІЇа-щьюяґєії0-9 '-]+$",
            message = "{error.dish.name_ua}")
    @Column(name = "name_ua")
    private String nameUa;

    @Min(value = 1/100L, message = "{error.dish.price}")
    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Categories categories;
    private LocalDateTime time;
}

