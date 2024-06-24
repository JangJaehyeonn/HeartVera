package com.sparta.heartvera.domain.admin.controller;

import com.sparta.heartvera.domain.admin.service.AdminService;
import com.sparta.heartvera.domain.post.dto.PostRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "백오피스 API",description = "백오피스와 관련된 기능을 담당하는 API 입니다.")
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "익명글 전체 조회",description = "익명글을 전체 조회합니다.")
    @GetMapping("/posts")
    public ResponseEntity getAllPost(@RequestParam("page") int page, @RequestParam(value = "amount", defaultValue = "5") int amount) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllPost(page -1, amount));
    }

    @Operation(summary = "익명글 수정",description = "익명글을 수정합니다.")
    @PatchMapping("/posts/{postId}")
    public ResponseEntity editPost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.editPost(postId, requestDto));
    }

    @Operation(summary = "익명글 삭제",description = "선택한 익명글을 삭제합니다.")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.deletePost(postId));
    }

    @Operation(summary = "공개글 전체 조회",description = "공개글을 전체 조회합니다.")
    @GetMapping("/pubposts")
    public ResponseEntity getAllPublicPost(@RequestParam("page") int page, @RequestParam(value = "amount", defaultValue = "5") int amount) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllPublicPost(page -1, amount));
    }

    @Operation(summary = "공개글 수정",description = "공개글을 수정합니다.")
    @PatchMapping("/pubposts/{postId}")
    public ResponseEntity editPublicPost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.editPublicPost(postId, requestDto));
    }

    @Operation(summary = "공개글 삭제",description = "공개글을 삭제합니다.")
    @DeleteMapping("/pubposts/{postId}")
    public ResponseEntity deletePublicPost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.deletePublicPost(postId));
    }

    @Operation(summary = "전체 사용자 조회",description = "전체 사용자를 조회합니다.")
    @GetMapping("/user")
    public ResponseEntity findAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.findAllUser());
    }

    @Operation(summary = "사용자 권한 변경",description = "사용자의 권한을 변경합니다.")
    @PatchMapping("/user/{userId}")
    public ResponseEntity changeUserAuthority(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.changeUserAuthority(userId));
    }

    @Operation(summary = "댓글 전체 조회",description = "댓글을 전체 조회합니다.")
    @GetMapping("/comments")
    public ResponseEntity getAllComment() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllComment());
    }
}
