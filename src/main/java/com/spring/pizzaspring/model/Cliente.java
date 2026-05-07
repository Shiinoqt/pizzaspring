package com.spring.pizzaspring.model;

public class Cliente {
    private int id;
    private String nome, indirizzo, telefono;

    public Cliente(int id, String indirizzo, String nome, String telefono) {
        this.id = id;
        this.indirizzo = indirizzo;
        this.nome = nome;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
