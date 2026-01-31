package edu.wgu.d288.backend.services;

import edu.wgu.d288.backend.dao.CartRepository;
import edu.wgu.d288.backend.dao.CustomerRepository;
import edu.wgu.d288.backend.entities.Cart;
import edu.wgu.d288.backend.entities.CartItem;
import edu.wgu.d288.backend.entities.Customer;
import edu.wgu.d288.backend.entities.StatusType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository, CartRepository cartRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // Get the customer ID from the purchase
        Customer customerFromPurchase = purchase.getCustomer();

        // Fetch the ACTUAL customer from the database
        Customer customer = customerRepository.findById(customerFromPurchase.getId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerFromPurchase.getId()));

        // Create a NEW cart (don't use the cart from the frontend DTO)
        Cart cart = new Cart();

        // Generate order tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        // Set cart status to ordered
        cart.setStatus(StatusType.ordered);

        // Set package price and party size from the purchase DTO
        cart.setPackage_price(purchase.getCart().getPackage_price());
        cart.setParty_size(purchase.getCart().getParty_size());

        // Associate cart with the existing customer
        cart.setCustomer(customer);

        // Get cart items from purchase
        Set<CartItem> cartItems = purchase.getCartItems();

        // Associate cart items with the new cart
        cartItems.forEach(item -> item.setCart(cart));
        cart.setCartItems(cartItems);

        // Save the NEW cart (this will cascade to cart items)
        cartRepository.save(cart);

        // Return response with tracking number
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}