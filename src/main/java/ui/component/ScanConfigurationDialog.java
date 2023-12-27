package ui.component;

import entities.HistoryCommandLine;
import entities.OptionsCommandLine;
import excutors.ScanTasksWithConfigeAllTimeExecutor;
import excutors.ScanTasksWithConfigeOneTimeExecutor;
import util.GlobalEnv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("FieldMayBeFinal")
public class ScanConfigurationDialog extends JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 9003924762653792586L;

    private JPanel upPanel;
    private JPanel upSubUpPanel;
    private JLabel historyLabel;
    private JComboBox<String> historyComboBox;
    private JButton useHistoryButton;
    private JPanel upSubDownPanel;
    private JLabel commonLabel;
    private JButton openCommonConfigurationDialogButton;

    private JScrollPane centerPanel;
    private JTextArea textArea;

    private JPanel downPanel;

    private JButton dateTimePickerButton;
    LocalDate date;
    LocalTime time;
    //    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String start_datetime;
    private JButton buttonOK;
    private JButton buttonCancel;

    enum ScanConfigurationDialogType {
        ONE_TIME, ALL_TIME
    }

    private ScanConfigurationDialogType scanConfigurationDialogType;


    ScanTasksWithConfigeOneTimeExecutor scanTasksWithConfigeOneTimeExecutor;
    private ScanTasksWithConfigeAllTimeExecutor scanTasksWithConfigeAllTimeExecutor;

    public ScanConfigurationDialog(ScanTasksWithConfigeOneTimeExecutor scanTasksWithConfigeOneTimeExecutor) {
        this.scanTasksWithConfigeOneTimeExecutor = scanTasksWithConfigeOneTimeExecutor;
        scanConfigurationDialogType = ScanConfigurationDialogType.ONE_TIME;
        initComponents();
    }

    public ScanConfigurationDialog(ScanTasksWithConfigeAllTimeExecutor scanTasksWithConfigeAllTimeExecutor) {
        this.scanTasksWithConfigeAllTimeExecutor = scanTasksWithConfigeAllTimeExecutor;
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
            if (tmp.length() > 32) {
                tmp = tmp.substring(0, 32) + "...";
            }
            index++;
            historys.add(String.format("[%d] %s", index, tmp));
        }
        historyComboBox = new JComboBox<>(historys.toArray(new String[0]));
        historyComboBox.setSelectedIndex(historys.size() - 1);
        useHistoryButton = new JButton("Use");

        useHistoryButton.addActionListener(this::useHistoryButtonActionPerformed);

        upSubUpPanel.add(historyLabel);
        upSubUpPanel.add(historyComboBox);
        upSubUpPanel.add(useHistoryButton);

        upPanel.add(upSubUpPanel, BorderLayout.NORTH);


        upSubDownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        commonLabel = new JLabel("Common Configuration:");
        openCommonConfigurationDialogButton = new JButton("choose from common table");

        openCommonConfigurationDialogButton.addActionListener(this::openCommonConfigurationDialogButtonActionPerformed);

        upSubDownPanel.add(commonLabel);
        upSubDownPanel.add(openCommonConfigurationDialogButton);

        upPanel.add(upSubDownPanel, BorderLayout.CENTER);

        add(upPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setRows(2);

//        textField.setFocusTraversalKeysEnabled(false);
//        Autocomplete autoComplete = new Autocomplete(textField, GlobalEnv.OPTIONS_KEYWORDS);
//        textField.getDocument().addDocumentListener(autoComplete);
//        textField.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
//        textField.getActionMap().put(COMMIT_ACTION, autoComplete.new CommitAction());

//        CompletionProvider provider = GlobalEnv.provider;
//        AutoCompletion ac = new AutoCompletion(provider);
//        ac.install(textArea);

        centerPanel = new JScrollPane(textArea);

        add(centerPanel, BorderLayout.CENTER);

        downPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        ImageIcon icon = new ImageIcon(BurpExtender.class.getResource("/icons/datetimePicker.png"));
//        if (icon != null){
//            dateTimePickerButton = new JButton(icon);
//        }else{
//            dateTimePickerButton = new JButton("Pick Date Time");
//        }

        // 加载原始图标
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/datetimePicker.png")));

        // 获取按钮的理想尺寸
        int buttonWidth = 24;
        int buttonHeight = 24;

        // 调整图标尺寸
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        dateTimePickerButton = new JButton(resizedIcon);
        start_datetime = "";
        date = LocalDate.now();
        time = LocalTime.now();
        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Cancel");

        dateTimePickerButton.addActionListener(this::dateTimePickerButtonPerformAction);
        buttonOK.addActionListener(this::buttonOKPerformAction);
        buttonCancel.addActionListener(this::buttonCancelPerformAction);

        downPanel.add(dateTimePickerButton);
        downPanel.add(buttonOK);
        downPanel.add(buttonCancel);


        add(downPanel, BorderLayout.SOUTH);
    }

    private void dateTimePickerButtonPerformAction(ActionEvent actionEvent) {
        MyDateTimePicker myDateTimePicker = new MyDateTimePicker(ScanConfigurationDialog.this);
        myDateTimePicker.okButton.addActionListener(e->{
            if (myDateTimePicker.getFormatDateTime()!= null) {
                start_datetime = myDateTimePicker.getFormatDateTime();
            }
        });
        myDateTimePicker.setVisible(true);
//        myDateTimePicker.setDialogListener(dateTime -> {
//            start_datetime = dateTime;
//        });

//        TaskStarDateTimePicker taskStarDateTimePicker = new TaskStarDateTimePicker(ScanConfigurationDialog.this);
//        taskStarDateTimePicker.dateTimePicker.addDateTimeChangeListener(new DateTimeChangeListener() {
//            @Override
//            public void dateOrTimeChanged(DateTimeChangeEvent dateTimeChangeEvent) {
//                DateChangeEvent dateEvent = dateTimeChangeEvent.getDateChangeEvent();
//                if (dateEvent != null) {
////                    String dateChangeMessage = "\nThe DatePicker value has changed from (" + dateEvent.getOldDate() + ") to (" + dateEvent.getNewDate() + ").";
//                    date = dateEvent.getNewDate();
////                    System.out.println(dateChangeMessage);
//                }
//                TimeChangeEvent timeEvent = dateTimeChangeEvent.getTimeChangeEvent();
//                if (timeEvent != null) {
////                    String timeChangeMessage = "\nThe TimePicker value has changed from (" + timeEvent.getOldTime() + ") to (" + timeEvent.getNewTime() + ").";
//                    time = timeEvent.getNewTime();
////                    System.out.println(timeChangeMessage);
//                }
//                // 将 LocalDate 和 LocalTime 组合成 LocalDateTime
//                LocalDateTime dateTime = LocalDateTime.of(date, time);
//
//                // 格式化 LocalDateTime
//                start_datetime = dateTime.format(formatter);
//
//                // 输出格式化的日期时间
//                BurpExtender.stdout.println("start_datetime: " +start_datetime);
//            }
//        });
//        taskStarDateTimePicker.setVisible(true);
    }

    private void openCommonConfigurationDialogButtonActionPerformed(ActionEvent actionEvent) {
        CommonConfigurationDialog commonConfigurationDialog = new CommonConfigurationDialog();
        commonConfigurationDialog.okBnt.addActionListener(e -> {
            int selectRow = commonConfigurationDialog.table.getSelectedRow();
            if (selectRow < 0) {
                commonConfigurationDialog.dispose();
                return;
            }
            OptionsCommandLine optionsCommandLine = GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL.getOptionsCommandLineByRow(selectRow);
            if (null == optionsCommandLine) {
                commonConfigurationDialog.dispose();
                return;
            }

            String commandLineStr = optionsCommandLine.getCommandLineStr();
            if (null == commandLineStr || commandLineStr.trim().isEmpty()) {
                commonConfigurationDialog.dispose();
                return;
            }

            textArea.setText(commandLineStr);
            commonConfigurationDialog.dispose();
        });
        commonConfigurationDialog.setVisible(true);
    }

    private void useHistoryButtonActionPerformed(ActionEvent actionEvent) {
        int selectedIndex = historyComboBox.getSelectedIndex();
        if (selectedIndex < 0) {
            return;
        }

        HistoryCommandLine historyCommandLine = GlobalEnv.HISTORY_COMMANDLINE_LIST.get(selectedIndex);
        if (historyCommandLine == null) {
            return;
        }

        String history = historyCommandLine.getCommandLineStr();
        if (history == null || history.trim().isEmpty()) {
            return;
        }
        textArea.setText(history);
    }

    public void showDialog() {
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        setModal(true);
        setVisible(true);
    }

    private void buttonOKPerformAction(ActionEvent e) {
        if (textArea == null || textArea.getText().trim().isEmpty()) {
            dispose();
            return;
        }

        String commandLineStr = textArea.getText().trim();
        commandLineStr = commandLineStr.replaceAll("\\n", "");
        if (commandLineStr.isEmpty()) {
            dispose();
            return;
        }

        if (start_datetime == null || start_datetime.trim().isEmpty()) {
            if (scanConfigurationDialogType == ScanConfigurationDialogType.ONE_TIME
                    && scanTasksWithConfigeOneTimeExecutor != null) {
                scanTasksWithConfigeOneTimeExecutor.onConfigComplete(commandLineStr);
            } else if (scanConfigurationDialogType == ScanConfigurationDialogType.ALL_TIME &&
                    scanTasksWithConfigeAllTimeExecutor != null) {
                scanTasksWithConfigeAllTimeExecutor.onConfigComplete(commandLineStr);
            }

        } else {
            if (scanConfigurationDialogType == ScanConfigurationDialogType.ONE_TIME
                    && scanTasksWithConfigeOneTimeExecutor != null) {
                scanTasksWithConfigeOneTimeExecutor.onConfigCompleteAtDateTime(commandLineStr, start_datetime);
            } else if (scanConfigurationDialogType == ScanConfigurationDialogType.ALL_TIME &&
                    scanTasksWithConfigeAllTimeExecutor != null) {
                scanTasksWithConfigeAllTimeExecutor.onConfigCompleteAtDateTime(commandLineStr, start_datetime);
            }
        }


        dispose();
    }

    private void buttonCancelPerformAction(ActionEvent e) {
        dispose();
    }
}
