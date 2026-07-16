package com.synaptix.isp;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DAOTest {

    private Connection conn;

    @BeforeEach
    public void setUp() throws Exception {
        conn = javaconnect.ConnecrDb();
        Assumptions.assumeTrue(conn != null, "Database connection not available; skipping DAO unit tests.");
        // Use transaction rollback to ensure tests don't modify database state permanently
        conn.setAutoCommit(false);
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (conn != null) {
            conn.rollback();
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    @Test
    public void testPlanDAOOperations() throws Exception {
        // 1. Add plan
        PlanDAO.addPlan(conn, "999", "JUnitTestPlan", "150", "999", "12");

        // 2. Fetch and assert plan details
        try (ResultSet rs = PlanDAO.getPlanByName(conn, "JUnitTestPlan")) {
            Statement stmt = rs.getStatement();
            assertTrue(rs.next(), "Plan should exist");
            assertEquals("150 Mbps", rs.getString("Speed") + " Mbps");
            assertEquals(999, rs.getInt("Cost"));
            assertEquals("12 Months", rs.getString("Duration") + " Months");
            if (stmt != null) stmt.close();
        }

        // 3. Delete plan
        PlanDAO.deletePlan(conn, "JUnitTestPlan");

        // 4. Verify plan no longer exists
        try (ResultSet rs = PlanDAO.getPlanByName(conn, "JUnitTestPlan")) {
            Statement stmt = rs.getStatement();
            assertFalse(rs.next(), "Plan should be deleted");
            if (stmt != null) stmt.close();
        }
    }

    @Test
    public void testCustomerDAOOperations() throws Exception {
        // 1. Create a prerequisite plan
        PlanDAO.addPlan(conn, "999", "JUnitTestPlan", "150", "999", "12");

        // 2. Add customer
        CustomerDAO.addCustomer(
            conn, 
            "999", 
            "JUnit Customer", 
            "99999999", 
            "MALE", 
            "INDIVIDUAL", 
            "123 JUnit Street", 
            "JUnitTestPlan", 
            "junit@example.com"
        );

        // 3. Verify customer exists
        boolean found = false;
        try (ResultSet rs = CustomerDAO.getAllCustomers(conn)) {
            Statement stmt = rs.getStatement();
            while (rs.next()) {
                if (rs.getInt("ID") == 999) {
                    found = true;
                    assertEquals("JUnit Customer", rs.getString("Name"));
                    assertEquals("99999999", rs.getString("Contact"));
                    assertEquals("MALE", rs.getString("Sex"));
                    assertEquals("INDIVIDUAL", rs.getString("Purpose"));
                    assertEquals("123 JUnit Street", rs.getString("Address"));
                    assertEquals("JUnitTestPlan", rs.getString("Plan"));
                    assertEquals("junit@example.com", rs.getString("email"));
                    break;
                }
            }
            if (stmt != null) stmt.close();
        }
        assertTrue(found, "Customer should be added");

        // 4. Update customer plan
        CustomerDAO.updateCustomerPlan(conn, "JUnitTestPlanUpdated", "99999999");

        // 5. Update customer email
        CustomerDAO.updateCustomerEmail(conn, "junit_updated@example.com", "999");

        // 6. Delete customer
        CustomerDAO.deleteCustomer(conn, "999");
    }

    @Test
    public void testEmployeeDAOOperations() throws Exception {
        // 1. Add employee
        EmployeeDAO.addEmployee(conn, "999", "JUnit Employee", "88888888", "2026-07-16", "456 JUnit Rd");

        // 2. Verify employee details
        boolean found = false;
        try (ResultSet rs = EmployeeDAO.getAllEmployees(conn)) {
            Statement stmt = rs.getStatement();
            while (rs.next()) {
                if (rs.getInt("ID") == 999) {
                    found = true;
                    assertEquals("JUnit Employee", rs.getString("Name"));
                    assertEquals("88888888", rs.getString("Contact"));
                    assertEquals("2026-07-16", rs.getString("JoinDate"));
                    assertEquals("456 JUnit Rd", rs.getString("Address"));
                    assertNull(rs.getString("LeaveDate"));
                    break;
                }
            }
            if (stmt != null) stmt.close();
        }
        assertTrue(found, "Employee should be added");

        // 3. Update employee leave date
        EmployeeDAO.updateEmployeeLeaveDate(conn, "2026-08-16", "999");

        // 4. Delete employee
        EmployeeDAO.deleteEmployee(conn, "999");
    }

    @Test
    public void testComplaintDAOOperations() throws Exception {
        // 1. Add complaint
        ComplaintDAO.addComplaint(conn, "999", "JUnit Connectivity Issue", "2026-07-16");

        // 2. Verify complaint details
        boolean found = false;
        try (ResultSet rs = ComplaintDAO.getAllComplaints(conn)) {
            Statement stmt = rs.getStatement();
            while (rs.next()) {
                if (rs.getInt("ID") == 999) {
                    found = true;
                    assertEquals("JUnit Connectivity Issue", rs.getString("Complaint"));
                    assertEquals("2026-07-16", rs.getString("Date"));
                    assertNull(rs.getString("Action"));
                    break;
                }
            }
            if (stmt != null) stmt.close();
        }
        assertTrue(found, "Complaint should be added");

        // 3. Solve complaint
        ComplaintDAO.solveComplaint(conn, "999");

        // 4. Verify status is solved
        found = false;
        try (ResultSet rs = ComplaintDAO.getAllComplaints(conn)) {
            Statement stmt = rs.getStatement();
            while (rs.next()) {
                if (rs.getInt("ID") == 999) {
                    found = true;
                    assertEquals("Solved", rs.getString("Action"));
                    break;
                }
            }
            if (stmt != null) stmt.close();
        }
        assertTrue(found, "Complaint should be solved");
    }
}
