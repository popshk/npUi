package com.popshk.npui.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.popshk.npui.conf.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ApiRequests {

    private final WebClient webClient;

    @Autowired
    private AppConfig appConfig;

    private ApiRequests() {
        this.webClient = WebClient.builder().baseUrl("https://api.novaposhta.ua/v2.0/json/").build();
    }

    private String fetchOrdersFromApi() {
        Mono<String> responseBody = webClient
                .post().accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(incomingDocumentsBody()))
                .retrieve()
                .bodyToMono(String.class);

        return responseBody.block();
    }

    public String redirectRequest(JsonNode body) {
        Mono<String> responseBody = webClient
                .post().accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(String.class);

        return responseBody.block();
    }

    public JsonNode getResponse() {
        return JsonReader.parseResponse(fetchOrdersFromApi());
    }

    private JsonNode incomingDocumentsBody (){
        JsonNode jsonNode = JsonReader.readJsonFile(appConfig.getIncomingDocuments());
        ((ObjectNode) jsonNode).put("apiKey",appConfig.getApiKey());
        ((ObjectNode) jsonNode.get("methodProperties")).put("DateFrom", DateUtils.customDateFromForJson(false));
        ((ObjectNode) jsonNode.get("methodProperties")).put("DateTo", DateUtils.customDateFromForJson(true));
        return jsonNode;
    }
}