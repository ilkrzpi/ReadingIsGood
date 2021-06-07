package com.readingisgood.ReadingIsGood.customer;

import com.readingisgood.ReadingIsGood.customer.CustomerTestUtil;
import com.readingisgood.ReadingIsGood.customer.CustomerTestUtil;
import com.readingisgood.ReadingIsGood.dao.CustomerRepository;
import com.readingisgood.ReadingIsGood.dto.CustomerDTO;
import com.readingisgood.ReadingIsGood.dto.CustomerDTO;
import com.readingisgood.ReadingIsGood.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class CustomerControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerMapper customerMapper;

    @BeforeEach
    void reset(){
        Mockito.reset(customerMapper);
    }

    @Nested
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    @ExtendWith(SpringExtension.class)
    class CreateCustomer {
        @Test
        @Sql(scripts = "classpath:cleancustomer.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void givenCustomer_whenCreateCustomer_thenReturnCreated() {
            when(customerMapper.customerDTOToCustomerEntity(any())).thenReturn(CustomerTestUtil.createTestCustomerEntity());
            when(customerMapper.customerEntityToCustomerDTO(any())).thenReturn(CustomerTestUtil.createTestCustomerDTO());

            ResponseEntity<CustomerDTO> responseEntity = testRestTemplate.postForEntity("/customers" , CustomerTestUtil.createTestCustomerDTO(), CustomerDTO.class);
            assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
            assertNotNull(responseEntity.getBody());
            assertEquals(CustomerTestUtil.createTestCustomerDTO().getName(), responseEntity.getBody().getName());
            assertEquals(CustomerTestUtil.createTestCustomerDTO().getEmail(), responseEntity.getBody().getEmail());

            verify(customerMapper, times(1)).customerDTOToCustomerEntity(any());
            verify(customerMapper, times(1)).customerEntityToCustomerDTO(any());

        }

        @Test
        void givenCustomerWithInvalidEmailFormat_whenCreateCustomer_thenReturnBadRequest() {
            ResponseEntity<CustomerDTO> responseEntity = testRestTemplate.postForEntity("/customers" , CustomerTestUtil.createTestCustomerDTOWithInvalidEmailFormat(), CustomerDTO.class);
            assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        }

        @Test
        @Sql(scripts = "classpath:populatecustomer.sql")
        @Sql(scripts = "classpath:cleancustomer.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void givenCustomerWithExistingEmail_whenCreateCustomer_thenReturnConflict() {
            when(customerMapper.customerDTOToCustomerEntity(any())).thenReturn(CustomerTestUtil.createTestCustomerEntity());

            ResponseEntity<CustomerDTO> responseEntity = testRestTemplate.postForEntity("/customers" , CustomerTestUtil.createTestCustomerDTO(), CustomerDTO.class);
            assertEquals(responseEntity.getStatusCode(), HttpStatus.CONFLICT);
            assertNull(responseEntity.getBody());

            verify(customerMapper, times(1)).customerDTOToCustomerEntity(any());
        }
    }
}
