package com.example.project_v2.board;

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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;


    // 게시글 수정
    @PutMapping("/api/boards/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid  @RequestBody BoardRequest.UpdateDTO reqDTO, Errors errors) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        BoardResponse.DTO respDTO = boardService.update(id, sessionUser.getId(), reqDTO);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }
    // 게시글 삭제
    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        SessionUser sessionUSer = (SessionUser) session.getAttribute("sessionUser");
        boardService.delete(id, sessionUSer.getId());
        return ResponseEntity.ok(new ApiUtil<>(null));
    }


    // 게시글 작성
    @PostMapping("/api/boards")
    public ResponseEntity<?> save(@Valid @RequestBody BoardRequest.SaveDTO reqDTO, Errors errors) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        // 로그인 여부 체크
        BoardResponse.DTO respDTO = boardService.save(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    // 게시글 목록 보기
    @GetMapping("/boards")
    public ResponseEntity<?> index(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sortBy,
                                   @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        List<BoardResponse.MainDTO> respDTO = boardService.boardMain(pageable);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 게시글 상세 보기
    @GetMapping("/boards/{id}/detail")
    public ResponseEntity<?> detail(@PathVariable Integer id) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        BoardResponse.DetailDTO respDTO = boardService.boardDetail(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }
}

