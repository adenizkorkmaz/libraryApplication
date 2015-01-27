package com.n11.hw.controller;

import com.n11.hw.model.Book;
import com.n11.hw.service.BookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.List;

/**
 * Created by akorkmaz on 1/6/2015.
 */

@Controller
@RequestMapping("/books")
public class BookController {

    private BookService  bookService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Book> list(){
        return bookService.getAllBooks();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Book find(@PathVariable("id") String id) {
        Book book = bookService.getBook(id);
        if (book == null) {
            System.out.print("hata");
        }
        return book;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<?> create(@RequestBody Book book, @Value("#{request.requestURL}") StringBuffer parentUri) {
        this.bookService.saveBook(book);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(childLocation(parentUri, book.getId()));
        return new HttpEntity<Object>(headers);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        this.bookService.deleteById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") String id, @RequestBody Book book) {
        book.setId(id);
        this.bookService.updateBook(book);
    }

    private URI childLocation(StringBuffer parentUri, Object childId) {
        UriTemplate uri = new UriTemplate(parentUri.append("/{childId}").toString());
        return uri.expand(childId);
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
}
