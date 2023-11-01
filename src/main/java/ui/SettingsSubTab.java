package ui;

import imps.SqlMapApiImpl;
import okhttp3.OkHttpClient;
import sqlmapApi.SqlmapApiClient;
import util.GlobalEnv;
import util.MyStringUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class SettingsSubTab extends JPanel{
    private JPanel northPanel;
    private JPanel hostPanel;
    private JLabel hostLabel;
    private JTextField hostTextField;
    private JPanel portPanel;
    private JLabel portLabel;
    private JTextField portTextField;
    private JPanel northButtonPanel;
    private  JButton saveButton;
    private JButton connectButton;

    private JPanel centerPanel;
    private JLabel tmpFilePathLabel;
    private JTextField tmpFilePathTextField;
    private JButton  tmpFilePathChooseButton;
    private JPanel southPanel;
    private JLabel statusLabel;
    private final String userHome = System.getProperty("user.home");

    public SettingsSubTab() {
        setLayout(new BorderLayout());


        //North Panel
        northPanel = new JPanel(new BorderLayout());
        northPanel.setBorder(new TitledBorder("Server Settings"));

        hostPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        hostLabel = new JLabel("Host:");
        hostTextField = new JTextField(GlobalEnv.HOST);
        hostPanel.add(hostLabel);
        hostPanel.add(hostTextField);

        northPanel.add(hostPanel,BorderLayout.NORTH);
        // North port panel
        portPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        portLabel = new JLabel("Port:");
        portTextField= new JTextField(String.valueOf(GlobalEnv.PORT));
        portPanel.add(portLabel);
        portPanel.add(portTextField);

        northPanel.add(portPanel,BorderLayout.CENTER);

        //North Button Panel
        northButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButton = new JButton("Save");
        connectButton = new JButton("Connect");
        northButtonPanel.add(saveButton);
        northButtonPanel.add(connectButton);
        
        saveButton.addActionListener(this::saveButtonActionPerformed);
        connectButton.addActionListener(this::connectButtonActionPerformed);

        northPanel.add(northButtonPanel,BorderLayout.SOUTH);

        add(northPanel,BorderLayout.NORTH);

        // tmp file path setting panel
        centerPanel =  new JPanel(new FlowLayout(FlowLayout.LEFT));
        centerPanel.setBorder(new TitledBorder("temp file path Setting"));

        tmpFilePathLabel = new JLabel("Tmp File Path:");
        tmpFilePathTextField = new JTextField(GlobalEnv.TMP_REQUEST_FILE_DIR_PATH);
        tmpFilePathChooseButton = new JButton("Choose");
        centerPanel.add(tmpFilePathLabel);
        centerPanel.add(tmpFilePathTextField);
        centerPanel.add(tmpFilePathChooseButton);
        
        tmpFilePathChooseButton.addActionListener(this::tmpFilePathChooseButtonActionPerformed);        

        add(centerPanel,BorderLayout.CENTER);

        southPanel  = new JPanel(new FlowLayout(FlowLayout.LEFT));
        southPanel.setBorder(new TitledBorder("status"));

        statusLabel =  new JLabel("Status: unconnected");
        statusLabel.setBackground(Color.GRAY);
        southPanel.add(statusLabel);

        add(southPanel,BorderLayout.SOUTH);
    }

    private void tmpFilePathChooseButtonActionPerformed(ActionEvent actionEvent) {
        JFileChooser fileChooser = new JFileChooser(userHome);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showOpenDialog(SettingsSubTab.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (null == file) {
                return;
            }

            String directPath = file.getAbsolutePath();

            if (directPath.trim().isEmpty()) {
                return;
            }


            GlobalEnv.TMP_REQUEST_FILE_DIR_PATH = directPath;
            tmpFilePathTextField.setText(directPath);
        }
    }

    private void connectButtonActionPerformed(ActionEvent actionEvent) {
        String btnText = connectButton.getText();
        if (btnText.equals("Connect")){
            String host = GlobalEnv.HOST;
            int port = GlobalEnv.PORT;
            if (!MyStringUtil.isValidIPAddress(host)){
                JOptionPane.showMessageDialog(this, "host is invalid");
                return;
            }

            GlobalEnv.okHttpClient = new OkHttpClient();
            SqlMapApiImpl sqlMapApi = new SqlMapApiImpl(host, port, GlobalEnv.okHttpClient);
            GlobalEnv.sqlmapApiClient = new SqlmapApiClient(sqlMapApi);

            SqlmapApiClient sqlmapApiClient = GlobalEnv.sqlmapApiClient;
            if (sqlmapApiClient.isConnected()){
                statusLabel.setText("Connected");
                statusLabel.setForeground(Color.GREEN);
                connectButton.setText("Disconnect");
                GlobalEnv.IS_CONNECTED = true;
            }else {
                statusLabel.setText("Not connected");
                statusLabel.setForeground(Color.RED);
                connectButton.setText("Connect");
                GlobalEnv.IS_CONNECTED = false;
            }
        }else  {
            GlobalEnv.IS_CONNECTED = false;
            GlobalEnv.okHttpClient = null;
            GlobalEnv.sqlmapApiClient = null;
            statusLabel.setText("Not connected");
            statusLabel.setForeground(Color.RED);
            connectButton.setText("Connect");
            GlobalEnv.IS_CONNECTED = false;
        }


    }

    private boolean saveServerSettings(){
        String host = hostTextField.getText();
        String port = portTextField.getText();

        if (host.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "host is empty");
            return false;
        }
        if (port.trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "port is empty");
            return false;
        }

        if (!MyStringUtil.isValidIPAddress(host)) {
            JOptionPane.showMessageDialog(this, "host is invalid");
            return false;
        }

        if (!MyStringUtil.isTruePortNumber(port)) {
            JOptionPane.showMessageDialog(this, "port is invalid");
            return false;
        }

        if (GlobalEnv.HOST.equals(host) && GlobalEnv.PORT == Integer.parseInt(port)) {
            JOptionPane.showMessageDialog(this, "host and port are same");
            return false;
        }

        GlobalEnv.HOST = host;
        GlobalEnv.PORT = Integer.parseInt(port);

        return true;
    }

    private void saveButtonActionPerformed(ActionEvent actionEvent) {
        saveServerSettings();
    }
}
