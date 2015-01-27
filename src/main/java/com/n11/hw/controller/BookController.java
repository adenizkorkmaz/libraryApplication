package com.n11.hw.controller;

import com.n11.hw.dao.BookDao;
import com.n11.hw.model.Book;
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

    private BookDao bookDao;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Book> list(){
        return bookDao.listAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Book find(@PathVariable("id") String id) {
        Book book = this.bookDao.getById(id);
        if (book == null) {
            System.out.print("hata");
        }
        return book;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<?> create(@RequestBody Book book, @Value("#{request.requestURL}") StringBuffer parentUri) {
        this.bookDao.save(book);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(childLocation(parentUri, book.getId()));
        return new HttpEntity<Object>(headers);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        this.bookDao.deleteById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") String id, @RequestBody Book book) {
        book.setId(id);
        this.bookDao.update(book);
    }


    private URI childLocation(StringBuffer parentUri, Object childId) {
        UriTemplate uri = new UriTemplate(parentUri.append("/{childId}").toString());
        return uri.expand(childId);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class BookNotFoundException extends RuntimeException {
        public BookNotFoundException(Integer id) {
            super("Book '" + id + "' not found.");
        }
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
