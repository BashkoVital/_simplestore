package com.boshko.storebe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "goods")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idGoods;

    private String name;

    private Double price;

    @OneToMany(mappedBy = "goods_id", cascade = CascadeType.ALL)
    private List<OrderLine> orderLineForGoodsList;

    public Goods(Long idGoods, String name, Double price) {
        this.idGoods = idGoods;
        this.name = name;
        this.price = price;
    }

    public Goods( String name, Double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods that = (Goods) o;
        return  Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + idGoods +
                ", name='" + name +
                ", price=" + price +
                "}";
    }
}
