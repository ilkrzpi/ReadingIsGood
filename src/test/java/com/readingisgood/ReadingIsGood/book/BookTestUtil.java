package com.readingisgood.ReadingIsGood.book;

import com.readingisgood.ReadingIsGood.dao.BookEntity;
import com.readingisgood.ReadingIsGood.dto.BookDTO;

import java.math.BigDecimal;

public class BookTestUtil {
    public static BookDTO createTestBookDTO(){
        return new BookDTO(1L, "book", BigDecimal.TEN, 1);
    }

    public static BookDTO createTestBookDTOWithNegativeStock(){
        return new BookDTO(1L, "book", BigDecimal.TEN, -1);
    }

    public static BookEntity createTestBookEntity(){
        return new BookEntity(1L, "book", BigDecimal.TEN, 1);
    }

    public static BookEntity createTestBookEntityWithNegativeStock(){
        return new BookEntity(1L, "book", BigDecimal.TEN, -1);
    }


}
