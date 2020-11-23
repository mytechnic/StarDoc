package com.github.mytechnic.doc.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "star-doc")
@Setter
@Getter
@ToString
public class StarConfig {
}
