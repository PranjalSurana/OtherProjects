package com.fidelity.business;

import java.util.Objects;

public class Book {
	private String title;
	private String author;
	private String cover;
	private long bookId;
	
	public Book() {}
	
	public Book(String title, String author, String cover, long bookId) {
		super();
		this.title = title;
		this.author = author;
		this.cover = cover;
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, bookId, cover, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Book)) {
			return false;
		}
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && bookId == other.bookId && Objects.equals(cover, other.cover)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", cover=" + cover + ", bookId=" + bookId + "]";
	}
	
	
}
