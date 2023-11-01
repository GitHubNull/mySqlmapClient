package excutors;

import burp.BurpExtender;
import burp.IHttpRequestResponse;
import interfaces.TaskCallback;
import sqlmapApi.SqlmapApiClient;
import util.GlobalEnv;

public class ScanTasksWithConfigeAllTimeExecutor implements TaskCallback{
    IHttpRequestResponse httpRequestResponse;
    public  ScanTasksWithConfigeAllTimeExecutor(IHttpRequestResponse httpRequestResponse) {
        this.httpRequestResponse = httpRequestResponse;
    }

    public void setHttpRequestResponse(IHttpRequestResponse httpRequestResponse) {
        this.httpRequestResponse = httpRequestResponse;
    }

    public IHttpRequestResponse getHttpRequestResponse() {
        return httpRequestResponse;
    }

    @Override
    public void onConfigComplete(String result) {
       if (result == null || result.trim().isEmpty()){
           BurpExtender.stderr.println("[!] onConfigComplete result is null");
           return;
       }

       BurpExtender.stdout.println("[*] onConfigComplete result is "+result);

        SqlmapApiClient sqlmapApiClient = GlobalEnv.sqlmapApiClient;
        if (sqlmapApiClient != null && sqlmapApiClient.isSqlMapApiImplSet()){
            try {
                sqlmapApiClient.startScan(result, httpRequestResponse);
            }catch (Exception e){
                BurpExtender.stderr.println("[!] startScan error:"+e.getMessage());
            }
        }
    }

    @Override
    public void onConfigError(String error) {

    }

    @Override
    public void onConfigCancel() {

    }
}
