package com.example.usedcars.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDto {

    @NotBlank(message = "Comment content is required")
    private String content;

    @NotBlank(message = "Comment text is required")
    private String text;

    @NotNull(message = "Date posted is required")
    private LocalDate datePosted;

    private Integer rating;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Ad ID is required")
    private Long adId;
}
