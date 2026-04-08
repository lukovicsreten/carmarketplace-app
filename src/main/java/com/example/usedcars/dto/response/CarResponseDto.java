package com.example.usedcars.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarResponseDto {

    private Long id;
    private String brand;
    private String make;
    private String model;
    private String vin;
    private Integer year;
    private Integer mileage;
    private String fuelType;
    private String transmission;
    private Integer enginePower;
    private Integer engineDisplacement;
    private String vehicleType;
    private String color;
}
