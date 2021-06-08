package com.readingisgood.ReadingIsGood.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticEntity {
    int month;

    Long totalOrder;

    Long totalBook;

    BigDecimal totalAmount;
}
