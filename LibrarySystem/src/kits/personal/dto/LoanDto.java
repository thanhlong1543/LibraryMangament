package kits.personal.dto;

import java.sql.Date;

public class LoanDto {
	private int id;
	private Date loanDate;
	private int isActive;
	private String bookName;
	private String userName;
	public LoanDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoanDto(int id, Date loanDate, int isActive, String bookName, String userName) {
		super();
		this.id = id;
		this.loanDate = loanDate;
		this.isActive = isActive;
		this.bookName = bookName;
		this.userName = userName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
