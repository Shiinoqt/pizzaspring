package com.spring.pizzaspring.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pizza")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPizza;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descrizione;

    @Column(nullable = false)
    private double prezzo;
}
