package com.fidelity.model;

import java.util.Objects;

public class Book {
	private String isbn;
	private String author;
	private double price;
	private String title;
		
	public Book(String isbn, String author, double price, String title) {
		this.isbn = isbn;
		this.author = author;
		this.price = price;
		this.title = title;
	}
	
	public String getIsbn() {
		return isbn;
	}
	public String getAuthor() {
		return author;
	}
	public double getPrice() {
		return price;
	}
	public String getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, isbn, price, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && Objects.equals(isbn, other.isbn)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(title, other.title);
	}
	
	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", author=" + author + ", price=" + price + ", title=" + title + "]";
	}
	
}
