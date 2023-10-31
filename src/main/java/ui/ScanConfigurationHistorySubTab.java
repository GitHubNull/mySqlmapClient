package ui;

import table_models.ScanTaskHistoryCommandLineTableModel;

import javax.swing.*;
import java.awt.*;

public class ScanConfigurationHistorySubTab extends JPanel {
    private JPanel northPanel;
    private JLabel searchLabel;
    private  JTextField searchField;
    private JButton searchButton;
    private JScrollPane centerPanel;
    private JTable  table;
    private ScanTaskHistoryCommandLineTableModel tableModel;
    private JPanel southPanel;
    private JButton deleteButton;

    public ScanConfigurationHistorySubTab() {

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());


        northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchLabel = new JLabel("Search:");
        searchField = new JTextField(64);
        searchButton = new JButton("Search");
        northPanel.add(searchLabel);
        northPanel.add(searchField);
        northPanel.add(searchButton);

        add(northPanel, BorderLayout.NORTH);

        table = new JTable();
        tableModel = new ScanTaskHistoryCommandLineTableModel();
        table.setModel(tableModel);
        centerPanel = new JScrollPane(table);

        add(centerPanel, BorderLayout.CENTER);

        southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deleteButton = new JButton("delete");
        southPanel.add(deleteButton);

        add(southPanel, BorderLayout.SOUTH);
    }
}
