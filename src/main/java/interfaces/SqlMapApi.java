package interfaces;

import jsonModel.ScanConfiguration;
import okhttp3.Call;

public interface SqlMapApi {

    // new task
    // GET {{sqlmap_api}}/task/new
    Call taskNew();

    // scan task start
    // POST {{sqlmap_api}}/scan/{{taskid}}/start
    Call scanStart(String taskId, ScanConfiguration scanConfiguration);

    Call scanStartWithStarDateTime(String taskId, ScanConfiguration scanConfiguration, String startDateTime);

    // delete task
    // GET {{sqlmap_api}}/task/{{taskid}}/delete
    Call taskDelete(String taskId);

    // get sqlmap api version
    // GET {{sqlmap_api}}/version
    Call getVersion();
}
