package com.github.mytechnic.doc.domain.result;

import com.github.mytechnic.doc.annotation.StarSampleHeader;
import com.github.mytechnic.doc.annotation.StarSampleRequest;
import com.github.mytechnic.doc.annotation.StarSampleResponse;

public interface ApiSample {

    @StarSampleHeader
    Object header();

    @StarSampleRequest
    Object request();

    @StarSampleResponse
    Object response();
}
