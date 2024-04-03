package com.example.project_v2.resume;

import com.example.project_v2._core.errors.exception.Exception403;
import com.example.project_v2._core.errors.exception.Exception404;
import com.example.project_v2.skill.Skill;
import com.example.project_v2.skill.SkillJPARepository;
import com.example.project_v2.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ResumeService {
    private final ResumeJPARepository resumeJPARepository;
    private final SkillJPARepository skillJPARepository;

    @Transactional
    public ResumeResponse.DTO update(Integer resumeId, User sessionUser, ResumeRequest.UpdateDTO reqDTO) {
        Resume resume = resumeJPARepository.findById(resumeId)
                .orElseThrow(() -> new Exception404("이력서를 찾을 수 없습니다."));

        if (sessionUser.getId() != resume.getUser().getId()) {
            throw new Exception403("이력서를 수정할 권한이 없습니다.");
        }

        resume.setUser(reqDTO.getUser());
        resume.setTitle(reqDTO.getTitle());
        resume.setCareer(reqDTO.getCareer());
        resume.setLicense(reqDTO.getLicense());
        resume.setEducation(reqDTO.getEducation());
        resume.setMajor(reqDTO.getMajor());

        // 이력서에 스킬 정보 삭제 후, 추가
        if (!skillJPARepository.findByResumeId(resumeId).isEmpty()) {
            skillJPARepository.deleteAllByResumeId(resumeId);
        }

        // 스킬 정보를 생성
        List<Skill> skills = new ArrayList<>();
        for (ResumeRequest.UpdateDTO.SkillDTO skillDTO : reqDTO.getSkills()) {
            Skill skill = Skill.builder()
                    .name(skillDTO.getName())
                    .role(skillDTO.getRole())
                    .resume(resume)
                    .build();
            skills.add(skill);
        }


        skills = skillJPARepository.saveAll(skills);
        resume.setSkills(skills);

        return new ResumeResponse.DTO(resume, sessionUser);
    }

    @Transactional
    public void delete(Integer resumeId, Integer sessionUserId) {
        Resume resume = resumeJPARepository.findById(resumeId)
                .orElseThrow(() -> new Exception404("이력서를 찾을 수 없습니다."));
        if (sessionUserId != resume.getUser().getId()) {
            throw new Exception403("이력서를 삭제할 권한이 없습니다.");
        }
        resumeJPARepository.deleteById(resumeId);
    }

    @Transactional
    public ResumeResponse.DTO save(ResumeRequest.SaveDTO reqDTO, User sessionUser) {
        // 이력서 정보 저장
        Resume resume = resumeJPARepository.save(reqDTO.toEntity(sessionUser));

        // 스킬 정보를 생성
        List<Skill> skills = new ArrayList<>();
        for (ResumeRequest.SaveDTO.SkillDTO skillDTO : reqDTO.getSkills()) {
            Skill skill = Skill.builder()
                    .name(skillDTO.getName())
                    .role(skillDTO.getRole())
                    .resume(resume)
                    .build();
            skills.add(skill);
        }

        // 이력서에 스킬 정보 추가
        skills = skillJPARepository.saveAll(skills);
        resume.setSkills(skills);

        Resume newResume = resumeJPARepository.save(resume);

        return new ResumeResponse.DTO(newResume, sessionUser);
    }

    // 이력서 상세보기
    @Transactional
    public ResumeResponse.DetailDTO resumeDetail(int resumeId, User sessionUser) {
        Resume resume = resumeJPARepository.findByIdJoinUser(resumeId)
                .orElseThrow(() -> new Exception404("이력서를 찾을 수 없습니다."));
        return new ResumeResponse.DetailDTO(resume, sessionUser);
    }

    // 이력서 리스트
    @Transactional
    public List<ResumeResponse.ResumeListDTO> resumeList(Pageable pageable) {
        Page<Resume> resumeList = resumeJPARepository.findAll(pageable);
        return resumeList.stream().map(resume -> new ResumeResponse.ResumeListDTO(resume)).toList();
    }

    // 이력서 리스트(개인)
    @Transactional
    public List<ResumeResponse.ResumeListDTO> resumeListByUser(User user, Pageable pageable) {
        List<Resume> resumeList = resumeJPARepository.findByUser(user, pageable);
        return resumeList.stream().map(resume -> new ResumeResponse.ResumeListDTO(resume)).toList();
    }
}
