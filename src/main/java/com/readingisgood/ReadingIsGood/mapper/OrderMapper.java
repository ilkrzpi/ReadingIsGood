package com.readingisgood.ReadingIsGood.mapper;

import com.readingisgood.ReadingIsGood.dao.OrderEntity;
import com.readingisgood.ReadingIsGood.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {
     public static final OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

     public abstract OrderEntity orderDTOToOrderEntity(OrderDTO orderDTO);

     public abstract OrderDTO orderEntityToOrderDTO(OrderEntity orderEntity);

     public abstract List<OrderEntity> orderDTOListToOrderEntityList(List<OrderDTO> orderDTOList);

     public abstract List<OrderDTO> orderEntityListToOrderDTOList(List<OrderEntity> orderEntityList);
   
}
