package com.sparta.heartvera.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.heartvera.common.exception.ErrorCode;
import com.sparta.heartvera.domain.auth.dto.LoginRequestDto;
import com.sparta.heartvera.domain.auth.dto.TokenResponseDto;
import com.sparta.heartvera.domain.user.entity.UserRoleEnum;
import com.sparta.heartvera.domain.user.repository.UserRepository;
import com.sparta.heartvera.security.service.UserDetailsImpl;
import com.sparta.heartvera.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;

        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto UserServiceReqDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class); // httpServletRequest의 request를 LoginRequestDto로 변환

            return getAuthenticationManager().authenticate( // 인증객체 만들기.
                    new UsernamePasswordAuthenticationToken(
                            UserServiceReqDto.getUserId(),
                            UserServiceReqDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // 콜백메서드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");

        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getAuthority();
        TokenResponseDto tokenResponse = jwtUtil.createToken(username, role);

        // 액세스 토큰 헤더에 추가
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, tokenResponse.getAccessToken());

        // 리프래쉬 토큰 유저 DB에 추가
        userRepository.findByUserId(username).ifPresent(
                user -> {
                    user.setRefreshToken(tokenResponse.getRefreshToken());
                    userRepository.save(user);
                }
        );

        // 설정된 메시지와 상태 코드 반환
        response.setStatus(HttpStatus.OK.value());
        ErrorResponseWriter.writeMessageResponse(response, "로그인을 성공하였습니다.");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");

        // 설정된 메시지와 상태 코드 response body로 반환
        ErrorResponseWriter.writeMessageResponse(response, ErrorCode.USER_NOT_FOUND);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        if (uri.startsWith("/api/user")) {
            chain.doFilter(request, response);
            return;
        }
        super.doFilter(request, response, chain);

    }

}
