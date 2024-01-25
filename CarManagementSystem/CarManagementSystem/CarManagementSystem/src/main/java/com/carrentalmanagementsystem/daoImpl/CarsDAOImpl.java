package com.carrentalmanagementsystem.daoImpl;


import com.carrentalmanagementsystem.dao.CarsDAO;
import com.carrentalmanagementsystem.entity.Cars;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class CarsDAOImpl implements CarsDAO {

    private final SessionFactory sessionFactory;

    public CarsDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addCar(Cars car) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(car);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCar(Cars car) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(car);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCar(String carID) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Cars car = session.get(Cars.class, carID);
            if (car != null) {
                session.delete(car);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cars getCarByID(String carID) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Cars.class, carID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cars> getAllCars() {
        try (Session session = sessionFactory.openSession()) {
            Query<Cars> query = session.createQuery("FROM Cars", Cars.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

