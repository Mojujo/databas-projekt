package dao;

import model.WorkRole;
import utility.JDBCUtil;
import utility.LoggerUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class WorkRoleService {

    private final WorkRoleDAO workRoleDAO;

    public WorkRoleService(WorkRoleDAO workRoleDAO) {
        this.workRoleDAO = workRoleDAO;
    }

    // VERIFIES ROLE TITLE IS UNIQUE AND SALARY IS POSITIVE
    public void verifyWorkRole(WorkRole workRole) {
        checkUniqueTitle(workRole);
    }

    // Checks for a valid and unique title
    private void checkUniqueTitle(WorkRole workRole) {
        WorkRole uniqueWorkRole = workRoleDAO.getRole(workRole.getTitle());
        if (uniqueWorkRole == null) {
            addWorkRole(workRole);
        }
    }

    // Checks for a positive salary
    public void addWorkRole(WorkRole workRole) {
        if (workRole.getSalary() > 0) {
            try (Connection conn = JDBCUtil.getConnection()) {
                workRoleDAO.insertRole(workRole, conn);
            } catch (SQLException e) {
                LoggerUtil.logError("Error checking for positive salary", e);
            }
        } else {
            LoggerUtil.logWarning("Salary cannot be negative");
        }
    }
}
