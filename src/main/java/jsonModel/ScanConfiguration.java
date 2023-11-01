package jsonModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScanConfiguration {
    /*
      Options:
        -h, --help            Show basic help message and exit
        -hh                   Show advanced help message and exit
        --version             Show program's version number and exit
        -v VERBOSE            Verbosity level: 0-6 (default 1)
     */
    @Builder.Default
    private Integer verbose = 1; // default value 1

    /*
        Target:
          At least one of these options has to be provided to define the
          target(s)

          -u URL, --url=URL   Target URL (e.g. "http://www.site.com/vuln.php?id=1")
          -d DIRECT           Connection string for direct database connection
          -l LOGFILE          Parse target(s) from Burp or WebScarab proxy log file
          -m BULKFILE         Scan multiple targets given in a textual file
          -r REQUESTFILE      Load HTTP request from a file
          -g GOOGLEDORK       Process Google dork results as target URLs
          -c CONFIGFILE       Load options from a configuration INI file
    */

    private String url;
    private String direct;
    private String logfile;
    private String bulkFile;
    private String requestFile;
    private String googleDork;
    private String configFile;

    /*
        Request:
            These options can be used to specify how to connect to the target URL

            -A AGENT, --user..  HTTP User-Agent header value
            -H HEADER, --hea..  Extra header (e.g. "X-Forwarded-For: 127.0.0.1")
            --method=METHOD     Force usage of given HTTP method (e.g. PUT)
            --data=DATA         Data string to be sent through POST (e.g. "id=1")
            --param-del=PARA..  Character used for splitting parameter values (e.g. &)
            --cookie=COOKIE     HTTP Cookie header value (e.g. "PHPSESSID=a8d127e..")
            --cookie-del=COO..  Character used for splitting cookie values (e.g. ;)
            --live-cookies=L..  Live cookies file used for loading up-to-date values
            --load-cookies=L..  File containing cookies in Netscape/wget format
            --drop-set-cookie   Ignore Set-Cookie header from response
            --mobile            Imitate smartphone through HTTP User-Agent header
            --random-agent      Use randomly selected HTTP User-Agent header value
            --host=HOST         HTTP Host header value
            --referer=REFERER   HTTP Referer header value
            --headers=HEADERS   Extra headers (e.g. "Accept-Language: fr\nETag: 123")
            --auth-type=AUTH..  HTTP authentication type (Basic, Digest, Bearer, ...)
            --auth-cred=AUTH..  HTTP authentication credentials (name:password)
            --auth-file=AUTH..  HTTP authentication PEM cert/private key file
            --ignore-code=IG..  Ignore (problematic) HTTP error code (e.g. 401)
            --ignore-proxy      Ignore system default proxy settings
            --ignore-redirects  Ignore redirection attempts
            --ignore-timeouts   Ignore connection timeouts
            --proxy=PROXY       Use a proxy to connect to the target URL
            --proxy-cred=PRO..  Proxy authentication credentials (name:password)
            --proxy-file=PRO..  Load proxy list from a file
            --proxy-freq=PRO..  Requests between change of proxy from a given list
            --tor               Use Tor anonymity network
            --tor-port=TORPORT  Set Tor proxy port other than default
            --tor-type=TORTYPE  Set Tor proxy type (HTTP, SOCKS4 or SOCKS5 (default))
            --check-tor         Check to see if Tor is used properly
            --delay=DELAY       Delay in seconds between each HTTP request
            --timeout=TIMEOUT   Seconds to wait before timeout connection (default 30)
            --retries=RETRIES   Retries when the connection timeouts (default 3)
            --retry-on=RETRYON  Retry request on regexp matching content (e.g. "drop")
            --randomize=RPARAM  Randomly change value for given parameter(s)
            --safe-url=SAFEURL  URL address to visit frequently during testing
            --safe-post=SAFE..  POST data to send to a safe URL
            --safe-req=SAFER..  Load safe HTTP request from a file
            --safe-freq=SAFE..  Regular requests between visits to a safe URL
            --skip-urlencode    Skip URL encoding of payload data
            --csrf-token=CSR..  Parameter used to hold anti-CSRF token
            --csrf-url=CSRFURL  URL address to visit for extraction of anti-CSRF token
            --csrf-method=CS..  HTTP method to use during anti-CSRF token page visit
            --csrf-data=CSRF..  POST data to send during anti-CSRF token page visit
            --csrf-retries=C..  Retries for anti-CSRF token retrieval (default 0)
            --force-ssl         Force usage of SSL/HTTPS
            --chunked           Use HTTP chunked transfer encoded (POST) requests
            --hpp               Use HTTP parameter pollution method
            --eval=EVALCODE     Evaluate provided Python code before the request (e.g.
                                "import hashlib;id2=hashlib.md5(id).hexdigest()")
     */

    private String agent;
    private String header;
    private String method;
    private String data;
    private String paramDel;
    private String cookie;
    private String cookieDel;
    private String liveCookies;
    private String loadCookies;

    @Builder.Default
    private Boolean dropSetCookie = false; // default value false

    @Builder.Default
    private Boolean mobile = false; // default value false

    @Builder.Default
    private Boolean randomAgent = false; // default value false

    private String host;
    private String referer;
    private String headers;
    private String authType;
    private String authCred;
    private String authFile;
    private String ignoreCode;

    @Builder.Default
    private Boolean ignoreProxy = false; // default value true

    @Builder.Default
    private Boolean ignoreRedirects = false; // default value false

    @Builder.Default
    private Boolean ignoreTimeouts = false; // default value false

    private String proxy;
    private String proxyCred;
    private String proxyFile;
    private Integer proxyFreq;

    @Builder.Default
    private Boolean tor = false; // default value false

    private Integer torPort;

    @Builder.Default
    private String torType = "SOCKS5"; // default value SOCKS5

    @Builder.Default
    private Boolean checkTor = false; // default value false

    @Builder.Default
    private Float delay = 0f; // default value 0

    @Builder.Default
    private Float timeout = 30f; // default value 30

    @Builder.Default
    private Integer retries = 3; // default value 3

    private String retryOn;

    // --randomize=RPARAM  Randomly change value for given parameter(s)
    private String rParam;

    private String safeUrl;
    private String safePost;

    // --safe-req=SAFER..  Load safe HTTP request from a file
    private  String safeReqFile;

    @Builder.Default
    private Integer safeFreq = 0; // default value 0

    @Builder.Default
    private Boolean skipUrlEncode = false; // default value false

    private String csrfToken;
    private String csrfUrl;
    private String csrfMethod;
    private String csrfData;

    @Builder.Default
    private Integer csrfRetries = 0; // default value 0

    @Builder.Default
    private Boolean forceSSL = false; // default value false

    @Builder.Default
    private Boolean chunked = false; // default value false

    @Builder.Default
    private Boolean hpp = false; // default value false

    /*
    --eval=EVALCODE     Evaluate provided Python code before the request (e.g.
                        "import hashlib;id2=hashlib.md5(id).hexdigest()")

     // python code
             request.add_argument("--eval", dest="evalCode",
            help="Evaluate provided Python code before the request (e.g. \"import hashlib;id2=hashlib.md5(id).hexdigest()\")")
     */
    private String evalCode;

    /*
      Optimization:
        These options can be used to optimize the performance of sqlmap

        -o                  Turn on all optimization switches
        --predict-output    Predict common queries output
        --keep-alive        Use persistent HTTP(s) connections
        --null-connection   Retrieve page length without actual HTTP response body
        --threads=THREADS   Max number of concurrent HTTP(s) requests (default 1)
     */

    @Builder.Default
    private Boolean optimize = false; // default value false

    @Builder.Default
    private Boolean predictOutput = false; // default value false

    @Builder.Default
    private Boolean keepAlive = false; // default value false

    @Builder.Default
    private Boolean nullConnection = false; // default value false

    @Builder.Default
    private Integer threads = 1; // default value 1

    /*
      Injection:
        These options can be used to specify which parameters to test for,
        provide custom injection payloads and optional tampering scripts

        -p TESTPARAMETER    Testable parameter(s)
        --skip=SKIP         Skip testing for given parameter(s)
        --skip-static       Skip testing parameters that not appear to be dynamic
        --param-exclude=..  Regexp to exclude parameters from testing (e.g. "ses")
        --param-filter=P..  Select testable parameter(s) by place (e.g. "POST")
        --dbms=DBMS         Force back-end DBMS to provided value
        --dbms-cred=DBMS..  DBMS authentication credentials (user:password)
        --os=OS             Force back-end DBMS operating system to provided value
        --invalid-bignum    Use big numbers for invalidating values
        --invalid-logical   Use logical operations for invalidating values
        --invalid-string    Use random strings for invalidating values
        --no-cast           Turn off payload casting mechanism
        --no-escape         Turn off string escaping mechanism
        --prefix=PREFIX     Injection payload prefix string
        --suffix=SUFFIX     Injection payload suffix string
        --tamper=TAMPER     Use given script(s) for tampering injection data
     */

    private String testParameter;
    private String skip;

    @Builder.Default
    private Boolean skipStatic = false; // default value false

    private String paramExclude;
    private String paramFilter;
    private String dbms;
    private String dbmsCred;
    private String os;

    @Builder.Default
    private Boolean invalidBignum = false; // default value false

    @Builder.Default
    private Boolean invalidLogical = false; // default value false

    @Builder.Default
    private Boolean invalidString = false; // default value false

    @Builder.Default
    private Boolean noCast = false; // default value false

    @Builder.Default
    private Boolean noEscape = false; // default value false

    private String prefix;
    private String suffix;
    private String tamper;

    /*
      Detection:
        These options can be used to customize the detection phase

        --level=LEVEL       Level of tests to perform (1-5, default 1)
        --risk=RISK         Risk of tests to perform (1-3, default 1)
        --string=STRING     String to match when query is evaluated to True
        --not-string=NOT..  String to match when query is evaluated to False
        --regexp=REGEXP     Regexp to match when query is evaluated to True
        --code=CODE         HTTP code to match when query is evaluated to True
        --smart             Perform thorough tests only if positive heuristic(s)
        --text-only         Compare pages based only on the textual content
        --titles            Compare pages based only on their titles
     */

    @Builder.Default
    private Integer level = 1; // default value 1

    @Builder.Default
    private Integer risk = 1; // default value 1

    private String string;
    private String notString;
    private String regexp;
    private Integer code;

    @Builder.Default
    private Boolean smart = false; // default value false

    @Builder.Default
    private Boolean textOnly = false; // default value false

    @Builder.Default
    private Boolean titles = false; // default value false


    /*
      Techniques:
        These options can be used to tweak testing of specific SQL injection
        techniques

        --technique=TECH..  SQL injection techniques to use (default "BEUSTQ")
        --time-sec=TIMESEC  Seconds to delay the DBMS response (default 5)
        --union-cols=UCOLS  Range of columns to test for UNION query SQL injection
        --union-char=UCHAR  Character to use for bruteforcing number of columns
        --union-from=UFROM  Table to use in FROM part of UNION query SQL injection
        --dns-domain=DNS..  Domain name used for DNS exfiltration attack
        --second-url=SEC..  Resulting page URL searched for second-order response
        --second-req=SEC..  Load second-order HTTP request from file
     */

    @Builder.Default
    private String technique = "BEUSTQ"; // default value BEUSTQ

    @Builder.Default
    private Integer timeSec = 5; // default value 5

    private String uCols;
    private String uChar;
    private String uFrom;
    private String dnsDomain;
    private String secondUrl;
    private String secondReq;


    /*
      Fingerprint:
        -f, --fingerprint   Perform an extensive DBMS version fingerprint

        // python code
           fingerprint.add_argument("-f", "--fingerprint", dest="extensiveFp", action="store_true",
                    help="Perform an extensive DBMS version fingerprint")
     */

    @Builder.Default
    private Boolean extensiveFp = false; // default value false



    /*
      Enumeration:
        These options can be used to enumerate the back-end database
        management system information, structure and data contained in the
        tables

        -a, --all           Retrieve everything
        -b, --banner        Retrieve DBMS banner
        --current-user      Retrieve DBMS current user
        --current-db        Retrieve DBMS current database
        --hostname          Retrieve DBMS server hostname
        --is-dba            Detect if the DBMS current user is DBA
        --users             Enumerate DBMS users
        --passwords         Enumerate DBMS users password hashes
        --privileges        Enumerate DBMS users privileges
        --roles             Enumerate DBMS users roles
        --dbs               Enumerate DBMS databases
        --tables            Enumerate DBMS database tables
        --columns           Enumerate DBMS database table columns
        --schema            Enumerate DBMS schema
        --count             Retrieve number of entries for table(s)
        --dump              Dump DBMS database table entries
        --dump-all          Dump all DBMS databases tables entries
        --search            Search column(s), table(s) and/or database name(s)
        --comments          Check for DBMS comments during enumeration
        --statements        Retrieve SQL statements being run on DBMS
        -D DB               DBMS database to enumerate
        -T TBL              DBMS database table(s) to enumerate
        -C COL              DBMS database table column(s) to enumerate
        -X EXCLUDE          DBMS database identifier(s) to not enumerate
        -U USER             DBMS user to enumerate
        --exclude-sysdbs    Exclude DBMS system databases when enumerating tables
        --pivot-column=P..  Pivot column name
        --where=DUMPWHERE   Use WHERE condition while table dumping
        --start=LIMITSTART  First dump table entry to retrieve
        --stop=LIMITSTOP    Last dump table entry to retrieve
        --first=FIRSTCHAR   First query output word character to retrieve
        --last=LASTCHAR     Last query output word character to retrieve
        --sql-query=SQLQ..  SQL statement to be executed
        --sql-shell         Prompt for an interactive SQL shell
        --sql-file=SQLFILE  Execute SQL statements from given file(s)
     */

    /*
    -a, --all           Retrieve everything

    // python code
            enumeration.add_argument("-a", "--all", dest="getAll", action="store_true",
            help="Retrieve everything")
     */
    @Builder.Default
    private Boolean getAll = false; // default value false

    /*
    -b, --banner        Retrieve DBMS banner
            enumeration.add_argument("-b", "--banner", dest="getBanner", action="store_true",
            help="Retrieve DBMS banner")
     */
    @Builder.Default
    private Boolean getBanner = false; // default value false

    /*
    -c, --current-user  Retrieve current user
     enumeration.add_argument("--current-user", dest="getCurrentUser", action="store_true",
            help="Retrieve DBMS current user")
     */
    @Builder.Default
    private Boolean getCurrentUser = false; // default value false

    /*
    -d, --current-db    Retrieve current database
            enumeration.add_argument("--current-db", dest="getCurrentDb", action="store_true",
            help="Retrieve DBMS current database")

     */
    @Builder.Default
    private Boolean getCurrentDb = false; // default value false

    /*
    -h, --hostname      Retrieve hostname
     enumeration.add_argument("--hostname", dest="getHostname", action="store_true",
            help="Retrieve DBMS server hostname")

     */
    @Builder.Default
    private Boolean getHostname = false; // default value false


    @Builder.Default
    private Boolean isDba = false; // default value false


    @Builder.Default
    private Boolean getUsers = false; // default value false


    @Builder.Default
    private Boolean getPasswordHashes = false; // default value false


    @Builder.Default
    private Boolean getPrivileges = false; // default value false


    @Builder.Default
    private Boolean getRoles = false; // default value false


    @Builder.Default
    private Boolean getDbs = false; // default value false


    @Builder.Default
    private Boolean getTables = false; // default value false


    @Builder.Default
    private Boolean getColumns = false; // default value false


    @Builder.Default
    private Boolean getSchema = false; // default value false


    @Builder.Default
    private Boolean getCount = false; // default value false


    @Builder.Default
    private Boolean dumpTable = false; // default value false


    @Builder.Default
    private Boolean dumpAll = false; // default value false


    @Builder.Default
    private Boolean search = false; // default value false


    @Builder.Default
    private Boolean getComments = false; // default value false


    @Builder.Default
    private Boolean getStatements = false; // default value false

    private String db;
    private String tbl;
    private String col;
    private String exclude;
    private String user;

    @Builder.Default
    private Boolean excludeSysDbs = false; // default value false

    private String pivotColumn;
    private String dumpWhere;

    private Integer limitStart;
    private Integer limitStop;
    private Integer firstChar;
    private Integer lastChar;
    private String sqlQuery;
    private String sqlFile;


    /*
      Brute force:
        These options can be used to run brute force checks

        --common-tables     Check existence of common tables
        --common-columns    Check existence of common columns
        --common-files      Check existence of common files
     */

    @Builder.Default
    private Boolean commonTables = false; // default value false

    @Builder.Default
    private Boolean commonColumns = false; // default value false


    @Builder.Default
    private Boolean commonFiles = false; // default value false


    /*
      User-defined function injection:
        These options can be used to create custom user-defined functions

        --udf-inject        Inject custom user-defined functions
        --shared-lib=SHLIB  Local path of the shared library
     */
    @Builder.Default
    private Boolean udfInject = false; // default value false

    private String shLib;


    /*
      File system access:
        These options can be used to access the back-end database management
        system underlying file system

        --file-read=FILE..  Read a file from the back-end DBMS file system
        --file-write=FIL..  Write a local file on the back-end DBMS file system
        --file-dest=FILE..  Back-end DBMS absolute filepath to write to
     */
    private String fileRead;
    private String fileWrite;
    private String fileDest;


    /*
      Operating system access:
        These options can be used to access the back-end database management
        system underlying operating system

        --os-cmd=OSCMD      Execute an operating system command
        --os-shell          Prompt for an interactive operating system shell
        --os-pwn            Prompt for an OOB shell, Meterpreter or VNC
        --os-smbrelay       One click prompt for an OOB shell, Meterpreter or VNC
        --os-bof            Stored procedure buffer overflow exploitation
        --priv-esc          Database process user privilege escalation
        --msf-path=MSFPATH  Local path where Metasploit Framework is installed
        --tmp-path=TMPPATH  Remote absolute path of temporary files directory
     */
    private String osCmd;

    @Builder.Default
    private Boolean osShell = false; // default value false

    private String osPwn; // default value false

    @Builder.Default
    private Boolean osSmb = false; // default value false

    @Builder.Default
    private Boolean osBof = false; // default value false

    @Builder.Default
    private Boolean privEsc = false; // default value false

    private String msfPath;
    private String tmpPath;


    /*
      Windows registry access:
        These options can be used to access the back-end database management
        system Windows registry

        --reg-read          Read a Windows registry key value
        --reg-add           Write a Windows registry key value data
        --reg-del           Delete a Windows registry key value
        --reg-key=REGKEY    Windows registry key
        --reg-value=REGVAL  Windows registry key value
        --reg-data=REGDATA  Windows registry key value data
        --reg-type=REGTYPE  Windows registry key value type
     */

    @Builder.Default
    private Boolean regRead = false; // default value false

    @Builder.Default
    private Boolean regAdd = false; // default value false

    @Builder.Default
    private Boolean regDel = false; // default value false

    private String regKey;
    private String regVal;
    private String regData;
    private String regType;


    /*
      General:
        These options can be used to set some general working parameters

        -s SESSIONFILE      Load session from a stored (.sqlite) file
        -t TRAFFICFILE      Log all HTTP traffic into a textual file
        --answers=ANSWERS   Set predefined answers (e.g. "quit=N,follow=N")
        --base64=BASE64P..  Parameter(s) containing Base64 encoded data
        --base64-safe       Use URL and filename safe Base64 alphabet (RFC 4648)
        --batch             Never ask for user input, use the default behavior
        --binary-fields=..  Result fields having binary values (e.g. "digest")
        --check-internet    Check Internet connection before assessing the target
        --cleanup           Clean up the DBMS from sqlmap specific UDF and tables
        --crawl=CRAWLDEPTH  Crawl the website starting from the target URL
        --crawl-exclude=..  Regexp to exclude pages from crawling (e.g. "logout")
        --csv-del=CSVDEL    Delimiting character used in CSV output (default ",")
        --charset=CHARSET   Blind SQL injection charset (e.g. "0123456789abcdef")
        --dump-file=DUMP..  Store dumped data to a custom file
        --dump-format=DU..  Format of dumped data (CSV (default), HTML or SQLITE)
        --encoding=ENCOD..  Character encoding used for data retrieval (e.g. GBK)
        --eta               Display for each output the estimated time of arrival
        --flush-session     Flush session files for current target
        --forms             Parse and test forms on target URL
        --fresh-queries     Ignore query results stored in session file
        --gpage=GOOGLEPAGE  Use Google dork results from specified page number
        --har=HARFILE       Log all HTTP traffic into a HAR file
        --hex               Use hex conversion during data retrieval
        --output-dir=OUT..  Custom output directory path
        --parse-errors      Parse and display DBMS error messages from responses
        --preprocess=PRE..  Use given script(s) for preprocessing (request)
        --postprocess=PO..  Use given script(s) for postprocessing (response)
        --repair            Redump entries having unknown character marker (?)
        --save=SAVECONFIG   Save options to a configuration INI file
        --scope=SCOPE       Regexp for filtering targets
        --skip-heuristics   Skip heuristic detection of vulnerabilities
        --skip-waf          Skip heuristic detection of WAF/IPS protection
        --table-prefix=T..  Prefix used for temporary tables (default: "sqlmap")
        --test-filter=TE..  Select tests by payloads and/or titles (e.g. ROW)
        --test-skip=TEST..  Skip tests by payloads and/or titles (e.g. BENCHMARK)
        --web-root=WEBROOT  Web server document root directory (e.g. "/var/www")
     */

    private String sessionFile;
    private String trafficFile;

    private String answers;
    private String base64Parameter;

    @Builder.Default
    private Boolean base64Safe = false; // default value fals

    @Builder.Default
    private Boolean batch = true; // default value true

    private String binaryFields;

    @Builder.Default
    private Boolean checkInternet = false; // default value false

    @Builder.Default
    private Boolean cleanup = false; // default value false

    private Integer crawlDepth;
    private String crawlExclude;

    @Builder.Default
    private String csvDel = ","; // default value ,

    private String charset;

    private String dumpFile;

    @Builder.Default
    private String dumpFormat = "CSV"; // default value CSV

    private String encoding;

    @Builder.Default
    private Boolean eta = false; // default value false

    @Builder.Default
    private Boolean flushSession = false; // default value false

    @Builder.Default
    private Boolean forms = false; // default value false

    @Builder.Default
    private Boolean freshQueries = false; // default value false

    @Builder.Default
    private Integer googlePage = 1; // default value 1

    private String harFile;

    @Builder.Default
    private Boolean hexConvert = false; // default value false

    private String outputDir;

    @Builder.Default
    private Boolean parseErrors = false; // default value false

    private String preprocess;
    private String postprocess;

    @Builder.Default
    private Boolean repair = false; // default value false

    private String saveConfig;
    private String scope;

    @Builder.Default
    private Boolean skipHeuristics = false; // default value false

    @Builder.Default
    private Boolean skipWaf = false; // default value false

    private String testFilter;
    private String testSkip;
    private String webRoot;


    /*
      Miscellaneous:
    These options do not fit into any other category

        -z MNEMONICS        Use short mnemonics (e.g. "flu,bat,ban,tec=EU")
        --alert=ALERT       Run host OS command(s) when SQL injection is found
        --beep              Beep on question and/or when vulnerability is found
        --dependencies      Check for missing (optional) sqlmap dependencies
        --disable-coloring  Disable console output coloring
        --list-tampers      Display list of available tamper scripts
        --no-logging        Disable logging to a file
        --offline           Work in offline mode (only use session data)
        --purge             Safely remove all content from sqlmap data directory
        --results-file=R..  Location of CSV results file in multiple targets mode
        --shell             Prompt for an interactive sqlmap shell
        --tmp-dir=TMPDIR    Local directory for storing temporary files
        --unstable          Adjust options for unstable connections
        --update            Update sqlmap
        --wizard            Simple wizard interface for beginner users
     */
    private String mnemonics;
    private String alert;

    @Builder.Default
    private Boolean beep = false; // default value false

    @Builder.Default
    private Boolean dependencies = false; // default value false

    @Builder.Default
    private Boolean disableColoring = true; // default value true

    @Builder.Default
    private Boolean listTampers = false; // default value false

    @Builder.Default
    private Boolean noLogging = false; // default value false

    @Builder.Default
    private Boolean offline = false; // default value false

    @Builder.Default
    private Boolean purge = false; // default value false

    private String resultsFile;

    @Builder.Default
    private Boolean shell = false;

    private String tmpDir;

    @Builder.Default
    private Boolean unstable = false; // default value false

    @Builder.Default
    private Boolean updateAll = false; // default value false

//    @Builder.Default
//    private Boolean wizard = false;

//    private String hashFile;
//
//    @Builder.Default
//    private Boolean dummy = false; // default value false
//
//    @Builder.Default
//    private Boolean disablePrecon = false; // default value false
//
//    @Builder.Default
//    private Boolean profile = false; // default value false
//
//    @Builder.Default
//    private Boolean forceDns = false; // default value false
//
//    private String murphyRate;
//
//    @Builder.Default
//    private Boolean smokeTest = false; // default value false
//
//    @Builder.Default
//    private Boolean api = true; // default value true
//
//    private String taskid; // default value b65dd9496ba03e0c
//
//    private String database; // default value C:\Users\wangg\AppData\Local\Temp\sqlmapipc-4_tty95h
}
