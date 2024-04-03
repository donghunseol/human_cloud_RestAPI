package com.example.project_v2._core.errors;

import com.example.project_v2._core.errors.exception.*;
import com.example.project_v2._core.util.ApiUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
// RuntimeException이 터지면 해당 파일로 오류가 모인다
@RestControllerAdvice // 데이터 응답
public class MyExceptionHandler {

    @ExceptionHandler(Exception400.class)
    public String ex400(Exception400 e, HttpServletRequest request) {
        request.setAttribute("msg", e.getMessage());
        log.warn("400 : " + e.getMessage());
        return "err/400";
        // ApiUtil<?> apiUtil = new ApiUtil<>(400, e.getMessage()); // http body -> 구성한 객체 body 에도 상태 코드를 넣는 이유는 프론트가 편하게 해주기 위해서!
        // return new ResponseEntity<>(apiUtil, HttpStatus.BAD_REQUEST); // http body, http header
    }

    @ExceptionHandler(Exception401.class)
    public String ex401(Exception401 e, HttpServletRequest request) {
        request.setAttribute("msg", e.getMessage());
        log.warn("401 : " + e.getMessage()); // ex) 로그인 실패 다이렉트 메세지 [위험도는 낮지만 주의해야 하는 점이 있다 : 강제로 접속하는 인원이 발생]
        log.warn("IP : " + request.getRemoteAddr()); // 누군지 IP 확인
        log.warn("Agent : " + request.getHeader("User-Agent")); // 장비 확인
        return "err/401";
        // ApiUtil<?> apiUtil = new ApiUtil<>(401, e.getMessage());
        // return new ResponseEntity<>(apiUtil, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception403.class)
    public String ex403(Exception403 e, HttpServletRequest request) {
        request.setAttribute("msg", e.getMessage());
        log.warn("403 : " + e.getMessage());
        return "err/403";
        // ApiUtil<?> apiUtil = new ApiUtil<>(403, e.getMessage());
        // return new ResponseEntity<>(apiUtil, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception404.class)
    public String ex404(Exception404 e, HttpServletRequest request) {
        request.setAttribute("msg", e.getMessage());
        log.info("403 : " + e.getMessage());
        return "err/404";
        // ApiUtil<?> apiUtil = new ApiUtil<>(404, e.getMessage());
        // return new ResponseEntity<>(apiUtil, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception500.class)
    public String ex500(Exception500 e, HttpServletRequest request) {
        request.setAttribute("msg", e.getMessage());
        log.error("500 : " + e.getMessage());
        return "err/500";
        // ApiUtil<?> apiUtil = new ApiUtil<>(500, e.getMessage());
        // return new ResponseEntity<>(apiUtil, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
