package util;

import org.apache.commons.cli.Option;

import static util.GlobalEnv.OPTIONS;

public class ScanOptionsParserData {
    public static void initScanOptionsParserData() {

        /*
          Options:
            -h, --help            Show basic help message and exit
            -hh                   Show advanced help message and exit
            --version             Show program's version number and exit
            -v VERBOSE            Verbosity level: 0-6 (default 1)
         */

        Option verboseOption = Option.builder("v")
                .longOpt("verbose")
                .hasArg()
                .argName("level")
                .desc("Verbosity level: 0-6 (default 1)")
                .build();

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



        Option urlOption = Option.builder("u")
                .longOpt("url")
                .hasArg()
                .argName("URL")
                .desc("Target URL (e.g. \"http://www.site.com/vuln.php?id=1\")")
                .build();

        Option directOption = Option.builder("d")
                .longOpt("direct")
                .hasArg()
                .argName("DIRECT")
                .desc("Connection string for direct database connection")
                .build();

        Option logfileOption = Option.builder("l")
                .longOpt("logfile")
                .hasArg()
                .argName("LOGFILE")
                .desc("Parse target(s) from Burp or WebScarab proxy log file")
                .build();

        Option bulkFileOption = Option.builder("m")
                .longOpt("bulkFile")
                .hasArg()
                .argName("BULKFILE")
                .desc("Scan multiple targets given in a textual file")
                .build();

        Option requestfileOption = Option.builder("r")
                .longOpt("requestFile")
                .hasArg()
                .argName("REQUESTFILE")
                .desc("Load HTTP request from a file")
                .build();

        Option googledorkOption = Option.builder("g")
                .longOpt("googleDork")
                .hasArg()
                .argName("GOOGLEDORK")
                .desc("Process Google dork results as target URLs")
                .build();

        Option configFileOption = Option.builder("c")
                .longOpt("configFile")
                .hasArg()
                .argName("CONFIGFILE")
                .desc("Load options from a configuration INI file")
                .build();

        OPTIONS.addOption(verboseOption);
        OPTIONS.addOption(urlOption);
        OPTIONS.addOption(directOption);
        OPTIONS.addOption(logfileOption);
        OPTIONS.addOption(bulkFileOption);
        OPTIONS.addOption(requestfileOption);
        OPTIONS.addOption(googledorkOption);
        OPTIONS.addOption(configFileOption);

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
              --abort-code=ABO..  Abort on (problematic) HTTP error code(s) (e.g. 401)
              --ignore-code=IG..  Ignore (problematic) HTTP error code(s) (e.g. 401)
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

        Option ignoreCodeOption = Option.builder()
                .longOpt("ignore-code")
                .hasArg()
                .argName("ignoreCode")
                .desc("Ignore (problematic) HTTP error code(s) (e.g. 401)")
                .build();

        Option ignoreProxyOption = Option.builder()
                .longOpt("ignore-proxy")
                .desc("Ignore system default proxy settings")
                .build();

        Option ignoreRedirectsOption = Option.builder()
                .longOpt("ignore-redirects")
                .desc("Ignore redirection attempts")
                .build();

        Option ignoreTimeoutsOption = Option.builder()
                .longOpt("ignore-timeouts")
                .desc("Ignore connection timeouts")
                .build();

        Option proxyOption = Option.builder()
                .longOpt("proxy")
                .hasArg()
                .argName("proxy")
                .desc("Use a proxy to connect to the target URL")
                .build();

        Option proxyCredOption = Option.builder()
                .longOpt("proxy-cred")
                .hasArg()
                .argName("proxyCred")
                .desc("Proxy authentication credentials (name:password)")
                .build();

        Option proxyFileOption = Option.builder()
                .longOpt("proxy-file")
                .hasArg()
                .argName("proxyFile")
                .desc("Load proxy list from a file")
                .build();

        Option proxyFreqOption = Option.builder()
                .longOpt("proxy-freq")
                .hasArg()
                .argName("proxyFreq")
                .desc("Requests between change of proxy from a given list")
                .build();

        Option torOption = Option.builder()
                .longOpt("tor")
                .desc("Use Tor anonymity network")
                .build();

        Option torPortOption = Option.builder()
                .longOpt("tor-port")
                .hasArg()
                .argName("torPort")
                .desc("Set Tor proxy port other than default")
                .build();

        Option torTypeOption = Option.builder()
                .longOpt("tor-type")
                .hasArg()
                .argName("torType")
                .desc("Set Tor proxy type (HTTP, SOCKS4 or SOCKS5 (default))")
                .build();

        Option checkTorOption = Option.builder()
                .longOpt("check-tor")
                .desc("Check to see if Tor is used properly")
                .build();

        Option delayOption = Option.builder()
                .longOpt("delay")
                .hasArg()
                .argName("delay")
                .desc("Delay in seconds between each HTTP request")
                .build();

        Option timeoutOption = Option.builder()
                .longOpt("timeout")
                .hasArg()
                .argName("timeout")
                .desc("Seconds to wait before timeout connection (default 30)")
                .build();

        Option retriesOption = Option.builder()
                .longOpt("retries")
                .hasArg()
                .argName("retries")
                .desc("Retries when the connection timeouts (default 3)")
                .build();

        Option retryOnOption = Option.builder()
                .longOpt("retry-on")
                .hasArg()
                .argName("retryOn")
                .desc("Retry request on regexp matching content (e.g. \"drop\")")
                .build();

        Option randomizeOption = Option.builder()
                .longOpt("randomize")
                .hasArg()
                .argName("randomize")
                .desc("Randomly change value for given parameter(s)")
                .build();

        Option safeUrlOption = Option.builder()
                .longOpt("safe-url")
                .hasArg()
                .argName("safeUrl")
                .desc("URL address to visit frequently during testing")
                .build();

        Option safePostOption = Option.builder()
                .longOpt("safe-post")
                .hasArg()
                .argName("safePost")
                .desc("POST data to send to a safe URL")
                .build();

        Option safeReqOption = Option.builder()
                .longOpt("safe-req")
                .hasArg()
                .argName("safeReq")
                .desc("Load safe HTTP request from a file")
                .build();

        Option safeFreqOption = Option.builder()
                .longOpt("safe-freq")
                .hasArg()
                .argName("safeFreq")
                .desc("Regular requests between visits to a safe URL")
                .build();

        Option skipURLEncodeOption = Option.builder()
                .longOpt("skip-urlencode")
                .desc("Skip URL encoding of payload data")
                .build();

        Option csrfTokenOption = Option.builder()
                .longOpt("csrf-token")
                .hasArg()
                .argName("csrfToken")
                .desc("Parameter used to hold anti-CSRF token")
                .build();

        Option csrfURLOption = Option.builder()
                .longOpt("csrf-url")
                .hasArg()
                .argName("csrfUrl")
                .desc("URL address to visit for extraction of anti-CSRF token")
                .build();

        Option csrfMethodOption = Option.builder()
                .longOpt("csrf-method")
                .hasArg()
                .argName("csrfMethod")
                .desc("HTTP method to use during anti-CSRF token page visit")
                .build();

        Option csrfDataOption = Option.builder()
                .longOpt("csrf-data")
                .hasArg()
                .argName("csrfData")
                .desc("POST data to send during anti-CSRF token page visit")
                .build();

        Option csrfRetriesOption = Option.builder()
                .longOpt("csrf-retries")
                .hasArg()
                .argName("csrfRetries")
                .desc("Retries for anti-CSRF token retrieval (default 0)")
                .build();

        Option forceSSLOption = Option.builder()
                .longOpt("force-ssl")
                .desc("Force usage of SSL/HTTPS")
                .build();

        Option chunkedOption = Option.builder()
                .longOpt("chunked")
                .desc("Use HTTP chunked transfer encoded (POST) requests")
                .build();

        Option hppOption = Option.builder()
                .longOpt("hpp")
                .desc("Use HTTP parameter pollution method")
                .build();

        Option evalOption = Option.builder()
                .longOpt("eval")
                .hasArg()
                .argName("evalCode")
                .desc("Evaluate provided Python code before the request (e.g. \"import hashlib;id2=hashlib.md5(id).hexdigest()\")")
                .build();

        // 把以上定义的 option 都加入 OPTIONS 中
        OPTIONS.addOption(userAgentOption);
        OPTIONS.addOption(headerOption);
        OPTIONS.addOption(methodOption);
        OPTIONS.addOption(dataOption);
        OPTIONS.addOption(paramDelOption);
        OPTIONS.addOption(cookieOption);
        OPTIONS.addOption(cookieDelOption);
        OPTIONS.addOption(liveCookiesOption);
        OPTIONS.addOption(loadCookiesOption);
        OPTIONS.addOption(dropSetCookieOption);
        OPTIONS.addOption(mobileOption);
        OPTIONS.addOption(randomAgentOption);
        OPTIONS.addOption(hostOption);
        OPTIONS.addOption(refererOption);
        OPTIONS.addOption(headersOption);
        OPTIONS.addOption(authTypeOption);
        OPTIONS.addOption(authCredOption);
        OPTIONS.addOption(authFileOption);
        OPTIONS.addOption(ignoreCodeOption);
        OPTIONS.addOption(ignoreProxyOption);
        OPTIONS.addOption(ignoreRedirectsOption);
        OPTIONS.addOption(ignoreTimeoutsOption);
        OPTIONS.addOption(proxyOption);
        OPTIONS.addOption(proxyCredOption);
        OPTIONS.addOption(proxyFileOption);
        OPTIONS.addOption(proxyFreqOption);
        OPTIONS.addOption(torOption);
        OPTIONS.addOption(torPortOption);
        OPTIONS.addOption(torTypeOption);
        OPTIONS.addOption(checkTorOption);
        OPTIONS.addOption(delayOption);
        OPTIONS.addOption(timeoutOption);
        OPTIONS.addOption(retriesOption);
        OPTIONS.addOption(retryOnOption);
        OPTIONS.addOption(randomizeOption);
        OPTIONS.addOption(safeUrlOption);
        OPTIONS.addOption(safePostOption);
        OPTIONS.addOption(safeReqOption);
        OPTIONS.addOption(safeFreqOption);
        OPTIONS.addOption(skipURLEncodeOption);
        OPTIONS.addOption(csrfTokenOption);
        OPTIONS.addOption(csrfURLOption);
        OPTIONS.addOption(csrfMethodOption);
        OPTIONS.addOption(csrfDataOption);
        OPTIONS.addOption(csrfRetriesOption);
        OPTIONS.addOption(forceSSLOption);
        OPTIONS.addOption(chunkedOption);
        OPTIONS.addOption(hppOption);
        OPTIONS.addOption(evalOption);


        /*
            Optimization:
              These options can be used to optimize the performance of sqlmap

              -o                  Turn on all optimization switches
              --predict-output    Predict common queries output
              --keep-alive        Use persistent HTTP(s) connections
              --null-connection   Retrieve page length without actual HTTP response body
              --threads=THREADS   Max number of concurrent HTTP(s) requests (default 1)
         */

        Option optimizationOption = Option.builder("o")
                .longOpt("optimize")
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

        OPTIONS.addOption(optimizationOption);
        OPTIONS.addOption(predictOutputOption);
        OPTIONS.addOption(keepAliveOption);
        OPTIONS.addOption(nullConnectionOption);
        OPTIONS.addOption(threadsOption);


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

        OPTIONS.addOption(testParameterOption);
        OPTIONS.addOption(skipOption);
        OPTIONS.addOption(skipStaticOption);
        OPTIONS.addOption(paramExcludeOption);
        OPTIONS.addOption(paramFilterOption);
        OPTIONS.addOption(dbmsOption);
        OPTIONS.addOption(dbmsCredOption);
        OPTIONS.addOption(osOption);
        OPTIONS.addOption(invalidBignumOption);
        OPTIONS.addOption(invalidLogicalOption);
        OPTIONS.addOption(invalidStringOption);
        OPTIONS.addOption(noCastOption);
        OPTIONS.addOption(noEscapeOption);
        OPTIONS.addOption(prefixOption);
        OPTIONS.addOption(suffixOption);
        OPTIONS.addOption(tamperOption);

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

        OPTIONS.addOption(levelOption);
        OPTIONS.addOption(riskOption);
        OPTIONS.addOption(stringOption);
        OPTIONS.addOption(notStringOption);
        OPTIONS.addOption(regexpOption);
        OPTIONS.addOption(codeOption);
        OPTIONS.addOption(smartOption);
        OPTIONS.addOption(textOnlyOption);
        OPTIONS.addOption(titlesOption);

        /*
           Techniques:
              These options can be used to tweak testing of specific SQL injection
              techniques

              --technique=TECH..  SQL injection techniques to use (default "BEUSTQ")
              --time-sec=TIMESEC  Seconds to delay the DBMS response (default 5)
              --union-cols=UCOLS  Range of columns to test for UNION query SQL injection
              --union-char=UCHAR  Character to use for bruteforcing number of columns
              --union-from=UFROM  Table to use in FROM part of UNION query SQL injection
              --union-values=U..  Column values to use for UNION query SQL injection
              --dns-domain=DNS..  Domain name used for DNS exfiltration attack
              --second-url=SEC..  Resulting page URL searched for second-order response
              --second-req=SEC..  Load second-order HTTP request from file
         */

        Option techniqueOption = Option.builder()
                .longOpt("technique")
                .hasArg()
                .argName("technique")
                .desc("SQL injection techniques to use (default \"BEUSTQ\")")
                .build();

        Option timeSecOption = Option.builder()
                .longOpt("time-sec")
                .hasArg()
                .argName("timeSec")
                .desc("Seconds to delay the DBMS response (default 5)")
                .build();

        Option unionColsOption = Option.builder()
                .longOpt("union-cols")
                .hasArg()
                .argName("unionCols")
                .desc("Range of columns to test for UNION query SQL injection")
                .build();

        Option unionCharOption = Option.builder()
                .longOpt("union-char")
                .hasArg()
                .argName("unionChar")
                .desc("Character to use for bruteforcing number of columns")
                .build();

        Option unionFromOption = Option.builder()
                .longOpt("union-from")
                .hasArg()
                .argName("unionFrom")
                .desc("Table to use in FROM part of UNION query SQL injection")
                .build();

        Option dnsDomainOption = Option.builder()
                .longOpt("dns-domain")
                .hasArg()
                .argName("dnsDomain")
                .desc("Domain name used for DNS exfiltration attack")
                .build();

        Option secondUrlOption = Option.builder()
                .longOpt("second-url")
                .hasArg()
                .argName("secondUrl")
                .desc("Resulting page URL searched for second-order response")
                .build();

        Option secondReqOption = Option.builder()
                .longOpt("second-req")
                .hasArg()
                .argName("secondReq")
                .desc("Load second-order HTTP request from file")
                .build();

        OPTIONS.addOption(techniqueOption);
        OPTIONS.addOption(timeSecOption);
        OPTIONS.addOption(unionColsOption);
        OPTIONS.addOption(unionCharOption);
        OPTIONS.addOption(unionFromOption);
        OPTIONS.addOption(dnsDomainOption);
        OPTIONS.addOption(secondUrlOption);
        OPTIONS.addOption(secondReqOption);

        /*
           Fingerprint:
              -f, --fingerprint   Perform an extensive DBMS version fingerprint
         */

        Option fingerprintOption = Option.builder("f")
                .longOpt("fingerprint")
                .desc("Perform an extensive DBMS version fingerprint")
                .build();

        OPTIONS.addOption(fingerprintOption);


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
                .longOpt("db")
                .hasArg()
                .argName("DB")
                .desc("DBMS database to enumerate")
                .build();

        Option tblOption = Option.builder("T")
                .longOpt("tbl")
                .hasArg()
                .argName("TBL")
                .desc("DBMS database table(s) to enumerate")
                .build();

        Option colOption = Option.builder("C")
                .longOpt("col")
                .hasArg()
                .argName("COL")
                .desc("DBMS database table column(s) to enumerate")
                .build();

        Option excludeOption = Option.builder("X")
                .longOpt("exclude")
                .hasArg()
                .argName("EXCLUDE")
                .desc("DBMS database identifier(s) to not enumerate")
                .build();

        Option userOption = Option.builder("U")
                .longOpt("user")
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
                .argName("limitStart")
                .desc("First dump table entry to retrieve")
                .build();

        Option stopOption = Option.builder()
                .longOpt("stop")
                .hasArg()
                .argName("limitStop")
                .desc("Last dump table entry to retrieve")
                .build();

        Option firstOption = Option.builder()
                .longOpt("first")
                .hasArg()
                .argName("firstChar")
                .desc("First query output word character to retrieve")
                .build();

        Option lastOption = Option.builder()
                .longOpt("last")
                .hasArg()
                .argName("lastChar")
                .desc("Last query output word character to retrieve")
                .build();

        Option sqlQueryOption = Option.builder()
                .longOpt("sql-query")
                .hasArg()
                .argName("sqlQuery")
                .desc("SQL statement to be executed")
                .build();

        Option sqlFileOption = Option.builder()
                .longOpt("sql-file")
                .hasArg()
                .argName("sqlFile")
                .desc("Execute SQL statements from given file(s)")
                .build();

        OPTIONS.addOption(allOption);
        OPTIONS.addOption(bannerOption);
        OPTIONS.addOption(currentUserOption);
        OPTIONS.addOption(currentDbOption);
        OPTIONS.addOption(hostnameOption);
        OPTIONS.addOption(isDbaOption);
        OPTIONS.addOption(usersOption);
        OPTIONS.addOption(passwordsOption);
        OPTIONS.addOption(privilegesOption);
        OPTIONS.addOption(rolesOption);
        OPTIONS.addOption(dbsOption);
        OPTIONS.addOption(tablesOption);
        OPTIONS.addOption(columnsOption);
        OPTIONS.addOption(schemaOption);
        OPTIONS.addOption(countOption);
        OPTIONS.addOption(dumpOption);
        OPTIONS.addOption(dumpAllOption);
        OPTIONS.addOption(searchOption);
        OPTIONS.addOption(commentsOption);
        OPTIONS.addOption(statementsOption);
        OPTIONS.addOption(dbOption);
        OPTIONS.addOption(tblOption);
        OPTIONS.addOption(colOption);
        OPTIONS.addOption(excludeOption);
        OPTIONS.addOption(userOption);
        OPTIONS.addOption(excludeSysDbsOption);
        OPTIONS.addOption(pivotColumnOption);
        OPTIONS.addOption(whereOption);
        OPTIONS.addOption(startOption);
        OPTIONS.addOption(stopOption);
        OPTIONS.addOption(firstOption);
        OPTIONS.addOption(lastOption);
        OPTIONS.addOption(sqlQueryOption);
        OPTIONS.addOption(sqlFileOption);

        /*
          Brute force:
              These options can be used to run brute force checks

              --common-tables     Check existence of common tables
              --common-columns    Check existence of common columns
              --common-files      Check existence of common files
         */

        Option commonTablesOption = Option.builder()
                .longOpt("common-tables")
                .desc("Check existence of common tables")
                .build();

        Option commonColumnsOption = Option.builder()
                .longOpt("common-columns")
                .desc("Check existence of common columns")
                .build();

        Option commonFilesOption = Option.builder()
                .longOpt("common-files")
                .desc("Check existence of common files")
                .build();

        OPTIONS.addOption(commonTablesOption);
        OPTIONS.addOption(commonColumnsOption);
        OPTIONS.addOption(commonFilesOption);


        /*
           User-defined function injection:
              These options can be used to create custom user-defined functions

              --udf-inject        Inject custom user-defined functions
              --shared-lib=SHLIB  Local path of the shared library
         */

        Option udfInjectOption = Option.builder()
                .longOpt("udf-inject")
                .desc("Inject custom user-defined functions")
                .build();

        Option sharedLibOption = Option.builder()
                .longOpt("shared-lib")
                .hasArg()
                .argName("sharedLib")
                .desc("Local path of the shared library")
                .build();

        OPTIONS.addOption(udfInjectOption);
        OPTIONS.addOption(sharedLibOption);


        /*
            File system access:
              These options can be used to access the back-end database management
              system underlying file system

              --file-read=FILE..  Read a file from the back-end DBMS file system
              --file-write=FIL..  Write a local file on the back-end DBMS file system
              --file-dest=FILE..  Back-end DBMS absolute filepath to write to
         */

        Option fileReadOption = Option.builder()
                .longOpt("file-read")
                .hasArgs()
                .argName("files")
                .desc("Read a file from the back-end DBMS file system")
                .build();

        Option fileWriteOption = Option.builder()
                .longOpt("file-write")
                .hasArgs()
                .argName("files")
                .desc("Write a local file on the back-end DBMS file system")
                .build();

        Option fileDestOption = Option.builder()
                .longOpt("file-dest")
                .hasArgs()
                .argName("files")
                .desc("Back-end DBMS absolute filepath to write to")
                .build();

        OPTIONS.addOption(fileReadOption);
        OPTIONS.addOption(fileWriteOption);
        OPTIONS.addOption(fileDestOption);


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

        Option osCmdOption = Option.builder()
                .longOpt("os-cmd")
                .hasArg()
                .argName("osCmd")
                .desc("Execute an operating system command")
                .build();

        Option osShellOption = Option.builder()
                .longOpt("os-shell")
                .desc("Prompt for an interactive operating system shell")
                .build();

        Option osPwnOption = Option.builder()
                .longOpt("os-pwn")
                .desc("Prompt for an OOB shell, Meterpreter or VNC")
                .build();

        Option osSmbRelayOption = Option.builder()
                .longOpt("os-smbrelay")
                .desc("One click prompt for an OOB shell, Meterpreter or VNC")
                .build();

        Option osBofOption = Option.builder()
                .longOpt("os-bof")
                .desc("Stored procedure buffer overflow exploitation")
                .build();

        Option privEscOption = Option.builder()
                .longOpt("priv-esc")
                .desc("Database process user privilege escalation")
                .build();

        Option msfPathOption = Option.builder()
                .longOpt("msf-path")
                .hasArg()
                .argName("msfPath")
                .desc("Local path where Metasploit Framework is installed")
                .build();

        Option tmpPathOption = Option.builder()
                .longOpt("tmp-path")
                .hasArg()
                .argName("tmpPath")
                .desc("Remote absolute path of temporary files directory")
                .build();

        OPTIONS.addOption(osCmdOption);
        OPTIONS.addOption(osShellOption);
        OPTIONS.addOption(osPwnOption);
        OPTIONS.addOption(osSmbRelayOption);
        OPTIONS.addOption(osBofOption);
        OPTIONS.addOption(privEscOption);
        OPTIONS.addOption(msfPathOption);
        OPTIONS.addOption(tmpPathOption);


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

        Option regReadOption = Option.builder()
                .longOpt("reg-read")
                .desc("Read a Windows registry key value")
                .build();

        Option regAddOption = Option.builder()
                .longOpt("reg-add")
                .desc("Write a Windows registry key value data")
                .build();

        Option regDelOption = Option.builder()
                .longOpt("reg-del")
                .desc("Delete a Windows registry key value")
                .build();

        Option regKeyOption = Option.builder()
                .longOpt("reg-key")
                .hasArg()
                .argName("regKey")
                .desc("Windows registry key")
                .build();

        Option regValueOption = Option.builder()
                .longOpt("reg-value")
                .hasArg()
                .argName("regValue")
                .desc("Windows registry key value")
                .build();

        Option regDataOption = Option.builder()
                .longOpt("reg-data")
                .hasArg()
                .argName("regData")
                .desc("Windows registry key value data")
                .build();

        Option regTypeOption = Option.builder()
                .longOpt("reg-type")
                .hasArg()
                .argName("regType")
                .desc("Windows registry key value type")
                .build();

        OPTIONS.addOption(regReadOption);
        OPTIONS.addOption(regAddOption);
        OPTIONS.addOption(regDelOption);
        OPTIONS.addOption(regKeyOption);
        OPTIONS.addOption(regValueOption);
        OPTIONS.addOption(regDataOption);
        OPTIONS.addOption(regTypeOption);


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
              --time-limit=TIM..  Run with a time limit in seconds (e.g. 3600)
              --web-root=WEBROOT  Web server document root directory (e.g. "/var/www")
         */

        Option sessionFileOption = Option.builder("s")
                .longOpt("sessionFile")
                .hasArg()
                .argName("sessionFile")
                .desc("Load session from a stored (.sqlite) file")
                .build();

        Option trafficFileOption = Option.builder("t")
                .longOpt("trafficFile")
                .hasArg()
                .argName("trafficFile")
                .desc("Log all HTTP traffic into a textual file")
                .build();

        Option answersOption = Option.builder()
                .longOpt("answers")
                .hasArg()
                .argName("answers")
                .desc("Set predefined answers (e.g. \"quit=N,follow=N\")")
                .build();

        Option base64Option = Option.builder()
                .longOpt("base64")
                .hasArg()
                .argName("base64Param")
                .desc("Parameter(s) containing Base64 encoded data")
                .build();

        Option base64SafeOption = Option.builder()
                .longOpt("base64-safe")
                .desc("Use URL and filename safe Base64 alphabet (RFC 4648)")
                .build();

        Option batchOption = Option.builder()
                .longOpt("batch")
                .desc("Never ask for user input, use the default behavior")
                .build();

        Option binaryFieldsOption = Option.builder()
                .longOpt("binary-fields")
                .hasArg()
                .argName("binaryFields")
                .desc("Result fields having binary values (e.g. \"digest\")")
                .build();

        Option checkInternetOption = Option.builder()
                .longOpt("check-internet")
                .desc("Check Internet connection before assessing the target")
                .build();

        Option cleanupOption = Option.builder()
                .longOpt("cleanup")
                .desc("Clean up the DBMS from sqlmap specific UDF and tables")
                .build();

        Option crawlOption = Option.builder()
                .longOpt("crawl")
                .hasArg()
                .argName("crawlDepth")
                .desc("Crawl the website starting from the target URL")
                .build();

        Option crawlExcludeOption = Option.builder()
                .longOpt("crawl-exclude")
                .hasArg()
                .argName("crawlExclude")
                .desc("Regexp to exclude pages from crawling (e.g. \"logout\")")
                .build();

        Option csvDelOption = Option.builder()
                .longOpt("csv-del")
                .hasArg()
                .argName("csvDel")
                .desc("Delimiting character used in CSV output (default \",\")")
                .build();

        Option charsetOption = Option.builder()
                .longOpt("charset")
                .hasArg()
                .argName("charset")
                .desc("Blind SQL injection charset (e.g. \"0123456789abcdef\")")
                .build();

        Option dumpFileOption = Option.builder()
                .longOpt("dump-file")
                .hasArg()
                .argName("dumpFile")
                .desc("Store dumped data to a custom file")
                .build();

        Option dumpFormatOption = Option.builder()
                .longOpt("dump-format")
                .hasArg()
                .argName("dumpFormat")
                .desc("Format of dumped data (CSV (default), HTML or SQLITE)")
                .build();

        Option encodingOption = Option.builder()
                .longOpt("encoding")
                .hasArg()
                .argName("encoding")
                .desc("Character encoding used for data retrieval (e.g. GBK)")
                .build();

        Option etaOption = Option.builder()
                .longOpt("eta")
                .desc("Display for each output the estimated time of arrival")
                .build();

        Option flushSessionOption = Option.builder()
                .longOpt("flush-session")
                .desc("Flush session files for current target")
                .build();

        Option formsOption = Option.builder()
                .longOpt("forms")
                .desc("Parse and test forms on target URL")
                .build();

        Option freshQueriesOption = Option.builder()
                .longOpt("fresh-queries")
                .desc("Ignore query results stored in session file")
                .build();

        Option gpageOption = Option.builder()
                .longOpt("gpage")
                .hasArg()
                .argName("googlePage")
                .desc("Use Google dork results from specified page number")
                .build();

        Option harFileOption = Option.builder()
                .longOpt("har")
                .hasArg()
                .argName("harFile")
                .desc("Log all HTTP traffic into a HAR file")
                .build();

        Option hexOption = Option.builder()
                .longOpt("hex")
                .desc("Use hex conversion during data retrieval")
                .build();

        Option outputDirOption = Option.builder()
                .longOpt("output-dir")
                .hasArg()
                .argName("outputDir")
                .desc("Custom output directory path")
                .build();

        Option parseErrorsOption = Option.builder()
                .longOpt("parse-errors")
                .desc("Parse and display DBMS error messages from responses")
                .build();

        Option preprocessOption = Option.builder()
                .longOpt("preprocess")
                .hasArgs()
                .argName("preprocessScripts")
                .desc("Use given script(s) for preprocessing (request)")
                .build();

        Option postprocessOption = Option.builder()
                .longOpt("postprocess")
                .hasArgs()
                .argName("postprocessScripts")
                .desc("Use given script(s) for postprocessing (response)")
                .build();

        Option repairOption = Option.builder()
                .longOpt("repair")
                .desc("Redump entries having unknown character marker (?)")
                .build();

        Option saveConfigOption = Option.builder()
                .longOpt("save")
                .hasArg()
                .argName("saveConfig")
                .desc("Save options to a configuration INI file")
                .build();

        Option scopeOption = Option.builder()
                .longOpt("scope")
                .hasArg()
                .argName("scope")
                .desc("Regexp for filtering targets")
                .build();

        Option skipHeuristicsOption = Option.builder()
                .longOpt("skip-heuristics")
                .desc("Skip heuristic detection of vulnerabilities")
                .build();

        Option skipWafOption = Option.builder()
                .longOpt("skip-waf")
                .desc("Skip heuristic detection of WAF/IPS protection")
                .build();

        Option tablePrefixOption = Option.builder()
                .longOpt("table-prefix")
                .hasArg()
                .argName("tablePrefix")
                .desc("Prefix used for temporary tables (default: \"sqlmap\")")
                .build();

        Option testFilterOption = Option.builder()
                .longOpt("test-filter")
                .hasArgs()
                .argName("testFilters")
                .desc("Select tests by payloads and/or titles (e.g. ROW)")
                .build();

        Option testSkipOption = Option.builder()
                .longOpt("test-skip")
                .hasArgs()
                .argName("testSkips")
                .desc("Skip tests by payloads and/or titles (e.g. BENCHMARK)")
                .build();

        Option timeLimitOption = Option.builder()
                .longOpt("time-limit")
                .hasArg()
                .argName("timeLimit")
                .desc("Run with a time limit in seconds (e.g. 3600)")
                .build();

        Option webRootOption = Option.builder()
                .longOpt("web-root")
                .hasArg()
                .argName("webRoot")
                .desc("Web server document root directory (e.g. \"/var/www\")")
                .build();

        OPTIONS.addOption(sessionFileOption);
        OPTIONS.addOption(trafficFileOption);
        OPTIONS.addOption(answersOption);
        OPTIONS.addOption(base64Option);
        OPTIONS.addOption(base64SafeOption);
        OPTIONS.addOption(batchOption);
        OPTIONS.addOption(binaryFieldsOption);
        OPTIONS.addOption(checkInternetOption);
        OPTIONS.addOption(cleanupOption);
        OPTIONS.addOption(crawlOption);
        OPTIONS.addOption(crawlExcludeOption);
        OPTIONS.addOption(csvDelOption);
        OPTIONS.addOption(charsetOption);
        OPTIONS.addOption(dumpFileOption);
        OPTIONS.addOption(dumpFormatOption);
        OPTIONS.addOption(encodingOption);
        OPTIONS.addOption(etaOption);
        OPTIONS.addOption(flushSessionOption);
        OPTIONS.addOption(formsOption);
        OPTIONS.addOption(freshQueriesOption);
        OPTIONS.addOption(gpageOption);
        OPTIONS.addOption(harFileOption);
        OPTIONS.addOption(hexOption);
        OPTIONS.addOption(outputDirOption);
        OPTIONS.addOption(parseErrorsOption);
        OPTIONS.addOption(preprocessOption);
        OPTIONS.addOption(postprocessOption);
        OPTIONS.addOption(repairOption);
        OPTIONS.addOption(saveConfigOption);
        OPTIONS.addOption(scopeOption);
        OPTIONS.addOption(skipHeuristicsOption);
        OPTIONS.addOption(skipWafOption);
        OPTIONS.addOption(tablePrefixOption);
        OPTIONS.addOption(testFilterOption);
        OPTIONS.addOption(testSkipOption);
        OPTIONS.addOption(timeLimitOption);
        OPTIONS.addOption(webRootOption);

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

        Option mnemonicsOption = Option.builder("z")
                .longOpt("mnemonics")
                .hasArg()
                .argName("mnemonics")
                .desc("Use short mnemonics (e.g. \"flu,bat,ban,tec=EU\")")
                .build();

        Option alertOption = Option.builder()
                .longOpt("alert")
                .hasArg()
                .argName("alert")
                .desc("Run host OS command(s) when SQL injection is found")
                .build();

        Option beepOption = Option.builder()
                .longOpt("beep")
                .desc("Beep on question and/or when vulnerability is found")
                .build();

        Option dependenciesOption = Option.builder()
                .longOpt("dependencies")
                .desc("Check for missing (optional) sqlmap dependencies")
                .build();

        Option disableColoringOption = Option.builder()
                .longOpt("disable-coloring")
                .desc("Disable console output coloring")
                .build();

        Option listTampersOption = Option.builder()
                .longOpt("list-tampers")
                .desc("Display list of available tamper scripts")
                .build();

        Option noLoggingOption = Option.builder()
                .longOpt("no-logging")
                .desc("Disable logging to a file")
                .build();

        Option offlineOption = Option.builder()
                .longOpt("offline")
                .desc("Work in offline mode (only use session data)")
                .build();

        Option purgeOption = Option.builder()
                .longOpt("purge")
                .desc("Safely remove all content from sqlmap data directory")
                .build();

        Option resultsFileOption = Option.builder()
                .longOpt("results-file")
                .hasArg()
                .argName("resultsFile")
                .desc("Location of CSV results file in multiple targets mode")
                .build();

        Option shellOption = Option.builder()
                .longOpt("shell")
                .desc("Prompt for an interactive sqlmap shell")
                .build();

        Option tmpDirOption = Option.builder()
                .longOpt("tmp-dir")
                .hasArg()
                .argName("tmpDir")
                .desc("Local directory for storing temporary files")
                .build();

        Option unstableOption = Option.builder()
                .longOpt("unstable")
                .desc("Adjust options for unstable connections")
                .build();

        Option updateOption = Option.builder()
                .longOpt("update")
                .desc("Update sqlmap")
                .build();

        Option wizardOption = Option.builder()
                .longOpt("wizard")
                .desc("Simple wizard interface for beginner users")
                .build();

        OPTIONS.addOption(mnemonicsOption);
        OPTIONS.addOption(alertOption);
        OPTIONS.addOption(beepOption);
        OPTIONS.addOption(dependenciesOption);
        OPTIONS.addOption(disableColoringOption);
        OPTIONS.addOption(listTampersOption);
        OPTIONS.addOption(noLoggingOption);
        OPTIONS.addOption(offlineOption);
        OPTIONS.addOption(purgeOption);
        OPTIONS.addOption(resultsFileOption);
        OPTIONS.addOption(shellOption);
        OPTIONS.addOption(tmpDirOption);
        OPTIONS.addOption(unstableOption);
        OPTIONS.addOption(updateOption);
        OPTIONS.addOption(wizardOption);


    }
}