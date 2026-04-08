package com.example.usedcars.service;

import com.example.usedcars.dto.request.CarRequestDto;
import com.example.usedcars.dto.response.CarResponseDto;

import java.util.List;

public interface CarService {

    CarResponseDto getCarById(Long id);

    List<CarResponseDto> getAllCars();

    CarResponseDto updateCar(Long id, CarRequestDto dto);

    void deleteCar(Long id);

    // Vraca sve razlicite marke automobila
    List<String> getAllBrands();
}
