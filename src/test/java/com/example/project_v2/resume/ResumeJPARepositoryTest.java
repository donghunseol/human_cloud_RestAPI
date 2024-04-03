package com.example.project_v2.resume;

import com.example.project_v2.user.User;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ResumeJPARepositoryTest {

    @Autowired
    private ResumeJPARepository resumeJPARepository;

    @Autowired
    private EntityManager em;

    @Test
    public void save_test() {
        // given
        User sessionUser = User.builder().id(1).build();
        Resume resume = Resume.builder()
                .user(sessionUser)
                .title("제목5")
                .career("1년")
                .license("정보처리기사, SQLD")
                .education("쌀대학교, 졸업")
                .major("컴퓨터공학과")
                .build();

        // when
        resumeJPARepository.save(resume);

        // then
        Assertions.assertThat(resume.getId()).isEqualTo(7);
    }
    @Test
    public void findByIdJoinUser_test() {
        // Given
        int id = 2;

        // When
        Optional<Resume> optionalResume = resumeJPARepository.findByIdJoinUser(id);

        // Then
        assertTrue(optionalResume.isPresent()); // 해당 id에 해당하는 Resume가 존재하는지 확인
        assertNotNull(optionalResume.get().getUser()); // Resume에 연결된 User가 존재하는지 확인
    }

    @Test
    public void delete_test(){
        // given
        int id = 1;

        // when
        resumeJPARepository.deleteById(1);
        em.flush();

        List<Resume> resumeList = resumeJPARepository.findAll();

        // then
        Assertions.assertThat(resumeList.size()).isEqualTo(5);

    }

    public void findByUser_test() {
        // Given
        User user = new User();
        user.setId(1);

        // When
        List<Resume> result = resumeJPARepository.findByUser(user, (Pageable) Sort.unsorted());

        // Then
        assertEquals(4, result.size()); // 해당 사용자에 대한 이력서 개수를 확인
    }

    @Test
    public void findAll_test() {
        // When
        List<Resume> result = resumeJPARepository.findAll();

        // Then
        assertEquals(6, result.size()); // 전체 이력서 개수를 확인
    }
}
