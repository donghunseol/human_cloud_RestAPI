package com.example.project_v2.love;

import com.example.project_v2._core.errors.exception.Exception401;
import com.example.project_v2._core.errors.exception.Exception403;
import com.example.project_v2.board.Board;
import com.example.project_v2.board.BoardJPARepository;
import com.example.project_v2.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoveService {
    private final LoveJPARepository loveJPARepository;
    private final BoardJPARepository boardJPARepository;

    @Transactional
    public Love save(LoveRequest.SaveDTO reqDTO, User sessionUser, Board board){
        Love love = loveJPARepository.save(reqDTO.toEntity(sessionUser, board));
        return love;
    }

    @Transactional
    public void delete(Integer loveId, User sessionUser){
        Love love = loveJPARepository.findById(loveId)
                .orElseThrow(() -> new Exception401("존재하지 않는 좋아요 입니다"));

        if (love.getUser().getId() != sessionUser.getId()){
            throw new Exception403("좋아요 삭제할 권한이 없습니다");
        }

        loveJPARepository.deleteById(loveId);
    }
}
