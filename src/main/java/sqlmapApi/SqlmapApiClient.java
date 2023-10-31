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
        Call call = sqlMapApiImpl.taskNew();
        if (call == null) {
            return;
        }

        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                assert response.body() != null;
                TaskNewResponse taskNewResponse = JSON.parseObject(response.body().string(), TaskNewResponse.class);
                if (!taskNewResponse.getSuccess()) {
                    return;
                }

                ScanConfiguration scanConfiguration;
                try {
                    scanConfiguration = ScanConfigurationHelper.CommandLineToScanConfiguration(commandLineStr);
                } catch (IllegalAccessException ex) {
                    BurpExtender.stderr.println(ex.getMessage());
                    return;
                }


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
                            if (response != null && response.body() != null && response.body().contentLength() > 0) {
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
            if(response == null){
                return false;
            }
            if (response.isSuccessful()){
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            BurpExtender.stderr.println(e.getMessage());
            return false;
        }
    }
}
