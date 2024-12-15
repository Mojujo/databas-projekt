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
    Employee employee;
    WorkRole workRole;

    String menuEdit;
    String workTitle;
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
        if (menuEdit == null) {
            if (user.getWorkRole().getRoleId() == 0) {
                PrintUtil.optionPrintAdmin();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1 -> {
                        menuEdit = "role";
                        System.out.println("Editing roles.");
                    }
                    case 2 -> {
                        menuEdit = "employee";
                        System.out.println("Editing employees.");
                    }
                    case 3 -> {
                        loggedIn = false;
                        return true; // Return to log in
                    }
                    case 4 -> {
                        return false; // Exit the application
                    }
                }
            } else {
                PrintUtil.optionPrintProfile();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1 -> employeeService.findEmployee(user.getEmail(), user);
                    case 2 -> {
                        loggedIn = false;
                        return true; // Return to log in
                    }
                    case 3 -> {
                        return false; // Exit the application
                    }
                }
            }
        }

        if ("role".equals(menuEdit)) {
            processInputRole();
        } else if ("employee".equals(menuEdit)) {
            processInputEmployee();
        }
        return true;
    }

    public void processInputRole() {
        PrintUtil.optionPrintRole();
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1 -> workRoleService.verifyWorkRole(createWorkRole());

            case 2 -> workRoleService.verifyUpdateRole(updateWorkRole(), workTitle);

            case 3 -> {
                System.out.println("Enter role ID to delete: ");
                workRoleService.deleteRole(scanner.nextInt());
                scanner.nextLine(); // Consume newline character
            }
            case 4 -> {
                System.out.println("Enter role title: ");
                workRoleService.findWorkRole(scanner.nextLine(), user);
            }
            case 5 -> workRoleService.listAllWorkRoles();

            case 6 -> menuEdit = null;
        }
    }

    public void processInputEmployee() {
        PrintUtil.optionPrintEmployee();
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1 -> employeeService.verifyEmployee(createEmployee());

            case 2 -> employeeService.verifyUpdateEmployee(updateEmployee());
            case 3 -> {
                System.out.println("Enter employee ID to delete: ");
                employeeService.deleteEmployee(scanner.nextInt());
                scanner.nextLine(); // Consume newline character
            }
            case 4 -> {
                System.out.println("Enter employee Email: ");
                employeeService.findEmployee(scanner.nextLine(), user);
            }
            case 5 -> employeeService.listAllEmployees();

            case 6 -> menuEdit = null;
        }
    }

    private Employee createEmployee() {
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
        System.out.println("Which employee do you want to update? (Email) ");
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

    public WorkRole createWorkRole() {
        System.out.println("Enter title: ");
        String title = scanner.nextLine();

        System.out.println("Enter description: ");
        String description = scanner.nextLine();

        System.out.println("Enter salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character

        System.out.println("Enter date: ");
        String date = scanner.nextLine();

        workRole = new WorkRole(title, description, salary, date);

        return workRole;
    }

    public WorkRole updateWorkRole() {
        System.out.println("Which role do you want to update? (Title) ");
        workTitle = scanner.nextLine();
        workRole = workRoleDAO.getRole(workTitle);
        System.out.println("Leave input blank to skip edit");

        System.out.println("Enter new title: ");
        workRole.setTitle(scanner.nextLine());

        System.out.println("Enter new description: ");
        workRole.setDescription(scanner.nextLine());

        System.out.println("Enter new salary: ");
        String input = scanner.nextLine();
        double salary;

        if (input.isEmpty()) {
            salary = 0;
        } else {
            try {
                salary = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                LoggerUtil.logError("Error parsing salary", e);
                salary = 0;
            }
        }
        workRole.setSalary(salary);

        return workRole;
    }
}
