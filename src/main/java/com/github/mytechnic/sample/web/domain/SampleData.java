package com.github.mytechnic.sample.web.domain;

import com.github.mytechnic.doc.annotation.StarSampleHeader;
import com.github.mytechnic.doc.annotation.StarSampleRequest;
import com.github.mytechnic.doc.annotation.StarSampleResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SampleData {

    public static class sample1 {

        @StarSampleHeader
        public SampleRequest header() {

            SampleRequest sample = new SampleRequest();
            sample.setAa("Test Request");

            return sample;
        }

        @StarSampleRequest
        public SampleRequest request() {

            SampleRequest sample = new SampleRequest();
            sample.setAa("Test Request");

            return sample;
        }

        @StarSampleResponse
        public SampleResponse<?> response() {

            SampleResponse<?> sample = new SampleResponse<>();
            sample.setResponseName("response Test");

            return sample;
        }
    }
}
