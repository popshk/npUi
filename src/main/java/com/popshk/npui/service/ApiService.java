package com.popshk.npui.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.popshk.npui.conf.AppConfig;
import com.popshk.npui.model.OrderInfo;
import com.popshk.npui.utils.ApiRequests;
import com.popshk.npui.utils.JsonReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ApiService {

    private final ApiRequests request;

    private final AppConfig appConfig;

    @Autowired
    public ApiService(ApiRequests request, AppConfig appConfig) {
        this.request = request;
        this.appConfig = appConfig;
    }

    private Iterator<JsonNode> getResponseDocument() {
        return request.getResponse().get("data").get(0).get("result").elements();
    }

    public List<OrderInfo> getOrders() {
        List<OrderInfo> list = new ArrayList<>();

        getResponseDocument().forEachRemaining(element -> {
            JsonNode clientBarcodes = element.get("ClientBarcodes").get(0);
            String scheduledDeliveryDate = element.get("ScheduledDeliveryDate").asText();
            String trackingStatusName = element.get("TrackingStatusName").asText();
            String warehouseNumber = element.get("SettlmentAddressData").get("RecipientWarehouseNumber").asText();
            String docNumber = element.get("Number").asText();
            if (clientBarcodes != null && !trackingStatusName.equals("Змінено адресу")) list.add(new OrderInfo(
                                                    clientBarcodes.asText(),
                                                    scheduledDeliveryDate,
                                                    trackingStatusName,
                                                    StringUtils.EMPTY,
                                                    warehouseNumber,
                                                    docNumber));
        });

        return list;
    }

    @Scheduled(cron = "0 0 * * * *")
    private void createRedirectingRequest()
    {
        List<OrderInfo> orders = getOrders();
        List<OrderInfo> wrongWarehouseNumberOrders = orders.stream().filter(x -> !x.warehouseNumber().equals("94")).toList();
        JsonNode orderRedirectingBody = JsonReader.parseFile("src/main/resources/static/orderRedirecting.json");
        ((ObjectNode) orderRedirectingBody).put("apiKey",appConfig.getApiKey());
        wrongWarehouseNumberOrders.forEach(info ->{

            ((ObjectNode) orderRedirectingBody.get("methodProperties")).put("IntDocNumber", info.docNumber());
            String response = request.redirectRequest(orderRedirectingBody);
            System.out.println(StringEscapeUtils.unescapeJava(response));
        });
        System.out.println("Cron Job_______________________________");
    }
}
