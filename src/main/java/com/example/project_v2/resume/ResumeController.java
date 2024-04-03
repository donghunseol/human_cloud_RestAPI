package com.example.project_v2.resume;

import com.example.project_v2._core.util.ApiUtil;
import com.example.project_v2.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ResumeController {

    private final ResumeService resumeService;
    private final HttpSession session;

    // 이력서 회원 리스트 출력
    @GetMapping("/api/resumes/{id}")
    public ResponseEntity<?> index(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiUtil<>(null));
    }

    // 이력서 작성
    @PostMapping("/api/resumes")
    public ResponseEntity<?> save(@Valid @RequestBody ResumeRequest.SaveDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        ResumeResponse.DTO respDTO = resumeService.save(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 이력서 삭제
    @DeleteMapping("/api/resumes/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        resumeService.delete(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(null));
    }

    // 이력서 수정
    @PutMapping("/api/resumes/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody ResumeRequest.UpdateDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        reqDTO.toEntity(sessionUser);
        ResumeResponse.DTO respDTO = resumeService.update(id, sessionUser, reqDTO);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 이력서 상세 보기
    @GetMapping("/api/resumes/{id}/detail")
    public ResponseEntity<?> detail(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        ResumeResponse.DetailDTO respDTO = resumeService.resumeDetail(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 이력서 리스트
    @GetMapping("/api/resumes")
    public ResponseEntity<?> resumeList(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sortBy,
                                        @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        List<ResumeResponse.ResumeListDTO> respDTO = resumeService.resumeList(pageable);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 이력서 리스트(개인)
    @GetMapping("/api/resumes/my-resumes")
    public ResponseEntity<?> myResumeList(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy,
                                          @RequestParam(defaultValue = "desc") String direction) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        List<ResumeResponse.ResumeListDTO> respDTO = resumeService.resumeListByUser(sessionUser, pageable);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }
}
