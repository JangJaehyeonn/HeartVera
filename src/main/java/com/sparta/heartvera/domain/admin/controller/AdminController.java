package com.sparta.heartvera.domain.admin.controller;

import com.sparta.heartvera.domain.admin.service.AdminService;
import com.sparta.heartvera.domain.post.dto.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/posts")
    public ResponseEntity getAllPost(@RequestParam int page) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllPost(page -1));
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity editPost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.editPost(postId, requestDto));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.deletePost(postId));
    }

    @GetMapping("/pubposts")
    public ResponseEntity getAllPublicPost(@RequestParam int page) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllPublicPost(page -1));
    }

    @PatchMapping("/pubposts/{postId}")
    public ResponseEntity editPublicPost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.editPublicPost(postId, requestDto));
    }

    @DeleteMapping("/pubposts/{postId}")
    public ResponseEntity deletePublicPost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.deletePublicPost(postId));
    }

    @GetMapping("/user")
    public ResponseEntity findAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.findAllUser());
    }

    @PatchMapping("/user/{userId}")
    public ResponseEntity changeUserAuthority(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.changeUserAuthority(userId));
    }

    @GetMapping("/comments")
    public ResponseEntity getAllComment() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllComment());
    }
}
