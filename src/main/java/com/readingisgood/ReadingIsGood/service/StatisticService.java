package com.readingisgood.ReadingIsGood.service;

import com.readingisgood.ReadingIsGood.dao.OrderEntity;
import com.readingisgood.ReadingIsGood.dao.OrderRepository;
import com.readingisgood.ReadingIsGood.dao.StatisticEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class StatisticService {
    private OrderRepository orderRepository;

    public List<StatisticEntity> getOrderByCustomerId(Long customerId){
        log.info("getOrderByCustomerId", customerId);
        return orderRepository.findStatisticByCustomerId(customerId);
    }
}
