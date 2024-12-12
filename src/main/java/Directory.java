import dao.*;
import model.Employee;
import model.WorkRole;
import utility.JDBCUtil;
import utility.LoggerUtil;
import utility.PrintUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Directory {
    EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    EmployeeService employeeService = new EmployeeService(employeeDAO);
    WorkRoleDAO workRoleDAO = new WorkRoleDAOImpl();
    WorkRoleService workRoleService = new WorkRoleService(workRoleDAO);
    Scanner scanner = new Scanner(System.in);

    Employee user;
    WorkRole workRole;

    Boolean run = true;
    boolean loggedIn = false;

    public void start() {
        while (run) {
            if (loggedIn) {
                run = processInput(user);
            } else {
                login();
            }
        }
    }

    public void login() {
        System.out.println("""
                Slovenia Auto Factory A&B
                Login:""");
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        try (Connection conn = JDBCUtil.getConnection()) {
            AuthService authService = new AuthService(conn);
            if (authService.authenticate(email, password)) {
                System.out.println("Login successful");
                loggedIn = true;
                user = employeeDAO.getEmployee(email);
                start();
            } else {
                System.out.println("Wrong email or password");
            }

        } catch (SQLException e) {
            LoggerUtil.logError("Error logging in", e);
        }
    }

    private void options() {
        System.out.println("""
                1. Edit roles
                2. Edit employees
                """);
    }

    public boolean processInput(Employee user) {
        if (user.getWorkRole().getTitle().equals("Admin")) {
            switch (scanner.nextInt()) {
                case 1 -> {
                    PrintUtil.optionPrintRole();

                }
            }
        }
        return true;
    }


    public String getUserInput() {
        return scanner.nextLine();
    }
}
