package com.boshko.storebe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDto {
    private Long idGoods;
    private String name;
    private Double price;

    public GoodsDto(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}
