package util;

import entities.ScanTaskHistoryCommandLine;
import okhttp3.OkHttpClient;
import org.apache.commons.cli.Options;
import sqlmapApi.SqlmapApiClient;

import java.util.ArrayList;
import java.util.List;

public class GlobalEnv {
    public static String HOST = "127.0.0.1";
    public static int PORT = 5678;

    public static boolean IS_CONNECTED = false;

    public static String DEFAULT_COMMAND_LINE_STR = "";
    public static String SCAN_OPTIONS_HELP_TEXT = "";


    public static List<String> scanConfigurationHistory = new ArrayList<>();
    public static List<ScanTaskHistoryCommandLine> scanTaskHistoryCommandLineList = new ArrayList<>();

    public static Options SCAN_OPTIONS_PARSER_DATA = new Options(); // scan_options_parser_data
    public static String TMP_REQUEST_FILE_DIR_PATH = "E:/tmp"; // tmp_Request_File_dir_Path

    public static OkHttpClient okHttpClient;

    public static SqlmapApiClient sqlmapApiClient;



}
