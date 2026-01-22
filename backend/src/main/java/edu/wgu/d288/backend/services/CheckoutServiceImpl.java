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
        
        // Retrieve cart info from purchase
        Cart cart = purchase.getCart();
        
        // Generate order tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);
        
        // Set cart status to ordered
        cart.setStatus(StatusType.ordered);
        
        // Get cart items from purchase
        Set<CartItem> cartItems = purchase.getCartItems();
        
        // Associate cart items with cart
        cartItems.forEach(item -> item.setCart(cart));
        cart.setCartItems(cartItems);
        
        // Get customer from purchase
        Customer customer = purchase.getCustomer();
        
        // Associate cart with customer
        cart.setCustomer(customer);
        
        // Save the customer (which will cascade to cart and cart items)
        customerRepository.save(customer);
        
        // Return response with tracking number
        return new PurchaseResponse(orderTrackingNumber);
    }
    
    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
