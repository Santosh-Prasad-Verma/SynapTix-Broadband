package com.synaptix.isp;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hunter Fox
 */
public class Complaint extends javax.swing.JFrame {
Connection conn;
PreparedStatement pst;
ResultSet rs;
    public Complaint() {
        super("SynapTix Broadband - Complaints Support");
        initComponents();
        conn=javaconnect.ConnecrDb();
        setupCleanLayout();
        showDate();
        jTable1();
    }

    private void setupCleanLayout() {
        // 1. Reset main panel and set modern padding/BorderLayout
        jPanel1.removeAll();
        jPanel1.setLayout(new java.awt.BorderLayout(20, 15));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        // 2. Header Panel (North)
        javax.swing.JPanel headerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        headerPanel.setOpaque(false);
        
        // Header Left: Logo + Title
        javax.swing.JPanel headerLeft = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 12, 0));
        headerLeft.setOpaque(false);
        headerLeft.add(jLabel5); // Logo icon
        jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        jLabel1.setForeground(new java.awt.Color(33, 43, 54));
        jLabel1.setText("COMPLAINTS SUPPORT");
        headerLeft.add(jLabel1);
        headerPanel.add(headerLeft, java.awt.BorderLayout.WEST);
        
        // Header Right: Back Button
        javax.swing.JPanel headerRight = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 5));
        headerRight.setOpaque(false);
        btnBack.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        headerRight.add(btnBack);
        headerPanel.add(headerRight, java.awt.BorderLayout.EAST);
        
        jPanel1.add(headerPanel, java.awt.BorderLayout.NORTH);
        
        // 3. Left Panel (Form Panel wrapper)
        jPanel2.setBackground(java.awt.Color.WHITE);
        jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224), 1),
            javax.swing.BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        // Re-align elements inside jPanel2 using GridBagLayout for alignment
        jPanel2.removeAll();
        jPanel2.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(8, 8, 8, 8);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        
        // Row 0: ID
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        jPanel2.add(jLabel2, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        jPanel2.add(jTextField1, gbc);
        
        // Row 1: COMPLAINT DESCRIPTION
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        gbc.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(jLabel3, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        jPanel2.add(jScrollPane1, gbc);
        
        // Row 2: DATE
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        gbc.weighty = 0.0;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(jLabel4, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        jPanel2.add(jt1, gbc);
        
        // Row 3: Buttons
        javax.swing.JPanel btnPanel = new javax.swing.JPanel(new java.awt.GridLayout(1, 2, 10, 0));
        btnPanel.setOpaque(false);
        btnAddCom.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnSolve.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnPanel.add(btnAddCom);
        btnPanel.add(btnSolve);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.insets = new java.awt.Insets(15, 8, 8, 8);
        jPanel2.add(btnPanel, gbc);
        
        javax.swing.JPanel leftWrapper = new javax.swing.JPanel(new java.awt.BorderLayout());
        leftWrapper.setOpaque(false);
        leftWrapper.add(jPanel2, java.awt.BorderLayout.NORTH);
        
        // 4. Right Panel (Table panel)
        jPanel3.setBackground(java.awt.Color.WHITE);
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224), 1),
            "Open Support Tickets",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12),
            new java.awt.Color(120, 120, 120)
        ));
        jScrollPane2.setBorder(null);
        jPanel3.removeAll();
        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);
        
        // Combine Left and Right using SplitPane
        javax.swing.JSplitPane mainSplit = new javax.swing.JSplitPane(javax.swing.JSplitPane.HORIZONTAL_SPLIT, leftWrapper, jPanel3);
        mainSplit.setDividerLocation(310);
        mainSplit.setDividerSize(0);
        mainSplit.setBorder(null);
        mainSplit.setOpaque(false);
        leftWrapper.setOpaque(false);
        jPanel3.setOpaque(false);
        
        jPanel1.add(mainSplit, java.awt.BorderLayout.CENTER);
        
        jPanel1.revalidate();
        jPanel1.repaint();
    }
    
    void showDate(){
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
        jt1.setText(s.format(d));
    }
    
    public void jTable1(){
        try{
            rs = ComplaintDAO.getAllComplaints(conn);
            java.sql.Statement stmt = rs.getStatement();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            UIUtils.styleTable(jTable1);
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void Update(){
        final String ticketId = jTextField1.getText();
        final String ticketDesc = jTextArea1.getText();
        if (ticketId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a complaint first!");
            return;
        }

        try{
            ComplaintDAO.solveComplaint(conn, ticketId);
            JOptionPane.showMessageDialog(null, "Complaint updated successfully!");

            final String email = JOptionPane.showInputDialog(null, 
                "Enter customer's email to notify them of resolution (leave blank to skip):", 
                "Ticket Resolved", JOptionPane.QUESTION_MESSAGE);

            if (email != null && !email.trim().isEmpty()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String subject = "Ticket Resolved: SynapTix Broadband Ticket #" + ticketId;
                            String body = "Dear Customer,\n\n" +
                                          "We are pleased to inform you that your complaint ticket #" + ticketId + " has been resolved.\n\n" +
                                          "Complaint Details:\n" +
                                          "---------------------------------\n" +
                                          ticketDesc + "\n" +
                                          "---------------------------------\n\n" +
                                          "If you continue to face any connectivity issues, please feel free to raise another ticket.\n\n" +
                                          "Best Regards,\n" +
                                          "Technical Support Team\n" +
                                          "SynapTix Broadband Services";

                            EmailUtility.sendEmail(email.trim(), subject, body, null);
                        } catch (final Exception ex) {
                            ex.printStackTrace();
                            java.awt.EventQueue.invokeLater(new Runnable() {
                                public void run() {
                                    JOptionPane.showMessageDialog(null, "Failed to send email notification: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            });
                        }
                    }
                }).start();
            }

            jTable1();
            jTextField1.setText("");
            jTextArea1.setText("");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        btnSolve = new javax.swing.JButton();
        btnAddCom = new javax.swing.JButton();
        jt1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(240, 244, 248));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(33, 43, 54));
        jLabel1.setText("COMPLAINT");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(33, 43, 54));
        jLabel4.setText("Date");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(33, 43, 54));
        jLabel3.setText("COMPLAINT");

        jTextField1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(33, 43, 54));
        jLabel2.setText("ID");

        btnSolve.setBackground(new java.awt.Color(24, 144, 255));
        btnSolve.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSolve.setForeground(new java.awt.Color(255, 255, 255));
        btnSolve.setText("SOLVED");
        btnSolve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolveActionPerformed(evt);
            }
        });

        btnAddCom.setBackground(new java.awt.Color(24, 144, 255));
        btnAddCom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAddCom.setForeground(new java.awt.Color(255, 255, 255));
        btnAddCom.setText("ADD COMPLAINT");
        btnAddCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddComActionPerformed(evt);
            }
        });

        jt1.setEditable(false);
        jt1.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jt1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jt1))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane1)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAddCom, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSolve, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddCom)
                    .addComponent(btnSolve))
                .addGap(108, 108, 108))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable1.setBackground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/complaint.png"))); // NOI18N

        btnBack.setBackground(new java.awt.Color(24, 144, 255));
        btnBack.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBack))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBack)
                .addContainerGap(167, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jt1ActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        try { if (conn != null) conn.close(); } catch(Exception e){}
        Admin ob=new Admin();
        ob.inheritWindowState(this);
        ob.setVisible(true);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                dispose();
            }
        });
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnAddComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddComActionPerformed
        try{
           ComplaintDAO.addComplaint(conn, jTextField1.getText(), jTextArea1.getText(), jt1.getText());
           JOptionPane.showMessageDialog(null, "Successfully Complaint Added.....!!!");
           jTable1();
           jTextField1.setText("");
           jTextArea1.setText("");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding complaint: " + e.getMessage());
        }
    }//GEN-LAST:event_btnAddComActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
         int selectedRowIndex = jTable1.getSelectedRow();
         jTextField1.setText(model.getValueAt(selectedRowIndex, 0).toString());
         jTextArea1.setText(model.getValueAt(selectedRowIndex, 1).toString());
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnSolveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolveActionPerformed
        Update();
        jTable1(); 
    }//GEN-LAST:event_btnSolveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Complaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Complaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Complaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Complaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Complaint().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCom;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSolve;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jt1;
    // End of variables declaration//GEN-END:variables

    public void inheritWindowState(javax.swing.JFrame parent) {
        if (parent != null) {
            int state = parent.getExtendedState();
            if ((state & javax.swing.JFrame.MAXIMIZED_BOTH) == javax.swing.JFrame.MAXIMIZED_BOTH) {
                this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
            } else {
                this.setBounds(parent.getBounds());
            }
        }
    }
}
