package kits.personal.entity;

public class Book {
	private int id;
	private String name;
	private int authorId;
	private int publisherId;
	private int available;
	public Book() {
		super();
	}
	public Book(String name, int authorId, int publisherId, int available) {
		super();
		this.name = name;
		this.authorId = authorId;
		this.publisherId = publisherId;
		this.available = available;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public int getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	
	
	
}
