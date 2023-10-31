package burp;

import controller.ContextMenuFactory;
import ui.ConsoleTab;

import java.awt.*;
import java.io.PrintWriter;

public class BurpExtender implements IBurpExtender, ITab, IExtensionStateListener {
    public static IBurpExtenderCallbacks callbacks;
    public static IExtensionHelpers helpers;
    public static PrintWriter stdout;
    public static PrintWriter stderr;
    public final static String NAME = "SqlMap client";

    public static ConsoleTab consoleTab;

    private final static String PYTHON_EXEC_PATH_CONFIG_VAR = "PYTHON_EXEC_PATH";
    private final static String SQLMAP_API_PATH_CONFIG_VAR = "SQLMAP_API_PATH";
    private final static String SQLMAP_API_PORT_CONFIG_VAR = "SQLMAP_API_PORT";
    private final static String TMP_REQUEST_FILE_DIR_PATH_CONFIG_VAR = "TMP_REQUEST_FILE_DIR_PATH";

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

//        loadExtenderConfig();
    }

    @Override
    public String getTabCaption() {
        return NAME;
    }

    @Override
    public Component getUiComponent() {
        return consoleTab;
    }


    private void saveSqlMapApiServiceConfig() {
    }

    private void saveCommandLines() {

    }

    private void saveExtenderConfig() {
        saveSqlMapApiServiceConfig();
        saveCommandLines();
    }

    private void loadSqlMapApiServiceConfig() {

    }

    private void loadCommandLines() {

    }

    private void loadExtenderConfig() {
        loadSqlMapApiServiceConfig();
        loadCommandLines();
    }

    @Override
    public void extensionUnloaded() {
//        saveExtenderConfig();
        stdout.println("extensionUnloaded...");
    }
}
