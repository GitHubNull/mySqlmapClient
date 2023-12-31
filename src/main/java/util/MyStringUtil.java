package util;

import burp.BurpExtender;
import jsonModel.ScanConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.regex.Pattern;

public class MyStringUtil {
    public static boolean isPositiveInteger(String numberStr) {
        if (null == numberStr || numberStr.trim().isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile("[0-9]+");
        if (!pattern.matcher(numberStr).matches()) {
            return false;
        }

        return !"0".equals(numberStr.substring(0, 1));

    }

    public static boolean isTruePortNumber(String portStr) {
        if (!isPositiveInteger(portStr)) {
            return false;
        }

        int number = Integer.parseInt(portStr);
        return 0 < number && number < 65535;
    }

    public static String getDateTimeStr(int type) {
        if (type == 1) {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        } else if (type == 2) {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + "--" + UUID.randomUUID().toString().substring(0, 8);
        }
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

    }


    public static String genTaskName() {
        return String.format("task-%s", getDateTimeStr(0));
    }


    public static String genScanOptionsHelpText() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<!DOCTYPE html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        .keyword {\n" +
                "            font-weight: bold; \n" +
                "            background-color: rgb(217, 204, 19);\n" +
                "        }\n" +
                "\n" +
                "        .default_value {\n" +
                "            font-weight: bold; \n" +
                "            background-color: rgba(95, 242, 3, 0.799);\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <ol>");


        ScanConfiguration scanConfiguration = new ScanConfiguration();

        //获取实体类 返回的是一个数组 数组的数据就是实体类中的字段
        Field[] fields = scanConfiguration.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            String fieldName = field.getName();
            Class<?> classes = field.getType();

            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);

            Object value;
            try {
                Method method = scanConfiguration.getClass().getMethod(getter);
                value = method.invoke(scanConfiguration);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
                BurpExtender.stderr.println(exception.getMessage());
                continue;
            }

            String tmp = String.format("<li><b><span class=\"keyword\">%s</span></b></li>\n", fieldName);


            if (classes.equals(String.class) && null != value) {
                tmp = String.format("<li><b><span class=\"keyword\">%s</span></b> default value: <span class=\"default_value\">%s</span></li>\n", fieldName, value);
            } else if (classes.equals(Boolean.class) && null != value) {
                tmp = String.format("<li><b><span class=\"keyword\">%s</span></b> default value: <span class=\"default_value\">%b</span></li>\n", fieldName, value);
            } else if (classes.equals(Integer.class) && null != value) {
                tmp = String.format("<li><b><span class=\"keyword\">%s</span></b> default value: <span class=\"default_value\">%d</span></li>\n", fieldName, (Integer) value);
            }
            stringBuilder.append(tmp);
        }

        stringBuilder.append("    </ol>\n" +
                "</body>\n" +
                "\n" +
                "</html>");

        return stringBuilder.toString();
    }

    public static boolean isValidIPAddress(String host) {
        String ipv4Pattern = "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";
        return host.matches(ipv4Pattern);
    }

    public static boolean isValidPort(String port) {
        try {
            int intPort = Integer.parseInt(port);
            return intPort >= 0 && intPort <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
