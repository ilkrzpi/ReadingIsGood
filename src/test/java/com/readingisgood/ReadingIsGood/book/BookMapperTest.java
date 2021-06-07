package com.readingisgood.ReadingIsGood.book;

import com.readingisgood.ReadingIsGood.dao.BookEntity;
import com.readingisgood.ReadingIsGood.dto.BookDTO;
import com.readingisgood.ReadingIsGood.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BookMapperTest {
    private BookDTO bookDTO;
    private BookEntity bookEntity;

    @BeforeEach
    void setup(){
        bookDTO = new BookDTO();
        bookEntity = new BookEntity();
    }

    @Test
    void givenNull_whenBookEntityToBookDTO_thenAssert(){
        BookDTO bookDTO = BookMapper.INSTANCE.bookEntityToBookDTO(null);
        assertNull(bookDTO);
    }

    @Test
    void givenNull_whenBookDTOToBookEntity_thenAssert(){
        BookEntity bookEntity = BookMapper.INSTANCE.bookDTOToBookEntity(null);
        assertNull(bookEntity);
    }

    @Test
    void givenBookEntity_whenBookEntityToBookDTO_thenAssert(){
        BookDTO bookDTO = BookMapper.INSTANCE.bookEntityToBookDTO(bookEntity);
        assertNotNull(bookDTO);
        assertEquals(bookEntity.getId(), bookDTO.getId());
        assertEquals(bookEntity.getStock(), bookDTO.getStock());
        assertEquals(bookEntity.getName(), bookDTO.getName());
        assertEquals(bookEntity.getPrice(), bookDTO.getPrice());
    }

    @Test
    void givenBookDTO_whenBookDTOToBookEntity_thenAssert(){
        BookEntity bookEntity = BookMapper.INSTANCE.bookDTOToBookEntity(bookDTO);
        assertNotNull(bookEntity);
        assertEquals(bookDTO.getId(), bookEntity.getId());
        assertEquals(bookDTO.getStock(), bookEntity.getStock());
        assertEquals(bookDTO.getName(), bookEntity.getName());
        assertEquals(bookDTO.getPrice(), bookEntity.getPrice());
    }
}
