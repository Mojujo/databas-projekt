package dao;

import model.Employee;
import utility.JDBCUtil;
import utility.LoggerUtil;
import utility.PasswordUtil;

import java.sql.Connection;
import java.sql.SQLException;

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
}
