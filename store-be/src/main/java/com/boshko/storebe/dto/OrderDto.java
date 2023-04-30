package com.boshko.storebe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long idOrder;
    private String client;
    private String date;
    private String address;

    public OrderDto(String client, String date, String address) {
        this.client = client;
        this.date = date;
        this.address = address;
    }

}
