package com.vo2.javatest.services;

import com.vo2.javatest.domain.dto.SampleDto;
import com.vo2.javatest.domain.entities.SampleEntity;
import com.vo2.javatest.domain.repositories.SampleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by VO2 on 16/01/2017.
 * Sample Service used by REST and Web controllers
 */
@Service("sampleService")
public class SampleService {

    private final static Logger LOG = LoggerFactory.getLogger(SampleService.class);

    private static final String NO_ENTITY_FOUND_BY_ID = "No entity found for this id";
    private static final String UNABLE_TO_PERSIST_NULL = "DTO object can't be null";
    private static final String ID_MUST_NOT_BE_SPECIFIED = "Id must not be specified";
    private static final String ID_NOT_SPECIFIED = "Id of entity must be specified";

    @Autowired
    private SampleRepository sampleRepository;

    public List<SampleDto> fetchByMessagePart(String part) {
        LOG.debug("calling fetchByMessagePart(part={})", part);
        return sampleRepository.findAllByMessageIgnoreCaseContaining(part)
                .stream()
                .map(SampleEntity::toDto)
                .collect(Collectors.toList());
    }

    public Optional<SampleDto> load(Long id) {
        LOG.debug("calling load for id {}", id);
        if (sampleRepository.exists(id)) {
            LOG.info("found entity with id {} and returned");
            return Optional.of(sampleRepository.findOne(id))
                    .map(SampleEntity::toDto);
        }
        LOG.warn("There is no entity with id {} in database", id);
        return Optional.empty();
    }

    public List<SampleDto> loadAll() {
        LOG.debug("loadAll called");
        List<SampleDto> list = new ArrayList<>();
        sampleRepository.findAll()
                .forEach(entity -> list.add(entity.toDto()));
        LOG.debug("loadAll found {} records", list.size());
        return list;
    }

    @Transactional
    public SampleDto create(SampleDto dto) {
        LOG.debug("calling update(dto={})", dto);
        Assert.notNull(dto, UNABLE_TO_PERSIST_NULL);
        Assert.isNull(dto.getId(), ID_MUST_NOT_BE_SPECIFIED);
        SampleEntity entity = new SampleEntity();
        entity.setMessage(dto.getMessage());
        SampleEntity created = sampleRepository.save(entity);
        LOG.info("successfully created entity with id {}", created.getId());
        LOG.debug("created entity is {}", created);
        return created.toDto();
    }

    @Transactional
    public SampleDto update(SampleDto dto) {
        LOG.debug("calling update(dto={})", dto);
        Assert.notNull(dto, UNABLE_TO_PERSIST_NULL);
        Assert.notNull(dto.getId(), ID_NOT_SPECIFIED);
        SampleEntity inDb = sampleRepository.findOne(dto.getId());
        Assert.notNull(inDb, NO_ENTITY_FOUND_BY_ID);
        inDb.setMessage(dto.getMessage());
        SampleEntity fresh = sampleRepository.save(inDb);
        LOG.info("successfully updated entity with id {}", fresh.getId());
        LOG.debug("updated entity {}", inDb);
        return fresh.toDto();
    }

    @Transactional
    public void remove(Long id) {
        LOG.debug("calling remove(id={})", id);
        Assert.isTrue(sampleRepository.exists(id), NO_ENTITY_FOUND_BY_ID);
        sampleRepository.delete(id);
        LOG.info("successfully removed entity of id {}", id);
    }

}
