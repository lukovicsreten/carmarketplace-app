package com.example.usedcars.service;

import com.example.usedcars.dto.request.CommentRequestDto;
import com.example.usedcars.dto.response.CommentResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto createComment(CommentRequestDto dto);

    CommentResponseDto getCommentById(Long id);

    List<CommentResponseDto> getAllComments();

    List<CommentResponseDto> getCommentsByAdId(Long adId);

    List<CommentResponseDto> getCommentsByUserId(Long userId);

    CommentResponseDto updateComment(Long id, CommentRequestDto dto);

    void deleteComment(Long id);
}
