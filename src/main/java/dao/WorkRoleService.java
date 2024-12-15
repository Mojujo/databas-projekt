package dao;

import model.Employee;
import model.WorkRole;
import utility.JDBCUtil;
import utility.LoggerUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

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
        } else {
            LoggerUtil.logWarning("Cannot use existing work-title");
        }
    }

    // Checks for a positive salary
    private void addWorkRole(WorkRole workRole) {
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

    public void verifyUpdateRole(WorkRole workRole, String title) {
        WorkRole existingWorkRole = workRoleDAO.getRole(title);
        if (existingWorkRole != null) {
            if (!Objects.equals(workRole.getTitle(), "")) {
                existingWorkRole.setTitle(workRole.getTitle());
            }

            if (!Objects.equals(workRole.getDescription(), "")) {
                existingWorkRole.setDescription(workRole.getDescription());
            }

            if (workRole.getSalary() != 0) {
                existingWorkRole.setSalary(workRole.getSalary());
            }

            try (Connection conn = JDBCUtil.getConnection()) {
                workRoleDAO.updateRole(existingWorkRole, conn);

            } catch (SQLException e) {
                LoggerUtil.logError("Error verifying updated role", e);
            }
        }
    }

    public void deleteRole(int id) {
        try (Connection conn = JDBCUtil.getConnection()) {
            workRoleDAO.deleteRole(id, conn);
        } catch (SQLException e) {
            LoggerUtil.logError("Service error deleting role", e);
        }
    }

    public void findWorkRole(String title, Employee user) {
        WorkRole workRole = workRoleDAO.getRole(title);
        if (workRole != null) {
            if (user.getWorkRole().getTitle().equals("Admin")) {
                System.out.println("Admin user, showing all information");
                System.out.println(workRole);
            } else {
                System.out.println(
                        "[" + "Title= " + workRole.getTitle() +
                                ", Description= " + workRole.getDescription() +
                                ", Salary= " + workRole.getSalary() +
                                ", Creation date= " + workRole.getCreationDate() + "]");
            }
        }
    }

    public void listAllWorkRoles() {
        List<WorkRole> workRoles = workRoleDAO.getRoles();
        for (WorkRole workRole : workRoles) {
            System.out.println(workRole + "\n");
        }
    }
}
