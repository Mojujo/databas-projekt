package dao;

import model.WorkRole;

import java.sql.Connection;
import java.util.List;

public interface WorkRoleDAO {
    void insertRole(WorkRole workRole, Connection conn);

    void updateRole(WorkRole workRole, Connection conn);

    void deleteRole(int id, Connection conn);

    WorkRole getRole(String title);

    List<WorkRole> getRoles();
}
