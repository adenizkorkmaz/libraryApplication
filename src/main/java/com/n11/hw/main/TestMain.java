package com.n11.hw.main;

import com.n11.hw.dao.BookDao;
import com.n11.hw.model.Author;
import com.n11.hw.model.Book;
import com.n11.hw.service.BookService;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import java.util.List;

/**
 * Created by akorkmaz on 1/6/2015.
 */
public class TestMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

        BookService bookDao = ctx.getBean("bookService", BookService.class);

        Book p = new Book("kukla",new Author("deniz","korkmaz"));

        //create
        bookDao.saveBook(p);
        System.out.println("Generated ID="+p.getId());

        Book p2 = new Book("hobarey",new Author("ahmet","kacar"));

        //create
        bookDao.saveBook(p2);
        System.out.println("Generated ID="+p2.getId());


        List<Book> books = bookDao.getAllBooks();

        for(Book bookk : books){
            System.out.println(bookk.toString());
        }

        //read
        Book p1 = bookDao.getBook(p.getId());
        System.out.println("Retrieved Book="+p1);

        //update
        p1.setTitle("Beyoglu Rapsodisi");
        p1.setAuthor(new Author("Korkmaz","güner"));
        bookDao.updateBook(p1);
        Book temp = bookDao.getBook(p1.getId());
        System.out.println("Retrieved Person after update="+temp);

        //delete
//        bookDao.delete(p1.getId());
//        System.out.println("Number of records deleted=");
//
//        List<Book> booksLast = bookDao.getAllBook();
//
//        for(Book bookk : booksLast){
//            bookDao.delete(bookk);
//        }
//
//        List<Book> booksLast2 = bookDao.getAllBook();
        ctx.close();

    }
}
