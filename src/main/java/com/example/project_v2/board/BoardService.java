package com.example.project_v2.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardJPARepository boardJPARepository;

    // 게시글 상세보기
    public BoardResponse.DetailDTO boardDetail(int boardId) {
        Board board = boardJPARepository.findByIdJoinUser(boardId);
        return new BoardResponse.DetailDTO(board);
    }

    public List<BoardResponse.MainDTO> 게시글목록보기(){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Board> boardList = boardJPARepository.findAll(sort);
        return boardList.stream().map(board -> new BoardResponse.MainDTO(board)).toList();
    }

}
