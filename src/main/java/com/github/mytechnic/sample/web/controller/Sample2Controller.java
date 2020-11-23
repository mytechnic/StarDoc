package com.github.mytechnic.sample.web.controller;

import com.github.mytechnic.doc.annotation.StarApiDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@StarApiDocument(name = "회원 관리", order = 1)
@RestController
@RequestMapping("/sample2")
public class Sample2Controller {

//    @StarApiSpecification(name = "API 스펙 2-1번", order = 3)
//    @GetMapping("/{foo2}/{bar}/test")
//    public SampleResponse helloWorld(@PathVariable("foo2") String foo,
//                                     @PathVariable String bar,
//                                     SampleRequest request) {
//        log.debug("request : {}", request);
//        return new SampleResponse();
//    }
}
