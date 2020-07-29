package kits.personal.repository;

import java.sql.Date;
import java.util.List;

import kits.personal.dto.LoanDto;
import kits.personal.entity.Loan;

public interface LoanRepository {
	List<Loan> findAll();
	List<LoanDto> findByUser(int id);
	int add(Loan loan);
	int update(Loan loan);
	int deActivate (int bookId, Date returnDate);
}
