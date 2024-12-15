package dao;

import model.Employee;
import utility.JDBCUtil;
import utility.LoggerUtil;
import utility.PasswordUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class EmployeeService {

    private final EmployeeDAO employeeDAO;

    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    // VERIFIES EMAIL IS UNIQUE AND HASHES PASSWORD
    public void verifyEmployee(Employee employee) {
        checkUniqueEmail(employee);
    }

    // Checks for a valid and unique email
    private void checkUniqueEmail(Employee employee) {
        Employee uniqueEmployee = employeeDAO.getEmployee(employee.getEmail());
        if (uniqueEmployee == null) {
            addEmployeeWithHashedPassword(employee);
        } else {
            LoggerUtil.logWarning("Cannot use existing email");
        }
    }

    // Overwrites and hashes password before inserting employee to avoid plaintext password
    private void addEmployeeWithHashedPassword(Employee employee) {
        String hashedPassword = PasswordUtil.hashPassword(employee.getPassword());
        employee.setPassword(hashedPassword);

        try (Connection conn = JDBCUtil.getConnection()) {

            employeeDAO.insertEmployee(employee, conn);

        } catch (SQLException e) {
            LoggerUtil.logError("Error inserting hashed password", e);
        }
    }

    // VERIFIES EMPLOYEE UPDATES AND CHECKS FOR INPUT
    public void verifyUpdateEmployee(Employee employee) {
        Employee existingEmployee = employeeDAO.getEmployee(employee.getEmail());
        if (existingEmployee != null) {
            if (!Objects.equals(employee.getName(), "")) {
                existingEmployee.setName(employee.getName());
            }

            if (!Objects.equals(employee.getPassword(), "")) {
                existingEmployee.setPassword(PasswordUtil.hashPassword(employee.getPassword()));
            }

            if (employee.getWorkRole() != null) {
                existingEmployee.setWorkRole(employee.getWorkRole());
            }

            try (Connection conn = JDBCUtil.getConnection()) {
                employeeDAO.updateEmployee(existingEmployee, conn);

            } catch (SQLException e) {
                LoggerUtil.logError("Error verifying updated employee", e);
            }
        }
    }

    public void deleteEmployee(int id) {
        try (Connection conn = JDBCUtil.getConnection()) {
            employeeDAO.deleteEmployee(id, conn);
        } catch (SQLException e) {
            LoggerUtil.logError("Service error deleting employee", e);
        }
    }

    public void findEmployee(String email, Employee user) {
        Employee employee = employeeDAO.getEmployee(email);
        if (employee != null) {
            if (user.getWorkRole().getTitle().equals("Admin")) {
                System.out.println("Admin user, showing all information");
                System.out.println(employee);
            } else {
                System.out.println(
                        "[" + "Name= " + employee.getName() +
                                ", Email= " + employee.getEmail() +
                                ", Title= " + employee.getWorkRole().getTitle() +
                                ", Salary= " + employee.getWorkRole().getSalary() + "]");
            }
        }
    }

    public void listAllEmployees() {
        List<Employee> employees = employeeDAO.getEmployees();
        for (Employee employee : employees) {
            System.out.println(employee + "\n");
        }
    }
}
