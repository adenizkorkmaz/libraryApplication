package com.n11.hw;

import com.n11.hw.dao.BookDao;
import com.n11.hw.model.Author;
import com.n11.hw.model.Book;
import com.n11.hw.service.BookService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/api-servlet.xml")
public class BookControllerRequestTests {
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected BookService repository;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        this.repository.saveBook(new Book("Pro Spring", new Author("Rob", "Harrop")));
    }

    @Test
    public void listBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Pro Spring"))
                .andExpect(jsonPath("$[0].author.firstName").value("Rob"));
    }
    @Test
    @DirtiesContext
    public void deleteBook() throws Exception {
        List<Book> booklist = repository.getAllBooks();
        Book book = booklist.get(0);
        mockMvc.perform(delete("/books/{id}", book.getId()))
                .andExpect(status().isNoContent());
        assertNull(this.repository.getBook(book.getId()));
    }


}
