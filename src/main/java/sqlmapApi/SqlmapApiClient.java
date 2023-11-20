package sqlmapApi;

import burp.BurpExtender;
import burp.IHttpRequestResponse;
import com.alibaba.fastjson2.JSON;
import imps.SqlMapApiImpl;
import jsonModel.ScanConfiguration;
import jsonModel.TaskNewResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.GlobalEnv;
import util.ScanConfigurationHelper;
import util.TmpRequestFileHelper;

import java.io.IOException;

public class SqlmapApiClient {

    SqlMapApiImpl sqlMapApiImpl;

    public SqlmapApiClient(SqlMapApiImpl sqlMapApiImpl) {
        this.sqlMapApiImpl = sqlMapApiImpl;
    }

    public SqlmapApiClient() {

    }

    public void setSqlMapApiImpl(SqlMapApiImpl sqlMapApiImpl) {
        this.sqlMapApiImpl = sqlMapApiImpl;
    }

    public boolean isSqlMapApiImplSet() {
        return  sqlMapApiImpl != null;
    }

    public void startScan(String commandLineStr, IHttpRequestResponse httpRequestResponse) {
        BurpExtender.stdout.println("[INFO] sqlmapApiClient.startScan: commandLineStr: " + commandLineStr);
        Call call = sqlMapApiImpl.taskNew();
        if (call == null) {
            BurpExtender.stderr
                    .println("[ERROR] sqlmapApiClient.startScan: sqlMapApiImpl.taskNew() is null");
            return;
        }
        BurpExtender.stdout
                .println("[INFO] sqlmapApiClient.startScan: sqlMapApiImpl.taskNew() is not null");


        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.body() == null){
                    BurpExtender.stderr
                            .println("[ERROR] sqlmapApiClient.startScan: response.body() is null");
                    return;
                }

                BurpExtender.stdout
                        .println("[INFO] sqlmapApiClient.startScan: response.body() is not null");

                TaskNewResponse taskNewResponse = JSON.parseObject(response.body().string(), TaskNewResponse.class);
                if (!taskNewResponse.getSuccess()) {
                    BurpExtender.stderr
                            .println("[ERROR] sqlmapApiClient.startScan: taskNewResponse.getSuccess() is false");
                    return;
                }

                BurpExtender.stdout
                        .println("[INFO] sqlmapApiClient.startScan: taskNewResponse.getSuccess() is true");

                ScanConfiguration scanConfiguration;
                try {
                    BurpExtender.stdout
                            .println("[INFO] sqlmapApiClient.startScan: scanConfiguration is null");
                    scanConfiguration = ScanConfigurationHelper.CommandLineToScanConfiguration(commandLineStr);
                } catch (IllegalAccessException ex) {
                    BurpExtender.stderr.println(ex.getMessage());
                    return;
                }

                if (scanConfiguration == null){
                    BurpExtender.stderr.println("scan configuration is null");
                    return;
                }
                BurpExtender.stdout.println(String.format("after parse command line to scan configuration: %s",  scanConfiguration));


                // push task to sqlmapApi
                if (null == scanConfiguration.getRequestFile() || scanConfiguration.getRequestFile().isEmpty()) {
                    final String tmpRequestFilePath = TmpRequestFileHelper.writeBytesToFile(httpRequestResponse.getRequest());
                    if (null == tmpRequestFilePath) {
                        Call call1 = deleteScanTask(taskNewResponse.getTaskid());
                        if (null != call1) {
                            call1.enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    BurpExtender.stderr.println(e.getMessage());
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    assert response.body() != null;
                                    BurpExtender.stdout.println(response.body().string());
                                }
                            });
                        }
                        return;
                    }
                    scanConfiguration.setRequestFile(tmpRequestFilePath);
                }

                Call call2 = addScanTask(taskNewResponse.getTaskid(), scanConfiguration);

                if (null != call2) {
                    call2.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            BurpExtender.stderr.println(e.getMessage());
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if (response.body() != null && response.body().contentLength() > 0) {
                                BurpExtender.stdout.println(response.body().string());
                                GlobalEnv.HISTORY_COMMANDLINE_TABLE_MODEL.addScanTaskHistoryCommandLine(commandLineStr);
                            } else {
                                BurpExtender.stdout.println("response is null");
                            }
                        }
                    });

                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                BurpExtender.stderr.println(e.getMessage());
            }
        });

        BurpExtender.stdout.println("call had called.");
    }

    public Call addScanTask(String taskId, ScanConfiguration scanConfiguration) {
        if ((null == taskId || taskId.trim().isEmpty()) || (null == scanConfiguration)) {
            return null;
        }

        return sqlMapApiImpl.scanStart(taskId, scanConfiguration);
    }

    public Call deleteScanTask(String taskId) {
        if (null == taskId || taskId.trim().isEmpty()) {
            return null;
        }
        return sqlMapApiImpl.taskDelete(taskId);
    }

    public boolean isConnected() {
        Call call = sqlMapApiImpl.getVersion();
        try {
            Response response = call.execute();
            return response.isSuccessful();
        } catch (IOException e) {
            BurpExtender.stderr.println(e.getMessage());
            return false;
        }
    }
}
