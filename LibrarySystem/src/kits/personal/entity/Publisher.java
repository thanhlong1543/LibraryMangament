package kits.personal.entity;

public class Publisher {
	
	private int id;
	private String name;
	public Publisher() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Publisher(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
}
