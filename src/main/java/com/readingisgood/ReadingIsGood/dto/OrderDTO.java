package com.readingisgood.ReadingIsGood.dto;

import com.readingisgood.ReadingIsGood.dao.OrderStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;

    @NotNull
    private Long customerId;

    @NotNull
    private Long bookId;

    private Date createDate;

    private BigDecimal amount;

    private Integer piece;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PROCESSING;

}
