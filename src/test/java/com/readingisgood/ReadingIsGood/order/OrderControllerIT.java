package com.readingisgood.ReadingIsGood.order;

import com.readingisgood.ReadingIsGood.order.OrderTestUtil;
import com.readingisgood.ReadingIsGood.dao.OrderRepository;
import com.readingisgood.ReadingIsGood.dto.OrderDTO;
import com.readingisgood.ReadingIsGood.mapper.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class OrderControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @MockBean
    private OrderMapper orderMapper;

    @BeforeEach
    void reset(){
        Mockito.reset(orderMapper);
    }

    @Test
    @Sql(statements = {"INSERT INTO BOOKS (ID, NAME, PRICE, STOCK) VALUES (1, 'books', 10, 1)", "INSERT INTO CUSTOMERS (ID, NAME, EMAIL) VALUES (1, 'customer', 'customer@customer.com')"})
    void givenOrder_whenCreateOrder_thenReturnCreated() {
        when(orderMapper.orderDTOToOrderEntity(any())).thenReturn(OrderTestUtil.createTestOrderEntity());
        when(orderMapper.orderEntityToOrderDTO(any())).thenReturn(OrderTestUtil.createTestOrderDTO());

        ResponseEntity<OrderDTO> responseEntity = testRestTemplate.postForEntity("/orders", OrderTestUtil.createTestOrderDTO(), OrderDTO.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(responseEntity.getBody());
        assertEquals(OrderTestUtil.createTestOrderDTO().getAmount(), responseEntity.getBody().getAmount());
        assertEquals(OrderTestUtil.createTestOrderDTO().getPiece(), responseEntity.getBody().getPiece());
        assertEquals(OrderTestUtil.createTestOrderDTO().getStatus(), responseEntity.getBody().getStatus());

        verify(orderMapper, times(1)).orderDTOToOrderEntity(any());
        verify(orderMapper, times(1)).orderEntityToOrderDTO(any());

    }

}
