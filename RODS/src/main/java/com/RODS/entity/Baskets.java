package com.RODS.entity;



import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;



// todo names like Basket (без множини, тфльки в однині)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity
public class Baskets {
    @Id
    @GeneratedValue(generator = "sequence-baskets-id")
    @GenericGenerator(
            name = "sequence-baskets-id",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "baskets_id_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            })
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "login_id", referencedColumnName = "id")
    private Logins login;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id", referencedColumnName = "id")
    private Dishes dishes;
}

