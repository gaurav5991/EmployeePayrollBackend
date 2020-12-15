package com.bridgelabz.employeepayrollapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.employeepayrollapp.dto.EmployeePayrollDTO;
import com.bridgelabz.employeepayrollapp.exception.EmployeePayrollException;
import com.bridgelabz.employeepayrollapp.model.EmployeePayrollData;
import com.bridgelabz.employeepayrollapp.repository.EmployeePayrollRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeePayrollService implements IEmployeePayrollService {
	
	@Autowired
	private EmployeePayrollRepository employeePayrollRepository;

	private List<EmployeePayrollData> employeePayrollList = new ArrayList<>();

	@Override
	public List<EmployeePayrollData> getEmployeePayrollData() {
//		empDataList.add(new EmployeePayrollData(1,new EmployeePayrollDTO("Gaurav", 30000)));
		return employeePayrollList;
	}

	@Override
	public EmployeePayrollData getEmployeePayrollDataById(int empId) {
//		EmployeePayrollData empData = null;
//		empData = new EmployeePayrollData(1,new EmployeePayrollDTO("Gaurav", 30000));
//		return empData;
		return employeePayrollList.stream().filter(empData -> empData.getEmployeeId() == empId).findFirst()
				.orElseThrow(() -> new EmployeePayrollException("Employee not found"));
	}

	@Override
	public EmployeePayrollData createEmployeePayrollData(EmployeePayrollDTO empPayrollDTO) {
		EmployeePayrollData empData = null;
//		empData = new EmployeePayrollData(1, empPayrollDTO);
		empData = new EmployeePayrollData(employeePayrollList.size() + 1, empPayrollDTO);
		log.debug("Emp Data : "+empData.toString());
		employeePayrollList.add(empData);
		return employeePayrollRepository.save(empData);
	}

	@Override
	public EmployeePayrollData updateEmployeePayrollData(int empId, EmployeePayrollDTO empPayrollDTO) {
//		EmployeePayrollData empData = null;
//		empData = new EmployeePayrollData(1, empPayrollDTO);
//		return empData;

		EmployeePayrollData empData = this.getEmployeePayrollDataById(empId);
		empData.setName(empPayrollDTO.name);
		empData.setSalary(empPayrollDTO.salary);
		employeePayrollList.set(empId - 1, empData);
		return empData;
	}

	@Override
	public void deleteEmployeePayrollData(int empId) {
		employeePayrollList.remove(empId - 1);
		employeePayrollRepository.deleteById(empId);
	}
}
