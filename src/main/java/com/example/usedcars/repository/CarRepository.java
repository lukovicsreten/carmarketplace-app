package com.example.usedcars.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.usedcars.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByBrand(String brand);

    List<Car> findByBrandAndModel(String brand, String model);

    // Vraca sve razlicite marke automobila
    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT c.brand FROM Car c")
    List<String> findAllBrands();
}
