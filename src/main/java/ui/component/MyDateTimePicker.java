package ui.component;

import interfaces.DialogListener;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

// src/main/java/ui/component/MyDateTimePicker.java
// my datettime picker dialog
@SuppressWarnings("FieldCanBeLocal")
public class MyDateTimePicker extends JDialog {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private String formatDateTime;

    private static final String[] MONTHS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private static final String[] HOURS = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    private static final String[] MINUTES = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
    private static final String[] SECONDS = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};

    private final JComboBox<String> yearBox;
    private final JComboBox<String> monthBox;
    private JComboBox<String> dayBox;
    private final JComboBox<String> hourBox;
    private final JComboBox<String> minuteBox;
    private final JComboBox<String> secondBox;
    public final JButton okButton;
    private DialogListener listener;
    private final JButton cancelButton;
    private final JPanel buttonPanel;

    public MyDateTimePicker(ScanConfigurationDialog parent) {
        super(parent, "Date & Time Picker", true);
        if (parent != null) {
            setLocationRelativeTo(parent);
        }
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // 获取当前日期时间
        // 获取当前日期和时间
        LocalDateTime now = LocalDateTime.now();
        year = now.getYear();
        month = now.getMonthValue();
        day = now.getDayOfMonth();
        hour = now.getHour();
        minute = now.getMinute();
        second = now.getSecond();

        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);

        // 格式化当前日期时间
        formatDateTime = now.format(formatter);

        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel yearLabel = new JLabel("Year");
        panel.add(yearLabel);

        Year currentYear = Year.now();
        yearBox = new JComboBox<>();
        for (int i = currentYear.getValue() - 10; i <= currentYear.getValue() + 10; i++) {
            yearBox.addItem(Integer.toString(i));
        }
        yearBox.setSelectedItem(Integer.toString(year));
        panel.add(yearBox);

        yearBox.addActionListener(e -> year = Integer.parseInt((String) Objects.requireNonNull(yearBox.getSelectedItem())));

        JLabel monthLabel = new JLabel("Month");
        panel.add(monthLabel);

        LocalDate currentDate = LocalDate.now();
        monthBox = new JComboBox<>(MONTHS);
        monthBox.setSelectedItem(Integer.toString(month));
        panel.add(monthBox);

        monthBox.addActionListener(e -> {
            int selectedMonth = Integer.parseInt((String) Objects.requireNonNull(monthBox.getSelectedItem()));
            int daysInMonth = LocalDate.of(year, selectedMonth, 1).lengthOfMonth();
            dayBox.removeAllItems();
            for (int i = 0; i < daysInMonth; i++) {
                dayBox.addItem(Integer.toString(i + 1));
            }
        });

        JLabel dayLabel = new JLabel("Day");
        panel.add(dayLabel);

        // 通过现在所处月份推断该月有多少天作为初始值
        dayBox = new JComboBox<>();
        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());
        int daysInMonth = yearMonth.lengthOfMonth();
        for (int i = 0; i < daysInMonth; i++) {
            dayBox.addItem(Integer.toString(i + 1));
        }
        dayBox.setSelectedItem(Integer.toString(day));
        panel.add(dayBox);

        JLabel hourLabel = new JLabel("Hour");
        panel.add(hourLabel);

        hourBox = new JComboBox<>(HOURS);
        hourBox.setSelectedItem(Integer.toString(hour));
        panel.add(hourBox);

        JLabel minuteLabel = new JLabel("Minute");
        panel.add(minuteLabel);

        minuteBox = new JComboBox<>(MINUTES);
        minuteBox.setSelectedItem(Integer.toString(minute));
        panel.add(minuteBox);

        JLabel secondLabel = new JLabel("Second");
        panel.add(secondLabel);

        secondBox = new JComboBox<>(SECONDS);
        secondBox.setSelectedItem(Integer.toString(second));
        panel.add(secondBox);

        add(panel, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            if (listener != null) {
                listener.onOkClicked(getFormatDateTime());
            }
            dispose();
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

//        setSize(300, 200);
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        pack();
//        setVisible(true);
    }

    public String getFormatDateTime() {
        year = Integer.parseInt(Objects.requireNonNull(yearBox.getSelectedItem()).toString());
        month = Integer.parseInt(Objects.requireNonNull(monthBox.getSelectedItem()).toString());
        day = Integer.parseInt(Objects.requireNonNull(dayBox.getSelectedItem()).toString());
        hour = Integer.parseInt(Objects.requireNonNull(hourBox.getSelectedItem()).toString());
        minute = Integer.parseInt(Objects.requireNonNull(minuteBox.getSelectedItem()).toString());
        second = Integer.parseInt(Objects.requireNonNull(secondBox.getSelectedItem()).toString());

        formatDateTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;

//        System.out.println("Selected Date & Time: " + formatDateTime);
        return formatDateTime;
    }

    public void setDialogListener(DialogListener listener) {
        this.listener = listener;
    }

//    public static void main(String[] args) {
//        MyDateTimePicker picker = new MyDateTimePicker(null);
//        picker.setLocationRelativeTo(null);
//        picker.setVisible(true);
//    }
}
