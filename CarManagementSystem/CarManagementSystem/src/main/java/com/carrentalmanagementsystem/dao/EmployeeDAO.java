package com.carrentalmanagementsystem.dao;

import com.carrentalmanagementsystem.entity.Employee;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;


public class EmployeeDAO {
    private Session session;

    public EmployeeDAO(Session session) {
        this.session = session;
    }

    public String fetchLastAddedId() {
        Object empID = session.createQuery("select max(e.empID) from Employee e").getSingleResult();
        return String.valueOf(empID);
    }

    public Employee addEmployee(Employee emp) {
        Transaction transaction = null;
        try {
            if (!session.getTransaction().isActive()) {
                transaction = session.beginTransaction();
            }

            // to generate the custom id
            String empID = fetchLastAddedId();

            if (empID.contains("null")) {
                empID = "E100";
            }

            String prefix = empID.substring(0, 1); // E
            int postfix = Integer.parseInt(empID.substring(1)); // 102
            String EId = prefix + (postfix + 1); // E + (102+1) = E + 103 = E103

            emp.setEmpID(EId);

            // Saving the new employee to the database
            session.save(emp);

            transaction.commit();
            
            return emp;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    public Employee findByEmail(String empEmail) {
        try {
            Employee employee = session.createQuery("from Employee where email = :e", Employee.class)
                    .setParameter("e", empEmail)
                    .getSingleResult();

            return employee;

        } catch (Exception e) {
            System.out.println("Employee details not found!!");
        }

        return null;
    }

    public Employee getEmployeeById(int empID) {
        try {
            Employee employee = session.get(Employee.class, empID);
            return employee;
        } catch (Exception e) {
            e.printStackTrace();
            
        }
		return null;
       
    }
    public Employee getEmployeeByEmailAndPassword(String empEmail, String password) {
        try {
            Employee employee = session.createQuery("from Employee where empEmail = :email and password = :password", Employee.class)
                    .setParameter("email", empEmail)
                    .setParameter("password", password)
                    .getSingleResult();

            return employee;

        } catch (NoResultException e) {
            // Handle case where no employee is found
            return null;
        }
    }
}