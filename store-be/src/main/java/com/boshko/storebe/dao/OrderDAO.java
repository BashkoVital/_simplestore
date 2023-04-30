package com.boshko.storebe.dao;

import com.boshko.storebe.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDAO extends JpaRepository<Order,Long> {
}
