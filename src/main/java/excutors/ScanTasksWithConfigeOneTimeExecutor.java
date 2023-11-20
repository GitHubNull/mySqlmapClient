package excutors;

import burp.BurpExtender;
import burp.IHttpRequestResponse;
import interfaces.TaskCallback;
import sqlmapApi.SqlmapApiClient;
import util.GlobalEnv;

import static burp.BurpExtender.stdout;

public class ScanTasksWithConfigeOneTimeExecutor implements TaskCallback {
    private IHttpRequestResponse[] httpRequestResponses;
    public ScanTasksWithConfigeOneTimeExecutor(IHttpRequestResponse[] httpRequestResponses) {
        this.httpRequestResponses = httpRequestResponses;
    }
    @Override
    public void onConfigComplete(String result) {
        if (result == null || result.trim().isEmpty()){
            BurpExtender.stderr.println("[!] onConfigComplete result is null");
            return;
        }

        stdout.println("[*] ScanTasksWithConfigeOneTimeExecutor.onConfigComplete result:"+result);

        SqlmapApiClient sqlmapApiClient = GlobalEnv.sqlmapApiClient;
        if (sqlmapApiClient != null && sqlmapApiClient.isSqlMapApiImplSet()){
            stdout.println("[*] ScanTasksWithConfigeOneTimeExecutor.onConfigComplete sqlmapApiClient is set");
            for (IHttpRequestResponse httpRequestResponse : httpRequestResponses) {
//                stdout.println("[*] ScanTasksWithConfigeOneTimeExecutor.onConfigComplete httpRequestResponse:"+httpRequestResponse);
                try {
                    sqlmapApiClient.startScan(result, httpRequestResponse);
                }catch (Exception e){
                    BurpExtender.stderr.println("[!] startScan error:"+e.getMessage());
                }
            }
        }else {
            BurpExtender.stderr.println("[!] sqlmapApiClient is null or not set");
        }

    }

    @Override
    public void onConfigError(String error) {
        stdout.println("onConfigError error:"+error);
    }

    @Override
    public void onConfigCancel() {
        stdout.println("onConfigCancel");
    }
}
