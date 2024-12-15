package model;

import java.sql.Date;
import java.util.Objects;

public class WorkRole {
    private int roleId;
    private String title;
    private String description;
    private double salary;
    private Date creationDate;

    public WorkRole(int roleId, String title, String description, double salary, Date creationDate) {
        this.roleId = roleId;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.creationDate = creationDate;
    }

    public WorkRole(String title, String description, double salary, String creationDate) {
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.creationDate = java.sql.Date.valueOf(creationDate);
    }

    public int getRoleId() {
        return roleId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getSalary() {
        return salary;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkRole workRole = (WorkRole) o;
        return roleId == workRole.roleId && Double.compare(salary, workRole.salary) == 0 && Objects.equals(title, workRole.title) && Objects.equals(description, workRole.description) && Objects.equals(creationDate, workRole.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, title, description, salary, creationDate);
    }

    @Override
    public String toString() {
        return "\n" + "[" + "RoleId=" + roleId + ", Title= " + title + ", Description= " + description + ", Salary= " + salary + ", Creation-Date= " + creationDate + "]";
    }
}
