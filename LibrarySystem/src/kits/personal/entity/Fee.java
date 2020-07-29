package kits.personal.entity;

public class Fee {
	private int id;
	private String name;
	private int userId;
	private String tranDate;
	private float mount;
	public Fee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Fee(String name, int userId, float mount) {
		super();
		this.name = name;
		this.userId = userId;
		this.mount = mount;
	}
	public int getId() {
		return id;
	}
	public float getMount() {
		return mount;
	}
	public void setMount(float mount) {
		this.mount = mount;
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	
	
	
}
