package com.example.usedcars.service.impl;

import com.example.usedcars.dto.request.CarRequestDto;
import com.example.usedcars.dto.response.CarResponseDto;
import com.example.usedcars.entity.Car;
import com.example.usedcars.exception.ResourceNotFoundException;
import com.example.usedcars.mapper.CarMapper;
import com.example.usedcars.repository.CarRepository;
import com.example.usedcars.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    @Transactional(readOnly = true)
    public CarResponseDto getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));
        return carMapper.toResponseDto(car);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarResponseDto> getAllCars() {
        return carRepository.findAll().stream()
                .map(carMapper::toResponseDto)
                .toList();
    }

    @Override
    public CarResponseDto updateCar(Long id, CarRequestDto dto) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));
        carMapper.updateEntity(car, dto);
        Car updated = carRepository.save(car);
        return carMapper.toResponseDto(updated);
    }

    @Override
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException("Car", id);
        }
        carRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllBrands() {
        return carRepository.findAllBrands();
    }
}
