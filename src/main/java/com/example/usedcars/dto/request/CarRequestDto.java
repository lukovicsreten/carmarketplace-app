package com.example.usedcars.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarRequestDto {

    @NotBlank(message = "Brand is required")
    @Size(max = 50)
    private String brand;

    @NotBlank(message = "Make is required")
    private String make;

    @NotBlank(message = "Model is required")
    @Size(max = 50)
    private String model;

    @NotBlank(message = "VIN is required")
    private String vin;

    @Min(value = 1900, message = "Year must be 1900 or later")
    private Integer year;

    @Min(value = 0, message = "Mileage cannot be negative")
    private Integer mileage;

    @Size(max = 30)
    private String fuelType;

    @Size(max = 30)
    private String transmission;

    @Min(value = 0, message = "Engine power cannot be negative")
    private Integer enginePower;

    @Min(value = 0, message = "Engine displacement cannot be negative")
    private Integer engineDisplacement;

    @Size(max = 30)
    private String vehicleType;

    @Size(max = 30)
    private String color;
}
