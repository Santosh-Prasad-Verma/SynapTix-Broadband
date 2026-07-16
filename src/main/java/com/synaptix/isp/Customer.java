package com.synaptix.isp;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Element;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hunter Fox
 */
public class Customer extends javax.swing.JFrame {

Connection conn;
PreparedStatement pst;
ResultSet rs;
TableRowSorter<TableModel> rowSorter;
private javax.swing.JComboBox<String> comboFilterGender;
private javax.swing.JComboBox<String> comboFilterPurpose;
private javax.swing.JComboBox<String> comboFilterPlan;
    public Customer() {
        super("SynapTix Broadband - Customer Directory");
        initComponents();
        conn=javaconnect.ConnecrDb();
        setupCleanLayout();
        jTable1();
        jTable2();
        populatePlanFilter();
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
        headerLeft.add(jLabel11); // Logo
        jLabel9.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        jLabel9.setForeground(new java.awt.Color(24, 144, 255));
        jLabel9.setText("CUSTOMER DIRECTORY");
        headerLeft.add(jLabel9);
        headerPanel.add(headerLeft, java.awt.BorderLayout.WEST);
        
        // Header Right: Back Button
        javax.swing.JPanel headerRight = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 5));
        headerRight.setOpaque(false);
        btnBack.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        headerRight.add(btnBack);
        headerPanel.add(headerRight, java.awt.BorderLayout.EAST);
        
        jPanel1.add(headerPanel, java.awt.BorderLayout.NORTH);
        
        // 3. Left Panel (Form Panel)
        jPanel2.setBackground(java.awt.Color.WHITE);
        jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224), 1),
            javax.swing.BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        // Wrap Left Panel in a container to prevent vertical stretching
        javax.swing.JPanel leftWrapper = new javax.swing.JPanel(new java.awt.BorderLayout());
        leftWrapper.setOpaque(false);
        leftWrapper.add(jPanel2, java.awt.BorderLayout.NORTH);
        
        // 4. Right Panel (Tables and Actions Panel)
        javax.swing.JPanel rightPanel = new javax.swing.JPanel(new java.awt.BorderLayout(0, 15));
        rightPanel.setOpaque(false);
        
        // Search & Filter Panel (North of Right Panel)
        jPanel4.setBackground(java.awt.Color.WHITE);
        jPanel4.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224), 1),
            javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        rightPanel.add(jPanel4, java.awt.BorderLayout.NORTH);
        
        // Middle Panel: Nested split for Plans Table (jTable2) and Customers Table (jTable1)
        javax.swing.JPanel tablesContainer = new javax.swing.JPanel(new java.awt.GridBagLayout());
        tablesContainer.setOpaque(false);
        java.awt.GridBagConstraints tGbc = new java.awt.GridBagConstraints();
        tGbc.fill = java.awt.GridBagConstraints.BOTH;
        tGbc.gridx = 0;
        tGbc.weightx = 1.0;
        
        // Top Table: Plan Directory Table (jTable2) inside jPanel5
        tGbc.gridy = 0;
        tGbc.weighty = 0.35; // Plans table takes 35% height
        tGbc.insets = new java.awt.Insets(0, 0, 15, 0);
        jPanel5.setBackground(java.awt.Color.WHITE);
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224), 1),
            "Available Broadband Plans",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12),
            new java.awt.Color(120, 120, 120)
        ));
        jScrollPane2.setBorder(null);
        jPanel5.removeAll();
        jPanel5.setLayout(new java.awt.BorderLayout());
        jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);
        tablesContainer.add(jPanel5, tGbc);
        
        // Bottom Table: Customer List Table (jTable1) inside jPanel3
        tGbc.gridy = 1;
        tGbc.weighty = 0.65; // Customer table takes 65% height
        tGbc.insets = new java.awt.Insets(0, 0, 0, 0);
        jPanel3.setBackground(java.awt.Color.WHITE);
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224), 1),
            "Customer Directory List",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12),
            new java.awt.Color(120, 120, 120)
        ));
        jScrollPane1.setBorder(null);
        jPanel3.removeAll();
        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        tablesContainer.add(jPanel3, tGbc);
        
        rightPanel.add(tablesContainer, java.awt.BorderLayout.CENTER);
        
        // South Panel: Bottom Action Buttons
        javax.swing.JPanel actionPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 12, 0));
        actionPanel.setOpaque(false);
        btnGenerateBill.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        btnEmailInvoice.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        actionPanel.add(btnGenerateBill);
        actionPanel.add(btnEmailInvoice);
        rightPanel.add(actionPanel, java.awt.BorderLayout.SOUTH);
        
        // Combine Left and Right Panel in a side-by-side SplitPane
        javax.swing.JSplitPane mainSplit = new javax.swing.JSplitPane(javax.swing.JSplitPane.HORIZONTAL_SPLIT, leftWrapper, rightPanel);
        mainSplit.setDividerLocation(310);
        mainSplit.setDividerSize(0);
        mainSplit.setBorder(null);
        mainSplit.setOpaque(false);
        leftWrapper.setOpaque(false);
        rightPanel.setOpaque(false);
        
        jPanel1.add(mainSplit, java.awt.BorderLayout.CENTER);
        
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    
    public void jTable1(){
        try{
            rs = CustomerDAO.getAllCustomers(conn);
            TableModel tableModel = DbUtils.resultSetToTableModel(rs);
            jTable1.setModel(tableModel);
            rowSorter = new TableRowSorter<>(tableModel);
            jTable1.setRowSorter(rowSorter);
            UIUtils.styleTable(jTable1);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
        public void jTable2(){
        try{
            rs = PlanDAO.getAllPlans(conn);
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
            UIUtils.styleTable(jTable2);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
        public void Update(){
        try{
            CustomerDAO.updateCustomerPlan(conn, jTextField5.getText(), jTextField3.getText());
            CustomerDAO.updateCustomerEmail(conn, txtEmail.getText(), jTextField1.getText());
            JOptionPane.showMessageDialog(null, "Successfully Updated Customer Details!");
            jTable1();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
        jTable2();
    }

    public void Search(){
        applyFilters();
    }

    public void populatePlanFilter() {
        comboFilterPlan.removeAllItems();
        comboFilterPlan.addItem("All Plans");
        try {
            ResultSet planRs = PlanDAO.getAllPlans(conn);
            while (planRs.next()) {
                comboFilterPlan.addItem(planRs.getString("PlanName"));
            }
            planRs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void applyFilters() {
        if (rowSorter == null) return;
        
        List<RowFilter<Object, Object>> filters = new ArrayList<>();
        
        // 1. Text Search Filter (across ID, Name, Contact, Address, etc.)
        String searchText = jTextField7.getText().trim();
        if (!searchText.isEmpty()) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(searchText)));
        }
        
        // 2. Purpose Dropdown Filter
        Object purposeObj = comboFilterPurpose.getSelectedItem();
        String selectedPurpose = (purposeObj != null) ? purposeObj.toString() : "All Purposes";
        if (!selectedPurpose.equals("All Purposes")) {
            filters.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(selectedPurpose) + "$", 4)); // Purpose is column 4
        }
        
        // 3. Gender Dropdown Filter
        Object genderObj = comboFilterGender.getSelectedItem();
        String selectedGender = (genderObj != null) ? genderObj.toString() : "All Genders";
        if (!selectedGender.equals("All Genders")) {
            filters.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(selectedGender) + "$", 3)); // Sex is column 3
        }
        
        // 4. Plan Dropdown Filter
        Object planObj = comboFilterPlan.getSelectedItem();
        String selectedPlan = (planObj != null) ? planObj.toString() : "All Plans";
        if (!selectedPlan.equals("All Plans")) {
            filters.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(selectedPlan) + "$", 6)); // Plan is column 6
        }
        
        if (filters.isEmpty()) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.andFilter(filters));
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
        btnBack = new javax.swing.JButton();
        btnGenerateBill = new javax.swing.JButton();
        btnEmailInvoice = new javax.swing.JButton();
        comboFilterGender = new javax.swing.JComboBox<>();
        comboFilterPurpose = new javax.swing.JComboBox<>();
        comboFilterPlan = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        btnAddEmp = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<String>();
        jComboBox2 = new javax.swing.JComboBox<String>();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jTextField7 = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(240, 244, 248));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(33, 43, 54));
        jLabel1.setText("PLAN");

        btnBack.putClientProperty("JButton.buttonType", "roundRect");
        btnBack.setBackground(new java.awt.Color(24, 144, 255));
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnGenerateBill.putClientProperty("JButton.buttonType", "roundRect");
        btnGenerateBill.setBackground(new java.awt.Color(46, 204, 113));
        btnGenerateBill.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerateBill.setText("GENERATE BILL");
        btnGenerateBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateBillActionPerformed(evt);
            }
        });

        btnEmailInvoice.putClientProperty("JButton.buttonType", "roundRect");
        btnEmailInvoice.setBackground(new java.awt.Color(139, 92, 246));
        btnEmailInvoice.setForeground(new java.awt.Color(255, 255, 255));
        btnEmailInvoice.setText("EMAIL INVOICE");
        btnEmailInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmailInvoiceActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnAddEmp.setBackground(new java.awt.Color(24, 144, 255));
        btnAddEmp.setForeground(new java.awt.Color(255, 255, 255));
        btnAddEmp.setText("ADD");
        btnAddEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmpActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(33, 43, 54));
        jLabel5.setText("SEX");

        jTextField3.setFont(new java.awt.Font("Arial Narrow", 0, 11)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(33, 43, 54));
        jLabel2.setText("ID");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(33, 43, 54));
        jLabel3.setText("NAME");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(33, 43, 54));
        jLabel4.setText("CONTACT");

        jTextField4.setFont(new java.awt.Font("Arial Narrow", 0, 11)); // NOI18N

        jTextField1.setFont(new java.awt.Font("Arial Narrow", 0, 11)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(33, 43, 54));
        jLabel6.setText("PURPOSE");

        jTextField2.setFont(new java.awt.Font("Arial Narrow", 0, 11)); // NOI18N

        btnUpdate.setBackground(new java.awt.Color(24, 144, 255));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("UPDATE");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(33, 43, 54));
        jLabel7.setText("ADDRESS");

        jComboBox1.setFont(new java.awt.Font("Arial Narrow", 1, 11)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MALE", "FEMALE" }));

        jComboBox2.setFont(new java.awt.Font("Arial Narrow", 1, 11)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BUSINESS", "INDIVIDUAL" }));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(33, 43, 54));
        jLabel8.setText("PLAN");

        jTextField5.setFont(new java.awt.Font("Arial Narrow", 0, 11)); // NOI18N

        jPanel2.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(6, 6, 6, 6);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.anchor = java.awt.GridBagConstraints.WEST;

        // Row 0: ID
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        jPanel2.add(jLabel2, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        jPanel2.add(jTextField1, gbc);

        // Row 1: NAME
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        jPanel2.add(jLabel3, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        jPanel2.add(jTextField2, gbc);

        // Row 2: CONTACT
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        jPanel2.add(jLabel4, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        jPanel2.add(jTextField3, gbc);

        // Row 3: SEX
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        jPanel2.add(jLabel5, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        jPanel2.add(jComboBox1, gbc);

        // Row 4: PURPOSE
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.3;
        jPanel2.add(jLabel6, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        jPanel2.add(jComboBox2, gbc);

        // Row 5: ADDRESS
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.3;
        jPanel2.add(jLabel7, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        jPanel2.add(jTextField4, gbc);

        // Row 6: PLAN
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0.3;
        jPanel2.add(jLabel8, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        jPanel2.add(jTextField5, gbc);

        // Row 7: EMAIL
        jLabelEmail = new javax.swing.JLabel("EMAIL");
        jLabelEmail.setFont(new java.awt.Font("Arial", 1, 12));
        jLabelEmail.setForeground(new java.awt.Color(33, 43, 54));
        txtEmail = new javax.swing.JTextField();
        txtEmail.setFont(new java.awt.Font("Arial Narrow", 0, 11));

        gbc.gridx = 0; gbc.gridy = 7; gbc.weightx = 0.3;
        jPanel2.add(jLabelEmail, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        jPanel2.add(txtEmail, gbc);

        // Row 8: Buttons Panel (UPDATE and ADD)
        javax.swing.JPanel btnPanel = new javax.swing.JPanel(new java.awt.GridLayout(1, 2, 10, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnAddEmp); // btnAddEmp is the ADD button

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.insets = new java.awt.Insets(12, 6, 6, 6);
        jPanel2.add(btnPanel, gbc);

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
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTextField7.setFont(new java.awt.Font("Arial Narrow", 0, 11)); // NOI18N
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                applyFilters();
            }
        });

        comboFilterGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Genders", "Male", "Female" }));
        comboFilterPurpose.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Purposes", "Business", "Individual" }));
        comboFilterPlan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Plans" }));

        comboFilterGender.putClientProperty("JComponent.roundRect", true);
        comboFilterPurpose.putClientProperty("JComponent.roundRect", true);
        comboFilterPlan.putClientProperty("JComponent.roundRect", true);

        java.awt.event.ItemListener itemFilterListener = new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                applyFilters();
            }
        };
        comboFilterGender.addItemListener(itemFilterListener);
        comboFilterPurpose.addItemListener(itemFilterListener);
        comboFilterPlan.addItemListener(itemFilterListener);

        btnSearch.setBackground(new java.awt.Color(51, 0, 204));
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("SEARCH");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboFilterGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboFilterPurpose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboFilterPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboFilterGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboFilterPurpose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboFilterPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable2.setBackground(new java.awt.Color(255, 255, 255));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(24, 144, 255));
        jLabel9.setText("CUSTOMER");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(33, 43, 54));
        jLabel10.setText("Customer List");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGenerateBill)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEmailInvoice)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 38, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnBack)
                                    .addComponent(btnGenerateBill)
                                    .addComponent(btnEmailInvoice)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)))
                        .addGap(18, 18, 18)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        Update();
        jTable1();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

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

    private void btnAddEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmpActionPerformed
        try{
           CustomerDAO.addCustomer(conn, jTextField1.getText(), jTextField2.getText(), jTextField3.getText(), (String) jComboBox1.getSelectedItem(), (String) jComboBox2.getSelectedItem(), jTextField4.getText(), jTextField5.getText(), txtEmail.getText());
           JOptionPane.showMessageDialog(null, "Successfully Customer Added.....!!!");
           jTable1();
           jTable2();
           // Clear fields
           jTextField1.setText("");
           jTextField2.setText("");
           jTextField3.setText("");
           jTextField4.setText("");
           jTextField5.setText("");
           txtEmail.setText("");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding customer: " + e.getMessage());
        }
    }//GEN-LAST:event_btnAddEmpActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
         int selectedRowIndex = jTable2.getSelectedRow();
         jTextField5.setText(model.getValueAt(selectedRowIndex, 1).toString());
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
         int selectedRowIndex = jTable1.getSelectedRow();
         jTextField1.setText(model.getValueAt(selectedRowIndex, 0).toString());
         jTextField2.setText(model.getValueAt(selectedRowIndex, 1).toString());
         jTextField3.setText(model.getValueAt(selectedRowIndex, 2).toString());
         
         if (model.getValueAt(selectedRowIndex, 3) != null) {
             jComboBox1.setSelectedItem(model.getValueAt(selectedRowIndex, 3).toString());
         }
         if (model.getValueAt(selectedRowIndex, 4) != null) {
             jComboBox2.setSelectedItem(model.getValueAt(selectedRowIndex, 4).toString());
         }
         
         jTextField4.setText(model.getValueAt(selectedRowIndex, 5) != null ? model.getValueAt(selectedRowIndex, 5).toString() : "");
         jTextField5.setText(model.getValueAt(selectedRowIndex, 6) != null ? model.getValueAt(selectedRowIndex, 6).toString() : "");
         txtEmail.setText(model.getValueAt(selectedRowIndex, 7) != null ? model.getValueAt(selectedRowIndex, 7).toString() : "");
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        Search();
    }//GEN-LAST:event_btnSearchActionPerformed

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
            java.util.logging.Logger.getLogger(Customer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Customer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Customer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Customer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Customer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddEmp;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JButton btnGenerateBill;
    private javax.swing.JButton btnEmailInvoice;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JTextField txtEmail;
    // End of variables declaration//GEN-END:variables

    private void btnEmailInvoiceActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRowIndex = jTable1.getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer from the table first!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        final String customerId = model.getValueAt(selectedRowIndex, 0).toString();
        final String name = model.getValueAt(selectedRowIndex, 1).toString();
        final String contact = model.getValueAt(selectedRowIndex, 2).toString();
        final String purpose = model.getValueAt(selectedRowIndex, 4) != null ? model.getValueAt(selectedRowIndex, 4).toString() : "N/A";
        final String address = model.getValueAt(selectedRowIndex, 5) != null ? model.getValueAt(selectedRowIndex, 5).toString() : "N/A";
        final String planName = model.getValueAt(selectedRowIndex, 6) != null ? model.getValueAt(selectedRowIndex, 6).toString() : "None";

        String speedVal = "N/A";
        String costVal = "0";
        String durationVal = "N/A";

        if (!planName.equals("None") && !planName.trim().isEmpty()) {
            try {
                ResultSet planRs = PlanDAO.getPlanByName(conn, planName);
                if (planRs.next()) {
                    speedVal = planRs.getString("Speed") + " Mbps";
                    costVal = planRs.getString("Cost");
                    durationVal = planRs.getString("Duration") + " Months";
                }
                planRs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        final String speed = speedVal;
        final String cost = costVal;
        final String duration = durationVal;

        String initialEmail = model.getValueAt(selectedRowIndex, 7) != null ? model.getValueAt(selectedRowIndex, 7).toString().trim() : "";
        final String email = (String) JOptionPane.showInputDialog(this, "Enter customer's email address:", initialEmail);
        if (email == null || email.trim().isEmpty()) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                java.io.File tempFile = new java.io.File("Invoice_" + customerId + "_temp.pdf");
                try {
                    generatePDFInvoice(tempFile, customerId, name, contact, purpose, address, planName, speed, cost, duration);

                    String subject = "SynapTix Broadband Service Invoice (" + customerId + ")";
                    String body = "Dear " + name + ",\n\n" +
                                  "Please find attached your invoice for active broadband services.\n\n" +
                                  "Invoice Details:\n" +
                                  "---------------------------------\n" +
                                  "Customer ID: " + customerId + "\n" +
                                  "Customer Name: " + name + "\n" +
                                  "Active Plan: " + planName + " (" + speed + ")\n" +
                                  "Base Plan Cost: Rs. " + cost + "\n" +
                                  "---------------------------------\n\n" +
                                  "Thank you for choosing SynapTix Broadband!\n\n" +
                                  "Best Regards,\n" +
                                  "Billing Department\n" +
                                  "SynapTix Broadband Services";

                    EmailUtility.sendEmail(email.trim(), subject, body, tempFile);

                    try {
                        tempFile.delete();
                    } catch (Exception ex) {}

                    if (EmailUtility.isSmtpConfigured()) {
                        JOptionPane.showMessageDialog(Customer.this, "Invoice successfully emailed to " + email);
                    } else {
                        JOptionPane.showMessageDialog(Customer.this, "Email details printed to server console (SMTP not configured in db.properties).");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(Customer.this, "Failed to email invoice: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }).start();
    }

    private void btnGenerateBillActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRowIndex = jTable1.getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer from the table first!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        String customerId = model.getValueAt(selectedRowIndex, 0).toString();
        String name = model.getValueAt(selectedRowIndex, 1).toString();
        String contact = model.getValueAt(selectedRowIndex, 2).toString();
        String purpose = model.getValueAt(selectedRowIndex, 4) != null ? model.getValueAt(selectedRowIndex, 4).toString() : "N/A";
        String address = model.getValueAt(selectedRowIndex, 5) != null ? model.getValueAt(selectedRowIndex, 5).toString() : "N/A";
        String planName = model.getValueAt(selectedRowIndex, 6) != null ? model.getValueAt(selectedRowIndex, 6).toString() : "None";

        String speed = "N/A";
        String cost = "0";
        String duration = "N/A";

        if (!planName.equals("None") && !planName.trim().isEmpty()) {
            try {
                ResultSet planRs = PlanDAO.getPlanByName(conn, planName);
                if (planRs.next()) {
                    speed = planRs.getString("Speed") + " Mbps";
                    cost = planRs.getString("Cost");
                    duration = planRs.getString("Duration") + " Months";
                }
                planRs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("Invoice_" + customerId + ".pdf"));
        if (fileChooser.showSaveDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try {
                generatePDFInvoice(file, customerId, name, contact, purpose, address, planName, speed, cost, duration);
                JOptionPane.showMessageDialog(this, "Invoice generated successfully at:\n" + file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error generating PDF: " + e.getMessage());
            }
        }
    }

    private void generatePDFInvoice(File file, String customerId, String name, String contact, String purpose, String address, String planName, String speed, String costStr, String duration) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        // Fonts
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Color.WHITE);
        Font sectionHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, new Color(33, 43, 54));
        Font regularFont = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(33, 43, 54));
        Font regularBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new Color(33, 43, 54));
        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8, Color.GRAY);

        // Header table (title banner)
        PdfPTable headerTable = new PdfPTable(1);
        headerTable.setWidthPercentage(100);
        PdfPCell headerCell = new PdfPCell(new Paragraph("SYNAPTIX BROADBAND SERVICES", titleFont));
        headerCell.setBackgroundColor(new Color(24, 144, 255)); // Indigo Blue
        headerCell.setPadding(15);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setBorder(PdfPCell.NO_BORDER);
        headerTable.addCell(headerCell);
        document.add(headerTable);

        // Spacer
        document.add(new Paragraph(" "));

        // Invoice info & Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateStr = dateFormat.format(new Date());
        String invoiceNo = "INV-" + customerId + "-" + (100000 + new java.util.Random().nextInt(900000));

        Paragraph metaPara = new Paragraph();
        metaPara.add(new Paragraph("Invoice Number: " + invoiceNo, regularBold));
        metaPara.add(new Paragraph("Invoice Date: " + currentDateStr, regularFont));
        document.add(metaPara);

        // Divider line
        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

        // Customer details section
        document.add(new Paragraph("CUSTOMER DETAILS", sectionHeaderFont));
        document.add(new Paragraph(" "));

        PdfPTable custTable = new PdfPTable(2);
        custTable.setWidthPercentage(100);
        custTable.setSpacingBefore(5f);
        custTable.setSpacingAfter(15f);

        addTableCell(custTable, "Customer ID:", customerId, regularBold, regularFont);
        addTableCell(custTable, "Customer Name:", name, regularBold, regularFont);
        addTableCell(custTable, "Contact Number:", contact, regularBold, regularFont);
        addTableCell(custTable, "Connection Type:", purpose, regularBold, regularFont);
        addTableCell(custTable, "Installation Address:", address, regularBold, regularFont);
        document.add(custTable);

        // Plan details section
        document.add(new Paragraph("BILLING ITEM DETAILS", sectionHeaderFont));
        document.add(new Paragraph(" "));

        PdfPTable itemsTable = new PdfPTable(4);
        itemsTable.setWidthPercentage(100);
        itemsTable.setSpacingBefore(5f);
        itemsTable.setSpacingAfter(15f);
        
        // Headers
        float[] columnWidths = {3f, 2f, 2f, 3f};
        itemsTable.setWidths(columnWidths);

        addHeaderCell(itemsTable, "Plan Description", regularBold);
        addHeaderCell(itemsTable, "Speed", regularBold);
        addHeaderCell(itemsTable, "Validity", regularBold);
        addHeaderCell(itemsTable, "Base Amount", regularBold);

        // Value
        itemsTable.addCell(new PdfPCell(new Paragraph(planName, regularFont)));
        itemsTable.addCell(new PdfPCell(new Paragraph(speed, regularFont)));
        itemsTable.addCell(new PdfPCell(new Paragraph(duration, regularFont)));
        
        double baseCost = 0.0;
        try {
            baseCost = Double.parseDouble(costStr);
        } catch (NumberFormatException e) {
            // keep 0.0
        }
        itemsTable.addCell(new PdfPCell(new Paragraph("Rs. " + String.format("%.2f", baseCost), regularFont)));
        document.add(itemsTable);

        // Pricing summary details
        double cgst = baseCost * 0.09;
        double sgst = baseCost * 0.09;
        double totalCost = baseCost + cgst + sgst;

        PdfPTable costTable = new PdfPTable(2);
        costTable.setWidthPercentage(40);
        costTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        addSummaryCell(costTable, "Subtotal:", "Rs. " + String.format("%.2f", baseCost), regularFont, regularFont);
        addSummaryCell(costTable, "CGST (9%):", "Rs. " + String.format("%.2f", cgst), regularFont, regularFont);
        addSummaryCell(costTable, "SGST (9%):", "Rs. " + String.format("%.2f", sgst), regularFont, regularFont);
        addSummaryCell(costTable, "Total Due:", "Rs. " + String.format("%.2f", totalCost), regularBold, regularBold);
        document.add(costTable);

        // Spacer
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        // Footer terms
        Paragraph footerPara = new Paragraph("Important Notes:\n" +
                "1. Please pay the total due amount within 7 days of invoice generation to avoid service disconnection.\n" +
                "2. Check details carefully. For queries, contact SynapTix Broadband Support.\n" +
                "3. This is a system-generated electronic invoice and does not require a physical signature.", footerFont);
        footerPara.setAlignment(Element.ALIGN_CENTER);
        document.add(footerPara);

        document.close();
    }

    private void addTableCell(PdfPTable table, String label, String value, Font labelFont, Font valFont) {
        PdfPCell cell1 = new PdfPCell(new Paragraph(label, labelFont));
        cell1.setBorder(PdfPCell.NO_BORDER);
        cell1.setPadding(4);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Paragraph(value, valFont));
        cell2.setBorder(PdfPCell.NO_BORDER);
        cell2.setPadding(4);
        table.addCell(cell2);
    }

    private void addHeaderCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, font));
        cell.setBackgroundColor(new Color(240, 244, 248));
        cell.setPadding(8);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
    }

    private void addSummaryCell(PdfPTable table, String label, String value, Font labelFont, Font valFont) {
        PdfPCell cell1 = new PdfPCell(new Paragraph(label, labelFont));
        cell1.setBorder(PdfPCell.BOTTOM);
        cell1.setPadding(6);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Paragraph(value, valFont));
        cell2.setBorder(PdfPCell.BOTTOM);
        cell2.setPadding(6);
        cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell2);
    }

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
