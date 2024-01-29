package com.popshk.npui.repo;

import com.popshk.npui.model.OrderInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepo extends MongoRepository<OrderInfo, String> {

    void deleteByBarcodeNotIn(List<String> barcodes);
}
