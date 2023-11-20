package imps;

import com.alibaba.fastjson2.JSON;
import interfaces.SqlMapApi;
import jsonModel.ScanConfiguration;
import okhttp3.*;

public class SqlMapApiImpl  implements SqlMapApi {
    private String host;
    private int port;

    private OkHttpClient okHttpClient;
    private String base_url;

    private final static MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");

    public SqlMapApiImpl() {

    }

    public SqlMapApiImpl(String host, int port, OkHttpClient okHttpClient) {
        this.host = host;
        this.port = port;
        this.okHttpClient = okHttpClient;
        base_url = String.format("http://%s:%d", host, port);
    }

    public void setHost(String host) {
        this.host = host;
    }

    public  void setPort(int port) {
        this.port = port;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public void init(){
        okHttpClient = new OkHttpClient();
        base_url = String.format("http://%s:%d", host, port);
    }

    private String genApiUrl(String apiPath) {
        return String.format("%s%s", base_url, apiPath);
    }

    private Request buildRequest(String apiPath, String method, RequestBody requestBody) {
        String apiUrl = genApiUrl(apiPath);
        Request.Builder requestBuilder = new Request.Builder().url(apiUrl);
        requestBuilder.method(method, requestBody);
        return requestBuilder.build();
    }

    @Override
    public Call taskNew() {
        String apiPath = "/task/new";
        return okHttpClient.newCall(buildRequest(apiPath, "GET", null));
    }

    public Call scanStart(String taskId, ScanConfiguration scanConfiguration) {
        String apiPath = String.format("/scan/start/%s", taskId);
        String json = JSON.toJSONString(scanConfiguration);
        RequestBody requestBody = RequestBody.create(json, JSON_TYPE);
        return okHttpClient.newCall(buildRequest(apiPath, "POST", requestBody));
    }

    @Override
    public Call taskDelete(String taskId) {
        String apiPath = String.format("/task/delete/%s", taskId);
        return okHttpClient.newCall(buildRequest(apiPath, "GET", null));
    }

    @Override
    public Call getVersion() {
        String apiPath = "/version";
        return okHttpClient.newCall(buildRequest(apiPath, "GET", null));
    }
}
