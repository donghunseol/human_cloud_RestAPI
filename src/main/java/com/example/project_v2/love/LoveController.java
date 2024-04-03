package com.example.project_v2.love;

import com.example.project_v2._core.util.ApiUtil;
import com.example.project_v2.board.Board;
import com.example.project_v2.board.BoardService;
import com.example.project_v2.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LoveController {

    private final LoveService loveService;
    private final BoardService boardService;
    private final HttpSession session;

    // 좋아요 입력
    @PostMapping("/api/loves/{id}")
    public ResponseEntity<?> save(@PathVariable Integer id, @RequestBody LoveRequest.SaveDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.findById(id);
        LoveResponse.DTO respDTO = loveService.save(reqDTO, sessionUser, board);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 좋아요 삭제
    @DeleteMapping("/api/loves/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        loveService.delete(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}
