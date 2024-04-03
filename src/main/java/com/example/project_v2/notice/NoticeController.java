package com.example.project_v2.notice;

import com.example.project_v2._core.util.ApiUtil;
import com.example.project_v2.user.SessionUser;
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
public class NoticeController {

    private final NoticeService noticeService;
    private final HttpSession session;

    // 공고 회원 목록 보기
    @GetMapping("/api/notices/my-notices")
    public ResponseEntity<?> myNoticeList(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy,
                                          @RequestParam(defaultValue = "desc") String direction) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        List<NoticeResponse.NoticeListDTO> respDTO = noticeService.noticeListByUser(sessionUser, pageable);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 공고 목록 보기
    @GetMapping("/notices")
    public ResponseEntity<?> index(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sortBy,
                                   @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        List<NoticeResponse.NoticeListDTO> respDTO = noticeService.noticeList(pageable);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 공고 작성
    @PostMapping("/api/notices")
    public ResponseEntity<?> save(@Valid @RequestBody NoticeRequest.SaveDTO reqDTO, Errors errors) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        NoticeResponse.DTO respDTO = noticeService.save(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 공고 상세 보기
    @GetMapping("/notices/{id}/detail")
    public ResponseEntity<?> detail(@PathVariable Integer id) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        NoticeResponse.DetailDTO respDTO = noticeService.noticeDetail(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 공고 삭제
    @DeleteMapping("/api/notices/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        noticeService.delete(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(null));
    }

    // 공고 수정
    @PutMapping("/api/notices/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody NoticeRequest.UpdateDTO reqDTO, Errors errors) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        //reqDTO.toEntity(sessionUser);
        NoticeResponse.DTO respDTO = noticeService.update(id, reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }
}