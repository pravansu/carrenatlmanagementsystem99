package com.carrentalmanagementsystem.dao;

import java.util.List;

import com.carrentalmanagementsystem.entity.Customer;

public interface CustomerDAO {
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(String customerId);
    Customer getCustomerById(String customerId);
    List<Customer> getAllCustomers();
    // Other methods related to Customer entity
}
