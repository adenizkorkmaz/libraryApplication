package com.n11.hw.service;

import com.n11.hw.model.Book;

import java.util.List;

/**
 * Created by akorkmaz on 1/6/2015.
 */
public interface BookService {
    public Book getBook(String id);

    public List<Book> getAllBooks();

    public void deleteById(String id);

    public void deleteBook(Book book);

    public void saveBook(Book book);

    public void updateBook(Book book);

    public void deleteAll();

}
