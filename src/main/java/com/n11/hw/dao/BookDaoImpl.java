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
 * Created by akorkmaz on 1/5/2015.
 */

@Repository
public class BookDaoImpl implements BookDao {

    private MongoOperations mongoOperations;

    public BookDaoImpl() {
    }
    public BookDaoImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void save(Book b) {
        if(b.getId() == null){
            mongoOperations.insert(b, Constants.BOOK);
        }else{
            update(b);
        }
    }

    @Override
    public Book getById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoOperations.findOne(query, Book.class, Constants.BOOK);
    }

    @Override
    public void update(Book p) {
        mongoOperations.save(p,"Book");
    }

    @Override
    public int deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        WriteResult result = mongoOperations.remove(query, Book.class, Constants.BOOK);
        return result.getN();
    }

    @Override
    public List<Book> listAll(){
        return mongoOperations.findAll(Book.class,Constants.BOOK);
    }

}
