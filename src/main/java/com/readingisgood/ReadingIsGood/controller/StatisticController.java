package com.readingisgood.ReadingIsGood.controller;

import com.readingisgood.ReadingIsGood.dao.OrderEntity;
import com.readingisgood.ReadingIsGood.dao.StatisticEntity;
import com.readingisgood.ReadingIsGood.service.OrderService;
import com.readingisgood.ReadingIsGood.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.text.DateFormatSymbols;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/statistics")
public class StatisticController {
    private StatisticService statisticService;

    @GetMapping
    public ResponseEntity<List<StatisticEntity>> getOrderByCustomerId(@RequestParam Long customerId)
    {
        List<StatisticEntity> statisticEntityList = statisticService.getOrderByCustomerId(customerId);
        return new ResponseEntity<List<StatisticEntity>>(statisticEntityList, HttpStatus.OK);
    }

}
