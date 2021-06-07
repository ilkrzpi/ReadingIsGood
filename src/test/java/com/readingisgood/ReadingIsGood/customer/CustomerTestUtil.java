package com.readingisgood.ReadingIsGood.customer;

import com.readingisgood.ReadingIsGood.dao.CustomerEntity;
import com.readingisgood.ReadingIsGood.dto.CustomerDTO;

import java.math.BigDecimal;

public class CustomerTestUtil {
    public static CustomerDTO createTestCustomerDTO(){
        return new CustomerDTO(1L, "customer", "customer@customer.com");
    }

    public static CustomerEntity createTestCustomerEntity(){
        return new CustomerEntity(1L, "customer", "customer@customer.com");
    }

    public static CustomerDTO createTestCustomerDTOWithInvalidEmailFormat(){
        return new CustomerDTO(1L, "customer", "@customer.com");
    }

    public static CustomerEntity createTestCustomerEntityWithInvalidEmailFormat(){
        return new CustomerEntity(1L, "customer", "@customer.com");
    }
    
}
