package com.readingisgood.ReadingIsGood.controller;

import com.readingisgood.ReadingIsGood.dao.CustomerEntity;
import com.readingisgood.ReadingIsGood.dto.CustomerDTO;
import com.readingisgood.ReadingIsGood.mapper.CustomerMapper;
import com.readingisgood.ReadingIsGood.service.CustomerService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
@Api(tags = "Customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping
    ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerEntity customerEntity;
        try{
            customerEntity = customerService.createCustomer(customerMapper.customerDTOToCustomerEntity(customerDTO));
        } catch (DataIntegrityViolationException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(customerMapper.customerEntityToCustomerDTO(customerEntity), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CustomerEntity>> getAllCustomers(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize)
    {
        List<CustomerEntity> list = customerService.getAllCustomers(pageNo, pageSize);

        return new ResponseEntity<List<CustomerEntity>>(list, HttpStatus.OK);
    }
}
