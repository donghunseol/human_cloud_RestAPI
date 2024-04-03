package com.example.project_v2.love;

import com.example.project_v2._core.errors.exception.Exception401;
import com.example.project_v2._core.errors.exception.Exception403;
import com.example.project_v2.board.Board;
import com.example.project_v2.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoveService {
    private final LoveJPARepository loveJPARepository;

    @Transactional
    public LoveResponse.DTO save(LoveRequest.SaveDTO reqDTO, User sessionUser, Board board){
        Love love = loveJPARepository.save(reqDTO.toEntity(sessionUser, board));
        return new LoveResponse.DTO(love, sessionUser, board);
    }

    @Transactional
    public void delete(Integer boardId, User sessionUser){
        Love love = loveJPARepository.findLoveByBoardIdAndUserId(boardId, sessionUser.getId())
                .orElseThrow(() -> new Exception401("존재하지 않는 좋아요 입니다"));

        if (love.getUser().getId() != sessionUser.getId()){
            throw new Exception403("좋아요 삭제할 권한이 없습니다");
        }

        loveJPARepository.deleteById(love.getId());
    }
}
