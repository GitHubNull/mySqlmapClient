package ui;

import entities.CommonCommandLineColumnName;
import entities.CommonCommandLineColumnNameIndex;
import entities.OptionsCommandLine;
import table_models.CommandLineTableModel;
import ui.component.CommandLineEditorDialog;
import ui.component.ScanOptionsTipsDialog;
import util.GlobalEnv;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CommonScanConfigurationSubTab extends JPanel {
    JPanel northPanel;

    JPanel commandLineOperationPanel;

    JPanel tagContainerPanel;
    JLabel tagLabel;
    JTextField tagTextField;


    JPanel commandLineContainerPanel;
    JLabel commandLineLabel;
    JTextField commandLineTextField;
    JButton scanOptionsHelperBtn;
//    List<String> keywords;

    JPanel preOperationContainerPanel;
    JButton addBtn;
    JButton resetBtn;

    JPanel filterPanel;
    JLabel filterLabel;
    JComboBox<String> filterComboBox;
    JTextField filterTextField;
    JButton filterBtn;
    JButton configDefaultBtn;

    JScrollPane centerPanel;
    JTable table;
    TableRowSorter<CommandLineTableModel> sorter;


    JPanel southPanel;
    JButton deleteBtn;
    JButton updateBtn;
    JButton selectAllBtn;
    JButton selectNoneBtn;


    public CommonScanConfigurationSubTab() {
        setLayout(new BorderLayout());


        northPanel = new JPanel(new BorderLayout());

        commandLineOperationPanel = new JPanel(new BorderLayout());

        tagContainerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tagLabel = new JLabel("tag");
        tagTextField = new JTextField(32);
        tagContainerPanel.add(tagLabel);
        tagContainerPanel.add(tagTextField);

        commandLineOperationPanel.add(tagContainerPanel, BorderLayout.NORTH);

        commandLineContainerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        commandLineLabel = new JLabel("commandline");

        commandLineTextField = new JTextField(64);
        commandLineTextField.setFocusTraversalKeysEnabled(false);
//        Autocomplete autoComplete = new Autocomplete(commandLineTextField, GlobalStaticsVar.SCAN_OPTIONS_KEYWORDS);
//        commandLineTextField.getDocument().addDocumentListener(autoComplete);
//        commandLineTextField.getInputMap().put(KeyStroke.getKeyStroke("TAB"), utils.GlobalStaticsVar.COMMIT_ACTION);
//        commandLineTextField.getActionMap().put(utils.GlobalStaticsVar.COMMIT_ACTION, autoComplete.new CommitAction());


        scanOptionsHelperBtn = new JButton("help？");

        commandLineContainerPanel.add(commandLineLabel);
        commandLineContainerPanel.add(commandLineTextField);
        commandLineContainerPanel.add(scanOptionsHelperBtn);

        commandLineOperationPanel.add(commandLineContainerPanel, BorderLayout.CENTER);

        preOperationContainerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addBtn = new JButton("add");
        resetBtn = new JButton("reset");
        preOperationContainerPanel.add(addBtn);
        preOperationContainerPanel.add(resetBtn);

        commandLineOperationPanel.add(preOperationContainerPanel, BorderLayout.SOUTH);

        northPanel.add(commandLineOperationPanel, BorderLayout.CENTER);

        filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        filterLabel = new JLabel("by");

        filterComboBox = new JComboBox<>();
        filterComboBox.addItem(CommonCommandLineColumnName.ID.toString());
        filterComboBox.addItem(CommonCommandLineColumnName.TAG.toString());
        filterComboBox.addItem(CommonCommandLineColumnName.COMMAND_LINE_STR.toString());

        filterTextField = new JTextField("", 64);

        filterBtn = new JButton("filter");
        configDefaultBtn = new JButton("set default commandline");

        filterPanel.add(filterLabel);
        filterPanel.add(filterComboBox);
        filterPanel.add(filterTextField);
        filterPanel.add(filterBtn);
        filterPanel.add(configDefaultBtn);

        northPanel.add(filterPanel, BorderLayout.SOUTH);

//        northPanel

        add(northPanel, BorderLayout.NORTH);


        table = new JTable();
        table.setModel(GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL);

        sorter = new TableRowSorter<>(GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL);
        table.setRowSorter(sorter);

        centerPanel = new JScrollPane(table);

        add(centerPanel, BorderLayout.CENTER);


        southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        deleteBtn = new JButton("删除");
        updateBtn = new JButton("更新");
        selectAllBtn = new JButton("全选");
        selectNoneBtn = new JButton("全不选");

        southPanel.add(deleteBtn);
        southPanel.add(updateBtn);
        southPanel.add(selectAllBtn);
        southPanel.add(selectNoneBtn);

        add(southPanel, BorderLayout.SOUTH);

        initActionListening();

    }

    private void filterTable() {
        if (0 == GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.getRowCount()) {
            return;
        }

        Object selectedObject = filterComboBox.getSelectedItem();
        String filterText = filterTextField.getText();
        if (null == filterText || filterText.isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }

        if (null == selectedObject) {
            sorter.setRowFilter(RowFilter.regexFilter(filterText, CommonCommandLineColumnNameIndex.TAG_INDEX));
            return;
        }

        if (selectedObject.equals(CommonCommandLineColumnName.ID.toString())) {
            sorter.setRowFilter(RowFilter.regexFilter(filterText, CommonCommandLineColumnNameIndex.ID_INDEX));

        } else if (selectedObject.equals(CommonCommandLineColumnName.TAG.toString())) {
            sorter.setRowFilter(RowFilter.regexFilter(filterText, CommonCommandLineColumnNameIndex.TAG_INDEX));

        } else if (selectedObject.equals(CommonCommandLineColumnName.COMMAND_LINE_STR.toString())) {
            sorter.setRowFilter(RowFilter.regexFilter(filterText, CommonCommandLineColumnNameIndex.COMMAND_LINE_STR_INDEX));

        } else {
            sorter.setRowFilter(RowFilter.regexFilter(filterText, CommonCommandLineColumnNameIndex.TAG_INDEX));

        }

    }

    private void initNorthBtnActionListening() {
        addBtn.addActionListener(e -> {
            String tagStr = tagTextField.getText();
            String argsStr = commandLineTextField.getText();

            if (null == tagStr || null == argsStr || tagStr.trim().isEmpty() || argsStr.trim().isEmpty()) {
                return;
            }
            // 校验tag是否重复,重复则不添加
            if (GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.isTagExist(tagStr)) {
                return;
            }

            // todo 校验参数合法性

            GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.addOptionsCommandLine(tagStr, argsStr);
        });

        resetBtn.addActionListener(e -> {
            tagTextField.setText("");
            commandLineTextField.setText("");
        });

        scanOptionsHelperBtn.addActionListener(e -> {
            ScanOptionsTipsDialog scanOptionsTipsDialog = new ScanOptionsTipsDialog("");
            scanOptionsTipsDialog.setVisible(true);
        });

        filterBtn.addActionListener(this::actionPerformed);

        configDefaultBtn.addActionListener(e -> {
            if (0 == GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.getRowCount()) {
                return;
            }
            int[] rows = table.getSelectedRows();
            if (null == rows || 1 != rows.length) {
                return;
            }

            int row = rows[0];

            OptionsCommandLine optionsCommandLine = GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.getOptionsCommandLineByRow(row);
            if (null == optionsCommandLine) {
                return;
            }

            String cmdLineStr = optionsCommandLine.getCommandLineStr();
            if (null == cmdLineStr || cmdLineStr.trim().isEmpty()) {
                return;
            }

            GlobalEnv.DEFAULT_COMMAND_LINE_STR = cmdLineStr;
            GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.updateWasDefaultByRow(row, Boolean.TRUE);
        });
    }

    private void initCenterBtnActionListening() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int col = table.getSelectedColumn();


                if (CommonCommandLineColumnNameIndex.ID_INDEX == GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.getRowCount()) {
                    return;
                }

                int[] selectRows = table.getSelectedRows();
                if (null == selectRows || 1 != selectRows.length) {
                    return;
                }

                OptionsCommandLine optionsCommandLine = GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.getOptionsCommandLineByRow(selectRows[0]);
                if (null == optionsCommandLine) {
                    return;
                }

                if (0 == col) {
                    // 弹出参数详情
                    CommandLineEditorDialog commandLineEditorDialog = new CommandLineEditorDialog(GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL, selectRows[0], false);
                    commandLineEditorDialog.setVisible(true);
                }

            }
        });

        GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.addTableModelListener(e -> {
            int col = e.getColumn();
            if (CommonCommandLineColumnNameIndex.WAS_DEFAULT_INDEX != col) {
                return;
            }

            int row = e.getFirstRow();
            OptionsCommandLine optionsCommandLine0 = GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.getOptionsCommandLineByRow(row);
            if (Boolean.FALSE.equals(optionsCommandLine0.getWasDefault())) {
                int cnt = 0;
                for (int i = 0; i < GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.getRowCount(); i++) {
                    if (Boolean.FALSE.equals(GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.getOptionsCommandLineByRow(i).getWasDefault())) {
                        cnt++;
                    }
                }
                if (cnt == GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.getRowCount()) {
                    GlobalEnv.DEFAULT_COMMAND_LINE_STR = "";
                }

                return;
            }

            String cmdLine = optionsCommandLine0.getCommandLineStr();
            if (null != cmdLine && !cmdLine.trim().isEmpty()) {
                GlobalEnv.DEFAULT_COMMAND_LINE_STR = cmdLine;
            }

            SwingUtilities.invokeLater(() -> {
                for (int i = 0; i < GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.getRowCount(); i++) {
                    if (i != row) {
                        OptionsCommandLine optionsCommandLine = GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.getOptionsCommandLineByRow(i);
                        optionsCommandLine.setWasDefault(Boolean.FALSE);
                        GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.fireTableCellUpdated(i, col);
                    }
                }
            });

        });
    }

    private void initSouthBtnActionListening() {
        deleteBtn.addActionListener(e -> {

            if (0 == GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.getRowCount()) {
                return;
            }

            int[] selectRows = table.getSelectedRows();
            if (null == selectRows) {
                return;
            }

            for (int selectRow : selectRows) {
                GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.deleteOptionsCommandLineByRow(selectRow);
            }
        });

        updateBtn.addActionListener(e -> {
            if (0 == GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.getRowCount()) {
                return;
            }

            int[] selectRows = table.getSelectedRows();
            if (null == selectRows || 1 != selectRows.length) {
                return;
            }

            OptionsCommandLine optionsCommandLine = GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.getOptionsCommandLineByRow(selectRows[0]);
            if (null == optionsCommandLine) {
                return;
            }

            // 弹出编辑页面
            CommandLineEditorDialog commandLineEditorDialog = new CommandLineEditorDialog(GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL, selectRows[0], true);
            commandLineEditorDialog.setVisible(true);

        });

        selectAllBtn.addActionListener(e -> table.selectAll());

        selectNoneBtn.addActionListener(e -> table.clearSelection());
    }

    private void initActionListening() {
        initNorthBtnActionListening();
        initCenterBtnActionListening();
        initSouthBtnActionListening();
    }


    private void actionPerformed(ActionEvent e) {
        filterTable();
    }
}
