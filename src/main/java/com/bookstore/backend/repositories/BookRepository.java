package com.bookstore.backend.repositories;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.backend.domain.Book;

@Repository
@Transactional
public interface BookRepository extends CrudRepository<Book, Integer> {

	Book findOneByTitle(String title);
	
	Set<Book> findAllByTitle(String title);

	Book findOneByAuthor(String author);
	
	Set<Book> findAllByAuthor(String author);
	
	Book findOneByPublisher(String publisher);
	
	Set<Book> findAllByPublisher(String publisher);
	
	Book findOneByPublicationDate(Date publicationDate);
	
	Set<Book> findAllByPublicationDate(Date publicationDate);
    
	Set<Book> findAll();
	
    @Modifying // Indicates to JPA engine that the content of the @Query annotation will change the database state
    @Query("update Book book set book.title =:title where book.id =:bookId")
    void updateBookTitle(@Param("bookId") int bookId, @Param("title") String title);
    
    void removeBookById(Integer id);
}
