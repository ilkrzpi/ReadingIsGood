package com.readingisgood.ReadingIsGood.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingisgood.ReadingIsGood.controller.BookController;
import com.readingisgood.ReadingIsGood.dao.BookEntity;
import com.readingisgood.ReadingIsGood.dto.BookDTO;
import com.readingisgood.ReadingIsGood.mapper.BookMapper;
import com.readingisgood.ReadingIsGood.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookController bookController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Nested
    class CreateBook {
        @Test
        void givenBook_whenCreateBook_thenReturnCreated() throws Exception {
            BookEntity testBookEntity = BookTestUtil.createTestBookEntity();
            BookDTO testBookDTO = BookTestUtil.createTestBookDTO();
            when(bookMapper.bookDTOToBookEntity(any())).thenReturn(testBookEntity);
            when(bookMapper.bookEntityToBookDTO(any())).thenReturn(testBookDTO);
            when(bookService.createBook(any())).thenReturn(testBookEntity);

            mockMvc.perform(MockMvcRequestBuilders.post("/books").
                    contentType(MediaType.APPLICATION_JSON).
                    content(objectMapper.writeValueAsString(testBookDTO))).
                    andExpect(status().isCreated());

            verify(bookMapper, times(1)).bookDTOToBookEntity(any());
            verify(bookMapper, times(1)).bookEntityToBookDTO(any());
            verify(bookService, times(1)).createBook(any());

        }

        @Test
        void givenBookWithNegativeStock_whenCreateBook_thenReturnBadRequest() throws Exception {
            BookDTO testBookDTO = BookTestUtil.createTestBookDTOWithNegativeStock();

            mockMvc.perform(MockMvcRequestBuilders.post("/books").
                    contentType(MediaType.APPLICATION_JSON).
                    content(objectMapper.writeValueAsString(testBookDTO))).
                    andExpect(status().isBadRequest());
        }
    }

    @Nested
    class GetBook {
        @Test
        void givenBookId_whenGetBookById_thenReturnBook() throws Exception {
            BookEntity testBookEntity = BookTestUtil.createTestBookEntity();
            BookDTO testBookDTO = BookTestUtil.createTestBookDTO();
            when(bookMapper.bookEntityToBookDTO(any())).thenReturn(testBookDTO);
            when(bookService.getBookById(anyLong())).thenReturn(testBookEntity);

            mockMvc.perform(MockMvcRequestBuilders.get("/books/" + testBookDTO.getId()))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testBookDTO.getId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testBookDTO.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(testBookDTO.getPrice()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(testBookDTO.getStock()));

            verify(bookMapper, times(1)).bookEntityToBookDTO(any());
            verify(bookService, times(1)).getBookById(anyLong());

        }
    }
}
