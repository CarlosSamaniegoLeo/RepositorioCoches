package com.example.proyectohibernatecoches2;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Multas")
public class Multas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_multas")
    private Long id_multas;

    @Column(name = "precio_multa")
    private Double precio_multa;

    @Column(name = "fecha_multa")
    private LocalDate fecha_multa;

    @ManyToOne
    @JoinColumn(name = "matricula", referencedColumnName="matricula")
    private Coche coche;

    public Multas() {
    }
    public Multas(Double precioMulta, LocalDate fechaMulta, Coche coche) {

        this.precio_multa = precioMulta;
        this.fecha_multa = fechaMulta;
        this.coche = coche;
    }


    public Long getId_multas() {
        return id_multas;
    }

    public void setId_multas(Long idMulta) {
        this.id_multas = idMulta;
    }

    public Double getPrecio_multa() {
        return precio_multa;
    }

    public void setPrecio_multa(Double precioMulta) {
        this.precio_multa = precioMulta;
    }

    public LocalDate getFecha_multa() {
        return fecha_multa;
    }

    public void setFecha_multa(LocalDate fechaMulta) {
        this.fecha_multa = fechaMulta;
    }

    public Coche getCoche() {
        return coche;
    }

    public void setCoche(Coche coche) {
        this.coche = coche;
    }
}
