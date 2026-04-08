package com.example.usedcars.service;

import com.example.usedcars.dto.request.AdRequestDto;
import com.example.usedcars.dto.response.AdResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface AdService {

    AdResponseDto createAd(AdRequestDto dto);

    AdResponseDto uploadImage(Long adId, MultipartFile file);

    AdResponseDto getAdById(Long id);

    List<AdResponseDto> getAllAds();

    List<AdResponseDto> getAdsByUserId(Long userId);

    List<AdResponseDto> searchAds(String brand, String model, Integer yearFrom, Integer yearTo,
                                   Integer mileageMax, String fuelType, String vehicleType,
                                   String transmission, Integer engineDisplacementMin,
                                   Integer engineDisplacementMax, BigDecimal priceMin, BigDecimal priceMax,
                                   String make, Integer enginePowerMin, Integer enginePowerMax, String color);

    AdResponseDto updateAd(Long id, AdRequestDto dto);

    void deleteAd(Long id);
}
