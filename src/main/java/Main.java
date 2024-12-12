import dao.*;
import gui.LoginFrame;
import model.Employee;
import model.WorkRole;
import utility.JDBCUtil;
import utility.LoggerUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        SwingUtilities.invokeLater(() -> {
//            LoginFrame login = new LoginFrame();
//            login.setVisible(true);
//        });

        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        EmployeeService employeeService = new EmployeeService(employeeDAO);
        WorkRoleDAO workRoleDAO = new WorkRoleDAOImpl();
        WorkRoleService workRoleService = new WorkRoleService(workRoleDAO);
        Directory directory = new Directory();
        WorkRole workrole = new WorkRole("Service", "Repairs and maintenance", -18000, "2011-04-05");
        Employee employee = new Employee("Anna", "munk@gmail.com", "femhundra", workrole);

        workRoleService.addWorkRole(workrole);
    }
}
