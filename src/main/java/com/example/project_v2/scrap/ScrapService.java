package com.example.project_v2.scrap;

import com.example.project_v2._core.errors.exception.Exception401;
import com.example.project_v2._core.errors.exception.Exception403;
import com.example.project_v2._core.errors.exception.Exception404;
import com.example.project_v2.notice.Notice;
import com.example.project_v2.resume.Resume;
import com.example.project_v2.user.SessionUser;
import com.example.project_v2.user.User;
import com.example.project_v2.user.UserJPARepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ScrapService {
    private final ScrapJPARepository scrapJPARepository;
    private final UserJPARepository userJPARepository;

    @Transactional
    public List<ScrapResponse.ScrapListDTO> scrapList(SessionUser sessionUser, Integer id, Pageable pageable){
        User user = userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));
        if(user != null){ // 로그인 했을 때
            if(user.getId() != id){ // 로그인 한 회원의 id와 스크랩 목록 주소의 id랑 다른 경우
                throw new Exception403("스크랩 목록을 조회할 권한이 없습니다.");
            }
            // 해당 유저의 스크랩 내역 조회 및 출력
            List<Scrap> scrapList = scrapJPARepository.findById(user.getId(), pageable);
            return scrapList.stream().map(scrap -> new ScrapResponse.ScrapListDTO(scrap, user)).toList();
        }else { //로그인 하지 않은 경우
            throw new Exception401("로그인 먼저 해주세요");
        }
    }

    @Transactional
    public ScrapResponse.DTO save(Resume resume, ScrapRequest.SaveDTO reqDTO, SessionUser sessionUser) {
        User user =userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));
        Scrap scrap = scrapJPARepository.save(reqDTO.toEntity(user, resume));
        return new ScrapResponse.DTO(scrap);
    }

    @Transactional
    public ScrapResponse.DTO save(Notice notice, ScrapRequest.SaveDTO reqDTO, SessionUser sessionUser) {
        User user =userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));
        Scrap scrap = scrapJPARepository.save(reqDTO.toEntity(user, notice));
        return new ScrapResponse.DTO(scrap);
    }

    @Transactional
    public void delete(Integer id, SessionUser sessionUser) {
        User user = userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));
        Optional<Scrap> scrapOP = null;
        Scrap scrap = null;

        if(user.getRole()==0){
            scrapOP = scrapJPARepository.findByNoticeIdAndUserId(id, user.getId());
        }else{
            scrapOP = scrapJPARepository.findByResumeIdAndUserId(id, user.getId());
        }

        if(scrapOP.isEmpty()){
            throw new Exception401("존재하지 않는 스크랩입니다.");
        }else{
            scrap = scrapOP.get();
        }

        if(scrap.getUser().getId() != user.getId()){
            throw new Exception403("스크랩을 삭제할 권한이 없습니다.");
        }

        scrapJPARepository.deleteById(scrap.getId());
    }
}
