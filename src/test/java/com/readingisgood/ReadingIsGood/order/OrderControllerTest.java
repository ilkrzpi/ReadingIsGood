package com.readingisgood.ReadingIsGood.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingisgood.ReadingIsGood.order.OrderTestUtil;
import com.readingisgood.ReadingIsGood.dao.OrderEntity;
import com.readingisgood.ReadingIsGood.dto.OrderDTO;
import com.readingisgood.ReadingIsGood.order.OrderTestUtil;
import com.readingisgood.ReadingIsGood.controller.OrderController;
import com.readingisgood.ReadingIsGood.dao.OrderEntity;
import com.readingisgood.ReadingIsGood.dto.OrderDTO;
import com.readingisgood.ReadingIsGood.mapper.OrderMapper;
import com.readingisgood.ReadingIsGood.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderController orderController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Nested
    class CreateOrder {
        @Test
        void givenOrder_whenCreateOrder_thenReturnCreated() throws Exception {
            OrderEntity testOrderEntity = OrderTestUtil.createTestOrderEntity();
            OrderDTO testOrderDTO = OrderTestUtil.createTestOrderDTO();
            when(orderMapper.orderDTOToOrderEntity(any())).thenReturn(testOrderEntity);
            when(orderMapper.orderEntityToOrderDTO(any())).thenReturn(testOrderDTO);
            when(orderService.createOrder(any())).thenReturn(testOrderEntity);

            mockMvc.perform(MockMvcRequestBuilders.post("/orders").
                    contentType(MediaType.APPLICATION_JSON).
                    content(objectMapper.writeValueAsString(testOrderDTO))).
                    andExpect(status().isCreated());

            verify(orderMapper, times(1)).orderDTOToOrderEntity(any());
            verify(orderMapper, times(1)).orderEntityToOrderDTO(any());
            verify(orderService, times(1)).createOrder(any());

        }
    }


    @Nested
    class GetOrder {
        @Test
        void givenId_whenGetOrderById_thenReturnOrder() throws Exception {
            OrderEntity testOrderEntity = OrderTestUtil.createTestOrderEntity();
            OrderDTO testOrderDTO = OrderTestUtil.createTestOrderDTO();
            when(orderMapper.orderEntityToOrderDTO(any())).thenReturn(testOrderDTO);
            when(orderService.getOrderById(anyLong())).thenReturn(testOrderEntity);

            mockMvc.perform(MockMvcRequestBuilders.get("/orders/" + testOrderDTO.getId()))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testOrderDTO.getId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(testOrderDTO.getAmount()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(testOrderDTO.getCustomerId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.bookId").value(testOrderDTO.getBookId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.piece").value(testOrderDTO.getPiece()));


            verify(orderService, times(1)).getOrderById(anyLong());
            verify(orderMapper, times(1)).orderEntityToOrderDTO(any());

        }

        @Test
        void givenCustomerId_whenGetOrderByCustomerId_thenReturnOrder() throws Exception {
            OrderEntity testOrderEntity = OrderTestUtil.createTestOrderEntity();
            OrderDTO testOrderDTO = OrderTestUtil.createTestOrderDTO();
            when(orderService.getOrderByCustomerId(anyLong())).thenReturn(Arrays.asList(testOrderEntity));

            mockMvc.perform(MockMvcRequestBuilders.get("/orders/customerId/" + testOrderDTO.getCustomerId()))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(testOrderDTO.getId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0].amount").value(testOrderDTO.getAmount()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0].customerId").value(testOrderDTO.getCustomerId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0].bookId").value(testOrderDTO.getBookId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0].piece").value(testOrderDTO.getPiece()));


            verify(orderService, times(1)).getOrderByCustomerId(anyLong());

        }
    }



}

