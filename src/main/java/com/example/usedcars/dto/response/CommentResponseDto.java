package com.example.usedcars.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long id;
    private String content;
    private String text;
    private LocalDate datePosted;
    private Integer rating;
    private LocalDateTime createdAt;
    private Long userId;
    private String username;
    private Long adId;
}
