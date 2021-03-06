package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    CompensationService compensationService;

    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation){
        LOG.debug("Received compensation create request [{}]", compensation);

        return compensationService.create(compensation);
    }

    @GetMapping("/employee/{id}/compensation")
    public Compensation read(@PathVariable String id){
        LOG.debug("Getting compensation with employee id [{}]", id);

        return compensationService.read(id);

    }


}
