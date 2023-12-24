package ui.component;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import javax.swing.*;
import java.awt.*;

public class TaskStarDateTimePicker extends JDialog {
    DateTimePicker dateTimePicker;

    public TaskStarDateTimePicker(JFrame parent) {
        // 设置对话框的标题和关闭方式
        setTitle("选择任务日期时间");
        setLayout(new BorderLayout());


        DatePickerSettings dateSettings = new DatePickerSettings();
        TimePickerSettings timeSettings = new TimePickerSettings();
        dateSettings.setAllowEmptyDates(false);
        timeSettings.setAllowEmptyTimes(false);
        dateTimePicker = new DateTimePicker(dateSettings, timeSettings);

        add(dateTimePicker, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(parent);
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//        setVisible(true);
    }
}
