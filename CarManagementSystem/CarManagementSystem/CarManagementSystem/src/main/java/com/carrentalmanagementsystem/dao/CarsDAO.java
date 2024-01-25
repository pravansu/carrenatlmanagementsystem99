package com.carrentalmanagementsystem.dao;

import com.carrentalmanagementsystem.entity.Cars;

import java.util.List;

public interface CarsDAO {
    void addCar(Cars car);
    void updateCar(Cars car);
    void deleteCar(String carID);
    Cars getCarByID(String carID);
    List<Cars> getAllCars();
}


