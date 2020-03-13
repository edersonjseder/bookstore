package com.bookstore.integration.test.book;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bookstore.BookstoreApplication;
import com.bookstore.backend.domain.Book;
import com.bookstore.utils.BookUtils;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BookstoreApplication.class)
@SpringBootTest
public class BookControllerUnitTest {

	/**
	 *  MockMvc - The main entry point for server-side Spring MVC test support. 
	 *  Perform a request and return a type that allows chaining further 
	 *  actions, such as asserting expectations, on the result.
	 */
	private MockMvc mockMvc;
	
	private final String URL = "/api/book";
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	Book mockBook = BookUtils.createBook();
	
	@Before
	public void init() {
		
		/*
		 *  This builds a MockMvc instance by registering one or more @Controller 
		 *  instances and configuring Spring MVC infrastructure programmatically.
		 */
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
	}
	
	@Test
	public void verifyAllBookList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
		
	}
	
	@Test
	public void verifyBookById() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get(URL + "/1").accept(MediaType.APPLICATION_JSON))
			   .andExpect(jsonPath("$.id").exists())
			   .andExpect(jsonPath("$.title").exists())
			   .andExpect(jsonPath("$.author").exists())
			   .andExpect(jsonPath("$.id").value(1))
			   .andExpect(jsonPath("$.title").value("Guerra do Velho"))
			   .andExpect(jsonPath("$.author").value("Justin Jones"))
			   .andDo(print());
		
	}
	
	@Test
	public void verifyInvalidBookArgument() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get(URL + "/f").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error", is("Bad Request")))
				.andExpect(jsonPath("$.exception").value("org.springframework.web.method.annotation.MethodArgumentTypeMismatchException"))
				.andDo(print());
	
	}
	
	@Test
	public void verifyInvalidBookId() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get(URL + "/0").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error").value("Not Found"))
				.andExpect(jsonPath("$.exception").value("com.bookstore.exceptions.KeywordNotFoundException"))
				.andDo(print());
		
	}
	
	@Test
	public void verifyNullBook() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get(URL + "/6").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error").value("Not Found"))
				.andExpect(jsonPath("$.exception").value("com.bookstore.exceptions.KeywordNotFoundException"))
				.andDo(print());
		
	}
	
	@Test
	public void verifySaveBook() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL)
	           .contentType(MediaType.APPLICATION_JSON)
	           .content(BookUtils.saveBookTestJson())
	           .accept(MediaType.APPLICATION_JSON))
			   .andExpect(status().isCreated())
			   .andExpect(jsonPath("$.title").exists())
			   .andExpect(jsonPath("$.author").exists())
			   .andExpect(jsonPath("$.publisher").exists())
			   .andExpect(jsonPath("$.publicationDate").exists())
			   .andExpect(jsonPath("$.language").exists())
			   .andExpect(jsonPath("$.category").exists())
			   .andExpect(jsonPath("$.numberOfPages").exists())
			   .andExpect(jsonPath("$.format").exists())
			   .andExpect(jsonPath("$.isbn").exists())
			   .andExpect(jsonPath("$.shippingWeight").exists())
			   .andExpect(jsonPath("$.listPrice").exists())
			   .andExpect(jsonPath("$.ourPrice").exists())
			   .andExpect(jsonPath("$.active").exists())
			   .andExpect(jsonPath("$.description").exists())
			   .andExpect(jsonPath("$.inStockNumber").exists())
			   .andExpect(jsonPath("$.bookImage").exists())
			   .andExpect(jsonPath("$.title").value("Filhos do Eden"))
			   .andExpect(jsonPath("$.author").value("Spohr"))
			   .andExpect(jsonPath("$.publisher").value("DMR Arts"))
			   .andExpect(jsonPath("$.publicationDate").value(984628800000L))
			   .andExpect(jsonPath("$.language").value("portuguese"))
			   .andExpect(jsonPath("$.category").value("Fiction"))
			   .andExpect(jsonPath("$.numberOfPages").value(350))
			   .andExpect(jsonPath("$.format").value("ISBN"))
			   .andExpect(jsonPath("$.isbn").value("testing"))
			   .andExpect(jsonPath("$.shippingWeight").value(6.3))
			   .andExpect(jsonPath("$.listPrice").value(62.70))
			   .andExpect(jsonPath("$.ourPrice").value(60.00))
			   .andExpect(jsonPath("$.active").value(true))
			   .andExpect(jsonPath("$.description").value("War between angels and demons on Earth"))
			   .andExpect(jsonPath("$.inStockNumber").value(8))
			   .andExpect(jsonPath("$.bookImage").value(null))
			   .andDo(print());

	}
	
	@Test
	public void verifyMalformedSaveBook() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(URL)
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(BookUtils.saveBookTestMalformedJson())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("Bad Request"))
				.andExpect(jsonPath("$.exception").value("org.springframework.http.converter.HttpMessageNotReadableException"))
				.andDo(print());
	}
	
	@Test
	public void verifyUpdateBook() throws Exception {
	
		mockMvc.perform(MockMvcRequestBuilders.put(URL)
		       .contentType(MediaType.APPLICATION_JSON)
		       .content(BookUtils.updateBookTestJson())
		       .accept(MediaType.APPLICATION_JSON))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.id").exists())
			   .andExpect(jsonPath("$.title").exists())
			   .andExpect(jsonPath("$.author").exists())
			   .andExpect(jsonPath("$.publisher").exists())
			   .andExpect(jsonPath("$.publicationDate").exists())
			   .andExpect(jsonPath("$.language").exists())
			   .andExpect(jsonPath("$.category").exists())
			   .andExpect(jsonPath("$.numberOfPages").exists())
			   .andExpect(jsonPath("$.format").exists())
			   .andExpect(jsonPath("$.isbn").exists())
			   .andExpect(jsonPath("$.shippingWeight").exists())
			   .andExpect(jsonPath("$.listPrice").exists())
			   .andExpect(jsonPath("$.ourPrice").exists())
			   .andExpect(jsonPath("$.active").exists())
			   .andExpect(jsonPath("$.description").exists())
			   .andExpect(jsonPath("$.inStockNumber").exists())
			   .andExpect(jsonPath("$.bookImage").exists())
			   .andExpect(jsonPath("$.id").value(2))
			   .andExpect(jsonPath("$.title").value("Filhos do Eden - Anjo da Morte"))
			   .andExpect(jsonPath("$.author").value("Eduardo Spohr"))
			   .andExpect(jsonPath("$.publisher").value("DMR Arts"))
			   .andExpect(jsonPath("$.publicationDate").value(984628800000L))
			   .andExpect(jsonPath("$.language").value("portuguese"))
			   .andExpect(jsonPath("$.category").value("Fiction"))
			   .andExpect(jsonPath("$.numberOfPages").value(350))
			   .andExpect(jsonPath("$.format").value("ISBN"))
			   .andExpect(jsonPath("$.isbn").value("testing"))
			   .andExpect(jsonPath("$.shippingWeight").value(6.3))
			   .andExpect(jsonPath("$.listPrice").value(62.70))
			   .andExpect(jsonPath("$.ourPrice").value(60.00))
			   .andExpect(jsonPath("$.active").value(true))
			   .andExpect(jsonPath("$.description").value("History of an angel on the Second World War"))
			   .andExpect(jsonPath("$.inStockNumber").value(8))
			   .andExpect(jsonPath("$.bookImage").value(null))
			   .andDo(print());

	}
	
	@Test
	public void verifyInvalidBookUpdate() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.put(URL)
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{ \"id\": \"8\", \"text\" : \"New ToDo Sample\", \"completed\" : \"false\" }")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").value("com.bookstore.exceptions.KeywordNotFoundException"))
				.andDo(print());
		
	}
	
	@Test
	public void verifyDeleteBook() throws Exception {
	
		mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/2").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	
	}
	
	@Test
	public void verifyInvalidBookIdToDelete() throws Exception {
	
		mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/9").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.exception").value("com.bookstore.exceptions.KeywordNotFoundException"))
				.andDo(print());
	
	}
	
}
