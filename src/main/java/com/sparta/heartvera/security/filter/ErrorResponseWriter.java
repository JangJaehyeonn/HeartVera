package com.sparta.heartvera.security.filter;

import com.sparta.heartvera.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ErrorResponseWriter {
    // response body에 글로벌 예외 처리 메시지 날리기
    protected static void writeMessageResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(errorCode.getStatus());
        response.getWriter().write(errorCode.getMsg());
    }
    // response body에 글로벌 성공 메시지 날리기
    protected static void writeMessageResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(message);
    }
}