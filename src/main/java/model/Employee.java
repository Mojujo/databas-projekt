package model;

import java.util.Objects;

public class Employee {
    private int employeeId;
    private String name;
    private String email;
    private String password;
    private WorkRole workRole;

    public Employee(int employeeId, String name, String email, String password, WorkRole workRole) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.workRole = workRole;
    }

    public Employee(String name, String email, String password, WorkRole workRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.workRole = workRole;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public WorkRole getWorkRole() {
        return workRole;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWorkRole(WorkRole workRole) {
        this.workRole = workRole;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId && Objects.equals(name, employee.name) && Objects.equals(email, employee.email) && Objects.equals(password, employee.password) && Objects.equals(workRole, employee.workRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, name, email, password, workRole);
    }

    @Override
    public String toString() {
        return "model.Employee{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", workRole=" + workRole +
                '}';
    }
}
