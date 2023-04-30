package com.boshko.storebe;

import com.boshko.storebe.dao.GoodsDAO;
import com.boshko.storebe.entity.Goods;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class GoodsDAOTests {

    @Autowired
    private GoodsDAO goodsDAO;

    @Test
    public void findByIdTest() {
        Goods goods = getGoods();
        goodsDAO.save(goods);
        Goods result = goodsDAO.findById(goods.getIdGoods()).get();
        assertEquals(goods.getIdGoods(), result.getIdGoods());
    }

    @Test
    public void findAllTest() {
        Goods goods = getGoods();
        goodsDAO.save(goods);
        List<Goods> result = new ArrayList<>(goodsDAO.findAll());

        assertEquals(1, result.size());
    }

    @Test
    public void saveTest() {
        Goods goods = getGoods();
        goodsDAO.save(goods);

        Goods result = goodsDAO.findById(goods.getIdGoods()).get();
        assertEquals(goods.getIdGoods(), result.getIdGoods());
    }

    @Test
    public void deleteByIdTest() {
        Goods goods = getGoods();
        goodsDAO.save(goods);
        goodsDAO.deleteById(goods.getIdGoods());
        List<Goods> result = new ArrayList<>(goodsDAO.findAll());

        assertEquals(0, result.size());
    }


    private Goods getGoods() {
        Goods goods = new Goods();
        goods.setName("milk");
        goods.setPrice(89.99);
        return goods;
    }
}
