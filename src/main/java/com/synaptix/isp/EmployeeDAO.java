package com.synaptix.isp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeDAO {
    public static ResultSet getAllEmployees(Connection conn) throws Exception {
        String sql = "select ID, Name, Contact, JoinDate, Address, LeaveDate from employee";
        PreparedStatement pst = conn.prepareStatement(sql);
        return pst.executeQuery();
    }

    public static void addEmployee(Connection conn, String id, String name, String contact, String joinDate, String address) throws Exception {
        String sql = "INSERT INTO employee(ID, Name, Contact, JoinDate, Address) VALUES (?,?,?,?,?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, Integer.parseInt(id));
            pst.setString(2, name);
            pst.setString(3, contact);
            pst.setString(4, joinDate);
            pst.setString(5, address);
            pst.execute();
        }
    }

    public static void updateEmployeeLeaveDate(Connection conn, String leaveDate, String id) throws Exception {
        String sql = "UPDATE employee SET LeaveDate=? WHERE ID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, leaveDate);
            pst.setInt(2, Integer.parseInt(id));
            pst.execute();
        }
    }

    public static ResultSet searchEmployeeByContact(Connection conn, String contact) throws Exception {
        String sql = "SELECT ID, Name, Contact, JoinDate, Address, LeaveDate from employee WHERE Contact=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, contact);
        return pst.executeQuery();
    }

    public static void deleteEmployee(Connection conn, String id) throws Exception {
        String sql = "DELETE FROM employee WHERE ID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, Integer.parseInt(id));
            pst.execute();
        }
    }
}
