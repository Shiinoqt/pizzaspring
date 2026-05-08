package com.spring.pizzaspring.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rider")
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRider;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "rider")
    private Collection<Ordine> ordini;
}
