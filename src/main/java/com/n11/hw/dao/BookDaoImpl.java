package com.n11.hw.dao;

import com.mongodb.WriteResult;
import com.n11.hw.constant.Constants;
import com.n11.hw.model.Book;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mongo DB işlemlerini gerçekleştiren DAO Katmanı
 * <p/>
 * Created by akorkmaz on 1/5/2015.
 */

@Repository
public class BookDaoImpl implements BookDao {

    //mongoOperation spring xml conf. ile inject edilir
    private MongoOperations mongoOperations;

    public BookDaoImpl() {
    }

    public BookDaoImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    /**
     * Mongo DB kayıt metodu
     *
     * @param b
     */
    @Override
    public void save(Book b) {
        mongoOperations.insert(b, Constants.BOOK);
    }

    /**
     * Mongo DB find metodu
     *
     * @param id
     * @return
     */
    @Override
    public Book getById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoOperations.findOne(query, Book.class, Constants.BOOK);
    }

    /**
     * Mongo DB güncelleme metodu
     *
     * @param p
     */
    @Override
    public void update(Book p) {
        mongoOperations.save(p, "Book");
    }

    /**
     * Mongo DB silme metodu
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        WriteResult result = mongoOperations.remove(query, Book.class, Constants.BOOK);
        return result.getN();
    }

    /**
     * Bütün kayıtları getiren metod
     *
     * @return
     */
    @Override
    public List<Book> listAll() {
        return mongoOperations.findAll(Book.class, Constants.BOOK);
    }

    /**
     * Silme işlemi
     *
     * @param book
     */
    @Override
    public void delete(Book book) {
        mongoOperations.remove(book, Constants.BOOK);
    }

    @Override
    public void deleteAll() {
        mongoOperations.dropCollection(Book.class);
    }

}
