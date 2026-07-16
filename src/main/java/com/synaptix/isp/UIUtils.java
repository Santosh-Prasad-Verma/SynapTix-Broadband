package com.synaptix.isp;

import javax.swing.*;
import java.awt.*;

public class UIUtils {
    
    /**
     * Applies global FlatLaf look-and-feel customizations before/during system load.
     * Sets rounded corners, input field padding, custom scrollbars, and modern default fonts.
     */
    public static void applyPremiumTheme() {
        try {
            // Rounded corners on buttons, inputs, comboboxes, and textareas
            UIManager.put("Button.arc", 12);
            UIManager.put("Component.arc", 12);
            UIManager.put("TextComponent.arc", 12);
            
            // Margins & padding inside text inputs for comfortable reading
            UIManager.put("TextField.margin", new Insets(6, 12, 6, 12));
            UIManager.put("PasswordField.margin", new Insets(6, 12, 6, 12));
            UIManager.put("ComboBox.padding", new Insets(4, 8, 4, 8));
            
            // Custom modern scrollbar thumbs
            UIManager.put("ScrollBar.thumbArc", 999);
            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
            UIManager.put("ScrollBar.width", 12);
            
            // JTable global defaults
            UIManager.put("Table.rowHeight", 32);
            UIManager.put("Table.showHorizontalLines", true);
            UIManager.put("Table.gridColor", new Color(230, 235, 240));
            
            // Font upgrades (Bridges Tahoma to native high-clarity fonts Segoe UI/Inter)
            String fontName = System.getProperty("os.name").toLowerCase().contains("win") ? "Segoe UI" : "Inter";
            UIManager.put("defaultFont", new Font(fontName, Font.PLAIN, 14));
            
            // Setup FlatLaf Light Laf
            com.formdev.flatlaf.FlatLightLaf.setup();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Helper to style specific JTable headers and selections dynamically.
     */
    public static void styleTable(JTable table) {
        if (table == null) return;
        table.setRowHeight(32);
        
        // Style Header row
        if (table.getTableHeader() != null) {
            table.getTableHeader().setFont(new Font(table.getFont().getName(), Font.BOLD, 14));
            table.getTableHeader().setBackground(new Color(240, 244, 248));
            table.getTableHeader().setForeground(new Color(33, 43, 54));
            table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 36));
        }
        
        // Highlight styling
        table.setSelectionBackground(new Color(224, 242, 254));
        table.setSelectionForeground(new Color(3, 105, 161));
    }
}
