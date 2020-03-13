package com.bookstore.web.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.backend.domain.Book;
import com.bookstore.backend.service.BookService;
import com.bookstore.exceptions.KeywordNotFoundException;
import com.bookstore.utils.FileUploadUtils;
import com.bookstore.utils.Messages;
import com.bookstore.utils.RoutePathUtils;

@RestController
@RequestMapping(path = RoutePathUtils.PATH)
public class BookController {
	
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);
	
	/**
	 * Dependency Injection of Spring Framework
	 */
	@Autowired
	private BookService bookService;

    // =========================================== Add Book ==========================================	
	/**
	 * Method adds a book object in the database
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = RoutePathUtils.PATH_BOOK + 
							RoutePathUtils.PATH_ADD, method = RequestMethod.POST)
	public ResponseEntity<Book> addBookPost(@RequestBody Book book) {
		
		bookService.saveBook(book);
		
		return new ResponseEntity<Book>(book, HttpStatus.CREATED);
		
	}
	
    // =========================================== Upload Book Image ==========================================	
	@RequestMapping(value = RoutePathUtils.PATH_BOOK + 
							RoutePathUtils.PATH_ADD + 
							RoutePathUtils.PATH_IMAGE, method = RequestMethod.POST)
	public ResponseEntity<String> upload(@RequestParam("id") Integer id, 
								         HttpServletResponse response, 
								         HttpServletRequest request) {
		
		FileUploadUtils upload = new FileUploadUtils();
		
		try {
			
			Book book = bookService.findOneBook(id);
			
			String bookUrl = upload.storeImageBook(request, book.getTitle());
			
			LOG.info("Creating Images on temp folder {}", bookUrl);
			
			return new ResponseEntity<>("Upload success!", HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Upload Failed!", HttpStatus.BAD_REQUEST);
		}
	}

    // =========================================== Get All Books ==========================================	
	/**
	 * Method returns the list of books to the browser via JSON
	 * 
	 * @return
	 */
	@RequestMapping(value = RoutePathUtils.PATH_BOOK + 
			                RoutePathUtils.PATH_BOOK_ALL, method = RequestMethod.GET)
	public ResponseEntity<Set<Book>> getBookList() {
		
		Set<Book> books = bookService.findAll();
		
		if(books.isEmpty()){
			
			return new ResponseEntity<Set<Book>>(HttpStatus.NO_CONTENT);
			
		}
		
		return new ResponseEntity<Set<Book>>(books, HttpStatus.OK);
		
	}
	
    // =========================================== Update Book ==========================================	
	/**
	 * 
	 * Method updates a book object in the database
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = RoutePathUtils.PATH_BOOK +
							RoutePathUtils.PATH_UPDATE + "/{bookId}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Book> updateBookPost(@PathVariable Integer bookId, @RequestBody Book book) {

		Book bookUpdated = bookService.updateBookInformation(bookId, book);

		if (bookUpdated != null) {
			
			return new ResponseEntity<Book>(bookUpdated, HttpStatus.OK);
			
		} else {
			
			throw new KeywordNotFoundException(bookId, Messages.MESSAGE_UPDATE_FAIL);
			
		}
	}
	
    // =========================================== Delete Book by Id ==========================================	
	/**
	 * Method deletes a book object in the database
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = RoutePathUtils.PATH_BOOK +
							RoutePathUtils.PATH_DELETE + "/{bookId}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<?> deleteBook(@PathVariable Integer bookId) {

		try {
			
			bookService.removeOneBook(bookId);
			
		} catch (Exception e) {
			throw new KeywordNotFoundException(bookId, Messages.MESSAGE_DELETE_FAIL);
		}
		
		return ResponseEntity.ok().build();
		
	}
	
    // =========================================== Get Book by Id ==========================================	
	/**
	 * Method returns the book object by its id parameter
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = RoutePathUtils.PATH_BOOK + "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> getBookById(@PathVariable("id") Integer bookId) {

		Book book = bookService.findOneBook(bookId);
		
		if (book != null) {
		
			return new ResponseEntity<Book>(book, HttpStatus.OK);
			
		} else {
			
			throw new KeywordNotFoundException(bookId, Messages.MESSAGE_SEARCH_FAIL);
			
		}
		
	}

}
