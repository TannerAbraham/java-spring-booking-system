package edu.wgu.d288.backend.services;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
