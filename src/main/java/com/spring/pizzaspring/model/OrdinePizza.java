package com.spring.pizzaspring.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ordinepizza")
public class OrdinePizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordine_codice")
    private Ordine ordine;

    @ManyToOne
    @JoinColumn(name = "idPizza")
    private Pizza pizza;

    private Integer quantita;
}