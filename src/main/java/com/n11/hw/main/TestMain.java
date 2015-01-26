package com.n11.hw.main;

import com.n11.hw.dao.BookDao;
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

        Book p = new Book(null, "Kukla", "Ahmet Korkmaz");

        //create
        bookDao.save(p);
        System.out.println("Generated ID="+p.getId());

        Book p2 = new Book(null, "Kukla2", "Ahmet Korkmaz2");

        //create
        bookDao.save(p2);
        System.out.println("Generated ID="+p2.getId());


        List<Book> books = bookDao.getAllBook();

        for(Book bookk : books){
            System.out.println(bookk.toString());
        }

        //read
        Book p1 = bookDao.getBook(p.getId());
        System.out.println("Retrieved Book="+p1);

        //update
        p1.setName("Beyoglu Rapsodisi");p1.setAuthor("Ahmet Deniz");
        bookDao.update(p1);
        Book temp = bookDao.getBook(p1.getId());
        System.out.println("Retrieved Person after update="+temp);

        //delete
        bookDao.delete(p1.getId());
        System.out.println("Number of records deleted=");

        List<Book> booksLast = bookDao.getAllBook();

        for(Book bookk : booksLast){
            bookDao.delete(bookk);
        }

        List<Book> booksLast2 = bookDao.getAllBook();
        ctx.close();

    }
}
