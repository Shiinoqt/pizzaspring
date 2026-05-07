package com.spring.pizzaspring.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Getter //Lombok annotation
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Creates one table for each class hierarchy (in this case OrdinePizza)
@DiscriminatorColumn(name="tipo_ordine", discriminatorType = DiscriminatorType.INTEGER) // To differentiate the tables this creates a column
@Table(name = "ordine")
public class Ordine {
    @Id
    private String codice;

    @ManyToOne // Many "order" can be associated to one client
    @JoinColumn(name = "idCliente", nullable = false) // Creates column "idCliente" that points to the class primary key
    private Cliente cliente;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true) // One "order" is associated to many pizzas
    private Collection<OrdinePizza> pizzeOrdinate;

    @ManyToOne // Many "order" can be associated to one rider
    @JoinColumn(name = "idRider") // Creates column "idRider" that points to the class primary key
    private Rider rider;
}
