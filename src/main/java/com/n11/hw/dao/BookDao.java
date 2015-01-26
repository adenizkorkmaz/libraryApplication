package com.n11.hw.dao;

import com.n11.hw.model.Book;

import java.util.List;

/**
 * Created by akorkmaz on 1/5/2015.
 */


public interface BookDao {
    public void save(Book b);

    public Book getById(String id);

    public void update(Book p);

    public int deleteById(String id);

    public List<Book> listAll();
}
