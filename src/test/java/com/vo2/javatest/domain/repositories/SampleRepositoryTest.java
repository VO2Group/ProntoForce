package com.vo2.javatest.domain.repositories;

import com.vo2.javatest.domain.entities.SampleEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by VO2 on 19/01/2017.
 * Using Spring Data JPA Test features. After tests run, all transaction will rollback.
 * Tests runs in an instance of fresh in memory database (else explicitly specified by @AutoConfigureTestDatabase annotation)
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class SampleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SampleRepository repository;

    private SampleEntity newSample(String message) {
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setMessage(message);
        return sampleEntity;
    }


    @Test
    public void findAllByMessageIgnoreCaseContainingShouldFindOnlyMatchingEntities() throws Exception {

        //GIVEN: persisted entities in database
        SampleEntity sample1 = entityManager.persist(newSample("a sample eNtIty"));
        SampleEntity sample2 = entityManager.persist(newSample("another sample EntiTy matching"));
        SampleEntity sample3 = entityManager.persist(newSample("another sample EntiT-y not matching"));

        //WHEN : calling repo with conditional criteria
        List<SampleEntity> filtred = repository.findAllByMessageIgnoreCaseContaining("entity");

        //THEN : persistence is done as expected and method filter as designed for
        Arrays.asList(sample1, sample2, sample3).forEach(
            sample -> {
                assertThat(sample).isNotNull();
                assertThat(sample.getId()).isNotNull();
            }
        );

        assertThat(filtred)
                .isNotNull()
                .hasSize(2)
                .usingFieldByFieldElementComparator().containsExactlyInAnyOrder(sample1, sample2);
    }

}