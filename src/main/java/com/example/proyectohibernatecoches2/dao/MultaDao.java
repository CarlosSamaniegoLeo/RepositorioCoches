package com.example.proyectohibernatecoches2.dao;

import com.example.proyectohibernatecoches2.Coche;
import com.example.proyectohibernatecoches2.Multas;

import java.util.List;

public interface MultaDao {

    void saveMulta(Multas multa);

    Multas getMulta(String matricula);

    List<Multas> getAllMultas();

    void updateMulta(Multas multa);

    void deleteMulta(Long id);

    List<Multas> getAllMultasByMatricula(String matriculaSeleccionada);
}
