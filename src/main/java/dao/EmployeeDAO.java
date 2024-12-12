package dao;

import model.Employee;

import java.sql.Connection;
import java.util.List;

public interface EmployeeDAO {
    void insertEmployee(Employee employee, Connection conn);

    void updateEmployee(Employee employee, Connection conn);

    void deleteEmployee(int id, Connection conn);

    Employee getEmployee(String email);

    List<Employee> getEmployees();
}
