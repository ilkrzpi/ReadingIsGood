package com.readingisgood.ReadingIsGood.service;

import com.readingisgood.ReadingIsGood.dao.CustomerEntity;
import com.readingisgood.ReadingIsGood.dao.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerEntity createCustomer(CustomerEntity customerEntity) throws DataIntegrityViolationException {
        log.info("createCustomer", customerEntity);
        return customerRepository.save(customerEntity);
    }

    public List<CustomerEntity> getAllCustomers(Integer pageNo, Integer pageSize)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        Page<CustomerEntity> pagedResult = customerRepository.findAll(paging);

        return pagedResult.getContent();
    }
}
