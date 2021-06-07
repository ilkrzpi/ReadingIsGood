package com.readingisgood.ReadingIsGood.service;

import com.readingisgood.ReadingIsGood.dao.*;
import com.readingisgood.ReadingIsGood.exception.BookNotFoundException;
import com.readingisgood.ReadingIsGood.exception.CustomerNotFoundException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    @Transactional
    public OrderEntity createOrder(OrderEntity orderEntity) throws Exception {
        log.info("createOrder", orderEntity);

        if(!Objects.isNull(orderEntity)){
            if(!Objects.isNull(orderEntity.getCustomerId()) && !customerRepository.findById(orderEntity.getCustomerId()).isPresent()){
                log.info("customerNotFound", orderEntity);
                throw new CustomerNotFoundException();
            }
            if(!Objects.isNull(orderEntity.getBookId())){
                BookEntity bookEntity = bookRepository.findById(orderEntity.getBookId()).orElseThrow(BookNotFoundException::new);
                if(bookEntity.getStock()<orderEntity.getPiece()){
                    log.info("bookNotFound", orderEntity);
                    throw new BookNotFoundException();
                }
                bookRepository.updateStock(orderEntity.getBookId(), orderEntity.getPiece());
            }
        }
        return orderRepository.save(orderEntity);
    }

    public OrderEntity getOrderById(Long id) {
        log.info("getOrderById", id);
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        return orderEntity.orElseThrow(NoSuchElementException::new);
    }

    public List<OrderEntity> getOrderByCustomerId(Long customerId){
        log.info("getOrderByCustomerId", customerId);
        return orderRepository.findByCustomerId(customerId);
    }
}
