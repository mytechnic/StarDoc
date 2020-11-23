package com.github.mytechnic.sample.web.controller;

import com.github.mytechnic.doc.annotation.StarApiDocument;
import com.github.mytechnic.doc.annotation.StarApiSpecification;
import com.github.mytechnic.doc.annotation.StarParameter;
import com.github.mytechnic.sample.web.domain.SampleData;
import com.github.mytechnic.sample.web.domain.SampleRequest;
import com.github.mytechnic.sample.web.domain.SampleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@StarApiDocument(name = "게시판 관리", order = 2)
@RestController
public class Sample1Controller {
//
//    @StarApiSpecification(name = "API 스펙 1-0")
//    @RequestMapping("/")
//    public SampleResponse<?> empty(
//            @StarParameter("파람1") @RequestParam(value = "xxx", defaultValue = "") String param1,
//            @StarParameter("파람2") @RequestParam(value = "param2", defaultValue = "") String param2,
//            @StarParameter("파람3") @RequestParam(defaultValue = "") String param3) {
//
//        log.info("param1 : {}", param1);
//        log.info("param2 : {}", param2);
//        log.info("param3 : {}", param3);
//
//        return new SampleResponse<>();
//    }

    @StarApiSpecification(name = "API 스펙 1-1번", order = 1, samples = SampleData.sample1.class)
    @PostMapping(path = "/")
    public SampleResponse<?> helloWorld(@StarParameter("요청 데이터1") SampleRequest request,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        @StarParameter("헤더") @RequestHeader(name = "Content-Type", defaultValue = "") String contentType,
                                        @StarParameter("검색어") @RequestParam(name = "_search", defaultValue = "") String search,
                                        @StarParameter("ND") @RequestParam(defaultValue = "333") String requestName,
                                        @StarParameter("결과수") @RequestParam(required = false) Integer rows,
                                        @StarParameter("TWO") @RequestParam(defaultValue = "2") int two) {

        log.debug("request : {}", request);
        log.debug("search : {}", search);

        return new SampleResponse<>();
    }

//    @StarApiSpecification(name = "API 스펙 1-2번", order = 2)
//    @GetMapping("/{foo2}/{bar}/test2")
//    public SampleResponse2 helloWorld2(@PathVariable("foo2") String foo,
//                                       @PathVariable String bar,
//                                       SampleRequest request) {
//        log.debug("request : {}", request);
//        return new SampleResponse2();
//    }
//
//    @GetMapping("/{foo2}/{bar}/test3")
//    public SampleResponse<?> helloWorld3(@PathVariable("foo2") String foo,
//                                         @PathVariable String bar,
//                                         SampleRequest request) {
//        log.debug("request : {}", request);
//        return new SampleResponse<>();
//    }
}