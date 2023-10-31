package util;

import burp.BurpExtender;
import jsonModel.ScanConfiguration;
import org.apache.commons.cli.*;


import java.lang.reflect.Field;

public class ScanConfigurationHelper {

    public static ScanConfiguration CommandLineToScanConfiguration(String commandLine) throws IllegalAccessException {
        if (null == commandLine || commandLine.trim().isEmpty()) {
            return null;
        }

        Options options = GlobalEnv.OPTIONS;

        String[] commandLineArgs = commandLine.trim().split(" ");

        CommandLine cmd;
        CommandLineParser parser = new DefaultParser();
//        HelpFormatter helper = new HelpFormatter();
        try {
            cmd = parser.parse(options, commandLineArgs);
        } catch (ParseException ex) {
            BurpExtender.stderr.println(ex.getMessage());
            return null;
        }

        ScanConfiguration scanConfiguration = new ScanConfiguration();

        //获取实体类 返回的是一个数组 数组的数据就是实体类中的字段
        Field[] fields = scanConfiguration.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            String fieldName = field.getName();
            Class<?> classes = field.getType();

            if (!cmd.hasOption(fieldName)) {
                continue;
            }


            String value = cmd.getOptionValue(fieldName);
            if (classes.equals(String.class)) {
//                    BurpExtender.stdout.println(String.format("%s: %s", fieldName, value));
                field.set(scanConfiguration, value);
            } else if (classes.equals(Boolean.class)) {
                Boolean boolean_value = Boolean.valueOf(value);
                field.set(scanConfiguration, boolean_value);
//                    BurpExtender.stdout.println(String.format("%s: %s", fieldName, boolean_value));
            } else if (classes.equals(Integer.class)) {
                Integer integer_value = Integer.valueOf(value);
                field.set(scanConfiguration, integer_value);
//                    BurpExtender.stdout.println(String.format("%s: %s", fieldName, integer_value));
            }


        }

        return scanConfiguration;
    }
}
