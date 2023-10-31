package ui.component;

import entities.HistoryCommandLine;
import excutors.ScanTasksWithConfigeAllTimeExecutor;
import excutors.ScanTasksWithConfigeOneTimeExecutor;
import util.GlobalEnv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ScanConfigurationDialog  extends JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private  JPanel upPanel;
    private JPanel upSubUpPanel;
    private JLabel historyLabel;
    private JComboBox historyComboBox;
    private JButton  useHistoryButton;
    private JPanel upSubDownPanel;
    private JLabel commonLabel;
    private JButton openCommonConfigurationDialogButton;

    private   JScrollPane centerPanel;
    private JTextArea textArea;

    private  JPanel downPanel;

    private JButton buttonOK;
    private JButton buttonCancel;

    enum  ScanConfigurationDialogType {
        ONE_TIME, ALL_TIME
    }

    private ScanConfigurationDialogType  scanConfigurationDialogType;


    ScanTasksWithConfigeOneTimeExecutor scanTasksWithConfigeOneTimeExecutor;
    private ScanTasksWithConfigeAllTimeExecutor scanTasksWithConfigeAllTimeExecutor;

    public ScanConfigurationDialog(ScanTasksWithConfigeOneTimeExecutor scanTasksWithConfigeOneTimeExecutor) {
        this.scanTasksWithConfigeOneTimeExecutor =  scanTasksWithConfigeOneTimeExecutor;
        scanConfigurationDialogType = ScanConfigurationDialogType.ONE_TIME;
        initComponents();
    }

    public ScanConfigurationDialog(ScanTasksWithConfigeAllTimeExecutor scanTasksWithConfigeAllTimeExecutor) {
        this.scanTasksWithConfigeAllTimeExecutor =  scanTasksWithConfigeAllTimeExecutor;
        scanConfigurationDialogType = ScanConfigurationDialogType.ALL_TIME;
        initComponents();
    }

    private void initComponents() {
        setTitle("Scan Configuration");
        setLayout(new BorderLayout());

        upPanel = new JPanel(new BorderLayout());

        upSubUpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        historyLabel = new JLabel("Select history:");
        List<String> historys = new ArrayList<>();
        int index = 0;
        for (HistoryCommandLine historyCommandLine : GlobalEnv.HISTORY_COMMANDLINE_LIST) {
            String tmp = historyCommandLine.getCommandLineStr();
            if(tmp.length() > 32) {
                tmp = tmp.substring(0, 32) + "...";
            }
            index++;
            historys.add(String.format("[%d] %s", index, tmp));
        }
        historyComboBox = new JComboBox(historys.toArray());
        useHistoryButton = new JButton("Use");

        useHistoryButton.addActionListener(this::useHistoryButtonActionPerformed);

        upSubUpPanel.add(historyLabel);
        upSubUpPanel.add(historyComboBox);
        upSubUpPanel.add(useHistoryButton);

        upPanel.add(upSubUpPanel, BorderLayout.NORTH);


        upSubDownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        commonLabel = new JLabel("Common Configuration:");
        openCommonConfigurationDialogButton = new JButton("Open");


        upSubDownPanel.add(commonLabel);
        upSubDownPanel.add(openCommonConfigurationDialogButton);

        upPanel.add(upSubDownPanel, BorderLayout.CENTER);

        add(upPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setRows(1);
        centerPanel = new JScrollPane(textArea);

        add(centerPanel, BorderLayout.CENTER);

        downPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Cancel");

        buttonOK.addActionListener(this::buttonOKPerformAction);
        buttonCancel.addActionListener(this::buttonCancelPerformAction);

        downPanel.add(buttonOK);
        downPanel.add(buttonCancel);


        add(downPanel, BorderLayout.SOUTH);
    }

    private void useHistoryButtonActionPerformed(ActionEvent actionEvent) {
        Object selectItem = historyComboBox.getSelectedItem();
        if (selectItem == null) {
            return;
        }
        String history = (String) selectItem;
        if(history == null ||  history.trim().isEmpty()){
            return;
        }
        textArea.setText(history);
    }

    public void showDialog(){
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setVisible(true);
    }

    private void buttonOKPerformAction(ActionEvent e) {
        if (textArea ==  null || textArea.getText().trim().isEmpty()){
            dispose();
            return;
        }

        String commandLineStr = textArea.getText().trim();
        if (scanConfigurationDialogType == ScanConfigurationDialogType.ONE_TIME
                && scanTasksWithConfigeOneTimeExecutor != null){
            scanTasksWithConfigeOneTimeExecutor.onConfigComplete(commandLineStr);
        } else if (scanConfigurationDialogType == ScanConfigurationDialogType.ALL_TIME &&
                        scanTasksWithConfigeAllTimeExecutor != null) {
            scanTasksWithConfigeAllTimeExecutor.onConfigComplete(commandLineStr);
        }
        dispose();
    }

    private
    void buttonCancelPerformAction(ActionEvent e) {
        dispose();
    }
}
