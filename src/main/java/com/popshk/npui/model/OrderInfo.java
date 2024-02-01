package com.popshk.npui.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public record OrderInfo(@MongoId String barcode, String scheduledDeliveryDate, String trackingStatusName, String comment, String warehouseNumber, String docNumber) {
}