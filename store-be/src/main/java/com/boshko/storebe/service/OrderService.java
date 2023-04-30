package com.boshko.storebe.service;

import com.boshko.storebe.dao.OrderDAO;
import com.boshko.storebe.entity.Order;
import com.boshko.storebe.exceptionHandler.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderDAO orderDAO;

    @Autowired
    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    public Order findById(Long id) {
        return orderDAO.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Order save(Order order) {
        return orderDAO.save(order);
    }

    public Order update(Long id, Order order) {
        Order tempOrder = orderDAO.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (order.getClient() != null && !order.getClient().equals("") && !order.getClient().equals(tempOrder.getClient()))
            tempOrder.setClient(order.getClient());
        if (order.getDate() != null && !order.getDate().equals("") && !order.getDate().equals(tempOrder.getDate())) {
            tempOrder.setDate(order.getDate());
        }
        if (order.getAddress() != null && !order.getAddress().equals("") && !order.getAddress().equals(tempOrder.getAddress()))
            tempOrder.setAddress(order.getAddress());

        return orderDAO.save(tempOrder);
    }

    public void deleteById(Long id) {
        orderDAO.deleteById(id);
    }
}
