package kits.personal.entity;

import java.sql.Date;

public class User {
	private int id;
	private String name;
	private String password;
	private String phone;
	private String idNum;
	private int roleId;
	private Date regDate;
	public User() {
		super();
	}

	public User(String name, String password, String phone, String idNum) {
		super();
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.idNum = idNum;
	}


	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getId() {
		return id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

}
