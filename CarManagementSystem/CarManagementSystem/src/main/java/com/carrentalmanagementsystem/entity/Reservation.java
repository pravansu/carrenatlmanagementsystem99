package com.carrentalmanagementsystem.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation {

    @Id
    @Column(length = 10)
    private String reservationID;

    @Column(nullable = false)
    private LocalDate reservationDate;

    @Column(length = 20, nullable = false)
    private String reservationStatus;

    @ManyToOne
    @JoinColumn(name = "carID")
    private Cars reservedCar;

    @ManyToOne
    @JoinColumn(name = "customerID")
    private Customer reservingCustomer;

    public Reservation(LocalDate reservationDate, String reservationStatus, Cars reservedCar,
            Customer reservingCustomer) {
        super();
       // this.reservationID = reservationID;
        this.reservationDate = reservationDate;
        this.reservationStatus = reservationStatus;
        this.reservedCar = reservedCar;
        this.reservingCustomer = reservingCustomer;
    }

}
