package edu.wgu.d288.backend.services;

import lombok.Data;
import lombok.NonNull;

@Data
public class PurchaseResponse {
    @NonNull
    private String orderTrackingNumber;
}
