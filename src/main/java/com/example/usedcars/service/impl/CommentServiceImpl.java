package com.example.usedcars.service.impl;

import com.example.usedcars.dto.request.CommentRequestDto;
import com.example.usedcars.dto.response.CommentResponseDto;
import com.example.usedcars.entity.Ad;
import com.example.usedcars.entity.Comment;
import com.example.usedcars.entity.User;
import com.example.usedcars.exception.ResourceNotFoundException;
import com.example.usedcars.mapper.CommentMapper;
import com.example.usedcars.repository.AdRepository;
import com.example.usedcars.repository.CommentRepository;
import com.example.usedcars.repository.UserRepository;
import com.example.usedcars.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentResponseDto createComment(CommentRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", dto.getUserId()));
        Ad ad = adRepository.findById(dto.getAdId())
                .orElseThrow(() -> new ResourceNotFoundException("Ad", dto.getAdId()));

        Comment comment = commentMapper.toEntity(dto);
        comment.setUser(user);
        comment.setAd(ad);
        Comment saved = commentRepository.save(comment);
        return commentMapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponseDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", id));
        return commentMapper.toResponseDto(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByAdId(Long adId) {
        return commentRepository.findByAdId(adId).stream()
                .map(commentMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByUserId(Long userId) {
        return commentRepository.findByUserId(userId).stream()
                .map(commentMapper::toResponseDto)
                .toList();
    }

    @Override
    public CommentResponseDto updateComment(Long id, CommentRequestDto dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", id));
        checkOwnershipOrAdmin(comment.getUser().getUsername());
        commentMapper.updateEntity(comment, dto);
        Comment updated = commentRepository.save(comment);
        return commentMapper.toResponseDto(updated);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", id));
        checkOwnershipOrAdmin(comment.getUser().getUsername());
        commentRepository.delete(comment);
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
}
