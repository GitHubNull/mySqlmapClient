package controller;

import burp.IContextMenuFactory;
import burp.IContextMenuInvocation;
import burp.IHttpRequestResponse;
import excutors.ScanTasksWithConfigeAllTimeExecutor;
import excutors.ScanTasksWithConfigeOneTimeExecutor;
import ui.component.ScanConfigurationDialog;
import util.GlobalEnv;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ContextMenuFactory  implements IContextMenuFactory {

    public ContextMenuFactory() {

    }

    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation contextMenuInvocation) {
        IHttpRequestResponse[] httpRequestResponses = contextMenuInvocation.getSelectedMessages();
        if (httpRequestResponses == null || httpRequestResponses.length == 0){
            return null;
        }
        if (!GlobalEnv.IS_CONNECTED){
            return  null;
        }

        List<JMenuItem> menuItemList = new ArrayList<>();

        int requestResponseLength = httpRequestResponses.length;

        String suffixStr = requestResponseLength > 1 ? String.format("(%ds)", requestResponseLength) : "";

        JMenuItem scanTasksWithConfigOneTime = new JMenuItem(String.format("[*] new task%s config one time",  suffixStr));
        JMenuItem scanTasksWithConfigAllTime = new JMenuItem(String.format("[*] new task%s config all time",   suffixStr));

        scanTasksWithConfigOneTime.addActionListener(e-> scanTasksWithConfigOneTime(e, httpRequestResponses));
        scanTasksWithConfigAllTime.addActionListener(e-> scanTasksWithConfigAllTime(e, httpRequestResponses));

        menuItemList.add(scanTasksWithConfigOneTime);
        menuItemList.add(scanTasksWithConfigAllTime);

        return menuItemList;
    }
    private void scanTasksWithConfigOneTime(ActionEvent actionEvent, IHttpRequestResponse[] httpRequestResponses) {
        ScanTasksWithConfigeOneTimeExecutor scanTaskWithConfigeOneTime = new ScanTasksWithConfigeOneTimeExecutor(httpRequestResponses);
        ScanConfigurationDialog scanConfigurationDialog = new ScanConfigurationDialog(scanTaskWithConfigeOneTime);
        scanConfigurationDialog.showDialog();
    }

    private void scanTasksWithConfigAllTime(ActionEvent actionEvent, IHttpRequestResponse[] httpRequestResponses) {
        for (IHttpRequestResponse httpRequestResponse : httpRequestResponses) {
            ScanTasksWithConfigeAllTimeExecutor scanTaskWithConfigeAllTime = new ScanTasksWithConfigeAllTimeExecutor(httpRequestResponse);
            ScanConfigurationDialog scanConfigurationDialog = new ScanConfigurationDialog(scanTaskWithConfigeAllTime);
            scanConfigurationDialog.setModal(true);
            scanConfigurationDialog.showDialog();
        }
    }


}
