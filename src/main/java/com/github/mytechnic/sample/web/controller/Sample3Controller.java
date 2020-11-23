package com.github.mytechnic.sample.web.controller;

import com.github.mytechnic.sample.web.domain.SampleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = {"/x", "/y"})
public class Sample3Controller {

    // @RequestMapping(value = {"/aaa", "/bbb"})
    // @GetMapping
    // @PostMapping
    // @PutMapping
    // @PatchMapping
    // @DeleteMapping
    public SampleResponse<?> xxx(@RequestParam(name = "_search", defaultValue = "", required = false) String search) {
        return new SampleResponse<>();
    }
}
