package interfaces;

public interface TaskCallback {
    void onConfigComplete(String result);
    void onConfigError(String error);
    void onConfigCancel();
}
