package com.RODS.entity;



import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity
public class Categories {
    @Id
    @GeneratedValue(generator = "sequence-categories-id")
    @GenericGenerator(
            name = "sequence-categories-id",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "categories_id_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            })
    @Column(name = "id")
    private Long id;
    @Column(name = "category_en")
    private String categoryEn;
    @Column(name = "category_ua")
    private String categoryUa;

}

