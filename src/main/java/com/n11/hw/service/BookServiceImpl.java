package com.n11.hw.service;

import com.n11.hw.dao.BookDao;
import com.n11.hw.model.Book;

import java.io.Serializable;
import java.util.List;

/**
 * Service Katmanı implemetasyonu
 *
 * Created by akorkmaz on 1/26/2015.
 */
public class BookServiceImpl implements BookService,Serializable {

    private BookDao bookDao;

    @Override
    public Book getBook(String id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.listAll();
    }

    @Override
    public void deleteById(String id) {
        bookDao.deleteById(id);
    }

    @Override
    public void deleteBook(Book book) {
        bookDao.delete(book);
    }

    @Override
    public void saveBook(Book book) {
        bookDao.save(book);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.update(book);
    }

    @Override
    public void deleteAll() {
        bookDao.deleteAll();
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public BookDao getBookDao() {
        return bookDao;
    }
}
