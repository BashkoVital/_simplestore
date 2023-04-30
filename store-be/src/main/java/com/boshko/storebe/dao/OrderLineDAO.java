package com.boshko.storebe.dao;

import com.boshko.storebe.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineDAO extends JpaRepository<OrderLine,Long> {
}
