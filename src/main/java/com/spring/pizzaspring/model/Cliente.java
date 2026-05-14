package com.spring.pizzaspring.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(nullable = false)
    private String nome, indirizzo;

    @Column(nullable = false, unique = true)
    private String telefono;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ordine> ordini;

    public void addOrdine(Ordine ordine) {
        if (this.ordini == null) {
            this.ordini = new ArrayList<>();
        }
        this.ordini.add(ordine);
        ordine.setCliente(this); // Sync the "owning" side
    }
}
