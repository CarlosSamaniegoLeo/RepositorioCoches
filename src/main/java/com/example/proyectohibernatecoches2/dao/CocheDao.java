// CocheDao.java
package com.example.proyectohibernatecoches2.dao;

import com.example.proyectohibernatecoches2.Coche;

import java.util.List;

public interface CocheDao {

    void saveCoche(Coche coche);

    Coche getCoche(String matricula);

    List<Coche> getAllCoches();

    void updateCoche(Coche coche);

    void deleteCochebyMatricula(String numMatricula);

}
