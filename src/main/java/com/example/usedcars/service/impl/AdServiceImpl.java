package com.example.usedcars.service.impl;

import com.example.usedcars.dto.request.AdRequestDto;
import com.example.usedcars.dto.response.AdResponseDto;
import com.example.usedcars.entity.Ad;
import com.example.usedcars.entity.User;
import com.example.usedcars.exception.ResourceNotFoundException;
import com.example.usedcars.mapper.AdMapper;
import com.example.usedcars.repository.AdRepository;
import com.example.usedcars.repository.UserRepository;
import com.example.usedcars.service.AdService;
import com.example.usedcars.specification.AdSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Override
    public AdResponseDto createAd(AdRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User", dto.getUserId()));

        // Provera vlasništva NIJE potrebna za kreiranje oglasa
        Ad ad = adMapper.toEntity(dto);
        ad.setUser(user);
        Ad saved = adRepository.save(ad);
        return adMapper.toResponseDto(saved);
    }

    @Override
    public AdResponseDto uploadImage(Long adId, MultipartFile file) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new ResourceNotFoundException("Ad", adId));
        checkOwnershipOrAdmin(ad.getUser().getUsername());

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = adId + "_" + UUID.randomUUID() + extension;

        try {
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            // Delete old image if exists
            Files.list(uploadPath)
                    .filter(p -> p.getFileName().toString().startsWith(adId + "_"))
                    .forEach(p -> { try { Files.delete(p); } catch (IOException ignored) {} });
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }

        ad.setImageUrl("/api/ads/" + adId + "/image");
        adRepository.save(ad);
        return adMapper.toResponseDto(ad);
    }

    @Override
    @Transactional(readOnly = true)
    public AdResponseDto getAdById(Long id) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ad", id));
        return adMapper.toResponseDto(ad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdResponseDto> getAllAds() {
        return adRepository.findAll().stream()
                .map(adMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdResponseDto> getAdsByUserId(Long userId) {
        return adRepository.findByUserId(userId).stream()
                .map(adMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdResponseDto> searchAds(String brand, String model, Integer yearFrom, Integer yearTo,
                                          Integer mileageMax, String fuelType, String vehicleType,
                                          String transmission, Integer engineDisplacementMin,
                                          Integer engineDisplacementMax, BigDecimal priceMin, BigDecimal priceMax,
                                          String make, Integer enginePowerMin, Integer enginePowerMax, String color) {
        Specification<Ad> spec = Specification.where(AdSpecification.isActive());

        if (brand != null && !brand.isBlank()) spec = spec.and(AdSpecification.hasBrand(brand));
        if (model != null && !model.isBlank()) spec = spec.and(AdSpecification.hasModel(model));
        if (yearFrom != null) spec = spec.and(AdSpecification.hasYearFrom(yearFrom));
        if (yearTo != null) spec = spec.and(AdSpecification.hasYearTo(yearTo));
        if (mileageMax != null) spec = spec.and(AdSpecification.hasMileageMax(mileageMax));
        if (fuelType != null && !fuelType.isBlank()) spec = spec.and(AdSpecification.hasFuelType(fuelType));
        if (vehicleType != null && !vehicleType.isBlank()) spec = spec.and(AdSpecification.hasVehicleType(vehicleType));
        if (transmission != null && !transmission.isBlank()) spec = spec.and(AdSpecification.hasTransmission(transmission));
        if (engineDisplacementMin != null) spec = spec.and(AdSpecification.hasEngineDisplacementMin(engineDisplacementMin));
        if (engineDisplacementMax != null) spec = spec.and(AdSpecification.hasEngineDisplacementMax(engineDisplacementMax));
        if (priceMin != null) spec = spec.and(AdSpecification.hasPriceMin(priceMin));
        if (priceMax != null) spec = spec.and(AdSpecification.hasPriceMax(priceMax));
        if (make != null && !make.isBlank()) spec = spec.and(AdSpecification.hasMake(make));
        if (enginePowerMin != null) spec = spec.and(AdSpecification.hasEnginePowerMin(enginePowerMin));
        if (enginePowerMax != null) spec = spec.and(AdSpecification.hasEnginePowerMax(enginePowerMax));
        if (color != null && !color.isBlank()) spec = spec.and(AdSpecification.hasColor(color));

        return adRepository.findAll(spec).stream()
                .map(adMapper::toResponseDto)
                .toList();
    }

    @Override
    public AdResponseDto updateAd(Long id, AdRequestDto dto) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ad", id));
        checkOwnershipOrAdmin(ad.getUser().getUsername());
        adMapper.updateEntity(ad, dto);
        Ad updated = adRepository.save(ad);
        return adMapper.toResponseDto(updated);
    }

    @Override
    public void deleteAd(Long id) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ad", id));
        checkOwnershipOrAdmin(ad.getUser().getUsername());
        adRepository.delete(ad);
    }

    private void checkOwnershipOrAdmin(String ownerUsername) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return;
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin && !auth.getName().equals(ownerUsername)) {
            throw new org.springframework.security.access.AccessDeniedException("You can only modify your own resources");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdResponseDto> getCarsByPriceAndMileage(BigDecimal cenaMax, Integer kilometrazaMax) {
        return adRepository.findAll().stream()
                .filter(ad -> ad.getPrice().compareTo(cenaMax) <= 0)
                .filter(ad -> ad.getCar() != null && ad.getCar().getMileage() != null && ad.getCar().getMileage() <= kilometrazaMax)
                .map(adMapper::toResponseDto)
                .toList();
    }
}
