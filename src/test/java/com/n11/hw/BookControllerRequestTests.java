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
        this.repository.saveBook(new Book("Test Book", new Author("Test Name", "Test Surname")));
    }

    @Test
    public void listBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"))
                .andExpect(jsonPath("$[0].author.firstName").value("Test Name"));
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

    @Test
    public void getUnknownBook() throws Exception {
        mockMvc.perform(get("/books/{id}", "100"))
                .andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    public void createBook() throws Exception {
        Book book = new Book("Pro Struts", new Author("Rob", "Harrop"));
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(book);
        mockMvc.perform(post("/books")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<Book> booklist = repository.getAllBooks();
        book = booklist.get(booklist.size()-1);
        assertNotNull(this.repository.getBook(book.getId()));
    }

    @Test
    @DirtiesContext
    public void updateBook() throws Exception {
        Book book = new Book("Deniz", new Author("Deniz", "Korkmaz"));
        List<Book> booklist = repository.getAllBooks();

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(book);
        mockMvc.perform(put("/books/{id}", booklist.get(0).getId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertEquals("Deniz", this.repository.getBook(booklist.get(0).getId()).getTitle());
    }
}
