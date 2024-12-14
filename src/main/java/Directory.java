import dao.*;
import model.Employee;
import model.WorkRole;
import utility.JDBCUtil;
import utility.LoggerUtil;
import utility.PrintUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Directory {
    EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    EmployeeService employeeService = new EmployeeService(employeeDAO);
    WorkRoleDAO workRoleDAO = new WorkRoleDAOImpl();
    WorkRoleService workRoleService = new WorkRoleService(workRoleDAO);
    Scanner scanner = new Scanner(System.in);

    Employee user;
    Employee employee;
    WorkRole workRole;

    String menuEdit;
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
        PrintUtil.welcomeMessage();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        try (Connection conn = JDBCUtil.getConnection()) {
            AuthService authService = new AuthService(conn);
            if (authService.authenticate(email, password)) {
                user = employeeDAO.getEmployee(email);
                loggedIn = true;
                System.out.println("Login successful, welcome " + user.getName());
                start();
            } else {
                System.out.println("Wrong email or password");
            }

        } catch (SQLException e) {
            LoggerUtil.logError("Error logging in", e);
        }
    }

    private void options() {
        if (menuEdit == null) {
            PrintUtil.optionPrintAdmin();
            switch (scanner.nextInt()) {
                case 1 -> {
                    menuEdit = "role";
                    System.out.println("Editing roles.");
                }
                case 2 -> {
                    menuEdit = "employee";
                    System.out.println("Editing employees.");
                }
            }
        }
    }

    public boolean processInput(Employee user) {
        if (user.getWorkRole().getTitle().equals("Admin")) {
            options();
            if (Objects.equals(menuEdit, "role")) {
                PrintUtil.optionPrintRole();
                switch (scanner.nextInt()) {
                    case 1 -> {
                    }
                    case 2 -> {

                    }
                    case 3 -> {
                    }
                    case 4 -> {
                    }
                    case 5 -> {

                    }
                    case 6 -> menuEdit = null;

                    case 7 -> {
                        return false;
                    }

                }
            } else if (Objects.equals(menuEdit, "employee")) {
                PrintUtil.optionPrintEmployee();
                switch (scanner.nextInt()) {
                    case 1 -> {
                        employeeService.verifyEmployee(createEmployee());
                    }
                    case 2 -> {
                        employeeService.verifyUpdateEmployee(updateEmployee());
                    }
                    case 3 -> {
                    }
                    case 4 -> {
                    }
                    case 5 -> {
                    }
                    case 6 -> menuEdit = null;
                    case 7 -> {
                        return false;
                    }
                }
            }
        } else {
            PrintUtil.optionPrintProfile();
            switch (scanner.nextInt()) {
                case 1 -> {

                }
            }
        }
        return true;
    }

    private Employee createEmployee() {
        scanner.nextLine();

        System.out.println("Enter name: ");
        String name = scanner.nextLine();

        System.out.println("Enter email: ");
        String email = scanner.nextLine();

        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        System.out.println("Enter role: ");
        String role = scanner.nextLine();
        workRole = workRoleDAO.getRole(role);

        employee = new Employee(name, email, password, workRole);

        return employee;
    }

    public Employee updateEmployee() {
        scanner.nextLine();

        System.out.println("Which employee do you want to update? (Email): ");
        employee = employeeDAO.getEmployee(scanner.nextLine());
        System.out.println("Leave input blank to skip edit");
        System.out.println("Enter new name: ");
        employee.setName(scanner.nextLine());
        System.out.println("Enter new password: ");
        employee.setPassword(scanner.nextLine());
        System.out.println("Enter new role: ");
        String role = scanner.nextLine();
        workRole = workRoleDAO.getRole(role);
        employee.setWorkRole(workRole);

        return employee;
    }
}
