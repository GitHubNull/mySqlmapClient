package ui.component;

import entities.OptionsCommandLine;
import table_models.CommandLineTableModel;

import javax.swing.*;
import java.awt.*;

public class CommandLineEditorDialog extends JDialog {
    OptionsCommandLine optionsCommandLine;
    CommandLineTableModel commandLineTableModel;
    int id;
    Boolean enableEdit;

    JPanel tagPanel;
    JLabel tagLabel;
    JTextField tagTextField;

    JPanel commandLinePanel;
    JLabel commandLineLabel;
    JTextField commandLineTextField;

    JPanel buttonPanel;
    JButton okBtn;
    JButton cancelBtn;

    public CommandLineEditorDialog(CommandLineTableModel commandLineTableModel, int id, Boolean enableEdit) {
        this.commandLineTableModel = commandLineTableModel;
        this.id = id;
        optionsCommandLine = commandLineTableModel.getOptionsCommandLineByRow(id);
        this.enableEdit = enableEdit;
        setLayout(new BorderLayout());

        if (enableEdit) {
            setTitle("edit  command line");
        } else {
            setTitle("show command line");
        }

        tagPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        tagLabel = new JLabel("tag");
        tagTextField = new JTextField(optionsCommandLine.getTag());
        tagTextField.setEnabled(enableEdit);

        tagPanel.add(tagLabel);
        tagPanel.add(tagTextField);

        add(tagPanel, BorderLayout.NORTH);

        commandLinePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        commandLineLabel = new JLabel("command line");
        commandLineTextField = new JTextField(optionsCommandLine.getCommandLineStr(), 128);
        commandLineTextField.setEnabled(enableEdit);

        commandLinePanel.add(commandLineLabel);
        commandLinePanel.add(commandLineTextField);

        add(commandLinePanel, BorderLayout.CENTER);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        okBtn = new JButton("Ok");
        okBtn.setEnabled(enableEdit);

        if (enableEdit) {
            cancelBtn = new JButton("Cancel");
        } else {
            cancelBtn = new JButton("Close");
        }

        buttonPanel.add(okBtn);
        buttonPanel.add(cancelBtn);

        add(buttonPanel, BorderLayout.SOUTH);


        pack();
        setSize(getPreferredSize());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        buttonEventListeningInit();
    }

    private void buttonEventListeningInit() {
        okBtn.addActionListener(e -> {
            String tagStr = tagTextField.getText();
            String commandLineStr = commandLineTextField.getText();

            if (null == tagStr || tagStr.trim().isEmpty() || null == commandLineStr || commandLineStr.trim().isEmpty()) {
                dispose();
                return;
            }

            tagStr = tagStr.trim();
            commandLineStr = commandLineStr.trim();

            commandLineTableModel.updateTagByRow(id, tagStr);
            commandLineTableModel.updateCommandLinesByRow(id, commandLineStr);

            dispose();
        });

        cancelBtn.addActionListener(e -> {
            dispose();
        });
    }
}
