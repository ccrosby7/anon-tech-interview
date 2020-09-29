package com.crosby.anontech.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Properties specific to anontechinterview.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private Long repeatInMillis;
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setRepeatInMillis(Long millis) {
        this.repeatInMillis = millis;
    }

    public String getUrl() {
        return url;
    }

    public Long getRepeatInMillis() {
        return repeatInMillis;
    }
}
