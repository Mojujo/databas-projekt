package dao;

import model.WorkRole;
import utility.JDBCUtil;
import utility.LoggerUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkRoleDAOImpl implements WorkRoleDAO {

    @Override
    public void insertRole(WorkRole workRole, Connection conn) {
        String sql = """
                INSERT INTO WORK_ROLE (title, description, salary, creation_date)
                VALUES (?, ?, ?, ?);
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            statementParameter(workRole, pstmt);
            pstmt.executeUpdate();
            JDBCUtil.commit(conn);

        } catch (SQLException e) {
            LoggerUtil.logError("Error inserting work role", e);
            JDBCUtil.rollback(conn);
        }
    }

    @Override
    public void updateRole(WorkRole workRole, Connection conn) {
        String sql = """
                UPDATE WORK_ROLE SET TITLE = ?, DESCRIPTION = ?, salary = ?, creation_date = ?
                WHERE ROLE_ID = ?;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            statementParameter(workRole, pstmt);
            pstmt.setInt(5, workRole.getRoleId());
            pstmt.executeUpdate();
            JDBCUtil.commit(conn);

        } catch (SQLException e) {
            LoggerUtil.logError("Error updating work role", e);
            JDBCUtil.rollback(conn);
        }
    }

    private void statementParameter(WorkRole workRole, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, workRole.getTitle());
        pstmt.setString(2, workRole.getDescription());
        pstmt.setDouble(3, workRole.getSalary());
        pstmt.setDate(4, workRole.getCreationDate());
    }

    @Override
    public void deleteRole(int id, Connection conn) {
        String sql = """
                DELETE FROM WORK_ROLE
                WHERE ROLE_ID = ?;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            JDBCUtil.commit(conn);

        } catch (SQLException e) {
            LoggerUtil.logError("Error deleting work role", e);
            JDBCUtil.rollback(conn);
        }
    }

    @Override
    public WorkRole getRole(String title) {
        WorkRole workRole = null;
        String sql = """
                SELECT * FROM WORK_ROLE
                WHERE TITLE = ?;
                """;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                workRole = new WorkRole(
                        rs.getInt("role_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("salary"),
                        rs.getDate("creation_date")
                );
            }
        } catch (SQLException e) {
            LoggerUtil.logError("Error getting work role", e);
        }
        return workRole;
    }

    @Override
    public List<WorkRole> getRoles() {
        List<WorkRole> Roles = new ArrayList<>();
        String sql = """
                SELECT * FROM WORK_ROLE
                """;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                WorkRole workrole = new WorkRole(
                        rs.getInt("role_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("salary"),
                        rs.getDate("creation_date")
                );
                Roles.add(workrole);
            }

        } catch (SQLException e) {
            LoggerUtil.logError("Error listing work roles", e);
        }
        return Roles;
    }
}
