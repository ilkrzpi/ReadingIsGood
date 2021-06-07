package com.readingisgood.ReadingIsGood.order;

import com.readingisgood.ReadingIsGood.dao.OrderEntity;
import com.readingisgood.ReadingIsGood.dto.OrderDTO;
import com.readingisgood.ReadingIsGood.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class OrderMapperTest {
    private OrderDTO orderDTO;
    private OrderEntity orderEntity;

    @BeforeEach
    void setup(){
        orderDTO = new OrderDTO();
        orderEntity = new OrderEntity();
    }

    @Test
    void givenNull_whenOrderEntityToOrderDTO_thenAssert(){
        OrderDTO orderDTO = OrderMapper.INSTANCE.orderEntityToOrderDTO(null);
        assertNull(orderDTO);
    }

    @Test
    void givenNull_whenOrderDTOToOrderEntity_thenAssert(){
        OrderEntity orderEntity = OrderMapper.INSTANCE.orderDTOToOrderEntity(null);
        assertNull(orderEntity);
    }

    @Test
    void givenOrderEntity_whenOrderEntityToOrderDTO_thenAssert(){
        OrderDTO orderDTO = OrderMapper.INSTANCE.orderEntityToOrderDTO(orderEntity);
        assertNotNull(orderDTO);
        assertEquals(orderEntity.getId(), orderDTO.getId());
        assertEquals(orderEntity.getBookId(), orderDTO.getBookId());
        assertEquals(orderEntity.getCustomerId(), orderDTO.getCustomerId());
        assertEquals(orderEntity.getAmount(), orderDTO.getAmount());
        assertEquals(orderEntity.getStatus(), orderDTO.getStatus());
        assertEquals(orderEntity.getCreateDate(), orderDTO.getCreateDate());
        assertEquals(orderEntity.getPiece(), orderDTO.getPiece());

    }

    @Test
    void givenOrderDTO_whenOrderDTOToOrderEntity_thenAssert(){
        OrderEntity orderEntity = OrderMapper.INSTANCE.orderDTOToOrderEntity(orderDTO);
        assertNotNull(orderEntity);
        assertEquals(orderDTO.getId(), orderEntity.getId());
        assertEquals(orderDTO.getBookId(), orderEntity.getBookId());
        assertEquals(orderDTO.getCustomerId(), orderEntity.getCustomerId());
        assertEquals(orderDTO.getAmount(), orderEntity.getAmount());
        assertEquals(orderDTO.getStatus(), orderEntity.getStatus());
        assertEquals(orderDTO.getCreateDate(), orderEntity.getCreateDate());
        assertEquals(orderDTO.getPiece(), orderEntity.getPiece());
    }
}
