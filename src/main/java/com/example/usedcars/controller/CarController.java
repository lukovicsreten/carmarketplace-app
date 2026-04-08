package com.example.usedcars.controller;

import com.example.usedcars.dto.request.CarRequestDto;
import com.example.usedcars.dto.response.CarResponseDto;
import com.example.usedcars.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDto> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @GetMapping
    public ResponseEntity<List<CarResponseDto>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarResponseDto> updateCar(@PathVariable Long id,
                                                    @Valid @RequestBody CarRequestDto dto) {
        return ResponseEntity.ok(carService.updateCar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint za sve marke automobila
    @GetMapping("/brands")
    public ResponseEntity<List<String>> getAllBrands() {
        return ResponseEntity.ok(carService.getAllBrands());
    }
}
