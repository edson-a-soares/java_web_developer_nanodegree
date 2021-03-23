package com.udacity.ecommerce.controllers;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import com.udacity.ecommerce.model.persistence.Cart;
import com.udacity.ecommerce.model.persistence.Item;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.requests.ModifyCartRequest;
import com.udacity.ecommerce.model.persistence.repositories.CartRepository;
import com.udacity.ecommerce.model.persistence.repositories.ItemRepository;
import com.udacity.ecommerce.model.persistence.repositories.UserRepository;

public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private final UserRepository users = mock(UserRepository.class);

    @Mock
    private final ItemRepository items = mock(ItemRepository.class);

    @Mock
    private final CartRepository cartRepository = mock(CartRepository.class);

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addToCartNoUserError(){

        ModifyCartRequest modifyCartRequest = createModifyCartRequest("", 1, 1);
        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);

        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void addToCartNoItemError(){

        when(users.findByUsername("Username")).thenReturn(new User());
        when(items.findById(1L)).thenReturn(Optional.empty());

        ModifyCartRequest modifyCartRequest = createModifyCartRequest("Username", 1, 1);
        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);

        assertNotNull(responseEntity);
        verify(items, times(1)).findById(1L);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void addToCartTest(){

        User user = createUser();
        Item item = createItem();
        Cart cart = user.getCart();
        cart.addItem(item);
        cart.setUser(user);
        user.setCart(cart);

        when(users.findByUsername("Username")).thenReturn(user);
        when(items.findById(1L)).thenReturn(Optional.of(item));

        ModifyCartRequest modifyCartRequest = createModifyCartRequest("Username", 1, 1);
        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        Cart responseCart = responseEntity.getBody();

        assertNotNull(responseCart);

        List<Item> items = responseCart.getItems();
        assertNotNull(items);

        assertEquals("Username", responseCart.getUser().getUsername());

        verify(cartRepository, times(1)).save(responseCart);
    }

    @Test
    public void removeFromCartNoUserError(){

        ModifyCartRequest modifyCartRequest = createModifyCartRequest("", 1, 1);
        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(modifyCartRequest);

        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void removeFromCartNoItemError(){

        when(users.findByUsername("Username")).thenReturn(new User());
        when(items.findById(1L)).thenReturn(Optional.empty());

        ModifyCartRequest modifyCartRequest = createModifyCartRequest("Username", 1, 1);
        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(modifyCartRequest);

        assertNotNull(responseEntity);
        verify(items, times(1)).findById(1L);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void removeFromCartTest(){

        User user = createUser();
        Item item = createItem();
        Cart cart = user.getCart();
        cart.addItem(item);
        cart.setUser(user);
        user.setCart(cart);

        when(users.findByUsername("Username")).thenReturn(user);
        when(items.findById(1L)).thenReturn(Optional.of(item));

        ModifyCartRequest modifyCartRequest = createModifyCartRequest("Username", 1, 1);
        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(modifyCartRequest);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        Cart responseCart = responseEntity.getBody();

        assertNotNull(responseCart);
        List<Item> items = responseCart.getItems();
        assertNotNull(items);
        assertEquals(0, items.size());
        assertEquals("Username", responseCart.getUser().getUsername());

        verify(cartRepository, times(1)).save(responseCart);

    }


    public static User createUser(){
        User user = new User();
        user.setId(1);
        user.setUsername("Username");
        user.setPassword("Password");

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(null);
        cart.setItems(new ArrayList<Item>());
        cart.setTotal(BigDecimal.valueOf(0.0));
        user.setCart(cart);

        return user;
    }

    public static Item createItem(){
        Item item = new Item();
        item.setId(1L);
        item.setName("Created Item");
        item.setDescription("This is fake item.");
        item.setPrice(BigDecimal.valueOf(10.0));
        return item;
    }

    public static ModifyCartRequest createModifyCartRequest(String username, long itemId, int quantity){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername(username);
        modifyCartRequest.setItemId(itemId);
        modifyCartRequest.setQuantity(quantity);
        return modifyCartRequest;
    }

}