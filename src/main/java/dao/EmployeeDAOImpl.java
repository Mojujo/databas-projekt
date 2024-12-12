package dao;

import model.Employee;
import model.WorkRole;
import utility.JDBCUtil;
import utility.LoggerUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public void insertEmployee(Employee employee, Connection conn) {
        String sql = """
                INSERT INTO EMPLOYEE (NAME, EMAIL, PASSWORD, ROLE_ID)
                VALUES (?, ?, ?, ?);
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            statementParameter(employee, pstmt);
            pstmt.executeUpdate();
            JDBCUtil.commit(conn);

        } catch (SQLException e) {
            LoggerUtil.logError("Error inserting employee", e);
            JDBCUtil.rollback(conn);
        }
    }

    @Override
    public void updateEmployee(Employee employee, Connection conn) {
        String sql = """
                UPDATE EMPLOYEE SET NAME = ?, EMAIL = ?, PASSWORD = ?, ROLE_ID = ?
                WHERE EMPLOYEE_ID = ?;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            statementParameter(employee, pstmt);
            pstmt.setInt(5, employee.getEmployeeId());
            pstmt.executeUpdate();
            JDBCUtil.commit(conn);

        } catch (SQLException e) {
            LoggerUtil.logError("Error updating employee", e);
            JDBCUtil.rollback(conn);
        }
    }

    private void statementParameter(Employee employee, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, employee.getName());
        pstmt.setString(2, employee.getEmail());
        pstmt.setString(3, employee.getPassword());
        pstmt.setInt(4, employee.getWorkRole().getRoleId());
    }

    @Override
    public void deleteEmployee(int id, Connection conn) {
        String sql = """
                DELETE FROM EMPLOYEE
                WHERE EMPLOYEE_ID = ?;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            JDBCUtil.commit(conn);

        } catch (SQLException e) {
            LoggerUtil.logError("Error deleting employee", e);
            JDBCUtil.rollback(conn);
        }
    }

    @Override
    public Employee getEmployee(String email) {
        Employee employee = null;
        String sql = """
                SELECT *
                FROM EMPLOYEE e
                LEFT JOIN WORK_ROLE r
                ON e.ROLE_ID = r.ROLE_ID
                WHERE e.EMAIL = ?;
                """;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        new WorkRole(
                                rs.getInt("role_id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getInt("salary"),
                                rs.getDate("creation_date"))
                );
            }
        } catch (SQLException e) {
            LoggerUtil.logError("Error getting employee", e);
        }
        return employee;
    }

    @Override
    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = """
                SELECT *
                FROM EMPLOYEE e
                LEFT JOIN WORK_ROLE r
                ON e.ROLE_ID = r.ROLE_ID
                """;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        new WorkRole(
                                rs.getInt("role_id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getInt("salary"),
                                rs.getDate("creation_date")
                        )
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            LoggerUtil.logError("Error listing employees", e);

        }
        return employees;
    }
}
