package com.readingisgood.ReadingIsGood.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByCreateDateBetween(Date startDate, Date endDate);

    List<OrderEntity> findByCustomerId(Long customerId);

    @Query(value = "select MONTH (createDate) AS month, COUNT(*) AS totalOrder, SUM(piece) AS totalBook, SUM(amount) AS totalAmount from orders where customerId=:customerId GROUP BY MONTH (createDate)",nativeQuery = true)
    List<StatisticEntity> findStatisticByCustomerId(Long customerId);
}
