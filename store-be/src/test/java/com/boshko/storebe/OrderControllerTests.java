package com.boshko.storebe;

import com.boshko.storebe.dao.OrderDAO;
import com.boshko.storebe.entity.Order;
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
public class OrderControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private OrderDAO orderDAO;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void printApplicationContext() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void resetdb() {
        orderDAO.deleteAll();
    }

    @Test
    public void addOrderTest() throws Exception {
        Order order = new Order("Tomas Smith", "2023-01-12", "Los Angeles");

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idOrder").isNumber())
                .andExpect(jsonPath("$.client").value("Tomas Smith"))
                .andExpect(jsonPath("$.date").value("2023-01-12"))
                .andExpect(jsonPath("$.address").value("Los Angeles"));
    }

    @Test
    public void addOrderWithEmptyBodyTest() throws Exception {
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateOrderByIdTest() throws Exception {
        Long id = createTestOrder("Tomas Smith", "2023-01-12", "Los Angeles").getIdOrder();


        mockMvc.perform(put("/orders/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Order("Tomas Spencer", "2023-01-13", "Saint Petersburg"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idOrder").value(id))
                .andExpect(jsonPath("$.client").value("Tomas Spencer"))
                .andExpect(jsonPath("$.date").value("2023-01-13"))
                .andExpect(jsonPath("$.address").value("Saint Petersburg"));
    }

    @Test
    public void getAllGoodsTest() throws Exception {
        Order order = createTestOrder("Tomas Smith", "2023-01-12", "Los Angeles");
        Order order1 = createTestOrder("Mike Petrov", "2023-01-15", "Moscow");

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("[0].client").value("Tomas Smith"))
                .andExpect(jsonPath("[0].date").value("2023-01-12"))
                .andExpect(jsonPath("[0].address").value("Los Angeles"))
                .andExpect(jsonPath("[1].client").value("Mike Petrov"))
                .andExpect(jsonPath("[1].date").value("2023-01-15"))
                .andExpect(jsonPath("[1].address").value("Moscow"));
    }
    //=========================================================================
    // test getOrderById(Long id)

    @Test
    public void getOrderByIdEqualZeroTest() throws Exception {
        mockMvc.perform(get("/orders/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getOrderByIdNotNumberTest() throws Exception {
        mockMvc.perform(get("/orders/test"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getOrderByIdNotExistTest() throws Exception {
        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        mvcResult.getResolvedException().getClass().equals(ResourceNotFoundException.class));
    }

    @Test
    public void getOrderByIdTest() throws Exception {
        Long id = createTestOrder("Tomas Smith", "2023-01-12", "Los Angeles").getIdOrder();

        mockMvc.perform(get("/orders/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idOrder").value(id))
                .andExpect(jsonPath("$.client").value("Tomas Smith"))
                .andExpect(jsonPath("$.date").value("2023-01-12"))
                .andExpect(jsonPath("$.address").value("Los Angeles"));
    }

    //=========================================================================
    // test deleteGoodsById(Long id)

    @Test
    public void deleteByIdTest() throws Exception {
        Long id = createTestOrder("Tomas Smith", "2023-01-12", "Los Angeles").getIdOrder();

        mockMvc.perform(delete("/orders/{id}", id))
                .andExpect(status().isOk());
    }

    private Order createTestOrder(String client, String date, String address) {
        return orderDAO.save(new Order(client, date, address));
    }
}
