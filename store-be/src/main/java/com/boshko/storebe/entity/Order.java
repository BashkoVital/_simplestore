package com.boshko.storebe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idOrder;

    private String client;
    private String date;
    private String address;

    @OneToMany(mappedBy = "order_id", cascade = CascadeType.ALL)
    private List<OrderLine> orderLineForOrderList;

    public Order(Long idOrder, String client, String date, String address) {
        this.idOrder = idOrder;
        this.client = client;
        this.date = date;
        this.address = address;
    }

    public Order(String client, String date, String address) {
        this.client = client;
        this.date = date;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order that = (Order) o;
        return Objects.equals(client, that.client) &&
                Objects.equals(date, that.date) &&
                Objects.equals(address, that.address);
    }
}
