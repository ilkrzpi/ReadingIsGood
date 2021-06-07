package com.readingisgood.ReadingIsGood.mapper;

import com.readingisgood.ReadingIsGood.dao.BookEntity;
import com.readingisgood.ReadingIsGood.dto.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class BookMapper {
      public static final BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

      public abstract BookEntity bookDTOToBookEntity(BookDTO bookDTO);

      public abstract BookDTO bookEntityToBookDTO(BookEntity bookEntity);

}
