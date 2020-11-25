package com.github.mytechnic.sample.web.domain;

import com.github.mytechnic.doc.domain.result.ApiSample;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SampleData {

    public static class sample1 implements ApiSample {

        @Override
        public SampleRequest header() {

            SampleRequest sample = new SampleRequest();
            sample.setAa("Test Request");

            return sample;
        }

        @Override
        public SampleRequest request() {

            SampleRequest sample = new SampleRequest();
            sample.setAa("Test Request");

            return sample;
        }

        @Override
        public SampleResponse<?> response() {

            SampleResponse<?> sample = new SampleResponse<>();
            sample.setResponseName("response Test");

            return sample;
        }
    }
}
