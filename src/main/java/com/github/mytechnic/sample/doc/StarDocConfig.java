package com.github.mytechnic.sample.doc;

import com.github.mytechnic.doc.container.StarDocContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class StarDocConfig {
    private final ApplicationContext applicationContext;

    @PostConstruct
    public void runner() {
        StarDocContainer container = new StarDocContainer(applicationContext);
        container.apiDocumentMetaScan();
        container.aggregation();
    }
}