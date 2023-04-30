package com.boshko.storebe.controller;

import com.boshko.storebe.dto.OrderDto;
import com.boshko.storebe.entity.Order;
import com.boshko.storebe.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins ="http://localhost:8080/")
public class OrdersController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrdersController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(name = "id") String id) {
        if (!isValidateId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(orderService.findById(Long.parseLong(id)), HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDto> addOrder(@RequestBody OrderDto orderDto) {
        if (orderDto == null || orderDto.getClient() == null || orderDto.getDate() == null || orderDto.getAddress() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(modelMapper.map(orderService.save(modelMapper.map(orderDto, Order.class)), OrderDto.class), HttpStatus.OK);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable(name = "id") String id, @RequestBody OrderDto orderDto) {
        if (!isValidateId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(modelMapper.map(orderService.update(Long.parseLong(id), modelMapper.map(orderDto, Order.class)), OrderDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable(name = "id") String id) {
        if (!isValidateId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        orderService.deleteById(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean isValidateId(String id) {
        try {
            Long aLong = Long.parseLong(id);
            if (aLong > 0 && aLong % 1 == 0L) return true;
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }
}
