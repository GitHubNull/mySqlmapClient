package ui;

import table_models.HistoryCommandLineTableModel;
import util.GlobalEnv;

import javax.swing.*;
import java.awt.*;

public class ScanConfigurationHistorySubTab extends JPanel {
    private JPanel northPanel;
    private JLabel searchLabel;
    private  JTextField searchField;
    private JButton searchButton;
    private JScrollPane centerPanel;
    private JTable  table;
//    private HistoryCommandLineTableModel;
    private JPanel southPanel;
    private JButton deleteButton;
    private JButton clearButton;

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
//        historyCommandLineTableModel = GlobalEnv.HISTORY_COMMANDLINE_TABLE_MODEL;
        table.setModel(GlobalEnv.HISTORY_COMMANDLINE_TABLE_MODEL);
        centerPanel = new JScrollPane(table);

        add(centerPanel, BorderLayout.CENTER);

        southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        deleteButton = new JButton("delete");
        clearButton = new JButton("clear");
        southPanel.add(deleteButton);
        southPanel.add(clearButton);

        deleteButton.addActionListener(e->{
            int[] selectRows = table.getSelectedRows();
            GlobalEnv.HISTORY_COMMANDLINE_TABLE_MODEL.deleteHistoryCommandLineByRowNumbers(selectRows);
        });

        clearButton.addActionListener(e->{
           GlobalEnv.HISTORY_COMMANDLINE_TABLE_MODEL.clearAll();
        });

        add(southPanel, BorderLayout.SOUTH);
    }
}
