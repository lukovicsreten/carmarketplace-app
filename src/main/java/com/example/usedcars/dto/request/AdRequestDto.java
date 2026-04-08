package com.example.usedcars.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdRequestDto {

    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title must be at most 150 characters")
    private String title;

    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Date posted is required")
    private LocalDate datePosted;

    private String location;

    @NotBlank(message = "Status is required")
    private String status;

    @Size(max = 500)
    private String imageUrl;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Car details are required")
    @Valid
    private CarRequestDto car;
}
