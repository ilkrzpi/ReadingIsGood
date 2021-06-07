package com.readingisgood.ReadingIsGood.mapper;

import com.readingisgood.ReadingIsGood.dao.CustomerEntity;
import com.readingisgood.ReadingIsGood.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper {
     public static final CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

     public abstract CustomerEntity customerDTOToCustomerEntity(CustomerDTO customerDTO) ;

     public abstract CustomerDTO customerEntityToCustomerDTO(CustomerEntity customerEntity) ;
       
}
