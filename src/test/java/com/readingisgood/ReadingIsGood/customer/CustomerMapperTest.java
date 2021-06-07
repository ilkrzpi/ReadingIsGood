package com.readingisgood.ReadingIsGood.customer;

import com.readingisgood.ReadingIsGood.dao.CustomerEntity;
import com.readingisgood.ReadingIsGood.dto.CustomerDTO;
import com.readingisgood.ReadingIsGood.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CustomerMapperTest {
    private CustomerDTO customerDTO;
    private CustomerEntity customerEntity;

    @BeforeEach
    void setup(){
        customerDTO = new CustomerDTO();
        customerEntity = new CustomerEntity();
    }

    @Test
    void givenNull_whenCustomerEntityToCustomerDTO_thenAssert(){
        CustomerDTO customerDTO = CustomerMapper.INSTANCE.customerEntityToCustomerDTO(null);
        assertNull(customerDTO);
    }

    @Test
    void givenNull_whenCustomerDTOToCustomerEntity_thenAssert(){
        CustomerEntity customerEntity = CustomerMapper.INSTANCE.customerDTOToCustomerEntity(null);
        assertNull(customerEntity);
    }

    @Test
    void givenCustomerEntity_whenCustomerEntityToCustomerDTO_thenAssert(){
        CustomerDTO customerDTO = CustomerMapper.INSTANCE.customerEntityToCustomerDTO(customerEntity);
        assertNotNull(customerDTO);
        assertEquals(customerEntity.getId(), customerDTO.getId());
        assertEquals(customerEntity.getName(), customerDTO.getName());
        assertEquals(customerEntity.getEmail(), customerDTO.getEmail());
    }

    @Test
    void givenCustomerDTO_whenCustomerDTOToCustomerEntity_thenAssert(){
        CustomerEntity customerEntity = CustomerMapper.INSTANCE.customerDTOToCustomerEntity(customerDTO);
        assertNotNull(customerEntity);
        assertEquals(customerDTO.getId(), customerEntity.getId());
        assertEquals(customerDTO.getName(), customerEntity.getName());
        assertEquals(customerDTO.getEmail(), customerEntity.getEmail());
    }
}

