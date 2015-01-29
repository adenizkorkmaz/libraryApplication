package com.n11.hw.model;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Book Entity Class
 *
 * Created by akorkmaz on 1/5/2015.
 */
public class Book implements Serializable {

    @Id
    private String id;
    private String title;
    private Author author;

    public Book() {
    }

    public Book(String title, Author author) {
        this.title = title;
        this.author = author;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author.toString() +
                '}';
    }
}
