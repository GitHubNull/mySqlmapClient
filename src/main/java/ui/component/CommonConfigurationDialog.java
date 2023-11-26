package ui.component;

import util.GlobalEnv;

import javax.swing.*;
import java.awt.*;

public class CommonConfigurationDialog extends JFrame{
    private JScrollPane northPanel;
    public JTable table;

    private JPanel centerPanel;
    public JButton okBnt;
    private JButton cancelBnt;

    public CommonConfigurationDialog(){
        setTitle("Common Configuration");
        setLayout(new BorderLayout());

        table = new JTable(GlobalEnv.COMMON_COMMANDLINE_TABLE_MODEL);
        northPanel = new JScrollPane(table);
        add(northPanel,BorderLayout.NORTH);

        centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());
        okBnt = new JButton("OK");
        cancelBnt = new JButton("Cancel");

        centerPanel.add(okBnt);
        centerPanel.add(cancelBnt);
        add(centerPanel,BorderLayout.CENTER);

//        okBnt.addActionListener(e->{
//            dispose();
//        });

        cancelBnt.addActionListener(e -> {
            dispose();
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
//        setVisible(true);
    }
}
