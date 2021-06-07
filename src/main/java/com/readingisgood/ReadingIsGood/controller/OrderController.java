package com.readingisgood.ReadingIsGood.controller;

import com.readingisgood.ReadingIsGood.dao.OrderEntity;
import com.readingisgood.ReadingIsGood.dao.OrderEntity;
import com.readingisgood.ReadingIsGood.dao.OrderEntity;
import com.readingisgood.ReadingIsGood.dto.OrderDTO;
import com.readingisgood.ReadingIsGood.dto.OrderDTO;
import com.readingisgood.ReadingIsGood.dto.OrderDTO;
import com.readingisgood.ReadingIsGood.exception.BookNotFoundException;
import com.readingisgood.ReadingIsGood.exception.CustomerNotFoundException;
import com.readingisgood.ReadingIsGood.mapper.OrderMapper;
import com.readingisgood.ReadingIsGood.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        OrderEntity orderEntity;
        try{
            orderEntity = orderService.createOrder(orderMapper.orderDTOToOrderEntity(orderDTO));
        } catch (BookNotFoundException | CustomerNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(orderMapper.orderEntityToOrderDTO(orderEntity), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable @NotNull Long orderId) {
        try {
            log.info("getOrderById", orderId);
            OrderEntity orderEntity = orderService.getOrderById(orderId);
            return new ResponseEntity<>(orderMapper.orderEntityToOrderDTO(orderEntity), HttpStatus.OK);
        } catch (Exception e) {
            log.error("getOrderById exception: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "customerId/{customerId}")
    public ResponseEntity<List<OrderEntity>> getOrderByCustomerId(@PathVariable @NotNull Long customerId) {
        try {
            log.info("getOrderByCustomerId", customerId);
            List<OrderEntity> orderEntityList = orderService.getOrderByCustomerId(customerId);
            return new ResponseEntity<>(orderEntityList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getOrderByCustomerId exception: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
