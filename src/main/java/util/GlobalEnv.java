package util;

import entities.HistoryCommandLine;
import okhttp3.OkHttpClient;
import org.apache.commons.cli.Option;
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

    public static Options SCAN_OPTIONS_PARSER_DATA = new Options(); // scan_options_parser_data
    public static String TMP_REQUEST_FILE_DIR_PATH = "E:/tmp"; // tmp_Request_File_dir_Path

    public static OkHttpClient okHttpClient;

    public static SqlmapApiClient sqlmapApiClient;

    static {
        init();
    }

    public static void init() {
        initScanOptionsParserData();
    }

    private static void initScanOptionsParserData() {

        /**
         *   Target:
         *     At least one of these options has to be provided to define the
         *     target(s)
         *
         *     -u URL, --url=URL   Target URL (e.g. "http://www.site.com/vuln.php?id=1")
         *     -d DIRECT           Connection string for direct database connection
         *     -l LOGFILE          Parse target(s) from Burp or WebScarab proxy log file
         *     -m BULKFILE         Scan multiple targets given in a textual file
         *     -r REQUESTFILE      Load HTTP request from a file
         *     -g GOOGLEDORK       Process Google dork results as target URLs
         *     -c CONFIGFILE       Load options from a configuration INI file
         */

        Option verboseOption = Option.builder("v")
                .longOpt("verbose")
                .hasArg()
                .argName("level")
                .desc("Verbosity level: 0-6 (default 1)")
                .build();

        Option urlOption = Option.builder("u")
                .longOpt("url")
                .hasArg()
                .argName("URL")
                .desc("Target URL (e.g. \"http://www.site.com/vuln.php?id=1\")")
                .build();

        Option directOption = Option.builder("d")
                .longOpt("direct")
                .hasArg()
                .argName("connection")
                .desc("Connection string for direct database connection")
                .build();

        Option logfileOption = Option.builder("l")
                .longOpt("logfile")
                .hasArg()
                .argName("file")
                .desc("Parse target(s) from Burp or WebScarab proxy log file")
                .build();

        Option bulkfileOption = Option.builder("m")
                .longOpt("bulkfile")
                .hasArg()
                .argName("file")
                .desc("Scan multiple targets given in a textual file")
                .build();

        Option requestfileOption = Option.builder("r")
                .longOpt("requestfile")
                .hasArg()
                .argName("file")
                .desc("Load HTTP request from a file")
                .build();

        Option googledorkOption = Option.builder("g")
                .longOpt("googledork")
                .hasArg()
                .argName("dork")
                .desc("Process Google dork results as target URLs")
                .build();

        Option configfileOption = Option.builder("c")
                .longOpt("configfile")
                .hasArg()
                .argName("file")
                .desc("Load options from a configuration INI file")
                .build();

        /**
         * Request:
         *     These options can be used to specify how to connect to the target URL
         *
         *     -A AGENT, --user..  HTTP User-Agent header value
         *     -H HEADER, --hea..  Extra header (e.g. "X-Forwarded-For: 127.0.0.1")
         *     --method=METHOD     Force usage of given HTTP method (e.g. PUT)
         *     --data=DATA         Data string to be sent through POST (e.g. "id=1")
         *     --param-del=PARA..  Character used for splitting parameter values (e.g. &)
         *     --cookie=COOKIE     HTTP Cookie header value (e.g. "PHPSESSID=a8d127e..")
         *     --cookie-del=COO..  Character used for splitting cookie values (e.g. ;)
         *     --live-cookies=L..  Live cookies file used for loading up-to-date values
         *     --load-cookies=L..  File containing cookies in Netscape/wget format
         *     --drop-set-cookie   Ignore Set-Cookie header from response
         *     --mobile            Imitate smartphone through HTTP User-Agent header
         *     --random-agent      Use randomly selected HTTP User-Agent header value
         *     --host=HOST         HTTP Host header value
         *     --referer=REFERER   HTTP Referer header value
         *     --headers=HEADERS   Extra headers (e.g. "Accept-Language: fr\nETag: 123")
         *     --auth-type=AUTH..  HTTP authentication type (Basic, Digest, Bearer, ...)
         *     --auth-cred=AUTH..  HTTP authentication credentials (name:password)
         *     --auth-file=AUTH..  HTTP authentication PEM cert/private key file
         *     --abort-code=ABO..  Abort on (problematic) HTTP error code(s) (e.g. 401)
         *     --ignore-code=IG..  Ignore (problematic) HTTP error code(s) (e.g. 401)
         *     --ignore-proxy      Ignore system default proxy settings
         *     --ignore-redirects  Ignore redirection attempts
         *     --ignore-timeouts   Ignore connection timeouts
         *     --proxy=PROXY       Use a proxy to connect to the target URL
         *     --proxy-cred=PRO..  Proxy authentication credentials (name:password)
         *     --proxy-file=PRO..  Load proxy list from a file
         *     --proxy-freq=PRO..  Requests between change of proxy from a given list
         *     --tor               Use Tor anonymity network
         *     --tor-port=TORPORT  Set Tor proxy port other than default
         *     --tor-type=TORTYPE  Set Tor proxy type (HTTP, SOCKS4 or SOCKS5 (default))
         *     --check-tor         Check to see if Tor is used properly
         *     --delay=DELAY       Delay in seconds between each HTTP request
         *     --timeout=TIMEOUT   Seconds to wait before timeout connection (default 30)
         *     --retries=RETRIES   Retries when the connection timeouts (default 3)
         *     --retry-on=RETRYON  Retry request on regexp matching content (e.g. "drop")
         *     --randomize=RPARAM  Randomly change value for given parameter(s)
         *     --safe-url=SAFEURL  URL address to visit frequently during testing
         *     --safe-post=SAFE..  POST data to send to a safe URL
         *     --safe-req=SAFER..  Load safe HTTP request from a file
         *     --safe-freq=SAFE..  Regular requests between visits to a safe URL
         *     --skip-urlencode    Skip URL encoding of payload data
         *     --csrf-token=CSR..  Parameter used to hold anti-CSRF token
         *     --csrf-url=CSRFURL  URL address to visit for extraction of anti-CSRF token
         *     --csrf-method=CS..  HTTP method to use during anti-CSRF token page visit
         *     --csrf-data=CSRF..  POST data to send during anti-CSRF token page visit
         *     --csrf-retries=C..  Retries for anti-CSRF token retrieval (default 0)
         *     --force-ssl         Force usage of SSL/HTTPS
         *     --chunked           Use HTTP chunked transfer encoded (POST) requests
         *     --hpp               Use HTTP parameter pollution method
         *     --eval=EVALCODE     Evaluate provided Python code before the request (e.g.
         *                         "import hashlib;id2=hashlib.md5(id).hexdigest()")
         */


        Option userAgentOption = Option.builder("A")
                .longOpt("user-agent")
                .hasArg()
                .argName("AGENT")
                .desc("HTTP User-Agent header value")
                .build();

        Option headerOption = Option.builder("H")
                .longOpt("header")
                .hasArg()
                .argName("HEADER")
                .desc("Extra header (e.g. \"X-Forwarded-For: 127.0.0.1\")")
                .build();

        Option methodOption = Option.builder()
                .longOpt("method")
                .hasArg()
                .argName("METHOD")
                .desc("Force usage of given HTTP method (e.g. PUT)")
                .build();

        Option dataOption = Option.builder()
                .longOpt("data")
                .hasArg()
                .argName("DATA")
                .desc("Data string to be sent through POST (e.g. \"id=1\")")
                .build();

        Option paramDelOption = Option.builder()
                .longOpt("param-del")
                .hasArg()
                .argName("PARAM_DEL")
                .desc("Character used for splitting parameter values (e.g. &)")
                .build();

        Option cookieOption = Option.builder()
                .longOpt("cookie")
                .hasArg()
                .argName("COOKIE")
                .desc("HTTP Cookie header value (e.g. \"PHPSESSID=a8d127e..\")")
                .build();

        Option cookieDelOption = Option.builder()
                .longOpt("cookie-del")
                .hasArg()
                .argName("COOKIE_DEL")
                .desc("Character used for splitting cookie values (e.g. ;)")
                .build();

        Option liveCookiesOption = Option.builder()
                .longOpt("live-cookies")
                .hasArg()
                .argName("LIVE_COOKIES")
                .desc("Live cookies file used for loading up-to-date values")
                .build();

        Option loadCookiesOption = Option.builder()
                .longOpt("load-cookies")
                .hasArg()
                .argName("LOAD_COOKIES")
                .desc("File containing cookies in Netscape/wget format")
                .build();

        Option dropSetCookieOption = Option.builder()
                .longOpt("drop-set-cookie")
                .desc("Ignore Set-Cookie header from response")
                .build();

        Option mobileOption = Option.builder()
                .longOpt("mobile")
                .desc("Imitate smartphone through HTTP User-Agent header")
                .build();

        Option randomAgentOption = Option.builder()
                .longOpt("random-agent")
                .desc("Use randomly selected HTTP User-Agent header value")
                .build();

        Option hostOption = Option.builder()
                .longOpt("host")
                .hasArg()
                .argName("HOST")
                .desc("HTTP Host header value")
                .build();

        Option refererOption = Option.builder()
                .longOpt("referer")
                .hasArg()
                .argName("REFERER")
                .desc("HTTP Referer header value")
                .build();

        Option headersOption = Option.builder()
                .longOpt("headers")
                .hasArg()
                .argName("HEADERS")
                .desc("Extra headers (e.g. \"Accept-Language: fr\\nETag: 123\")")
                .build();

        Option authTypeOption = Option.builder()
                .longOpt("auth-type")
                .hasArg()
                .argName("AUTH_TYPE")
                .desc("HTTP authentication type (Basic, Digest, Bearer, ...)")
                .build();

        Option authCredOption = Option.builder()
                .longOpt("auth-cred")
                .hasArg()
                .argName("AUTH_CRED")
                .desc("HTTP authentication credentials (name:password)")
                .build();

        Option authFileOption = Option.builder()
                .longOpt("auth-file")
                .hasArg()
                .argName("AUTH_FILE")
                .desc("HTTP authentication PEM cert/private key file")
                .build();

        Option abortCodeOption = Option.builder()
                .longOpt("abort-code")
                .hasArg()
                .argName("ABORT_CODE")
                .desc("Abort on (problematic) HTTP error code(s) (e.g. 401)")
                .build();

        /**
         *   Optimization:
         *     These options can be used to optimize the performance of sqlmap
         *
         *     -o                  Turn on all optimization switches
         *     --predict-output    Predict common queries output
         *     --keep-alive        Use persistent HTTP(s) connections
         *     --null-connection   Retrieve page length without actual HTTP response body
         *     --threads=THREADS   Max number of concurrent HTTP(s) requests (default 1)
         */

        Option optimizationOption = Option.builder("o")
                .longOpt("optimization")
                .desc("Turn on all optimization switches")
                .build();

        Option predictOutputOption = Option.builder()
                .longOpt("predict-output")
                .desc("Predict common queries output")
                .build();

        Option keepAliveOption = Option.builder()
                .longOpt("keep-alive")
                .desc("Use persistent HTTP(s) connections")
                .build();

        Option nullConnectionOption = Option.builder()
                .longOpt("null-connection")
                .desc("Retrieve page length without actual HTTP response body")
                .build();

        Option threadsOption = Option.builder()
                .longOpt("threads")
                .hasArg()
                .argName("THREADS")
                .desc("Max number of concurrent HTTP(s) requests (default 1)")
                .build();


        /**
         *   Injection:
         *     These options can be used to specify which parameters to test for,
         *     provide custom injection payloads and optional tampering scripts
         *
         *     -p TESTPARAMETER    Testable parameter(s)
         *     --skip=SKIP         Skip testing for given parameter(s)
         *     --skip-static       Skip testing parameters that not appear to be dynamic
         *     --param-exclude=..  Regexp to exclude parameters from testing (e.g. "ses")
         *     --param-filter=P..  Select testable parameter(s) by place (e.g. "POST")
         *     --dbms=DBMS         Force back-end DBMS to provided value
         *     --dbms-cred=DBMS..  DBMS authentication credentials (user:password)
         *     --os=OS             Force back-end DBMS operating system to provided value
         *     --invalid-bignum    Use big numbers for invalidating values
         *     --invalid-logical   Use logical operations for invalidating values
         *     --invalid-string    Use random strings for invalidating values
         *     --no-cast           Turn off payload casting mechanism
         *     --no-escape         Turn off string escaping mechanism
         *     --prefix=PREFIX     Injection payload prefix string
         *     --suffix=SUFFIX     Injection payload suffix string
         *     --tamper=TAMPER     Use given script(s) for tampering injection data
         */

        Option testParameterOption = Option.builder("p")
                .longOpt("testParameter")
                .hasArg()
                .argName("testParameter")
                .desc("Testable parameter(s)")
                .build();

        Option skipOption = Option.builder()
                .longOpt("skip")
                .hasArg()
                .argName("skip")
                .desc("Skip testing for given parameter(s)")
                .build();

        Option skipStaticOption = Option.builder()
                .longOpt("skip-static")
                .desc("Skip testing parameters that not appear to be dynamic")
                .build();

        Option paramExcludeOption = Option.builder()
                .longOpt("param-exclude")
                .hasArg()
                .argName("paramExclude")
                .desc("Regexp to exclude parameters from testing (e.g. \"ses\")")
                .build();

        Option paramFilterOption = Option.builder()
                .longOpt("param-filter")
                .hasArg()
                .argName("paramFilter")
                .desc("Select testable parameter(s) by place (e.g. \"POST\")")
                .build();

        Option dbmsOption = Option.builder()
                .longOpt("dbms")
                .hasArg()
                .argName("DBMS")
                .desc("Force back-end DBMS to provided value")
                .build();

        Option dbmsCredOption = Option.builder()
                .longOpt("dbms-cred")
                .hasArg()
                .argName("dbmsCred")
                .desc("DBMS authentication credentials (user:password)")
                .build();

        Option osOption = Option.builder()
                .longOpt("os")
                .hasArg()
                .argName("OS")
                .desc("Force back-end DBMS operating system to provided value")
                .build();

        Option invalidBignumOption = Option.builder()
                .longOpt("invalid-bignum")
                .desc("Use big numbers for invalidating values")
                .build();

        Option invalidLogicalOption = Option.builder()
                .longOpt("invalid-logical")
                .desc("Use logical operations for invalidating values")
                .build();

        Option invalidStringOption = Option.builder()
                .longOpt("invalid-string")
                .desc("Use random strings for invalidating values")
                .build();

        Option noCastOption = Option.builder()
                .longOpt("no-cast")
                .desc("Turn off payload casting mechanism")
                .build();

        Option noEscapeOption = Option.builder()
                .longOpt("no-escape")
                .desc("Turn off string escaping mechanism")
                .build();

        Option prefixOption = Option.builder()
                .longOpt("prefix")
                .hasArg()
                .argName("prefix")
                .desc("Injection payload prefix string")
                .build();

        Option suffixOption = Option.builder()
                .longOpt("suffix")
                .hasArg()
                .argName("suffix")
                .desc("Injection payload suffix string")
                .build();

        Option tamperOption = Option.builder()
                .longOpt("tamper")
                .hasArg()
                .argName("tamper")
                .desc("Use given script(s) for tampering injection data")
                .build();


        /**
         * Detection:
         *     These options can be used to customize the detection phase
         *
         *     --level=LEVEL       Level of tests to perform (1-5, default 1)
         *     --risk=RISK         Risk of tests to perform (1-3, default 1)
         *     --string=STRING     String to match when query is evaluated to True
         *     --not-string=NOT..  String to match when query is evaluated to False
         *     --regexp=REGEXP     Regexp to match when query is evaluated to True
         *     --code=CODE         HTTP code to match when query is evaluated to True
         *     --smart             Perform thorough tests only if positive heuristic(s)
         *     --text-only         Compare pages based only on the textual content
         *     --titles            Compare pages based only on their titles
         */

        Option levelOption = Option.builder()
                .longOpt("level")
                .hasArg()
                .argName("LEVEL")
                .desc("Level of tests to perform (1-5, default 1)")
                .build();

        Option riskOption = Option.builder()
                .longOpt("risk")
                .hasArg()
                .argName("RISK")
                .desc("Risk of tests to perform (1-3, default 1)")
                .build();

        Option stringOption = Option.builder()
                .longOpt("string")
                .hasArg()
                .argName("STRING")
                .desc("String to match when query is evaluated to True")
                .build();

        Option notStringOption = Option.builder()
                .longOpt("not-string")
                .hasArg()
                .argName("NOT_STRING")
                .desc("String to match when query is evaluated to False")
                .build();

        Option regexpOption = Option.builder()
                .longOpt("regexp")
                .hasArg()
                .argName("REGEXP")
                .desc("Regexp to match when query is evaluated to True")
                .build();

        Option codeOption = Option.builder()
                .longOpt("code")
                .hasArg()
                .argName("CODE")
                .desc("HTTP code to match when query is evaluated to True")
                .build();

        Option smartOption = Option.builder()
                .longOpt("smart")
                .desc("Perform thorough tests only if positive heuristic(s)")
                .build();

        Option textOnlyOption = Option.builder()
                .longOpt("text-only")
                .desc("Compare pages based only on the textual content")
                .build();

        Option titlesOption = Option.builder()
                .longOpt("titles")
                .desc("Compare pages based only on their titles")
                .build();

        /**
         *  Techniques:
         *     These options can be used to tweak testing of specific SQL injection
         *     techniques
         *
         *     --technique=TECH..  SQL injection techniques to use (default "BEUSTQ")
         *     --time-sec=TIMESEC  Seconds to delay the DBMS response (default 5)
         *     --union-cols=UCOLS  Range of columns to test for UNION query SQL injection
         *     --union-char=UCHAR  Character to use for bruteforcing number of columns
         *     --union-from=UFROM  Table to use in FROM part of UNION query SQL injection
         *     --union-values=U..  Column values to use for UNION query SQL injection
         *     --dns-domain=DNS..  Domain name used for DNS exfiltration attack
         *     --second-url=SEC..  Resulting page URL searched for second-order response
         *     --second-req=SEC..  Load second-order HTTP request from file
         */

        Option techniqueOption = Option.builder()
                .longOpt("technique")
                .hasArg()
                .argName("TECHNIQUE")
                .desc("SQL injection techniques to use (default \"BEUSTQ\")")
                .build();

        Option timeSecOption = Option.builder()
                .longOpt("timeSec")
                .hasArg()
                .argName("TIMESEC")
                .desc("Seconds to delay the DBMS response (default 5)")
                .build();

        Option unionColsOption = Option.builder()
                .longOpt("unionCols")
                .hasArg()
                .argName("UCOLS")
                .desc("Range of columns to test for UNION query SQL injection")
                .build();

        Option unionCharOption = Option.builder()
                .longOpt("unionChar")
                .hasArg()
                .argName("UCHAR")
                .desc("Character to use for bruteforcing number of columns")
                .build();

        Option unionFromOption = Option.builder()
                .longOpt("unionFrom")
                .hasArg()
                .argName("UFROM")
                .desc("Table to use in FROM part of UNION query SQL injection")
                .build();

        Option unionValuesOption = Option.builder()
                .longOpt("unionValues")
                .hasArg()
                .argName("UVALUES")
                .desc("Column values to use for UNION query SQL injection")
                .build();

        Option dnsDomainOption = Option.builder()
                .longOpt("dnsDomain")
                .hasArg()
                .argName("DNSDOMAIN")
                .desc("Domain name used for DNS exfiltration attack")
                .build();

        Option secondUrlOption = Option.builder()
                .longOpt("secondUrl")
                .hasArg()
                .argName("SECONDURL")
                .desc("Resulting page URL searched for second-order response")
                .build();

        Option secondReqOption = Option.builder()
                .longOpt("secondReq")
                .hasArg()
                .argName("SECONDREQ")
                .desc("Load second-order HTTP request from file")
                .build();


        /**
         *  Fingerprint:
         *     -f, --fingerprint   Perform an extensive DBMS version fingerprint
         */

        Option fingerprintOption = Option.builder("f")
                .longOpt("fingerprint")
                .desc("Perform an extensive DBMS version fingerprint")
                .build();


        /**
         *   Enumeration:
         *     These options can be used to enumerate the back-end database
         *     management system information, structure and data contained in the
         *     tables
         *
         *     -a, --all           Retrieve everything
         *     -b, --banner        Retrieve DBMS banner
         *     --current-user      Retrieve DBMS current user
         *     --current-db        Retrieve DBMS current database
         *     --hostname          Retrieve DBMS server hostname
         *     --is-dba            Detect if the DBMS current user is DBA
         *     --users             Enumerate DBMS users
         *     --passwords         Enumerate DBMS users password hashes
         *     --privileges        Enumerate DBMS users privileges
         *     --roles             Enumerate DBMS users roles
         *     --dbs               Enumerate DBMS databases
         *     --tables            Enumerate DBMS database tables
         *     --columns           Enumerate DBMS database table columns
         *     --schema            Enumerate DBMS schema
         *     --count             Retrieve number of entries for table(s)
         *     --dump              Dump DBMS database table entries
         *     --dump-all          Dump all DBMS databases tables entries
         *     --search            Search column(s), table(s) and/or database name(s)
         *     --comments          Check for DBMS comments during enumeration
         *     --statements        Retrieve SQL statements being run on DBMS
         *     -D DB               DBMS database to enumerate
         *     -T TBL              DBMS database table(s) to enumerate
         *     -C COL              DBMS database table column(s) to enumerate
         *     -X EXCLUDE          DBMS database identifier(s) to not enumerate
         *     -U USER             DBMS user to enumerate
         *     --exclude-sysdbs    Exclude DBMS system databases when enumerating tables
         *     --pivot-column=P..  Pivot column name
         *     --where=DUMPWHERE   Use WHERE condition while table dumping
         *     --start=LIMITSTART  First dump table entry to retrieve
         *     --stop=LIMITSTOP    Last dump table entry to retrieve
         *     --first=FIRSTCHAR   First query output word character to retrieve
         *     --last=LASTCHAR     Last query output word character to retrieve
         *     --sql-query=SQLQ..  SQL statement to be executed
         *     --sql-shell         Prompt for an interactive SQL shell
         *     --sql-file=SQLFILE  Execute SQL statements from given file(s)
         */

        Option allOption = Option.builder("a")
                .longOpt("all")
                .desc("Retrieve everything")
                .build();

        Option bannerOption = Option.builder("b")
                .longOpt("banner")
                .desc("Retrieve DBMS banner")
                .build();

        Option currentUserOption = Option.builder()
                .longOpt("current-user")
                .desc("Retrieve DBMS current user")
                .build();

        Option currentDbOption = Option.builder()
                .longOpt("current-db")
                .desc("Retrieve DBMS current database")
                .build();

        Option hostnameOption = Option.builder()
                .longOpt("hostname")
                .desc("Retrieve DBMS server hostname")
                .build();

        Option isDbaOption = Option.builder()
                .longOpt("is-dba")
                .desc("Detect if the DBMS current user is DBA")
                .build();

        Option usersOption = Option.builder()
                .longOpt("users")
                .desc("Enumerate DBMS users")
                .build();

        Option passwordsOption = Option.builder()
                .longOpt("passwords")
                .desc("Enumerate DBMS users password hashes")
                .build();

        Option privilegesOption = Option.builder()
                .longOpt("privileges")
                .desc("Enumerate DBMS users privileges")
                .build();

        Option rolesOption = Option.builder()
                .longOpt("roles")
                .desc("Enumerate DBMS users roles")
                .build();

        Option dbsOption = Option.builder()
                .longOpt("dbs")
                .desc("Enumerate DBMS databases")
                .build();

        Option tablesOption = Option.builder()
                .longOpt("tables")
                .desc("Enumerate DBMS database tables")
                .build();

        Option columnsOption = Option.builder()
                .longOpt("columns")
                .desc("Enumerate DBMS database table columns")
                .build();

        Option schemaOption = Option.builder()
                .longOpt("schema")
                .desc("Enumerate DBMS schema")
                .build();

        Option countOption = Option.builder()
                .longOpt("count")
                .desc("Retrieve number of entries for table(s)")
                .build();

        Option dumpOption = Option.builder()
                .longOpt("dump")
                .desc("Dump DBMS database table entries")
                .build();

        Option dumpAllOption = Option.builder()
                .longOpt("dump-all")
                .desc("Dump all DBMS databases tables entries")
                .build();

        Option searchOption = Option.builder()
                .longOpt("search")
                .desc("Search column(s), table(s) and/or database name(s)")
                .build();

        Option commentsOption = Option.builder()
                .longOpt("comments")
                .desc("Check for DBMS comments during enumeration")
                .build();

        Option statementsOption = Option.builder()
                .longOpt("statements")
                .desc("Retrieve SQL statements being run on DBMS")
                .build();

        Option dbOption = Option.builder("D")
                .hasArg()
                .argName("DB")
                .desc("DBMS database to enumerate")
                .build();

        Option tblOption = Option.builder("T")
                .hasArg()
                .argName("TBL")
                .desc("DBMS database table(s) to enumerate")
                .build();

        Option colOption = Option.builder("C")
                .hasArg()
                .argName("COL")
                .desc("DBMS database table column(s) to enumerate")
                .build();

        Option excludeOption = Option.builder("X")
                .hasArg()
                .argName("EXCLUDE")
                .desc("DBMS database identifier(s) to not enumerate")
                .build();

        Option userOption = Option.builder("U")
                .hasArg()
                .argName("USER")
                .desc("DBMS user to enumerate")
                .build();

        Option excludeSysDbsOption = Option.builder()
                .longOpt("exclude-sysdbs")
                .desc("Exclude DBMS system databases when enumerating tables")
                .build();

        Option pivotColumnOption = Option.builder()
                .longOpt("pivot-column")
                .hasArg()
                .argName("Pivot column name")
                .desc("Pivot column name")
                .build();

        Option whereOption = Option.builder()
                .longOpt("where")
                .hasArg()
                .argName("DUMPWHERE")
                .desc("Use WHERE condition while table dumping")
                .build();

        Option startOption = Option.builder()
                .longOpt("start")
                .hasArg()
                .argName("LIMITSTART")
                .desc("First dump table entry to retrieve")
                .build();

        Option stopOption = Option.builder()
                .longOpt("stop")
                .hasArg()
                .argName("LIMITSTOP")
                .desc("Last dump table entry to retrieve")
                .build();

        Option lastOption = Option.builder()
                .longOpt("last")
                .hasArg()
                .argName("LASTCHAR")
                .desc("Last query output word character to retrieve")
                .build();

        Option sqlQueryOption = Option.builder()
                .longOpt("sql-query")
                .hasArg()
                .argName("SQLQUERY")
                .desc("SQL statement to be executed")
                .build();

        Option sqlShellOption = Option.builder()
                .longOpt("sql-shell")
                .desc("Prompt for an interactive SQL shell")
                .build();

        Option sqlFileOption = Option.builder()
                .longOpt("sql-file")
                .hasArg()
                .argName("SQLFILE")
                .desc("Execute SQL statements from given file(s)")
                .build();

    }

}
