package com.synaptix.isp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlanDAO {
    public static ResultSet getAllPlans(Connection conn) throws Exception {
        String sql = "select Id, PlanName, Speed, Cost, Duration from plan";
        PreparedStatement pst = conn.prepareStatement(sql);
        return pst.executeQuery();
    }

    public static void addPlan(Connection conn, String id, String name, String speed, String cost, String duration) throws Exception {
        String sql = "INSERT INTO plan(Id, PlanName, Speed, Cost, Duration) VALUES (?,?,?,?,?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, Integer.parseInt(id));
            pst.setString(2, name);
            pst.setString(3, speed);
            pst.setInt(4, Integer.parseInt(cost));
            pst.setString(5, duration);
            pst.execute();
        }
    }

    public static void deletePlan(Connection conn, String planName) throws Exception {
        String sql = "DELETE FROM plan WHERE PlanName=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, planName);
            pst.execute();
        }
    }

    public static ResultSet getPlanByName(Connection conn, String planName) throws Exception {
        String sql = "SELECT Speed, Cost, Duration FROM plan WHERE PlanName=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, planName);
        return pst.executeQuery();
    }
}
