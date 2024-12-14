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
                run = options(user);
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

    private boolean options(Employee user) {
        if (user.getWorkRole().getTitle().equals("Admin")) {
            if (menuEdit == null) {
                PrintUtil.optionPrintAdmin();
                switch (scanner.nextInt()) {
                    case 1 -> {
                        menuEdit = "role";
                        System.out.println("Editing roles.");
                        processInputRole();
                    }
                    case 2 -> {
                        menuEdit = "employee";
                        System.out.println("Editing employees.");
                        processInputEmployee();
                    }
                    case 3 -> loggedIn = false;
                    case 4 -> {
                        return false;
                    }
                }
                scanner.nextLine();
            }
        } else {
            PrintUtil.optionPrintProfile();
            switch (scanner.nextInt()) {
                case 1 -> {

                }
                case 2 -> loggedIn = false;
                case 3 -> {
                    return false;
                }
            }
            scanner.nextLine();
        }
        return true;
    }

    public void processInputRole() {
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
        }
        scanner.nextLine();
    }

    public void processInputEmployee() {
        PrintUtil.optionPrintEmployee();
        switch (scanner.nextInt()) {
            case 1 -> employeeService.verifyEmployee(createEmployee());

            case 2 -> employeeService.verifyUpdateEmployee(updateEmployee());

            case 3 -> {
                System.out.println("Enter employee ID to delete: ");
                employeeService.deleteEmployee(scanner.nextInt());
                scanner.nextLine();
            }
            case 4 -> {
                System.out.println("Enter employee email: ");
                String email = scanner.nextLine();
                employeeService.findEmployee(email, user);
            }
            case 5 -> {
            }
            case 6 -> menuEdit = null;
        }
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
