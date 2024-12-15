package dao;

import model.WorkRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import utility.JDBCUtil;
import utility.LoggerUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkRoleServiceTest {
    WorkRoleDAO workRoleDAO = new WorkRoleDAOImpl();
    WorkRoleService workRoleService = new WorkRoleService(workRoleDAO);
    WorkRole workRole;
    List<WorkRole> workRoleList;

    @AfterEach
    public void cleanUp() {
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS WORK_ROLE");
        } catch (SQLException e) {
            LoggerUtil.logError("Error cleaning up test", e);
        }
        workRole = null;
        workRoleList = null;
    }

    @Test
    void insertWorkRoleTest() {
        workRole = new WorkRole("Test", "Test Description", 18000, "2001-01-01");

        try (Connection conn = JDBCUtil.getConnection()) {
            workRoleDAO.insertRole(workRole, conn);

        } catch (SQLException e) {
            LoggerUtil.logError("Error testing inserting work role", e);
        }

        workRoleList = workRoleDAO.getRoles();
        assertNotNull(workRoleList);
        assertFalse(workRoleList.isEmpty());
    }

    @Test
    void verifySalaryInsertWorkRoleTest() {
        workRole = new WorkRole("Test", "Test Description", -3000, "2001-01-01");
        workRoleService.verifyWorkRole(workRole);

        workRoleList = workRoleDAO.getRoles();
        assertNotNull(workRoleList);
        assertTrue(workRoleList.isEmpty());
    }
}
