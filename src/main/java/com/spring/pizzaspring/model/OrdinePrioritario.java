package com.spring.pizzaspring.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("prioritario")
public class OrdinePrioritario extends Ordine{
    @Column(name = "sovrapprezzo")
    private Double sovrapprezzo = 0.0;
}
