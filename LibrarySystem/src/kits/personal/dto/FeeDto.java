package kits.personal.dto;

public class FeeDto {
	private int id;
	private String name;
	private String userName;
	private String tranDate;
	private float mount;
	
	public float getMount() {
		return mount;
	}
	public void setMount(float mount) {
		this.mount = mount;
	}
	public FeeDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FeeDto(int id, String name, String userName, String tranDate) {
		super();
		this.id = id;
		this.name = name;
		this.userName = userName;
		this.tranDate = tranDate;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	
	
}
