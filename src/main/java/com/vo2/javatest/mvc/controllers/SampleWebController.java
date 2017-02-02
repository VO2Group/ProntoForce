package com.vo2.javatest.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by VO2 on 16/01/2017.
 * Sample Web Controller
 */
@Controller
public class SampleWebController {

    private final static Logger LOG = LoggerFactory.getLogger(SampleWebController.class);

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, path = "/")
    public ModelAndView index() {
        LOG.info("SampleWebController >> index method called");
        ModelAndView mdv = new ModelAndView("sample");
        mdv.addObject("msg", "WEB page called. Loaded on " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return mdv;
    }

}
