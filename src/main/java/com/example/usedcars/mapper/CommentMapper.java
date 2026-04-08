package com.example.usedcars.mapper;

import com.example.usedcars.dto.request.CommentRequestDto;
import com.example.usedcars.dto.response.CommentResponseDto;
import com.example.usedcars.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toEntity(CommentRequestDto dto) {
        return Comment.builder()
                .content(dto.getContent())
                .text(dto.getText())
                .datePosted(dto.getDatePosted())
                .rating(dto.getRating())
                .build();
    }

    public CommentResponseDto toResponseDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .text(comment.getText())
                .datePosted(comment.getDatePosted())
                .rating(comment.getRating())
                .createdAt(comment.getCreatedAt())
                .userId(comment.getUser().getId())
                .username(comment.getUser().getUsername())
                .adId(comment.getAd().getId())
                .build();
    }

    public void updateEntity(Comment comment, CommentRequestDto dto) {
        comment.setContent(dto.getContent());
        comment.setText(dto.getText());
        comment.setDatePosted(dto.getDatePosted());
        comment.setRating(dto.getRating());
    }
}
