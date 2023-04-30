package com.boshko.storebe;

import com.boshko.storebe.dao.GoodsDAO;
import com.boshko.storebe.entity.Goods;
import com.boshko.storebe.service.GoodsService;
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
public class GoodsServiceTests {

    @InjectMocks
    private GoodsService goodsService;

    @Mock
    private GoodsDAO goodsDAO;

    private Goods goods;

    @BeforeEach
    public void setup() {
        goods = new Goods(1L,"milk",89.99);
    }

    @Test
    public void saveGoodsTest() {

        when(goodsDAO.save(goods)).thenReturn(goods);

        Goods savedGoods = goodsService.save(goods);
        assertThat(savedGoods).isEqualTo(goods);
    }

    //=================================================================

    @Test
    public void getAllGoodsTest() {
        Goods goods1 = new Goods(2L, "bread", 189.99);

        when(goodsDAO.findAll()).thenReturn(List.of(goods, goods1));

        List<Goods> goodsList = goodsService.findAll();

        assertThat(goodsList).isNotNull();
        assertThat(goodsList.size()).isEqualTo(2);
    }

    //===========================================================

    @Test
    public void getGoodsById() {

        when(goodsDAO.findById(1L)).thenReturn(Optional.of(goods));

        Goods savedGoods = goodsService.findById(goods.getIdGoods());

        assertThat(savedGoods).isNotNull();
    }

    @Test
    public void updateGoodsTest() {

        when(goodsDAO.findById(goods.getIdGoods())).thenReturn(Optional.of(goods));
        when(goodsDAO.save(goods)).thenReturn(goods);

        goods.setPrice(99.99);
        Goods updatedGoods = goodsService.update(goods.getIdGoods(), goods);

        assertThat(updatedGoods.getPrice()).isEqualTo(99.99);
    }

    @Test
    public void deleteGoodsByIdTest() {
        long idGoods = 1L;

        willDoNothing().given(goodsDAO).deleteById(idGoods);

        goodsService.deleteById(idGoods);

        verify(goodsDAO, times(1)).deleteById(idGoods);
    }
}
