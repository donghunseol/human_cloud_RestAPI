package com.example.project_v2.notice;

import com.example.project_v2._core.errors.exception.Exception403;
import com.example.project_v2._core.errors.exception.Exception404;
import com.example.project_v2.skill.Skill;
import com.example.project_v2.skill.SkillJPARepository;
import com.example.project_v2.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeService {
    private final NoticeJPARepository noticeJPARepository;
    private final SkillJPARepository skillJPARepository;

    @Transactional
    public NoticeResponse.DTO update(Integer noticeId, NoticeRequest.UpdateDTO reqDTO, User sessionUser) {
        Notice notice = noticeJPARepository.findById(noticeId)
                .orElseThrow(() -> new Exception404("존재하지 않는 공고입니다"));
        notice.setTitle(reqDTO.getTitle());
        notice.setType(reqDTO.getType());
        notice.setField(reqDTO.getField());
        notice.setWorkPlace(reqDTO.getWorkPlace());
        notice.setDeadline(reqDTO.getDeadline());
        notice.setContent(reqDTO.getContent());
        notice.setUser(reqDTO.getUser());

        // 공고 스킬 정보 삭제 후, 추가 (스킬이 없는 경우에는 삭제 안함)
        if (!skillJPARepository.findByNoticeId(noticeId).isEmpty()) {
            skillJPARepository.deleteAllByNoticeId(noticeId);
        }

        // 스킬 정보 생성
        List<Skill> skills = new ArrayList<>();
        for (NoticeRequest.UpdateDTO.SkillDTO skill : reqDTO.getSkills()) {
            Skill skillBuild = Skill.builder()
                    .name(skill.getName())
                    .role(skill.getRole())
                    .notice(notice)
                    .build();
            skills.add(skillBuild);
        }

        skills = skillJPARepository.saveAll(skills);
        notice.setSkills(skills);

        return new NoticeResponse.DTO(notice, sessionUser);
    }

    @Transactional(readOnly = true)
    public NoticeResponse.DetailDTO noticeDetail(Integer noticeId, User sessionUser) {
        Notice notice = noticeJPARepository.findByIdJoinUser(noticeId)
                .orElseThrow(() -> new Exception404("공고 글을 찾을 수 없음"));

        return new NoticeResponse.DetailDTO(notice, sessionUser);
    }

    @Transactional
    public void delete(Integer noticeId, Integer sessionUserId) {
        Notice notice = noticeJPARepository.findById(noticeId)
                .orElseThrow(() -> new Exception404("공고를 찾을 수 없습니다"));

        if (sessionUserId != notice.getUser().getId()) {
            throw new Exception403("공고를 삭제할 권한이 없습니다");
        }
        noticeJPARepository.deleteById(noticeId);
    }

    @Transactional
    public NoticeResponse.DTO save(NoticeRequest.SaveDTO reqDTO, User sessionUser) {
        Notice notice = noticeJPARepository.save(reqDTO.toEntity(sessionUser));

        // 1번 방법 -> skill 로 안받으면 reqDTO 의 id 값이 null 로 json이 뜬다
//        reqDTO.getSkills().forEach(skill -> {
//            skill.setNotice(notice);
//            skillJPARepository.save(skill.toEntity());
//        });

        // 2번 방법
        List<Skill> skills = new ArrayList<>();
        for (NoticeRequest.SaveDTO.SkillDTO skill : reqDTO.getSkills()) {
            Skill skillBuild = Skill.builder()
                    .name(skill.getName())
                    .role(skill.getRole())
                    .notice(notice)
                    .build();
            skills.add(skillBuild);
        }
        skills = skillJPARepository.saveAll(skills);
        notice.setSkills(skills);

        Notice newNotice = noticeJPARepository.save(notice);

        return new NoticeResponse.DTO(newNotice, sessionUser);
    }


    @Transactional(readOnly = true)
    public List<NoticeResponse.NoticeListDTO> noticeList(Pageable pageable) {
        Page<Notice> noticeList = noticeJPARepository.findAll(pageable);
        return noticeList.stream().map(notice -> new NoticeResponse.NoticeListDTO(notice)).toList();
    }


    @Transactional(readOnly = true)
    public List<NoticeResponse.NoticeListDTO> noticeListByUser(User sessionUser, Pageable pageable) {
        List<Notice> noticeList = noticeJPARepository.findByUser(sessionUser, pageable);
        return noticeList.stream().map(notice -> new NoticeResponse.NoticeListDTO(notice)).toList();
    }
}
