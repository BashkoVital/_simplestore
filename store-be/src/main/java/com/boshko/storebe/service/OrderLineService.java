package com.boshko.storebe.service;

import com.boshko.storebe.dao.OrderLineDAO;
import com.boshko.storebe.entity.OrderLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderLineService {
    private final OrderLineDAO orderLineDAO;

    @Autowired
    public OrderLineService(OrderLineDAO orderLineDAO) {
        this.orderLineDAO = orderLineDAO;
    }

    public List<OrderLine> findAll() {
        return orderLineDAO.findAll();
    }

    public Optional<OrderLine> findById(Long id) {
        return orderLineDAO.findById(id);
    }

    public OrderLine save(String client, String address) {
        return null;
    }

    public OrderLine update(Long id, String client, String address) {
        return null;
    }

    public void deleteById(Long id) {
        orderLineDAO.deleteById(id);
    }
}
