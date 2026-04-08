package com.example.usedcars.mapper;

import com.example.usedcars.dto.request.AdRequestDto;
import com.example.usedcars.dto.response.AdResponseDto;
import com.example.usedcars.entity.Ad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdMapper {

    private final CarMapper carMapper;

    public Ad toEntity(AdRequestDto dto) {
        return Ad.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .datePosted(dto.getDatePosted())
                .location(dto.getLocation())
                .status(dto.getStatus())
                .imageUrl(dto.getImageUrl())
                .car(carMapper.toEntity(dto.getCar()))
                .build();
    }

    public AdResponseDto toResponseDto(Ad ad) {
        return AdResponseDto.builder()
                .id(ad.getId())
                .title(ad.getTitle())
                .description(ad.getDescription())
                .price(ad.getPrice())
                .datePosted(ad.getDatePosted())
                .location(ad.getLocation())
                .status(ad.getStatus())
                .imageUrl(ad.getImageUrl())
                .createdAt(ad.getCreatedAt())
                .updatedAt(ad.getUpdatedAt())
                .userId(ad.getUser().getId())
                .username(ad.getUser().getUsername())
                .car(carMapper.toResponseDto(ad.getCar()))
                .commentCount(ad.getComments() != null ? ad.getComments().size() : 0)
                .build();
    }

    public void updateEntity(Ad ad, AdRequestDto dto) {
        ad.setTitle(dto.getTitle());
        ad.setDescription(dto.getDescription());
        ad.setPrice(dto.getPrice());
        ad.setDatePosted(dto.getDatePosted());
        ad.setLocation(dto.getLocation());
        ad.setStatus(dto.getStatus());
        ad.setImageUrl(dto.getImageUrl());
        if (dto.getCar() != null) {
            carMapper.updateEntity(ad.getCar(), dto.getCar());
        }
    }
}
