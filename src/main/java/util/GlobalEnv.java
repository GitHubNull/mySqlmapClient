package util;

import entities.HistoryCommandLine;
import okhttp3.OkHttpClient;
import org.apache.commons.cli.Options;
import sqlmapApi.SqlmapApiClient;
import table_models.HistoryCommandLineTableModel;

import java.util.ArrayList;
import java.util.List;

public class GlobalEnv {
    public static String HOST = "127.0.0.1";
    public static int PORT = 5678;

    public static boolean IS_CONNECTED = false;

    public static String DEFAULT_COMMAND_LINE_STR = "";
    public static String SCAN_OPTIONS_HELP_TEXT = "";


//    public static List<String> scanConfigurationHistory = new ArrayList<>();

    public final static int MAX_HISTORY_COMMAND_LINE_LIST_LEN = 12; // max_history_command_line_list_len = 10
    public static List<HistoryCommandLine> HISTORY_COMMANDLINE_LIST = new ArrayList<>();
    public static HistoryCommandLineTableModel HISTORY_COMMANDLINE_TABLE_MODEL = new HistoryCommandLineTableModel();

    public static Options OPTIONS = new Options(); // scan_options_parser_data
    public static String TMP_REQUEST_FILE_DIR_PATH = "E:/tmp"; // tmp_Request_File_dir_Path

    public static OkHttpClient okHttpClient;

    public static SqlmapApiClient sqlmapApiClient;

    static {
        init();
    }

    public static void init() {
        ScanOptionsParserData.initScanOptionsParserData();
    }

}
