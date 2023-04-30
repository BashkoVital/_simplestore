package com.boshko.storebe.dao;

import com.boshko.storebe.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsDAO extends JpaRepository<Goods,Long> {

}
