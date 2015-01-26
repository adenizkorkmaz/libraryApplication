package com.n11.hw.service;

import com.n11.hw.dao.BookDao;
import com.n11.hw.model.Book;

import java.io.Serializable;
import java.util.List;

/**
 * Created by akorkmaz on 1/26/2015.
 */
public class BookServiceImpl implements BookService,Serializable {

    private BookDao bookDao;

    @Override
    public Book getBook(String id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAllBook() {
        return bookDao.listAll();
    }

    @Override
    public void delete(String id) {
        bookDao.deleteById(id);
    }

    @Override
    public void save(Book book) {
        bookDao.save(book);
    }

    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

    @Override
    public void deleteAll() {
        //todo:not implemented yet
    }

    public void delete(Book book){
        bookDao.delete(book);
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
