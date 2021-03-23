package com.udacity.ecommerce.controllers;

import java.util.List;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import java.util.ArrayList;
import java.math.BigDecimal;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.springframework.http.ResponseEntity;
import com.udacity.ecommerce.model.persistence.Cart;
import com.udacity.ecommerce.model.persistence.Item;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.UserOrder;
import com.udacity.ecommerce.model.persistence.repositories.OrderRepository;
import com.udacity.ecommerce.model.persistence.repositories.UserRepository;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private final UserRepository users = mock(UserRepository.class);

    @Mock
    private final OrderRepository orders = mock(OrderRepository.class);

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void submitTest(){
        User user = createUser();
        Item item = createItem();

        Cart cart = user.getCart();

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItems(itemList);

        cart.setUser(user);
        user.setCart(cart);

        when(users.findByUsername("Username")).thenReturn(user);

        ResponseEntity<UserOrder> response =  orderController.submit("Username");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserOrder retrievedUserOrder = response.getBody();
        assertNotNull(retrievedUserOrder);
        assertNotNull(retrievedUserOrder.getItems());
        assertNotNull(retrievedUserOrder.getTotal());
        assertNotNull(retrievedUserOrder.getUser());
    }

    @Test
    public void testSubmitNullUser() {
        User user = createUser();
        Item item = createItem();
        Cart cart = user.getCart();

        cart.setUser(user);
        user.setCart(cart);

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItems(itemList);

        when(users.findByUsername("Username")).thenReturn(null);
        ResponseEntity<UserOrder> response =  orderController.submit("Username");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void ordersByUserTest(){
        User user = createUser();
        Item item = createItem();
        Cart cart = user.getCart();

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItems(itemList);

        cart.setUser(user);
        user.setCart(cart);

        when(users.findByUsername("Username")).thenReturn(user);
        orderController.submit("Username");

        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser("Username");

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<UserOrder> userOrders = responseEntity.getBody();
        assertNotNull(userOrders);
        assertEquals(0, userOrders.size());
    }

    @Test
    public void ordersByUserNullUser(){
        User user = createUser();
        Item item = createItem();
        Cart cart = user.getCart();

        cart.setUser(user);
        user.setCart(cart);

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItems(itemList);


        when(users.findByUsername("Username")).thenReturn(null);

        orderController.submit("Username");

        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser("Username");

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    public static Item createItem(){
        Item item = new Item();

        item.setId(1L);
        item.setName("Created Item");
        item.setDescription("This is a fake item.");
        item.setPrice(BigDecimal.valueOf(55.0));

        return item;
    }

    public static User createUser(){
        User user = new User();

        user.setId(1);
        user.setUsername("Username");
        user.setPassword("Password");

        Cart emptyCart = new Cart();
        emptyCart.setId(1L);
        emptyCart.setUser(null);
        emptyCart.setItems(new ArrayList<Item>());
        emptyCart.setTotal(BigDecimal.valueOf(0.0));
        user.setCart(emptyCart);

        return user;
    }

}