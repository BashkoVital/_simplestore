package com.boshko.storebe;

import com.boshko.storebe.dao.OrderDAO;
import com.boshko.storebe.entity.Order;
import com.boshko.storebe.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderDAO orderDAO;

    private Order order;

    @BeforeEach
    public void setup() {
        order = new Order(1L, "Tomas Smith", "2023-01-12", "Los Angeles");
    }

    @Test
    public void saveGoodsTest() {

        when(orderDAO.save(order)).thenReturn(order);

        Order savedOrder = orderService.save(order);
        assertThat(savedOrder).isEqualTo(order);
    }

    //=================================================================

    @Test
    public void getAllOrderTest() {
        Order order1 = new Order(2L, "Mike Petrov", "2023-01-15", "Moscow");

        when(orderDAO.findAll()).thenReturn(List.of(order, order1));

        List<Order> goodsList = orderService.findAll();

        assertThat(goodsList).isNotNull();
        assertThat(goodsList.size()).isEqualTo(2);
    }

    //===========================================================

    @Test
    public void getOrderById() {
        when(orderDAO.findById(1L)).thenReturn(Optional.of(order));

        Order savedOrder = orderService.findById(order.getIdOrder());

        assertThat(savedOrder).isNotNull();
    }

    @Test
    public void updateOrderTest() {
        when(orderDAO.findById(order.getIdOrder())).thenReturn(Optional.of(order));
        when(orderDAO.save(order)).thenReturn(order);
        order.setAddress("Saint Petersburg");

        Order updatedOrder = orderService.update(order.getIdOrder(), order);

        assertThat(updatedOrder.getAddress()).isEqualTo("Saint Petersburg");
    }

    @Test
    public void deleteOrderByIdTest() {
        long idOrder = 1L;

        willDoNothing().given(orderDAO).deleteById(idOrder);
        orderService.deleteById(idOrder);
        verify(orderDAO, times(1)).deleteById(idOrder);
    }
}
