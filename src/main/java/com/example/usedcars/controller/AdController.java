package com.example.usedcars.controller;

import com.example.usedcars.dto.request.AdRequestDto;
import com.example.usedcars.dto.response.AdResponseDto;
import com.example.usedcars.service.AdService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/ads")
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AdResponseDto> createAd(@Valid @RequestBody AdRequestDto dto) {
        AdResponseDto created = adService.createAd(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdResponseDto> getAdById(@PathVariable Long id) {
        return ResponseEntity.ok(adService.getAdById(id));
    }

    @GetMapping
    public ResponseEntity<List<AdResponseDto>> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @GetMapping("/search")
    public ResponseEntity<List<AdResponseDto>> searchAds(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer yearFrom,
            @RequestParam(required = false) Integer yearTo,
            @RequestParam(required = false) Integer mileageMax,
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) String transmission,
            @RequestParam(required = false) Integer engineDisplacementMin,
            @RequestParam(required = false) Integer engineDisplacementMax,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) Integer enginePowerMin,
            @RequestParam(required = false) Integer enginePowerMax,
            @RequestParam(required = false) String color) {
        return ResponseEntity.ok(adService.searchAds(brand, model, yearFrom, yearTo,
                mileageMax, fuelType, vehicleType, transmission,
                engineDisplacementMin, engineDisplacementMax, priceMin, priceMax,
                make, enginePowerMin, enginePowerMax, color));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AdResponseDto>> getAdsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(adService.getAdsByUserId(userId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AdResponseDto> updateAd(@PathVariable Long id,
                                                  @Valid @RequestBody AdRequestDto dto) {
        return ResponseEntity.ok(adService.updateAd(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteAd(@PathVariable Long id) {
        adService.deleteAd(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/image")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AdResponseDto> uploadImage(@PathVariable Long id,
                                                     @RequestParam("file") MultipartFile file) {
        AdResponseDto updated = adService.uploadImage(id, file);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) throws IOException {
        AdResponseDto ad = adService.getAdById(id);
        if (ad.getImageUrl() == null) {
            return ResponseEntity.notFound().build();
        }

        Path dir = Paths.get(uploadDir);
        if (!Files.exists(dir)) {
            return ResponseEntity.notFound().build();
        }

        Path imagePath = Files.list(dir)
                .filter(p -> p.getFileName().toString().startsWith(id + "_"))
                .findFirst()
                .orElse(null);

        if (imagePath == null || !Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(imagePath.toUri());
        String contentType = Files.probeContentType(imagePath);
        if (contentType == null) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}
