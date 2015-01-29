package com.n11.hw;

import com.n11.hw.dao.BookDao;
import com.n11.hw.model.Author;
import com.n11.hw.model.Book;
import com.n11.hw.service.BookServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by akorkmaz on 1/6/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class BookServiceMockTest {

    @Mock
    private BookDao bookDao;

    @InjectMocks
    private BookServiceImpl bookService;


    @Before
    public void init() {

    }

    @Test
    public void saveTest() throws Exception {
        bookService.saveBook(any(Book.class));
        verify(bookService.getBookDao()).save(any(Book.class));
    }

    @Test
    public void getAllBookTest() throws Exception {
        bookService.getAllBooks();
        verify(bookService.getBookDao()).listAll();
    }

    @Test
    public void deleteBookTest() throws Exception {
        bookService.deleteBook(any(Book.class));
        verify(bookService.getBookDao()).delete(any(Book.class));
    }

    @Test
    public void updateBookTest() throws Exception {
        bookService.updateBook(any(Book.class));
        verify(bookService.getBookDao()).update(any(Book.class));
    }

    @Test
    public void deleteAllBookTest() throws Exception {
        bookService.deleteAll();
        verify(bookService.getBookDao()).deleteAll();
    }

}
