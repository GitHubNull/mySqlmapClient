package imps;

import com.alibaba.fastjson2.JSON;
import interfaces.SqlMapApi;
import jsonModel.ScanConfiguration;
import okhttp3.*;

import java.util.HashMap;
import java.util.Map;

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

    private Request buildRequestWithParams(String apiPath, String method, RequestBody requestBody, Map<String, String> params) {
        // 构建带有查询参数的URL
        HttpUrl.Builder urlBuilder = HttpUrl.parse(genApiUrl(apiPath)).newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                urlBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }
        String urlWithParams = urlBuilder.build().toString();

        // 构建请求
        Request.Builder requestBuilder = new Request.Builder().url(urlWithParams);
        if (method.equalsIgnoreCase("GET")) {
            requestBuilder.get();
        } else {
            requestBuilder.method(method, requestBody);
        }

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

    public Call scanStartWithStarDateTime(String taskId, ScanConfiguration scanConfiguration, String startDateTime) {
        String apiPath = String.format("/scan/start_at_datetime/%s", taskId);
        String json = JSON.toJSONString(scanConfiguration);
        RequestBody requestBody = RequestBody.create(json, JSON_TYPE);
        if (startDateTime == null) {
            return null;
        }
        Map<String, String> params = new HashMap<>();
        params.put("start_datetime", startDateTime);
        return okHttpClient.newCall(buildRequestWithParams(apiPath, "POST", requestBody, params));
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
