package com.example.project_v2.scrap;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ScrapJPARepositoryTest {

    @Autowired
    private ScrapJPARepository scrapJPARepository;

    @Autowired
    private EntityManager em;

}
