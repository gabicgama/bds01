package com.devsuperior.bds01.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.entities.Employee;
import com.devsuperior.bds01.repositories.DepartmentRepository;
import com.devsuperior.bds01.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository repository;
	
	@Autowired
	DepartmentRepository departmentRepository;

	public Page<EmployeeDTO> findAll(Pageable pageable) {
		Page<Employee> pages = repository.findAll(pageable);
		return pages.map(x -> new EmployeeDTO(x));
	}
	
	@Transactional
	public EmployeeDTO insert(EmployeeDTO dto) {
		Employee entity = new Employee();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new EmployeeDTO(entity);
	}
	
	private void copyDtoToEntity(EmployeeDTO dto, Employee entity) {
		Department department = departmentRepository.getOne(dto.getDepartmentId());
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setDepartment(department);
	}
}
