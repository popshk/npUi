package com.popshk.npui.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@Data
public class AppConfig {

    @Value("${custom.apiKey}")
    private String apiKey;

    @Value("classpath:static/getIncomingDocuments.json")
    private Resource incomingDocuments;

    @Value("classpath:static/orderRedirecting.json")
    private Resource orderRedirecting;
}