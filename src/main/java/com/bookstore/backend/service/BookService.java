package com.bookstore.backend.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.backend.domain.Book;
import com.bookstore.backend.repositories.BookRepository;

@Service
public class BookService {
	
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);
	
	/**
	 * Dependency Injection of Spring Framework
	 */
	@Autowired
	private BookRepository bookRepository;
	
    /**
     * Retrieves an active book list for the requested client.
     * 
     * @return an active Book list for the requested client or null/empty if none is found.
     */
	public Set<Book> findAllActiveBooks() {
		
		Set<Book> activeBookList = null;
		
		try {
			
			Set<Book> bookList = bookRepository.findAll();
			
			activeBookList = new HashSet<>();
			
			for (Book book : bookList) {
				if(book.isActive()){
					activeBookList.add(book);
				}
			}
			
		} catch (Exception e) {
			LOG.error("Problem with loading data {} ", e.getMessage());
		}
		
		return activeBookList;
	}
	
    /**
     * Retrieves a book list for the requested client.
     * 
     * @return a Book list for the requested client or null/empty if none is found.
     */
	public Set<Book> findAll() {
		
		Set<Book> bookList = null;
		
		try {
			
			bookList = bookRepository.findAll();
			
		} catch (Exception e) {
			LOG.error("Problem with loading data {} ", e.getMessage());
		}
		
		return bookList;
	}
	
    /**
     * Retrieves a book for the given id.
     * 
     * @param id The id to look for the book object
     * @return a Book given the id passed or null if none is found.
     */
	public Book findOneBook(Integer id) {
		
		Book bookLoaded = null;
		
		try {

			bookLoaded = bookRepository.findOne(id);

		} catch (Exception e) {
			LOG.error("Problem with loading data {} ", e.getMessage());
		}

		return bookLoaded;
	}

    /**
     * Retrieves a book for the given id.
     * 
     * @param title The id to look for the book object
     * @return a Book given the id passed or null if none is found.
     */
	public Book findBookByTitle(String title) {
		
		Book bookLoaded = null;
		
		try {

			bookLoaded = bookRepository.findOneByTitle(title);

		} catch (Exception e) {
			LOG.error("Problem with loading data {} ", e.getMessage());
		}

		return bookLoaded;
	}
	
    /**
     * Creates a new book for the client requested.
     *
     * @param book The Book object containing the data.
     * @return a new book object for the requested client.
     */
	public Book saveBook(Book book) {
		
		Book bookSaved = bookRepository.save(book);
		
		return bookSaved;
	}

    /**
     * Updates a book for the given id.
     * 
     * @param bookId The id to look for the city object
     * @param book The Book object
     * @return an updated Book given the id passed or null if none is found.
     */
	public Book updateBookInformation(Integer bookId, Book book) {

		Book bookUpdated = null;
		
		try {
			
			Book bookToUpdate = bookRepository.findOne(bookId);

			if (bookToUpdate != null) {

				bookToUpdate.setTitle(book.getTitle());
				bookToUpdate.setAuthor(book.getAuthor());
				bookToUpdate.setPublisher(book.getPublisher());
				bookToUpdate.setPublicationDate(book.getPublicationDate());
				bookToUpdate.setLanguage(book.getLanguage());
				bookToUpdate.setCategory(book.getCategory());
				bookToUpdate.setNumberOfPages(book.getNumberOfPages());
				bookToUpdate.setFormat(book.getFormat());
				bookToUpdate.setIsbn(book.getIsbn());
				bookToUpdate.setShippingWeight(book.getShippingWeight());
				bookToUpdate.setListPrice(book.getListPrice());
				bookToUpdate.setOurPrice(book.getOurPrice());
				bookToUpdate.setActive(book.isActive());
				bookToUpdate.setDescription(book.getDescription());
				bookToUpdate.setInStockNumber(book.getInStockNumber());
				bookToUpdate.setBookImage(book.getBookImage());
				
				bookUpdated = bookRepository.save(bookToUpdate);

			}
			
		} catch (Exception e) {
			LOG.error("Problem with loading data {} ", e.getMessage());
		}
		
		return bookUpdated;
			
	}
	
	/**
	 * Removes a book for the given title.
	 * 
	 * @param title The title to look for the book object
	 */
	public Set<Book> blurrySearch(String title) {
		
		Set<Book> activeBookListByTitle = null;
		
		try {
			
			Set<Book> bookListByTitle = bookRepository.findAllByTitle(title);
			
			activeBookListByTitle = new HashSet<>();
			
			for (Book book : bookListByTitle) {
				if(book.isActive()){
					activeBookListByTitle.add(book);
				}
			}

		} catch (Exception e) {
			LOG.error("Problem with loading data {} ", e.getMessage());
		}
		
		return activeBookListByTitle;
		
	}	
	
	/**
	 * Removes a book for the given id.
	 * 
	 * @param id The id to look for the book object
	 */
	public void removeOneBook(Integer id) {
		
		try {
			
			bookRepository.delete(id);
				
		} catch (Exception e) {
			LOG.error("Problem with loading data {} ", e.getMessage());
		}
		
	}
	
}
