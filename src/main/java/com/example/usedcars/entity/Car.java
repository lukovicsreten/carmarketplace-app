package com.example.usedcars.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "car")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand", nullable = false, length = 50)
    private String brand;

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "model", nullable = false, length = 50)
    private String model;

    @Column(name = "vin", nullable = false, unique = true)
    private String vin;

    @Column(name = "year")
    private Integer year;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "fuel_type", length = 30)
    private String fuelType;

    @Column(name = "transmission", length = 30)
    private String transmission;

    @Column(name = "engine_power")
    private Integer enginePower;

    @Column(name = "engine_displacement")
    private Integer engineDisplacement;

    @Column(name = "vehicle_type", length = 30)
    private String vehicleType;

    @Column(name = "color", length = 30)
    private String color;

    @OneToOne(mappedBy = "car")
    private Ad ad;
}
