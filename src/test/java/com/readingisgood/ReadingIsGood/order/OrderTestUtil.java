package com.readingisgood.ReadingIsGood.order;

import com.readingisgood.ReadingIsGood.dao.OrderEntity;
import com.readingisgood.ReadingIsGood.dao.OrderStatus;
import com.readingisgood.ReadingIsGood.dto.OrderDTO;
import org.assertj.core.util.DateUtil;

import java.math.BigDecimal;

public class OrderTestUtil {
    public static OrderDTO createTestOrderDTO(){
        return new OrderDTO(1L, 1L, 1L, DateUtil.now(), BigDecimal.TEN, 1, OrderStatus.PROCESSING);
    }

    public static OrderEntity createTestOrderEntity(){
        return new OrderEntity(1L, 1L, 1L, DateUtil.now(), BigDecimal.TEN, 1, OrderStatus.PROCESSING);

    }
}
