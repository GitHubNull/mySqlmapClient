package ui;

import javax.swing.*;

public class ConsoleTab extends JTabbedPane {
    private SettingsSubTab settingsSubTab;
    private ScanConfigurationHistorySubTab scanConfigurationHistorySubTab;
    private CommonScanConfigurationSubTab commonScanConfigurationSubTab;

    public ConsoleTab() {
        initComponents();
    }

    private void initComponents() {
        settingsSubTab = new SettingsSubTab();
        scanConfigurationHistorySubTab = new ScanConfigurationHistorySubTab();
        commonScanConfigurationSubTab = new CommonScanConfigurationSubTab();

        add("Server settings", settingsSubTab);
        add("Scan configuration history", scanConfigurationHistorySubTab);
        add("Common scan configuration", commonScanConfigurationSubTab);

        setComponentAt(0, settingsSubTab);
        setComponentAt(1, scanConfigurationHistorySubTab);
        setComponentAt(2, commonScanConfigurationSubTab);
    }
}
