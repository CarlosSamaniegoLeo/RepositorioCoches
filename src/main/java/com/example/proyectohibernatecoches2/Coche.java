package com.example.proyectohibernatecoches2;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "Coche")
public class Coche {

    @Id
    @Column(name = "matricula")
    private String matricula;

    @Column(name = "marca")
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "tipo")
    private String tipo;
    @OneToMany(mappedBy = "coche", cascade = CascadeType.ALL)
    private List<Multas> multas;//un coche puede tener varias multas

    public Coche() {
        super();
    }

    public Coche(String matricula, String marca, String modelo, String tipo) {
        super();
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return matricula;
    }
}
