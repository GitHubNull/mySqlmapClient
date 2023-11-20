package burp;

import controller.ContextMenuFactory;
import entities.HistoryCommandLine;
import entities.OptionsCommandLine;
import ui.ConsoleTab;
import util.GlobalEnv;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class BurpExtender implements IBurpExtender, ITab, IExtensionStateListener {
    public static IBurpExtenderCallbacks callbacks;
    public static IExtensionHelpers helpers;
    public static PrintWriter stdout;
    public static PrintWriter stderr;
    public final static String NAME = "SqlMap client";

    public static ConsoleTab consoleTab;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        BurpExtender.callbacks = callbacks;
        helpers = callbacks.getHelpers();

        stdout = new PrintWriter(callbacks.getStdout(), true);
        stderr = new PrintWriter(callbacks.getStderr(), true);

        callbacks.setExtensionName(NAME);

        consoleTab = new ConsoleTab();

        callbacks.addSuiteTab(this);

        callbacks.registerContextMenuFactory(new ContextMenuFactory());
        callbacks.registerExtensionStateListener(this);

        loadExtenderConfig();
    }

    @Override
    public String getTabCaption() {
        return NAME;
    }

    @Override
    public Component getUiComponent() {
        return consoleTab;
    }

    private void saveExtenderConfig() {
        saveSqlMapApiServiceConfig();
        saveHistoryCommandlineList();
        saveOptionsCommandlineList();
    }


    private void loadExtenderConfig() {
        loadSqlMapApiServiceConfig();
        loadHistoryCommandlineList();
        loadOptionsCommandlineList();
    }

    @Override
    public void extensionUnloaded() {
        saveExtenderConfig();
        stdout.println("extensionUnloaded...");
    }

    private void saveSqlMapApiServiceConfig() {
        callbacks.saveExtensionSetting(GlobalEnv.SETTING_KEY_HOST, GlobalEnv.HOST);
        callbacks.saveExtensionSetting(GlobalEnv.SETTING_KEY_PORT, String.valueOf(GlobalEnv.PORT));
        callbacks.saveExtensionSetting(GlobalEnv.SETTING_KEY_TMP_REQUEST_FILE_DIR_PATH, GlobalEnv.TMP_REQUEST_FILE_DIR_PATH);
        callbacks.printOutput("Settings saved successfully.");
    }

    private void loadSqlMapApiServiceConfig() {
        String host = callbacks.loadExtensionSetting(GlobalEnv.SETTING_KEY_HOST);
        if (host != null &&  !(host.trim().isEmpty())){
            GlobalEnv.HOST = host;
        }else{
            GlobalEnv.HOST = "127.0.0.1";
        }

        String portStr = callbacks.loadExtensionSetting(GlobalEnv.SETTING_KEY_PORT);

        if (portStr != null && !(portStr.trim().isEmpty())) {
            try {
                GlobalEnv.PORT = Integer.parseInt(portStr);
            } catch (NumberFormatException e) {
                GlobalEnv.PORT = 8775; // 设置默认端口
            }
        } else {
            GlobalEnv.PORT = 8775; // 设置默认端口
        }

        String tmpRequestFileDirPath = callbacks.loadExtensionSetting(GlobalEnv.SETTING_KEY_TMP_REQUEST_FILE_DIR_PATH);
        if (tmpRequestFileDirPath != null && !(tmpRequestFileDirPath.trim().isEmpty())){
            GlobalEnv.TMP_REQUEST_FILE_DIR_PATH = tmpRequestFileDirPath;
        }else {
            GlobalEnv.TMP_REQUEST_FILE_DIR_PATH = System.getProperty("java.io.tmpdir");
        }
    }

    private void saveHistoryCommandlineList() {
        try {
            String serializedData = serializeListHistoryCommandlineList(GlobalEnv.HISTORY_COMMANDLINE_LIST);
            callbacks.saveExtensionSetting(GlobalEnv.HISTORY_COMMANDLINE_LIST_SETTING_KEY, serializedData);
            callbacks.printOutput("History commandline list saved successfully.");
        } catch (IOException e) {
            callbacks.printError("Failed to save history commandline list: " + e.getMessage());
        }
    }

    private void loadHistoryCommandlineList() {
        String serializedData = callbacks.loadExtensionSetting(GlobalEnv.HISTORY_COMMANDLINE_LIST_SETTING_KEY);
        if (serializedData != null && !serializedData.isEmpty()) {
            try {
                GlobalEnv.HISTORY_COMMANDLINE_LIST = deserializeListHistoryCommandLine(serializedData);
                callbacks.printOutput("History commandline list loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                callbacks.printError("Failed to load history commandline list: " + e.getMessage());
            }
        } else {
            // 初始化一个空的历史命令行列表
            GlobalEnv.HISTORY_COMMANDLINE_LIST = new ArrayList<>();
        }
    }

    private String serializeListHistoryCommandlineList(List<HistoryCommandLine> list) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(list);
        objectOutputStream.close();
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    @SuppressWarnings("unchecked")
    private List<HistoryCommandLine> deserializeListHistoryCommandLine(String serializedData) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(serializedData);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        List<HistoryCommandLine> list = (List<HistoryCommandLine>) objectInputStream.readObject();
        objectInputStream.close();
        return list;
    }

    private void saveOptionsCommandlineList() {
        try {
            String serializedData = serializeListOptionsCommandLine(GlobalEnv.OPTIONS_COMMANDLINE_LIST);
            callbacks.saveExtensionSetting(GlobalEnv.OPTIONS_COMMANDLINE_LIST_SETTING_KEY, serializedData);
            callbacks.printOutput("Options commandline list saved successfully.");
        } catch (IOException e) {
            callbacks.printError("Failed to save options commandline list: " + e.getMessage());
        }
    }

    private void loadOptionsCommandlineList() {
        String serializedData = callbacks.loadExtensionSetting(GlobalEnv.OPTIONS_COMMANDLINE_LIST_SETTING_KEY);
        if (serializedData != null && !serializedData.isEmpty()) {
            try {
                GlobalEnv.OPTIONS_COMMANDLINE_LIST = deserializeListOptionsCommandLine(serializedData);
                callbacks.printOutput("Options commandline list loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                callbacks.printError("Failed to load options commandline list: " + e.getMessage());
            }
        }
    }

    private String serializeListOptionsCommandLine(List<OptionsCommandLine> list) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(list);
        objectOutputStream.close();
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    @SuppressWarnings("unchecked")
    private List<OptionsCommandLine> deserializeListOptionsCommandLine(String serializedData) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(serializedData);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        List<OptionsCommandLine> list = (List<OptionsCommandLine>) objectInputStream.readObject();
        objectInputStream.close();
        return list;
    }
}
