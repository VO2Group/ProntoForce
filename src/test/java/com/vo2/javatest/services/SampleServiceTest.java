package com.vo2.javatest.services;

import com.vo2.javatest.domain.dto.SampleDto;
import com.vo2.javatest.domain.entities.SampleEntity;
import com.vo2.javatest.domain.repositories.SampleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

/**
 * Created by VO2 on 19/01/2017.
 * Test SampleService class using no spring boot specific loading but only mockito and dependency injection.
 * The advantage of this method is tests run much faster as there is no container (even mocked) to load
 */
@RunWith(MockitoJUnitRunner.class)
public class SampleServiceTest {


    /**
     * mocked dependency
     */
    @Mock
    private SampleRepository sampleRepository;

    /**
     * instance of service to test
     */
    @InjectMocks
    private SampleService service;


    /**
     * Test the main behavior
     */
    @Test
    public void loadAllShouldReturnListOfDtos() throws Exception {
        //GIVEN : prepare behaviour of dependency
        List<SampleEntity> inDataStore = buiildSamples();
        List<SampleDto> dtos = inDataStore.stream().map(SampleEntity::toDto).collect(Collectors.toList());
        given(sampleRepository.findAll()).willReturn(inDataStore);

        //WHEN : call service method
        List<SampleDto> all = service.loadAll();

        //THEN : it should return an equivalent data
        assertThat(all).hasSize(inDataStore.size());
        assertThat(all).usingFieldByFieldElementComparator().containsAll(dtos);
    }

    private List<SampleEntity> buiildSamples() {
        List<SampleEntity> list = new ArrayList<>();
        list.add(newSample(1000L, "Sample entity one"));
        list.add(newSample(2000L, "Sample entity two"));
        return list;
    }

    private SampleEntity newSample(long id, String message) {
        SampleEntity entity = new SampleEntity();
        entity.setId(id);
        entity.setMessage(message);
        return entity;
    }

}