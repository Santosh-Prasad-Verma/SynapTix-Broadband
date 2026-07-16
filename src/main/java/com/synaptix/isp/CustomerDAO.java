package com.synaptix.isp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerDAO {
    public static ResultSet getAllCustomers(Connection conn) throws Exception {
        String sql = "SELECT ID, Name, Contact, Sex, Purpose, Address, Plan, email FROM customer";
        PreparedStatement pst = conn.prepareStatement(sql);
        return pst.executeQuery();
    }

    public static void addCustomer(Connection conn, String id, String name, String contact, String sex, String purpose, String address, String plan, String email) throws Exception {
        String sql = "INSERT INTO customer(ID, Name, Contact, Sex, Purpose, Address, Plan, email) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, Integer.parseInt(id));
            pst.setString(2, name);
            pst.setString(3, contact);
            pst.setString(4, sex);
            pst.setString(5, purpose);
            pst.setString(6, address);
            pst.setString(7, plan);
            pst.setString(8, email);
            pst.execute();
        }
    }

    public static void updateCustomerPlan(Connection conn, String planName, String contact) throws Exception {
        String sql = "UPDATE customer SET Plan=? WHERE Contact=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, planName);
            pst.setString(2, contact);
            pst.execute();
        }
    }

    public static void updateCustomerEmail(Connection conn, String email, String id) throws Exception {
        String sql = "UPDATE customer SET email=? WHERE ID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, email);
            pst.setInt(2, Integer.parseInt(id));
            pst.execute();
        }
    }

    public static ResultSet searchCustomerById(Connection conn, String id) throws Exception {
        String sql = "SELECT ID, Name, Contact, Sex, Purpose, Address, Plan, email FROM customer WHERE ID=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, Integer.parseInt(id));
        return pst.executeQuery();
    }

    public static void deleteCustomer(Connection conn, String id) throws Exception {
        String sql = "DELETE FROM customer WHERE ID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, Integer.parseInt(id));
            pst.execute();
        }
    }
}
