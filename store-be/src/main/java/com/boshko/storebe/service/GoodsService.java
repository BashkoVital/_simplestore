package com.boshko.storebe.service;

import com.boshko.storebe.dao.GoodsDAO;
import com.boshko.storebe.entity.Goods;
import com.boshko.storebe.exceptionHandler.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    private GoodsDAO goodsDAO;

    @Autowired
    public GoodsService(GoodsDAO goodsDAO) {
        this.goodsDAO = goodsDAO;
    }

    public List<Goods> findAll() {
        return goodsDAO.findAll();
    }

    public Goods findById(Long id) {
        return goodsDAO.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Goods save(Goods goods) {
        return goodsDAO.save(goods);
    }

    public Goods update(Long id, Goods goods) {
        Goods tempGoods = goodsDAO.findById(id).orElseThrow(ResourceNotFoundException::new);

        if (goods.getName() != null && !goods.getName().equals("") && !goods.getName().equals(tempGoods.getName())) tempGoods.setName(goods.getName());
        if (goods.getPrice() != null && !goods.getPrice().equals(tempGoods.getPrice())) tempGoods.setPrice(goods.getPrice());

        return goodsDAO.save(tempGoods);
    }

    public void deleteById(Long id) {
        goodsDAO.deleteById(id);
    }
}
