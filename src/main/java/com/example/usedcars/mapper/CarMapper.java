package com.example.usedcars.mapper;

import com.example.usedcars.dto.request.CarRequestDto;
import com.example.usedcars.dto.response.CarResponseDto;
import com.example.usedcars.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public Car toEntity(CarRequestDto dto) {
        return Car.builder()
                .brand(dto.getBrand())
                .make(dto.getMake())
                .model(dto.getModel())
                .vin(dto.getVin())
                .year(dto.getYear())
                .mileage(dto.getMileage())
                .fuelType(dto.getFuelType())
                .transmission(dto.getTransmission())
                .enginePower(dto.getEnginePower())
                .engineDisplacement(dto.getEngineDisplacement())
                .vehicleType(dto.getVehicleType())
                .color(dto.getColor())
                .build();
    }

    public CarResponseDto toResponseDto(Car car) {
        return CarResponseDto.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .make(car.getMake())
                .model(car.getModel())
                .vin(car.getVin())
                .year(car.getYear())
                .mileage(car.getMileage())
                .fuelType(car.getFuelType())
                .transmission(car.getTransmission())
                .enginePower(car.getEnginePower())
                .engineDisplacement(car.getEngineDisplacement())
                .vehicleType(car.getVehicleType())
                .color(car.getColor())
                .build();
    }

    public void updateEntity(Car car, CarRequestDto dto) {
        car.setBrand(dto.getBrand());
        car.setMake(dto.getMake());
        car.setModel(dto.getModel());
        car.setVin(dto.getVin());
        car.setYear(dto.getYear());
        car.setMileage(dto.getMileage());
        car.setFuelType(dto.getFuelType());
        car.setTransmission(dto.getTransmission());
        car.setEnginePower(dto.getEnginePower());
        car.setEngineDisplacement(dto.getEngineDisplacement());
        car.setVehicleType(dto.getVehicleType());
        car.setColor(dto.getColor());
    }
}
