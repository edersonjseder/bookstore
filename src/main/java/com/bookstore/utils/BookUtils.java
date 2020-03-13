package com.bookstore.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bookstore.backend.domain.Book;
import com.bookstore.integration.test.book.TheBook;

/**
 * Created by root on 01/07/17.
 */
public class BookUtils {

    /**
     * Non instantiable
     */
    private BookUtils(){
        throw new AssertionError("Non instantiable");
    }
    
    /**
     * Creates a book with basic attributes set for test.
     *.
     * @return The Book object.
     */
    public static Book createBook() {
    	
    	Book book = new Book();
    	
        book.setTitle("Guerra do Velho");
        book.setAuthor("Justin");
        book.setPublisher("DKU editor");
        book.setPublicationDate(new Date(984628800000L));
        book.setLanguage("English");
        book.setNumberOfPages(200);
        book.setFormat("ISBN");
        book.setIsbn("Dont know");
        book.setShippingWeight(5.2);
        book.setListPrice(52.45);
        book.setOurPrice(50.10);
        book.setActive(true);
        book.setDescription("Interestellar adventures by serving the interestellar army");
        book.setInStockNumber(4);
        book.setBookImage(null);

        return book;
    
    }
    
    public static String saveBookTestJson() {
    	
    	String jsonBook = "{\"title\" : \"Filhos do Eden\", \"author\" : \"Spohr\", \"publisher\" : \"DMR Arts\", " + 
    					   "\"publicationDate\" : \"984628800000\", \"language\" : \"portuguese\", \"category\" : \"Fiction\", \"numberOfPages\" : \"350\", " + 
    					   "\"format\" : \"ISBN\", \"isbn\" : \"testing\", \"shippingWeight\" : \"6.3\", " +
    					   "\"listPrice\" : \"62.70\", \"ourPrice\" : \"60.00\", \"active\" : \"true\", " +
    					   "\"description\" : \"War between angels and demons on Earth\", \"inStockNumber\" : \"8\", \"bookImage\" : \"null\" }";
    	
    	return jsonBook;
    	
    }
    
    public static String saveBookTestMalformedJson() {
    	
    	String jsonBook = "{\"title\"  \"Filhos do Eden\", \"author\" : \"Spohr\", \"publisher\" : \"DMR Arts\", " + 
    					   "\"publicationDate\" : \"984628800000\", \"language\" : \"portuguese\", \"category\" : \"Fiction\", \"numberOfPages\" : \"350\", " + 
    					   "\"format\" : \"ISBN\" \"isbn\" : \"testing\", \"shippingWeight\" : \"6.3\", " +
    					   "\"listPrice\" : \"62.70\", \"ourPrice\" : \"60.00\", \"active\" : \"true\", " +
    					   "\"description\" : \"War between angels and demons on Earth\", \"inStockNumber : \"8\", \"bookImage\" : \"null\" ";
    	
    	return jsonBook;
    	
    }
    
    public static String updateBookTestJson() {
    	
    	String jsonBook = "{\"title\" : \"Filhos do Eden - Anjo da Morte\", \"author\" : \"Eduardo Spohr\", \"publisher\" : \"DMR Arts\", " + 
    					   "\"publicationDate\" : \"984628800000\", \"language\" : \"portuguese\", \"category\" : \"Fiction\", \"numberOfPages\" : \"350\", " + 
    					   "\"format\" : \"ISBN\", \"isbn\" : \"testing\", \"shippingWeight\" : \"6.3\", " +
    					   "\"listPrice\" : \"62.70\", \"ourPrice\" : \"60.00\", \"active\" : \"true\", " +
    					   "\"description\" : \"History of an angel on the Second World War\", \"inStockNumber\" : \"8\", \"bookImage\" : \"null\" }";
    	
    	return jsonBook;
    	
    }
    
    public static Set<Book> getBookList() {
    	
    	Book book1 = new TheBook.BookBuilder(3)
    							  .setTitle("")
    							  .setAuthor("")
    							  .setPublisher("")
    							  .setPublicationDate(new Date(984628800000L))
    							  .setLanguage("")
    							  .setCategory("")
    							  .setNumberOfPages(200)
    							  .setFormat("")
    							  .setIsbn("")
    							  .setShippingWeight(6.2)
    							  .setListPrice(45.65)
    							  .setOurPrice(40.50)
    							  .setActive(true)
    							  .setDescription("")
    							  .setInStockNumber(7)
    							  .setBookImage(null)
    							  .build();

    	Book book2 = new TheBook.BookBuilder(3)
								  .setTitle("")
								  .setAuthor("")
								  .setPublisher("")
								  .setPublicationDate(new Date(984628800000L))
								  .setLanguage("")
								  .setCategory("")
								  .setNumberOfPages(200)
								  .setFormat("")
								  .setIsbn("")
								  .setShippingWeight(6.2)
								  .setListPrice(45.65)
								  .setOurPrice(40.50)
								  .setActive(true)
								  .setDescription("")
								  .setInStockNumber(7)
								  .setBookImage(null)
								  .build();
    	
    	Book book3 = new TheBook.BookBuilder(3)
								  .setTitle("")
								  .setAuthor("")
								  .setPublisher("")
								  .setPublicationDate(new Date(984628800000L))
								  .setLanguage("")
								  .setCategory("")
								  .setNumberOfPages(200)
								  .setFormat("")
								  .setIsbn("")
								  .setShippingWeight(6.2)
								  .setListPrice(45.65)
								  .setOurPrice(40.50)
								  .setActive(true)
								  .setDescription("")
								  .setInStockNumber(7)
								  .setBookImage(null)
								  .build();

		Set<Book> books = new HashSet<Book>();
		books.add(book1);
		books.add(book2);
		books.add(book3);
		
		return books;
    }
}
