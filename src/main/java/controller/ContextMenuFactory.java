package controller;

import burp.IContextMenuFactory;
import burp.IContextMenuInvocation;
import burp.IHttpRequestResponse;
import excutors.ScanTasksWithConfigeAllTimeExecutor;
import excutors.ScanTasksWithConfigeOneTimeExecutor;
import ui.component.ScanConfigurationDialog;

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

        List<JMenuItem> menuItemList = new ArrayList<>();

        int requestResponseLength = httpRequestResponses.length;

        String suffixStr = requestResponseLength > 1 ? String.format("(%ds)", requestResponseLength) : "";

        JMenuItem scanTasksWithConfigeOneTime = new JMenuItem(String.format("new task%s config one time",  suffixStr));
        JMenuItem scanTasksWithConfigeAllTime = new JMenuItem(String.format("new task%s config all time",   suffixStr));

        scanTasksWithConfigeOneTime.addActionListener(e-> scanTasksWithConfigeOneTime(e, httpRequestResponses));
        scanTasksWithConfigeAllTime.addActionListener(e-> scanTasksWithConfigeAllTime(e, httpRequestResponses));

        menuItemList.add(scanTasksWithConfigeOneTime);
        menuItemList.add(scanTasksWithConfigeAllTime);

        return menuItemList;
    }
    private void scanTasksWithConfigeOneTime(ActionEvent actionEvent, IHttpRequestResponse[] httpRequestResponses) {
        ScanTasksWithConfigeOneTimeExecutor scanTaskWithConfigeOneTime = new ScanTasksWithConfigeOneTimeExecutor(httpRequestResponses);
        ScanConfigurationDialog scanConfigurationDialog = new ScanConfigurationDialog(scanTaskWithConfigeOneTime);
        scanConfigurationDialog.showDialog();
    }

    private void scanTasksWithConfigeAllTime(ActionEvent actionEvent,  IHttpRequestResponse[] httpRequestResponses) {
        for (IHttpRequestResponse httpRequestResponse : httpRequestResponses) {
            ScanTasksWithConfigeAllTimeExecutor scanTaskWithConfigeAllTime = new ScanTasksWithConfigeAllTimeExecutor(httpRequestResponse);
            ScanConfigurationDialog scanConfigurationDialog = new ScanConfigurationDialog(scanTaskWithConfigeAllTime);
            scanConfigurationDialog.showDialog();
        }
    }


}
