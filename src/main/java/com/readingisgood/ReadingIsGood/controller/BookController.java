package com.readingisgood.ReadingIsGood.controller;

import com.readingisgood.ReadingIsGood.dao.BookEntity;
import com.readingisgood.ReadingIsGood.dto.BookDTO;
import com.readingisgood.ReadingIsGood.mapper.BookMapper;
import com.readingisgood.ReadingIsGood.service.BookService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@Api(tags = "Book")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        BookEntity bookEntity;
        try{
            log.info("createBook", bookDTO);
            bookEntity = bookService.createBook(bookMapper.bookDTOToBookEntity(bookDTO));
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(bookMapper.bookEntityToBookDTO(bookEntity), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{bookId}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable @NotNull Long bookId) {
        try {
            log.info("getBookById", bookId);
            BookEntity bookEntity = bookService.getBookById(bookId);
            return new ResponseEntity<>(bookMapper.bookEntityToBookDTO(bookEntity), HttpStatus.OK);
        } catch (Exception e) {
            log.error("getBookById exception: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "bookId/{bookId}/stock/{stock}")
    public ResponseEntity<BookDTO> updateStock(@PathVariable @NotNull Long bookId, @PathVariable @NotNull Integer stock) {
        try {
            bookService.updateStock(bookId, stock);
            log.info("updateStock successfully!");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("updateStock exception: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
