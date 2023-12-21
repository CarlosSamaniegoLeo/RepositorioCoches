package com.example.proyectohibernatecoches2.dao;

import com.example.proyectohibernatecoches2.Multas;
import com.example.proyectohibernatecoches2.Util.Connection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class MultaDaoImpl implements MultaDao {

    private SessionFactory factory = Connection.getFactory();

    @Override
    public void saveMulta(Multas multa) {
        org.hibernate.Transaction transaction = null;
        try (org.hibernate.Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(multa);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)

                transaction.rollback();
        }
    }

    @Override
    public Multas getMulta(String matricula) {
        try (org.hibernate.Session session = factory.openSession()) {
            org.hibernate.Transaction transaction = session.beginTransaction();
            Multas multa = session.get(Multas.class, matricula);
            transaction.commit();
            return multa;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Multas> getAllMultas() {
        org.hibernate.Transaction transaction = null;
        List<Multas> multa = null;
        try (org.hibernate.Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            multa = session.createQuery("from Multas").list();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
        return multa;
    }

    @Override
    public void updateMulta(Multas multa) {
        org.hibernate.Transaction transaction = null;
        try (org.hibernate.Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.update(multa);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
    }

    @Override
    public void deleteMulta(Long id) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Multas multa = session.get(Multas.class, id);
            if (multa != null) {
                session.delete(multa);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
    }

    @Override
    public List<Multas> getAllMultasByMatricula(String matricula) {
        Transaction transaction = null;
        List<Multas> multas = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            // Usa HQL para obtener todas las multas asociadas a la matrícula
            String hql = "FROM Multas WHERE coche.matricula = :matricula";
            Query query = session.createQuery(hql);
            query.setParameter("matricula", matricula);  // Aquí establece el parámetro correctamente

            multas = query.list();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return multas;
    }
}
