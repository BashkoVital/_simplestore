package com.boshko.storebe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_line")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id", referencedColumnName = "id")
    private Goods goods_id;

    private int count;
}
