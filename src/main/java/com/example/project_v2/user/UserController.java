package com.example.project_v2.user;

import com.example.project_v2._core.util.ApiUtil;
import com.example.project_v2._core.util.JwtUtil;
import com.example.project_v2.apply.Apply;
import com.example.project_v2.apply.ApplyJPARepository;
import com.example.project_v2.apply.ApplyResponse;
import com.example.project_v2.scrap.ScrapResponse;
import com.example.project_v2.scrap.ScrapService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final ScrapService scrapService;
    private final UserService userService;
    private final HttpSession session;

    // 메인 화면
    @GetMapping("/")
    public ResponseEntity<?> index(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sortBy,
                                   @RequestParam(defaultValue = "desc") String direction,
                                   @RequestParam(required = false) String skillName) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        List<?> mainPageList;

        if (skillName != null && !skillName.isEmpty()) {
            mainPageList = userService.getMainPageByUserRoleAndSkill(sessionUser, skillName, pageable);
        } else {
            mainPageList = userService.getMainPageByUserRole(sessionUser, pageable);
        }

        return ResponseEntity.ok(new ApiUtil<>(mainPageList));
    }

    // 회원 가입
    @PostMapping("/users/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserRequest.JoinDTO reqDTO, Errors errors, BindingResult bindingResult) {
        UserResponse.DTO respDTO = userService.join(reqDTO);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 로그인
    @PostMapping("/users/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest.LoginDTO reqDTO) {
        String jwt = userService.login(reqDTO);
        UserResponse.LoginDTO respDTO = userService.loginByDTO(reqDTO);
        return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).body(new ApiUtil<>(respDTO)); // header 문법
    }

    // 회원 정보 수정
    @PutMapping("/api/users/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody UserRequest.UpdateDTO reqDTO, Errors errors) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        SessionUser newSessionUser = userService.update(sessionUser.getId(), reqDTO);
        session.setAttribute("sessionUser", newSessionUser);
        return ResponseEntity.ok(new ApiUtil<>(newSessionUser));
    }

    // 로그아웃
    @GetMapping("/api/users/logout")
    public ResponseEntity<?> logout() {
        session.invalidate();
        return ResponseEntity.ok(new ApiUtil<>(null));
    }

    // 마이페이지 메인 (공고, 이력서 출력)
    @GetMapping("/api/myPages")
    public ResponseEntity<?> myPage(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy,
                                    @RequestParam(defaultValue = "desc") String direction) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<?> myPageList = userService.getMyPage(sessionUser, pageable);
        return ResponseEntity.ok(new ApiUtil<>(myPageList));
    }

    // 마이 페이지 - 지원한 공고 (공고 출력 / 이력서 신청 여부)
    @GetMapping("/api/myPages/{id}/selectList")
    public ResponseEntity<?> myPageList(@PathVariable("id") Integer userId) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");

        // 사용자가 지원한 공고 정보 조회
        List<Apply> applies = userService.findAppliesByUserId(sessionUser);

        // 응답 객체 구성
        List<ApplyResponse.UserListDTO> responseList = applies.stream()
                .map(apply -> new ApplyResponse.UserListDTO(apply))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ApiUtil<>(responseList));
    }


    // 회원가입(username) 중복 확인
    @GetMapping("/api/username-same-checks")
    public ApiUtil<?> usernameSameCheck(String username) {
        User user = userService.sameCheck(username);
        if (user == null) {
            return new ApiUtil<>(true);
        } else {
            return new ApiUtil<>(false);
        }
    }

    // 스크랩 리스트
    @GetMapping("/api/scraps/{id}")
    public ResponseEntity<?> scrapList(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sortBy,
                                       @RequestParam(defaultValue = "desc") String direction,
                                       @PathVariable Integer id) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        List<ScrapResponse.ScrapListDTO> respDTO = scrapService.scrapList(sessionUser, id, pageable);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }
}


