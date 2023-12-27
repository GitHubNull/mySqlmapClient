package test;

import java.net.URL;

public class MyTest {
    public static void main(String[] args) {
        URL resource = MyTest.class.getResource("/icons/datetimePicker.png");
        if (resource == null) {
            throw new IllegalStateException("Icon not found");
        }
    }
}
