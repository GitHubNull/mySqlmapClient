package interfaces;

public interface TaskCallback {
    void onConfigComplete(String result);
    void onConfigCompleteAtDateTime(String result, String startDateTime);
    void onConfigError(String error);
    void onConfigCancel();
}
