package com.bookstore.integration.test.book;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.bookstore.backend.domain.Book;
import com.bookstore.backend.repositories.BookRepository;
import com.bookstore.backend.service.BookService;
import com.bookstore.utils.BookUtils;

/**
 * Created by root on 10/06/17.
 */
@RunWith(SpringRunner.class)
public class BookServiceTest {

	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	private BookService bookService;
	
	@Before
	public void init() {
		
		// Initializes fields annotated with Mockito annotations.
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Test
	public void testGetAllBook() {
		
		when(bookRepository.findAll()).thenReturn(BookUtils.getBookList());
	
		Set<Book> results = bookService.findAll();
		
		assertEquals(1, results.size());
		
	}
	
	@Test
	public void testGetBookById() {
    	
		Book book = new TheBook
				.BookBuilder(4).setTitle("Batalha do Apocalipse").setAuthor("Eduardo").setPublisher("MKT")
				  			   .setPublicationDate(new Date(984628800000L)).setLanguage("Portuguese")
				  			   .setCategory("Fiction").setNumberOfPages(200).setFormat("ABNT").setIsbn("ABNT")
				  			   .setShippingWeight(6.2).setListPrice(45.65).setOurPrice(40.50).setActive(true)
				  			   .setDescription("War between angels and demons").setInStockNumber(7).setBookImage(null)
				  			   .build();
		
		when(bookRepository.findOne(1)).thenReturn(book);
		
		Book result = bookService.findOneBook(1);
		
		assertEquals("Batalha do Apocalipse", result.getTitle());
		assertEquals("Eduardo", result.getAuthor());
		assertEquals("MKT", result.getPublisher());
		assertEquals("Portuguese", result.getLanguage());
		assertEquals("Fiction", result.getCategory());
		assertEquals("ABNT", result.getFormat());
		assertEquals("War between angels and demons", result.getDescription());
		assertEquals(true, result.isActive());
		
	}
	
    @Test
    public void testCreateNewBook() throws Exception {
    	
		Book book = new TheBook
				.BookBuilder(6).setTitle("Heart of Steel").setAuthor("Sanderson").setPublisher("MMK")
				  			   .setPublicationDate(new Date(984628800000L)).setLanguage("English")
				  			   .setCategory("Fiction").setNumberOfPages(200).setFormat("ABNT").setIsbn("ABNT")
				  			   .setShippingWeight(6.2).setListPrice(45.65).setOurPrice(40.50).setActive(true)
				  			   .setDescription("War between Villains for Power").setInStockNumber(7).setBookImage(null)
				  			   .build();
		
		when(bookRepository.save(book)).thenReturn(book);
		
		Book result = bookService.saveBook(book);
		
		assertEquals("Heart of Steel", result.getTitle());
		assertEquals("Sanderson", result.getAuthor());
		assertEquals("MMK", result.getPublisher());
		assertEquals("English", result.getLanguage());
		assertEquals("Fiction", result.getCategory());
		assertEquals("ABNT", result.getFormat());
		assertEquals("War between Villains for Power", result.getDescription());
		assertEquals(true, result.isActive());
        
    }

/*  @Test
    public void testUpdateBook() throws Exception {

    	Book book = bookService.findBookByTitle(cityName);
        
        if(book != null) {
        	
        	Assert.assertNotNull(book);
        	Assert.assertNotNull(book.getId());

        	Assert.assertEquals(book.getTitle(), cityName);
        	
        	book.setTitle(cityName);
        	
        	book = bookService.updateBookInformation(book.getId(), book);

        }

    }*/

    @Test
    public void testRemoveBook() throws Exception {

		Book book = new TheBook
				.BookBuilder(6).setTitle("Heart of Steel").setAuthor("Sanderson").setPublisher("MMK")
				  			   .setPublicationDate(new Date(984628800000L)).setLanguage("English")
				  			   .setCategory("Fiction").setNumberOfPages(200).setFormat("ABNT").setIsbn("ABNT")
				  			   .setShippingWeight(6.2).setListPrice(45.65).setOurPrice(40.50).setActive(true)
				  			   .setDescription("War between Villains for Power").setInStockNumber(7).setBookImage(null)
				  			   .build();

		bookService.removeOneBook(book.getId());
		
        verify(bookRepository, times(1)).delete(book.getId());

    }
    
}
