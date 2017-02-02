package com.vo2.javatest.mvc.controllers;

import com.vo2.javatest.domain.dto.SampleDto;
import com.vo2.javatest.services.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Created by VO2 on 16/01/2017.
 * Sample REST controller
 */
@RestController
@RequestMapping("/rest")
public class SampleRestController {

    private final static Logger LOG = LoggerFactory.getLogger(SampleRestController.class);

    @Autowired
    private SampleService sampleService;

    @RequestMapping(method = {RequestMethod.GET}, path = "/hellovo2", produces = "application/json")
    public ResponseEntity<SampleDto> hellovo2() {
        SampleDto dto = new SampleDto();
        dto.setMessage("REST GET called. Loaded on " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        dto.setId(123L);
        LOG.debug("/rest/hellovo2 is successfully called");
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(method = {RequestMethod.GET}, path = "/samples", produces = "application/json")
    public ResponseEntity<List<SampleDto>> allMessages() {
        List<SampleDto> listOfDto = sampleService.loadAll();
        if (listOfDto.isEmpty()) {
            //404
            LOG.warn("No entities found in database!");
            return ResponseEntity.notFound().build();
        }

        //200
        return ResponseEntity.ok(listOfDto);
    }

    @RequestMapping(method = {RequestMethod.GET}, path = "/sample/{id}", produces = "application/json")
    public ResponseEntity<SampleDto> byIdMessage(@PathVariable(name = "id") Long id) {
        Optional<SampleDto> dto = sampleService.load(id);
        if (!dto.isPresent()) {
            //404
            LOG.warn("SampleEntity not found for id = {}", id);
            return ResponseEntity.notFound().build();
        }

        //200
        LOG.debug("Successfully loaded entity by id {}", dto.get());
        return ResponseEntity.ok(dto.get());
    }

    @RequestMapping(method = {RequestMethod.POST}, path = "/sample", produces = "application/json")
    public ResponseEntity<SampleDto> addSample(@RequestBody SampleDto dto) {

        SampleDto created;

        try {
            created = sampleService.create(dto);
        } catch (Exception e) {
            //LOG
            LOG.error("Unable to add sample : {} due to exception : {}", dto, e.getMessage(), e);
            //reply by a bad request status
            return ResponseEntity.badRequest().body(null);
        }

        //200
        LOG.info("Successfully added entity {}", created);
        return ResponseEntity.ok(created);
    }

    @RequestMapping(method = {RequestMethod.PUT}, path = "/sample/{id}", produces = "application/json")
    public ResponseEntity<SampleDto> updateSample(@PathVariable("id") Long id, @RequestBody SampleDto dto) {


        if (dto != null) {
            dto.setId(id);
        }

        SampleDto updated;

        try {
            updated = sampleService.update(dto);
        } catch (Exception e) {
            //LOG
            LOG.error("Unable to update sample : {} due to exception : {}", dto, e.getMessage(), e);
            //reply by a bad request status
            return ResponseEntity.badRequest().body(null);
        }

        //200
        LOG.info("Successfully updated entity by id {}", updated);
        return ResponseEntity.ok(updated);
    }

    @RequestMapping(method = {RequestMethod.DELETE}, path = "/sample/{id}", produces = "application/json")
    public ResponseEntity<String> deleteSample(@PathVariable("id") Long id) {


        try {
            sampleService.remove(id);
        } catch (Exception e) {
            //LOG
            LOG.error("Unable to delete sample with id : {} due to exception : {}", id, e.getMessage(), e);
            //reply by a bad request status
            return ResponseEntity.badRequest().body(null);
        }

        //200
        LOG.info("Successfully deleted entity by id {}", id);
        return ResponseEntity.ok("DELETED");
    }


    @RequestMapping(method = {RequestMethod.GET}, path = "/sample/like/{message}", produces = "application/json")
    public ResponseEntity<List<SampleDto>> likeMessage(@PathVariable(name = "message") String message) {
        List<SampleDto> listOfDto = sampleService.fetchByMessagePart(message);
        if (listOfDto.isEmpty()) {
            //404
            LOG.warn("No entities having message containing {}", message);
            return ResponseEntity.notFound().build();
        }

        //200
        LOG.debug("found {} entities with message containing {}", listOfDto.size(), message);
        return ResponseEntity.ok(listOfDto);
    }



}
