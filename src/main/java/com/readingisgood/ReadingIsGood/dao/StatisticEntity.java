package com.readingisgood.ReadingIsGood.dao;

import lombok.Data;

@Data
public class StatisticEntity {
    Integer month;

    Integer totalOrder;

    Integer totalBook;

    Integer totalAmount;
}
