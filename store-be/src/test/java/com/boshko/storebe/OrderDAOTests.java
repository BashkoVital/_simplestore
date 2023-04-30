package com.boshko.storebe;

import com.boshko.storebe.dao.OrderDAO;
import com.boshko.storebe.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class OrderDAOTests {
    @Autowired
    private OrderDAO orderDAO;

    @Test
    public void findByIdTest() {
        Order order = getOrder();
        orderDAO.save(order);
        Order result = orderDAO.findById(order.getIdOrder()).get();
        assertEquals(order.getIdOrder(), result.getIdOrder());
    }

    @Test
    public void findAllTest() {
        Order order = getOrder();
        orderDAO.save(order);
        List<Order> result = new ArrayList<>(orderDAO.findAll());

        assertEquals(1, result.size());
    }

    @Test
    public void saveTest() {
        Order order = getOrder();
        orderDAO.save(order);

        Order result = orderDAO.findById(order.getIdOrder()).get();
        assertEquals(order.getIdOrder(), result.getIdOrder());
    }

    @Test
    public void deleteByIdTest() {
        Order order = getOrder();
        orderDAO.save(order);

        orderDAO.deleteById(order.getIdOrder());
        List<Order> result = new ArrayList<>(orderDAO.findAll());

        assertEquals(0, result.size());
    }

    private Order getOrder() {
        Order order = new Order();
        order.setClient("Tomas Smith");
        order.setAddress("Los Angeles");
        return order;
    }
}
