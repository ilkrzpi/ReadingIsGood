package com.readingisgood.ReadingIsGood.service;

import com.readingisgood.ReadingIsGood.dao.BookEntity;
import com.readingisgood.ReadingIsGood.dao.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookEntity createBook(BookEntity bookEntity) {
        log.info("createBook", bookEntity);
        return bookRepository.save(bookEntity);
    }

    public BookEntity getBookById(Long id) {
        log.info("getBookById", id);
        Optional<BookEntity> bookEntity = bookRepository.findById(id);
        return bookEntity.orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void updateStock(Long id, Integer stock) {
        log.info("updateStock", id);
        getBookById(id);
        bookRepository.updateStock(id, stock);
    }
}
