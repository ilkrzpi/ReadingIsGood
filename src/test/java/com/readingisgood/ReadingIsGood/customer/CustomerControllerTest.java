package com.readingisgood.ReadingIsGood.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingisgood.ReadingIsGood.customer.CustomerTestUtil;
import com.readingisgood.ReadingIsGood.controller.CustomerController;
import com.readingisgood.ReadingIsGood.dao.CustomerEntity;
import com.readingisgood.ReadingIsGood.dto.CustomerDTO;
import com.readingisgood.ReadingIsGood.mapper.CustomerMapper;
import com.readingisgood.ReadingIsGood.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerController customerController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Nested
    class CreateCustomer {
        @Test
        void givenCustomer_whenCreateCustomer_thenReturnCreated() throws Exception {
            CustomerEntity testCustomerEntity = CustomerTestUtil.createTestCustomerEntity();
            CustomerDTO testCustomerDTO = CustomerTestUtil.createTestCustomerDTO();
            when(customerMapper.customerDTOToCustomerEntity(any())).thenReturn(testCustomerEntity);
            when(customerMapper.customerEntityToCustomerDTO(any())).thenReturn(testCustomerDTO);
            when(customerService.createCustomer(any())).thenReturn(testCustomerEntity);

            mockMvc.perform(MockMvcRequestBuilders.post("/customers").
                    contentType(MediaType.APPLICATION_JSON).
                    content(objectMapper.writeValueAsString(testCustomerDTO))).
                    andExpect(status().isCreated());

            verify(customerMapper, times(1)).customerDTOToCustomerEntity(any());
            verify(customerMapper, times(1)).customerEntityToCustomerDTO(any());
            verify(customerService, times(1)).createCustomer(any());

        }

        @Test
        void givenCustomerWithInvalidEmailFormat_whenCreateCustomer_thenReturnBadRequest() throws Exception {
            CustomerDTO testCustomerDTO = CustomerTestUtil.createTestCustomerDTOWithInvalidEmailFormat();

            mockMvc.perform(MockMvcRequestBuilders.post("/customers").
                    contentType(MediaType.APPLICATION_JSON).
                    content(objectMapper.writeValueAsString(testCustomerDTO))).
                    andExpect(status().isBadRequest());
        }
    }
}

