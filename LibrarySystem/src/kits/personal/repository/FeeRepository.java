package kits.personal.repository;

import java.util.List;

import kits.personal.dto.FeeDto;
import kits.personal.entity.Fee;

public interface FeeRepository {
	int add(Fee fee);
	List<FeeDto> findAll();
	int update(Fee fee);
	int delete(int id);
}	
