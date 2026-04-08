package com.example.usedcars.specification;

import com.example.usedcars.entity.Ad;
import com.example.usedcars.entity.Car;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class AdSpecification {

    private AdSpecification() {}

    public static Specification<Ad> hasBrand(String brand) {
        return (root, query, cb) -> {
            Join<Ad, Car> car = root.join("car");
            return cb.equal(cb.lower(car.get("brand")), brand.toLowerCase());
        };
    }

    public static Specification<Ad> hasModel(String model) {
        return (root, query, cb) -> {
            Join<Ad, Car> car = root.join("car");
            return cb.like(cb.lower(car.get("model")), "%" + model.toLowerCase() + "%");
        };
    }

    public static Specification<Ad> hasYearFrom(Integer yearFrom) {
        return (root, query, cb) -> {
            Join<Ad, Car> car = root.join("car");
            return cb.greaterThanOrEqualTo(car.get("year"), yearFrom);
        };
    }

    public static Specification<Ad> hasYearTo(Integer yearTo) {
        return (root, query, cb) -> {
            Join<Ad, Car> car = root.join("car");
            return cb.lessThanOrEqualTo(car.get("year"), yearTo);
        };
    }

    public static Specification<Ad> hasMileageMax(Integer mileageMax) {
        return (root, query, cb) -> {
            Join<Ad, Car> car = root.join("car");
            return cb.lessThanOrEqualTo(car.get("mileage"), mileageMax);
        };
    }

    public static Specification<Ad> hasFuelType(String fuelType) {
        return (root, query, cb) -> {
            Join<Ad, Car> car = root.join("car");
            return cb.equal(cb.lower(car.get("fuelType")), fuelType.toLowerCase());
        };
    }

    public static Specification<Ad> hasVehicleType(String vehicleType) {
        return (root, query, cb) -> {
            Join<Ad, Car> car = root.join("car");
            return cb.equal(cb.lower(car.get("vehicleType")), vehicleType.toLowerCase());
        };
    }

    public static Specification<Ad> hasTransmission(String transmission) {
        return (root, query, cb) -> {
            Join<Ad, Car> car = root.join("car");
            return cb.equal(cb.lower(car.get("transmission")), transmission.toLowerCase());
        };
    }

    public static Specification<Ad> hasEngineDisplacementMin(Integer min) {
        return (root, query, cb) -> {
            Join<Ad, Car> car = root.join("car");
            return cb.greaterThanOrEqualTo(car.get("engineDisplacement"), min);
        };
    }

    public static Specification<Ad> hasEngineDisplacementMax(Integer max) {
        return (root, query, cb) -> {
            Join<Ad, Car> car = root.join("car");
            return cb.lessThanOrEqualTo(car.get("engineDisplacement"), max);
        };
    }

    public static Specification<Ad> hasPriceMin(java.math.BigDecimal priceMin) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), priceMin);
    }

    public static Specification<Ad> hasPriceMax(java.math.BigDecimal priceMax) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), priceMax);
    }

    public static Specification<Ad> hasMake(String make) {
        return (root, query, cb) -> {
            Join<Ad, Car> car = root.join("car");
            return cb.equal(cb.lower(car.get("make")), make.toLowerCase());
        };
    }

    public static Specification<Ad> hasEnginePowerMin(Integer min) {
        return (root, query, cb) -> {
            Join<Ad, Car> car = root.join("car");
            return cb.greaterThanOrEqualTo(car.get("enginePower"), min);
        };
    }

    public static Specification<Ad> hasEnginePowerMax(Integer max) {
        return (root, query, cb) -> {
            Join<Ad, Car> car = root.join("car");
            return cb.lessThanOrEqualTo(car.get("enginePower"), max);
        };
    }

    public static Specification<Ad> hasColor(String color) {
        return (root, query, cb) -> {
            Join<Ad, Car> car = root.join("car");
            return cb.equal(cb.lower(car.get("color")), color.toLowerCase());
        };
    }

    public static Specification<Ad> isActive() {
        return (root, query, cb) -> cb.equal(root.get("status"), "ACTIVE");
    }
}
