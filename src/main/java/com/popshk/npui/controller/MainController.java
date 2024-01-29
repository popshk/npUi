package com.popshk.npui.controller;

import com.popshk.npui.model.OrderInfo;
import com.popshk.npui.service.ApiService;
import com.popshk.npui.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller("/")
public class MainController {

    private final ApiService apiService;
    private final OrderService orderService;

    @Autowired
    public MainController(ApiService apiService, OrderService orderService) {
        this.apiService = apiService;
        this.orderService = orderService;
    }

    @GetMapping
    public String getData(Model model) {
        List<OrderInfo> ordersFromApi = apiService.getOrders();
        List<OrderInfo> ordersFromDB = orderService.getOrders();
        Map<String, OrderInfo> mergedMap = Stream.concat(ordersFromApi.stream(), ordersFromDB.stream())
                .collect(Collectors.toMap(OrderInfo::barcode, Function.identity(), (existing, replacement) ->
                        new OrderInfo(existing.barcode(), existing.scheduledDeliveryDate(), existing.trackingStatusName(),
                                !existing.comment().isEmpty() ? existing.comment() : replacement.comment(),existing.warehouseNumber(), existing.docNumber())));

        model.addAttribute("orders", mergedMap.values());
        return "main";
    }

    @PostMapping("/saveComment")
    public String saveCommentToDB(@ModelAttribute OrderInfo order) {
        orderService.saveOrder(order);
        return "redirect:/";
    }

    @PostMapping("/cleanDB")
    public String cleanDB() {
        orderService.deleteOldOrders(apiService.getOrders());
        return "redirect:/";
    }
}
