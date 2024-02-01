package com.popshk.npui.service;

import com.popshk.npui.model.OrderInfo;
import com.popshk.npui.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OrderService {

    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public void saveOrder(OrderInfo order) {
        orderRepo.save(order);
    }

    public List<OrderInfo> getOrders(){
        return orderRepo.findAll();
    }

    public void deleteOldOrders(List<OrderInfo> orders)
    {
        List<String> barcodes = orders.stream().map(OrderInfo::barcode).toList();
        orderRepo.deleteByBarcodeNotIn(barcodes);
    }
}