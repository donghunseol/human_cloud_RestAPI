package com.example.project_v2.scrap;

import com.example.project_v2._core.errors.exception.Exception401;
import com.example.project_v2._core.errors.exception.Exception403;
import com.example.project_v2._core.errors.exception.Exception404;
import com.example.project_v2.notice.Notice;
import com.example.project_v2.resume.Resume;
import com.example.project_v2.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ScrapService {
    private final ScrapJPARepository scrapJPARepository;

    public List<ScrapResponse.ScrapListDTO> scrapList(Integer sessionUserId, Integer id, Pageable pageable){
        if(sessionUserId == null){
            throw new Exception401("로그인을 먼저 해주세요");
        } else if (id != sessionUserId) {
            throw new Exception403("스크랩 리스트 조회할 권한이 없습니다.");
        }
        List<Scrap> scrapList = scrapJPARepository.findById(sessionUserId, pageable);
        return scrapList.stream().map(scrap -> new ScrapResponse.ScrapListDTO(scrap, sessionUserId)).toList();
    }

    @Transactional
    public ScrapResponse.DTO save(Resume resume, ScrapRequest.SaveDTO reqDTO, User sessionUser) {
        Scrap scrap = scrapJPARepository.save(reqDTO.toEntity(sessionUser, resume));
        return new ScrapResponse.DTO(scrap);
    }

    @Transactional
    public ScrapResponse.DTO save(Notice notice, ScrapRequest.SaveDTO reqDTO, User sessionUser) {
        Scrap scrap = scrapJPARepository.save(reqDTO.toEntity(sessionUser, notice));
        return new ScrapResponse.DTO(scrap);
    }

    @Transactional
    public void delete(Integer id, User sessionUser) {
        Optional<Scrap> scrapOP = null;
        Scrap scrap = null;

        if(sessionUser.getRole()==0){
            scrapOP = scrapJPARepository.findByNoticeIdAndUserId(id, sessionUser.getId());
        }else{
            scrapOP = scrapJPARepository.findByResumeIdAndUserId(id, sessionUser.getId());
        }

        if(scrapOP.isEmpty()){
            throw new Exception401("존재하지 않는 스크랩입니다.");
        }else{
            scrap = scrapOP.get();
        }

        if(scrap.getUser().getId() != sessionUser.getId()){
            throw new Exception403("스크랩을 삭제할 권한이 없습니다.");
        }

        scrapJPARepository.deleteById(scrap.getId());
    }
}
