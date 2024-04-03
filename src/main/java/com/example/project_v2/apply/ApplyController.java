package com.example.project_v2.apply;

import com.example.project_v2._core.util.ApiUtil;
import com.example.project_v2.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ApplyController {

    private final ApplyService applyService;
    private final HttpSession session;

    // 합격 불합격
    @PostMapping("/api/applies/pass/{id}")
    public ResponseEntity<?> resumePass(@PathVariable Integer id, @RequestBody ApplyRequest.PassDTO passDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiUtil<>(401, "로그인이 필요합니다."));
        } // 로그인 안되어 있으면 로그인 해야함

        if (sessionUser.getRole() != 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiUtil<>(403, "권한이 없습니다."));
        } // 권한(기업 로그인 했을때만 유효)이 없으면 안됨

        ApplyResponse.DTO respDTO = applyService.resumePass(passDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 지원할 이력서 선택
    @GetMapping("/api/applies/{id}/resume-save")
    public ResponseEntity<?> resumeSave(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        ApplyResponse.SelectResumeDTO selectResumeDTO = applyService.findById(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(selectResumeDTO));
    }

    // 지원 취소
    @DeleteMapping("/api/applies/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        applyService.delete(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(null));
    }

    // 지원하기
    @PostMapping("/api/applies/{id}")
    public ResponseEntity<?> save(@RequestBody ApplyRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        ApplyResponse.DTO respDTO = applyService.save(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }
}

