package com.example.proyectohibernatecoches2.dao;


import com.example.proyectohibernatecoches2.Coche;
import com.example.proyectohibernatecoches2.Util.Connection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CocheDaoImpl implements CocheDao {

	private SessionFactory factory = Connection.getFactory();

	@Override
	public void saveCoche(Coche coche) {
		Transaction transaction = null;
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			session.save(coche);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)

				transaction.rollback();
		}
	}

	@Override
	public Coche getCoche(String matricula) {
		try (Session session = factory.openSession()) {
			Transaction transaction = session.beginTransaction();
			Coche coche = session.get(Coche.class, matricula);
			transaction.commit();
			return coche;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<Coche> getAllCoches() {
		Transaction transaction = null;
		List<Coche> coches = null;
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			coches = session.createQuery("from Coche").list();
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null)
			transaction.rollback();
		}
		return coches;
	}

	@Override
	public void updateCoche(Coche coche) {
		Transaction transaction = null;
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			session.update(coche);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
		}
	}

	@Override
	public void deleteCochebyMatricula(String numMatricula) {
		Transaction transaction = null;
		try (Session session = factory.openSession()) {
			transaction = session.beginTransaction();
			Coche coche = session.get(Coche.class, numMatricula);
			if (coche != null) {
				session.delete(coche);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
		}
	}

}
