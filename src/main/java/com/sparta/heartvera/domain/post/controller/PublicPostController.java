package com.sparta.heartvera.domain.post.controller;

import com.sparta.heartvera.domain.post.dto.PostRequestDto;
import com.sparta.heartvera.domain.post.service.PublicPostService;
import com.sparta.heartvera.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pubposts")
public class PublicPostController {

    private final PublicPostService postService;

    @PostMapping("/")
    public ResponseEntity saveRecipe(@Valid @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.savePost(requestDto, userDetails.getUser()));
    }

    @GetMapping("/{postId}")
    public ResponseEntity getPost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(postId));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity editPost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.editPost(postId, requestDto, userDetails.getUser()));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.deletePost(postId, userDetails.getUser()));
    }

    @GetMapping("/")
    public ResponseEntity getAllPost(@RequestParam("page") int page) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPost(page - 1));
    }
}
