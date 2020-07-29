package kits.personal.entity;

import java.sql.Date;

public class Loan {
	private Date loanDate;
	private int isActive;
	private int userId;
	private int bookId;
	private int id;
	public Loan() {
		super();
	}
	public Loan(Date loanDate, int isActive, int userId, int bookId) {
		super();
		this.loanDate = loanDate;
		this.isActive = isActive;
		this.userId = userId;
		this.bookId = bookId;
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	
}
