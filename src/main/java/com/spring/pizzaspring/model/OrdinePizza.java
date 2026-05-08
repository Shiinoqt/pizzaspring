package com.spring.pizzaspring.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "ordinepizza")
public class OrdinePizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "codice")
    private Ordine ordine;

    @ManyToOne
    @JoinColumn(name = "idPizza")
    private Pizza pizza;

    private Integer quantità;
}