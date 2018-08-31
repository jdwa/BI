package com.ldchotels.athena.dao;

import java.util.Date;
import java.util.List;

import com.ldchotels.athena.model.Employee;

public interface EmployeeDao {
	public Employee findById_nos(String id_nos);
	/*--
	public Employee add(Employee employee);
	public Employee delete(String id_nos);
	public Employee detail(String id_nos);
	public Employee update(Employee employee);
	--*/
	public List<Employee> list();
	public List<Employee> list(Date birth_dat);
}
