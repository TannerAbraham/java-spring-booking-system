package edu.wgu.d288.backend.services;

import edu.wgu.d288.backend.entities.Cart;
import edu.wgu.d288.backend.entities.CartItem;
import edu.wgu.d288.backend.entities.Customer;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Cart cart;
    private Set<CartItem> cartItems;
}
