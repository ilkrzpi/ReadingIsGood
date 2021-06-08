package com.readingisgood.ReadingIsGood.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByCreateDateBetween(Date startDate, Date endDate);

    List<OrderEntity> findByCustomerId(Long customerId);

}
