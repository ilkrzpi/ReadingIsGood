package com.readingisgood.ReadingIsGood.book;

import com.readingisgood.ReadingIsGood.dao.BookRepository;
import com.readingisgood.ReadingIsGood.dto.BookDTO;
import com.readingisgood.ReadingIsGood.mapper.BookMapper;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class BookControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private BookRepository bookRepository;

    @MockBean
    private BookMapper bookMapper;

    @BeforeEach
    void reset(){
        Mockito.reset(bookMapper);
    }

    @Nested
    class CreateBook {
        @Test
        void givenBook_whenCreateBook_thenReturnCreated() {
            when(bookMapper.bookDTOToBookEntity(any())).thenReturn(BookTestUtil.createTestBookEntity());
            when(bookMapper.bookEntityToBookDTO(any())).thenReturn(BookTestUtil.createTestBookDTO());

            ResponseEntity<BookDTO> responseEntity = testRestTemplate.postForEntity("/books" , BookTestUtil.createTestBookDTO(), BookDTO.class);
            assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
            assertNotNull(responseEntity.getBody());
            assertEquals(BookTestUtil.createTestBookDTO().getName(), responseEntity.getBody().getName());
            assertEquals(BookTestUtil.createTestBookDTO().getPrice(), responseEntity.getBody().getPrice());
            assertEquals(BookTestUtil.createTestBookDTO().getStock(), responseEntity.getBody().getStock());

            verify(bookMapper, times(1)).bookDTOToBookEntity(any());
            verify(bookMapper, times(1)).bookEntityToBookDTO(any());

        }

        @Test
        void givenBookWithNegativeStock_whenCreateBook_thenReturnBadRequest() {
            ResponseEntity<BookDTO> responseEntity = testRestTemplate.postForEntity("/books" , BookTestUtil.createTestBookDTOWithNegativeStock(), BookDTO.class);
            assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
    }
}
