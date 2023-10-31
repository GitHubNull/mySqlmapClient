package interfaces;

public interface TaskCallback {
    void onComplete(String result);
    void onError(String error);
    void onCancel();
}
