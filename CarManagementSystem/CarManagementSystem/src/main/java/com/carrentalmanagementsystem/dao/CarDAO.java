package com.carrentalmanagementsystem.dao;

import com.carrentalmanagementsystem.entity.Cars;

import java.util.List;

/*public interface CarsDAO {
    void addCar(Cars car);
    void updateCar(Cars car);
    void deleteCar(String carID);
    Cars getCarByID(String carID);
    List<Cars> getAllCars();
}
*/
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarDAO {
    private Session session;

    public CarDAO(Session session) {
        this.session = session;
    }

    public String fetchLastAddedId() {
        Object carID = session.createQuery("select max(c.carID) from Cars c").getSingleResult();
        return String.valueOf(carID);
    }

    public void addCar(Cars car) {
        Transaction transaction = null;
        try {
            if (!session.getTransaction().isActive()) {
                transaction = session.beginTransaction();
            }

            // to generate the custom id
            String carID = fetchLastAddedId();

            if (carID.contains("null")) {
                carID = "CAR100";
            }

            String prefix = carID.substring(0, 3); // CAR
            int postfix = Integer.parseInt(carID.substring(3)); // 102
            String newCarID = prefix + (postfix + 1); // CAR + (102+1) = CAR + 103 = CAR103

            car.setCarID(newCarID);

            // Saving the new car to the database
            session.save(car);

            transaction.commit();
            
            System.out.println("Car added successfully.");
            
           
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
     
    }

    public Cars getCarById(String carID) {
        try {
            Cars car = session.get(Cars.class, carID);
            return car;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Cars> getAllCars() {
        try {
            return session.createQuery("from Cars", Cars.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }     
    public void updateCarDetails(String carID, String carMake, String carName, String carModel, String carType, String carAvailability) {
        Transaction transaction = null;
        try {
            if (!session.getTransaction().isActive()) {
                transaction = session.beginTransaction();
            }

            Cars existingCar = session.get(Cars.class, carID);

            if (existingCar != null) {
                existingCar.setCarMake(carMake);
                existingCar.setCarName(carName);
                existingCar.setCarModel(carModel);
                existingCar.setCarType(carType);
                existingCar.setCarAvailability(carAvailability);

                session.update(existingCar);

                transaction.commit();
            } else {
                System.out.println("Car with ID " + carID + " not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public double getPerDayPriceByCarType(String carType) {
	    // Placeholder implementation - replace with actual logic
	    if ("Hatchback".equalsIgnoreCase(carType)) {
	        return 35.0; // Replace with the actual price for Hatchback
	    } else if ("Sedan".equalsIgnoreCase(carType)) {
	        return 45.0; // Replace with the actual price for Sedan
	    } else if ("SUV".equalsIgnoreCase(carType)) {
	        return 55.0; // Replace with the actual price for SUV
	    } else if ("Coupe".equalsIgnoreCase(carType)) {
	        return 50.0; // Replace with the actual price for Coupe
	    } else {
	        return 0.0; // Default value or handle unknown car types
	    }
	}
    public Cars getCarByType(String carType) {
        try {
            // Using an HQL query to select cars based on carType
            String hql = "from Cars where carType = :type";
            return (Cars) session.createQuery(hql, Cars.class)
                    .setParameter("type", carType)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    

}


