package com.sparta.heartvera.domain.admin.controller;

import com.sparta.heartvera.domain.admin.service.AdminService;
import com.sparta.heartvera.domain.post.dto.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/post")
    public ResponseEntity getAllPost(@RequestParam int page) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllPost(page));
    }

    @PatchMapping("/post/{postId}")
    public ResponseEntity editPost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.editPost(postId, requestDto));
    }
}
