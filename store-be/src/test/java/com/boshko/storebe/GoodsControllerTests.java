package com.boshko.storebe;

import com.boshko.storebe.dao.GoodsDAO;
import com.boshko.storebe.entity.Goods;
import com.boshko.storebe.exceptionHandler.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GoodsControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private GoodsDAO goodsDAO;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void printApplicationContext() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void resetdb() {
        goodsDAO.deleteAll();
    }

    @Test
    public void addGoodsTest() throws Exception {
        Goods goods = new Goods("milk", 89.99);

        mockMvc.perform(post("/goods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(goods)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idGoods").isNumber())
                .andExpect(jsonPath("$.name").value("milk"))
                .andExpect(jsonPath("$.price").value(89.99));
    }

    @Test
    public void addGoodsWithEmptyBodyTest() throws Exception {
        mockMvc.perform(post("/goods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateGoodsByIdTest() throws Exception {
        Long id = createTestGoods("milk", 89.99).getIdGoods();

        mockMvc.perform(put("/goods/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Goods("bread", 94.99))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idGoods").value(id))
                .andExpect(jsonPath("$.name").value("bread"))
                .andExpect(jsonPath("$.price").value(94.99));
    }

    @Test
    public void getAllGoodsTest() throws Exception {
        Goods goods = createTestGoods("milk", 89.99);
        Goods goods1 = createTestGoods("bread", 69.99);

        mockMvc.perform(get("/goods"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("[0].name").value("milk"))
                .andExpect(jsonPath("[0].price").value(89.99))
                .andExpect(jsonPath("[1].name").value("bread"))
                .andExpect(jsonPath("[1].price").value(69.99));
    }

    //=========================================================================
    // test getGoodsById(Long id)
    @Test
    public void getGoodsByIdEqualZeroTest() throws Exception {
        mockMvc.perform(get("/goods/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getGoodsByIdNotNumberTest() throws Exception {
        mockMvc.perform(get("/goods/test"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getGoodsByIdNotExistTest() throws Exception {
        mockMvc.perform(get("/goods/1"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        mvcResult.getResolvedException().getClass().equals(ResourceNotFoundException.class));
    }

    @Test
    public void getGoodsByIdTest() throws Exception {
        Long id = createTestGoods("milk", 89.99).getIdGoods();

        mockMvc.perform(get("/goods/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idGoods").value(id))
                .andExpect(jsonPath("$.name").value("milk"));
    }

    //=========================================================================
    // test deleteGoodsById(Long id)

    @Test
    public void deleteByIdTest() throws Exception {
        Long id = createTestGoods("milk", 89.99).getIdGoods();

        mockMvc.perform(delete("/goods/{id}", id))
                .andExpect(status().isOk());
    }

    private Goods createTestGoods(String name, Double price) {
        return goodsDAO.save(new Goods(name, price));
    }
}
