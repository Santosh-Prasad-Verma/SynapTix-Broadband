package com.synaptix.isp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ComplaintDAO {
    public static ResultSet getAllComplaints(Connection conn) throws Exception {
        String sql = "SELECT ID, Complaint, Date, Action FROM complain";
        PreparedStatement pst = conn.prepareStatement(sql);
        return pst.executeQuery();
    }

    public static void addComplaint(Connection conn, String id, String complaintText, String date) throws Exception {
        if (id == null || id.trim().isEmpty()) {
            String sql = "INSERT INTO complain(Complaint, Date) VALUES (?,?)";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, complaintText);
                pst.setString(2, date);
                pst.execute();
            }
        } else {
            String sql = "INSERT INTO complain(ID, Complaint, Date) VALUES (?,?,?)";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, Integer.parseInt(id));
                pst.setString(2, complaintText);
                pst.setString(3, date);
                pst.execute();
            }
        }
    }

    public static void solveComplaint(Connection conn, String id) throws Exception {
        String sql = "UPDATE complain SET Action='Solved' WHERE ID=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, Integer.parseInt(id));
            pst.execute();
        }
    }
}
