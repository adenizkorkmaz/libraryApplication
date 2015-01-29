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
 * Spring MVC Controller Sınıfı
 *
 * Created by akorkmaz on 1/6/2015.
 */

@Controller
@RequestMapping("/books")
public class BookController {

    /*
     * Service katmanı xml conf ile inject edilir.
     */
    private BookService  bookService;

    /**
     * Kitapları service katmanı aracılığıyla listeleyen GET metodu
     * @return list of books
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Book> list(){
        return bookService.getAllBooks();
    }


    /**
     * Service katmanı ile, param olarak gelen id parametresine göre
     * DB den kitap çeken GET metodu
     *
     * @param id
     * @return book
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Book find(@PathVariable("id") String id) {
        Book book = bookService.getBook(id);
        if (book == null) {
            System.out.print("hata");
        }
        return book;
    }

    /**
     * Kitabı DB ye kayıt etmek için post requestini işleyen metod.
     *
     * @param book
     * @param parentUri
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Book book, @Value("#{request.requestURL}") StringBuffer parentUri) {
        this.bookService.updateBook(book);
    }

    /**
     * Service katmanı aracılığıyla DB den kitap
     * silmeye yarayan DELETE metodu.
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        this.bookService.deleteById(id);
    }

    /**
     * Service katmanı aracılığıyla DB deki kitabı
     * güncellemeye yarayan PUT metodu.
     * @param id
     * @param book
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") String id, @RequestBody Book book) {
        book.setId(id);
        this.bookService.updateBook(book);
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
}
