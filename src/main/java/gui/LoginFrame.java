package gui;

import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import model.Employee;
import utility.LoggerUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField userEmail;
    private JPasswordField passField;
    Employee employee;
    EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    public LoginFrame() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(250, 250);
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(5, 2, 0, 5));
        JLabel userNameLabel = new JLabel("Email : ");
        userEmail = new JTextField(20);
        userEmail.setPreferredSize(new Dimension(100, 30));

        JLabel passwordLabel = new JLabel("Password : ");
        passField = new JPasswordField(20);
        passField.setPreferredSize(new Dimension(100, 30));

        JButton loginButton = new JButton("Login");

        panel.add(userNameLabel);
        panel.add(userEmail);
        panel.add(passwordLabel);
        panel.add(passField);
        panel.add(loginButton);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }
}
