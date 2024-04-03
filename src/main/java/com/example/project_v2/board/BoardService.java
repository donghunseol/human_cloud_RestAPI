package com.example.project_v2.board;

import com.example.project_v2._core.errors.exception.Exception403;
import com.example.project_v2._core.errors.exception.Exception404;
import com.example.project_v2.user.SessionUser;
import com.example.project_v2.user.User;
import com.example.project_v2.user.UserJPARepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardJPARepository boardJPARepository;
    private final UserJPARepository userJPARepository;

    @Transactional
    public Board findById(Integer id) {
        Board board = boardJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("존재하지 않는 게시물 입니다"));
        return board;
    }

    @Transactional
    public BoardResponse.DTO update(Integer boardId, Integer sessionUserId, BoardRequest.UpdateDTO reqDTO) {
        User user = userJPARepository.findById(sessionUserId)
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));

        if (user.getId() != board.getUser().getId()) {
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }

        board.setTitle(reqDTO.getTitle());
        board.setContent(reqDTO.getContent());

        return new BoardResponse.DTO(board);
    } // 더티 체킹

    @Transactional
    public void delete(int boardId, Integer sessionUserId) {
        User user = userJPARepository.findById(sessionUserId)
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));
        if (user.getId() != board.getUser().getId()) {
            throw new Exception403("게시글을 삭제할 권한이 없습니다.");
        }
        boardJPARepository.deleteById(boardId);
    }

    @Transactional
    public BoardResponse.DTO save(BoardRequest.SaveDTO reqDTO, SessionUser sessionUser) {
        User user = userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));
        Board board = boardJPARepository.save(reqDTO.toEntity(user));
        return new BoardResponse.DTO(board);
    }

    // 게시글 상세보기
    @Transactional
    public BoardResponse.DetailDTO boardDetail(int boardId, SessionUser sessionUser) {
        User user = userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));
        Board board = boardJPARepository.findByIdJoinUser(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));
        return new BoardResponse.DetailDTO(board, user);
    }


    // 게시글 목록 보기
    @Transactional
    public List<BoardResponse.MainDTO> boardMain(Pageable pageable) {
        List<Board> boardList = boardJPARepository.findAllWithUser();
        return boardList.stream().map(board -> new BoardResponse.MainDTO(board)).toList();
    }

}
