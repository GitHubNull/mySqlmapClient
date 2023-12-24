package util;

import entities.HistoryCommandLine;
import entities.OptionsCommandLine;
import okhttp3.OkHttpClient;
import org.apache.commons.cli.Options;
import sqlmapApi.SqlmapApiClient;
import table_models.CommandLineTableModel;
import table_models.HistoryCommandLineTableModel;

import java.util.ArrayList;
import java.util.List;

public class GlobalEnv {
    public static String HOST = "127.0.0.1";
    public static int PORT = 8775;

    public static boolean IS_CONNECTED = false;

    public static final String COMMIT_ACTION = "commit"; // commit_action

    public static String DEFAULT_COMMAND_LINE_STR = "";
    public static String SCAN_OPTIONS_HELP_TEXT = "";

    public static List<String> OPTIONS_KEYWORDS = new ArrayList<>(); // options_keywords
//    public static CompletionProvider provider;

    public static final String HISTORY_COMMANDLINE_LIST_SETTING_KEY = "historyCommandlineList";
    public static final String OPTIONS_COMMANDLINE_LIST_SETTING_KEY = "optionsCommandLineList";

    public static int  CommandLineTableModelId = 0;
    public static int  HistoryCommandLineTableModelId = 0;




//    public static List<String> scanConfigurationHistory = new ArrayList<>();

    public final static int MAX_HISTORY_COMMAND_LINE_LIST_LEN = 12; // max_history_command_line_list_len = 10
    public static List<HistoryCommandLine> HISTORY_COMMANDLINE_LIST = new ArrayList<>();

    public static HistoryCommandLineTableModel HISTORY_COMMANDLINE_TABLE_MODEL = new HistoryCommandLineTableModel();

    public static List<OptionsCommandLine>  OPTIONS_COMMANDLINE_LIST = new ArrayList<>(); // Options CommandLine list
    public static CommandLineTableModel COMMON_COMMANDLINE_TABLE_MODEL = new CommandLineTableModel();

    public static Options OPTIONS = new Options(); // scan_options_parser_data
    public static String TMP_REQUEST_FILE_DIR_PATH = System.getProperty("java.io.tmpdir"); // tmp_Request_File_dir_Path

    public static final String SETTING_KEY_HOST = "host";
    public static final String SETTING_KEY_PORT = "port";
    public static final String SETTING_KEY_TMP_REQUEST_FILE_DIR_PATH = "tmpRequestFileDirPath";

    public static OkHttpClient okHttpClient;

    public static SqlmapApiClient sqlmapApiClient;

    static {
        init();
//        provider = CompletionProviderInitiator.createCompletionProvider();
    }

    public static void init() {
        ScanOptionsParserData.initScanOptionsParserData();
    }

}
