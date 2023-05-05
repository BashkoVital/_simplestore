package com.boshko.storebe.controller;

import com.boshko.storebe.dto.GoodsDto;
import com.boshko.storebe.entity.Goods;
import com.boshko.storebe.service.GoodsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoodsController {

    private final GoodsService goodsService;
    private final ModelMapper modelMapper;

    @Autowired
    public GoodsController(GoodsService goodsService, ModelMapper modelMapper) {
        this.goodsService = goodsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/goods")
    public ResponseEntity<List<Goods>> getAllGoods() {
        return new ResponseEntity<>(goodsService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/goods/{id}")
    public ResponseEntity<Goods> getGoodsById(@PathVariable(name = "id") String id) {
        if (!isValidateId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(goodsService.findById(Long.parseLong(id)), HttpStatus.OK);
    }

    @PostMapping("/goods")
    public ResponseEntity<GoodsDto> addGoods(@RequestBody GoodsDto goodsDto) {
        if (goodsDto == null || goodsDto.getName() == null || goodsDto.getPrice() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(modelMapper.map(goodsService.save(modelMapper.map(goodsDto, Goods.class)), GoodsDto.class), HttpStatus.OK);
    }

    @PutMapping("/goods/{id}")
    public ResponseEntity<GoodsDto> updateGoods(@PathVariable(name = "id") String id, @RequestBody GoodsDto goodsDto) {

        if (!isValidateId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(modelMapper.map(goodsService.update(Long.parseLong(id), modelMapper.map(goodsDto, Goods.class)), GoodsDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/goods/{id}")
    public ResponseEntity<?> deleteGoodsById(@PathVariable(name = "id") String id) {
        if (!isValidateId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        goodsService.deleteById(Long.parseLong(id));
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
