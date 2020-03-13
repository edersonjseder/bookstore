package com.bookstore.integration.test.book;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.bookstore.backend.domain.Book;

public class TheBook {
	
	private Integer id;
	private String title;
	private String author;
	private String publisher;
	private Date publicationDate;
	private String language;
	private String category;
	private Integer numberOfPages;
	private String format;
	private String isbn;
	private Double shippingWeight;
	private Double listPrice;
	private Double ourPrice;
	private boolean active = true;
    private String description;
    private Integer inStockNumber;
    private MultipartFile bookImage;

	private TheBook() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getPublisher() {
		return publisher;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public String getLanguage() {
		return language;
	}

	public String getCategory() {
		return category;
	}

	public Integer getNumberOfPages() {
		return numberOfPages;
	}

	public String getFormat() {
		return format;
	}

	public String getIsbn() {
		return isbn;
	}

	public Double getShippingWeight() {
		return shippingWeight;
	}

	public Double getListPrice() {
		return listPrice;
	}

	public Double getOurPrice() {
		return ourPrice;
	}

	public boolean isActive() {
		return active;
	}

	public String getDescription() {
		return description;
	}

	public Integer getInStockNumber() {
		return inStockNumber;
	}

	public MultipartFile getBookImage() {
		return bookImage;
	}

	//Builder Class
	public static class BookBuilder {

		private Integer id;
		private String title;
		private String author;
		private String publisher;
		private Date publicationDate;
		private String language;
		private String category;
		private Integer numberOfPages;
		private String format;
		private String isbn;
		private Double shippingWeight;
		private Double listPrice;
		private Double ourPrice;
		private boolean active = true;
	    private String description;
	    private Integer inStockNumber;
	    private MultipartFile bookImage;

		public BookBuilder(Integer id) {
			this.id = id;
		}
		
		public BookBuilder setTitle(String title) {
			this.title = title;
			return this;
		}

		public BookBuilder setAuthor(String author) {
			this.author = author;
			return this;
		}

		public BookBuilder setPublisher(String publisher) {
			this.publisher = publisher;
			return this;
		}

		public BookBuilder setPublicationDate(Date publicationDate) {
			this.publicationDate = publicationDate;
			return this;
		}

		public BookBuilder setLanguage(String language) {
			this.language = language;
			return this;
		}

		public BookBuilder setCategory(String category) {
			this.category = category;
			return this;
		}

		public BookBuilder setNumberOfPages(Integer numberOfPages) {
			this.numberOfPages = numberOfPages;
			return this;
		}

		public BookBuilder setFormat(String format) {
			this.format = format;
			return this;
		}

		public BookBuilder setIsbn(String isbn) {
			this.isbn = isbn;
			return this;
		}		

		public BookBuilder setShippingWeight(double shippingWeight) {
			this.shippingWeight = shippingWeight;
			return this;
		}		

		public BookBuilder setListPrice(double listPrice) {
			this.listPrice = listPrice;
			return this;
		}		

		public BookBuilder setOurPrice(double ourPrice) {
			this.ourPrice = ourPrice;
			return this;
		}		

		public BookBuilder setDescription(String description) {
			this.description = description;
			return this;
		}

		public BookBuilder setInStockNumber(Integer inStockNumber) {
			this.inStockNumber = inStockNumber;
			return this;
		}
		
		public BookBuilder setActive(boolean active) {
			this.active = active;
			return this;
		}

		public BookBuilder setBookImage(MultipartFile bookImage) {
			this.bookImage = bookImage;
			return this;
		}
		
		public Book build() {
			
			Book book = new Book();
			
			book.setId(this.id);
			book.setTitle(this.title);
			book.setAuthor(this.author);
			book.setPublisher(this.publisher);
			book.setPublicationDate(this.publicationDate);
			book.setLanguage(this.language);
			book.setCategory(this.category);
			book.setNumberOfPages(this.numberOfPages);
			book.setFormat(this.format);
			book.setIsbn(this.isbn);
			book.setShippingWeight(this.shippingWeight);
			book.setListPrice(this.listPrice);
			book.setOurPrice(this.ourPrice);
			book.setActive(this.active);
			book.setDescription(this.description);
			book.setInStockNumber(this.inStockNumber);
			book.setBookImage(this.bookImage);
			
			return book;

		}

	}
	 
}
