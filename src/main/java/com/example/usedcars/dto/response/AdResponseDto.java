package com.example.usedcars.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdResponseDto {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private LocalDate datePosted;
    private String location;
    private String status;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String username;
    private CarResponseDto car;
    private int commentCount;
}
