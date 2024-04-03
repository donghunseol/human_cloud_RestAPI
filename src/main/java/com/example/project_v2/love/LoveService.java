package com.example.project_v2.love;

import com.example.project_v2._core.errors.exception.Exception401;
import com.example.project_v2._core.errors.exception.Exception403;
import com.example.project_v2._core.errors.exception.Exception404;
import com.example.project_v2.board.Board;
import com.example.project_v2.user.SessionUser;
import com.example.project_v2.user.User;
import com.example.project_v2.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoveService {
    private final LoveJPARepository loveJPARepository;
    private final UserJPARepository userJPARepository;

    @Transactional
    public LoveResponse.DTO save(LoveRequest.SaveDTO reqDTO, SessionUser sessionUser, Board board){
        User user = userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));
        Love love = loveJPARepository.save(reqDTO.toEntity(user, board));
        return new LoveResponse.DTO(love, user, board);
    }

    @Transactional
    public void delete(Integer boardId, SessionUser sessionUser){
        User user = userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));
        Love love = loveJPARepository.findLoveByBoardIdAndUserId(boardId, user.getId())
                .orElseThrow(() -> new Exception401("존재하지 않는 좋아요 입니다"));

        if (love.getUser().getId() != user.getId()){
            throw new Exception403("좋아요 삭제할 권한이 없습니다");
        }

        loveJPARepository.deleteById(love.getId());
    }
}
