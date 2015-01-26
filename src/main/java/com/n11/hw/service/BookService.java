package com.n11.hw.service;

import com.n11.hw.model.Book;

import java.util.List;

/**
 * Created by akorkmaz on 1/6/2015.
 */
public interface BookService {
    public Book getBook(String id);

    public List<Book> getAllBook();

    public void delete(String id);

    public void delete(Book book);

    public void save(Book book);

    public void update(Book book);

    public void deleteAll();

}
