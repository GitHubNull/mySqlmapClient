package util;

import burp.BurpExtender;
import com.alibaba.fastjson2.JSON;
import jsonModel.ScanConfiguration;
import org.apache.commons.cli.*;

public class ScanConfigurationHelper {

    public static ScanConfiguration CommandLineToScanConfiguration(String commandLine) throws IllegalAccessException {
        BurpExtender.stdout.println("ScanConfigurationHelper.ScanConfiguration Command line: " + commandLine);
        if (null == commandLine || commandLine.trim().isEmpty()) {
            BurpExtender.stdout.println("No command line arguments provided");
            return null;
        }

        BurpExtender.stdout.println("Command line arguments: " + commandLine);

        Options options = GlobalEnv.OPTIONS;

        String[] commandLineArgs = commandLine.trim().split(" ");

        for (String commandLineArg : commandLineArgs) {
            BurpExtender.stdout.println("ScanConfigurationHelper.CommandLineToScanConfiguration ommand line arg: " + commandLineArg);
        }

        CommandLine cmd;
        CommandLineParser parser = new DefaultParser();
//        HelpFormatter helper = new HelpFormatter();
        try {
            cmd = parser.parse(options, commandLineArgs);
        } catch (ParseException ex) {
            BurpExtender.stderr.println(ex.getMessage());
            return null;
        }

        if (cmd == null){
            BurpExtender.stderr.println("Command line parsing failed");
            return null;
        }

        ScanConfiguration scanConfiguration = new ScanConfiguration();

        /*
          Options:
            -h, --help            Show basic help message and exit
            -hh                   Show advanced help message and exit
            --version             Show program's version number and exit
            -v VERBOSE            Verbosity level: 0-6 (default 1)
         */

        if (cmd.hasOption("verbose")) {
            String verbose = cmd.getOptionValue("verbose").trim();
            if (!verbose.isEmpty() && MyStringUtil.isTruePortNumber(verbose)) {
                scanConfiguration.setVerbose(Integer.parseInt(verbose));
            }
        }
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

        if (cmd.hasOption("url")) {
            String url = cmd.getOptionValue("url").trim();
            if (!url.isEmpty()) {
                scanConfiguration.setUrl(url);
            }
        }

        if (cmd.hasOption("direct")) {
            String direct = cmd.getOptionValue("direct").trim();
            if (!direct.isEmpty()) {
                scanConfiguration.setDirect(direct);
            }
        }

        if (cmd.hasOption("logfile")) {
            String logfile = cmd.getOptionValue("logfile").trim();
            if (!logfile.isEmpty()) {
                scanConfiguration.setLogfile(logfile);
            }
        }

        if (cmd.hasOption("bulkFile")) {
            String bulkfile = cmd.getOptionValue("bulkFile").trim();
            if (!bulkfile.isEmpty()) {
                scanConfiguration.setBulkFile(bulkfile);
            }
        }

        if (cmd.hasOption("requestFile")) {
            String requestfile = cmd.getOptionValue("requestFile").trim();
            if (!requestfile.isEmpty()) {
                scanConfiguration.setRequestFile(requestfile);
            }
        }

        if (cmd.hasOption("googleDork")) {
            String googledork = cmd.getOptionValue("googleDork").trim();
            if (!googledork.isEmpty()) {
                scanConfiguration.setGoogleDork(googledork);
            }
        }

        if (cmd.hasOption("configFile")) {
            String configfile = cmd.getOptionValue("configFile").trim();
            if (!configfile.isEmpty()) {
                scanConfiguration.setConfigFile(configfile);
            }
        }
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
        if (cmd.hasOption("user-agent")) {
            String userAgent = cmd.getOptionValue("user-agent").trim();
            if (!userAgent.isEmpty()) {
                scanConfiguration.setAgent(userAgent);
            }
        }

        if (cmd.hasOption("header")) {
            String header = cmd.getOptionValue("header").trim();
            if (!header.isEmpty()) {
                scanConfiguration.setHeader(header);
            }
        }

        if (cmd.hasOption("method")) {
            String method = cmd.getOptionValue("method").trim();
            if (!method.isEmpty()) {
                scanConfiguration.setCookie(method);
            }
        }

        if (cmd.hasOption("data")) {
            String data = cmd.getOptionValue("data").trim();
            if (!data.isEmpty()) {
                scanConfiguration.setData(data);
            }
        }

        if (cmd.hasOption("param-del")) {
            String paramDel = cmd.getOptionValue("param-del").trim();
            if (!paramDel.isEmpty()) {
                scanConfiguration.setCookie(paramDel);
            }
        }

        if (cmd.hasOption("cookie")) {
            String cookie = cmd.getOptionValue("cookie").trim();
            if (!cookie.isEmpty()) {
                scanConfiguration.setCookie(cookie);
            }
        }

        if (cmd.hasOption("cookie-del")) {
            String cookieDel = cmd.getOptionValue("cookie-del").trim();
            if (!cookieDel.isEmpty()) {
                scanConfiguration.setCookieDel(cookieDel);
            }
        }

        if (cmd.hasOption("live-cookies")) {
            String liveCookies = cmd.getOptionValue("live-cookies").trim();
            BurpExtender.stdout
                    .println("[+] Loading live cookies from " + liveCookies);
            if (!liveCookies.isEmpty()) {
                BurpExtender.stdout.println("[+] liveCookies is not  empty");
                scanConfiguration.setLiveCookies(liveCookies);
            }else{
                BurpExtender.stdout
                        .println("[!] liveCookies is empty");
            }
        }else{
            BurpExtender.stdout.println("[!] cmd has no live-cookies");
        }

        if (cmd.hasOption("load-cookies")) {
            String loadCookies = cmd.getOptionValue("load-cookies").trim();
            if (!loadCookies.isEmpty()) {
                scanConfiguration.setLoadCookies(loadCookies);
            }
        }

        // drop-set-cookie
        if (cmd.hasOption("drop-set-cookie")) {
            scanConfiguration.setDropSetCookie(true);
        }

        // mobile
        if (cmd.hasOption("mobile")) {
            scanConfiguration.setMobile(true);
        }

        // random-agent
        if (cmd.hasOption("random-agent")) {
            scanConfiguration.setRandomAgent(true);
        }

        // host
        if (cmd.hasOption("host")) {
            String host = cmd.getOptionValue("host").trim();
            if (!host.isEmpty()) {
                scanConfiguration.setHost(host);
            }
        }

        // referer
        if (cmd.hasOption("referer")) {
            String referer = cmd.getOptionValue("referer").trim();
            if (!referer.isEmpty()) {
                scanConfiguration.setReferer(referer);
            }
        }

        // headers
        if (cmd.hasOption("headers")) {
            String headers = cmd.getOptionValue("headers").trim();
            if (!headers.isEmpty()) {
                scanConfiguration.setHeaders(headers);
            }
        }

        // auth-type
        if (cmd.hasOption("auth-type")) {
            String authType = cmd.getOptionValue("authType").trim();
            if (!authType.isEmpty()) {
                scanConfiguration.setAuthType(authType);
            }
        }

        // auth-cred
        if (cmd.hasOption("auth-cred")) {
            String authCred = cmd.getOptionValue("auth-cred").trim();
            if (!authCred.isEmpty()) {
                scanConfiguration.setAuthCred(authCred);
            }
        }

        // auth-file
        if (cmd.hasOption("auth-file")) {
            String authFile = cmd.getOptionValue("auth-file").trim();
            if (!authFile.isEmpty()) {
                scanConfiguration.setAuthFile(authFile);
            }
        }

        // ignore-code
        if (cmd.hasOption("ignore-code")) {
            String ignoreCode = cmd.getOptionValue("ignore-code").trim();
            if (!ignoreCode.isEmpty()) {
                scanConfiguration.setIgnoreCode(ignoreCode);
            }
        }

        // ignore-proxy
        if (cmd.hasOption("ignore-proxy")) {
            scanConfiguration.setIgnoreProxy(true);
        }

        // ignore-redirects
        if (cmd.hasOption("ignore-redirects")) {
            scanConfiguration.setIgnoreRedirects(true);
        }

        // ignore-timeouts
        if (cmd.hasOption("ignore-timeouts")) {
            scanConfiguration.setIgnoreTimeouts(true);
        }

        // proxy
        if (cmd.hasOption("proxy")) {
            String proxy = cmd.getOptionValue("proxy").trim();
            if (!proxy.isEmpty()) {
                scanConfiguration.setProxy(proxy);
            }
        }

        // proxy-cred
        if (cmd.hasOption("proxy-cred")) {
            String proxyCred = cmd.getOptionValue("proxyCred").trim();
            if (!proxyCred.isEmpty()) {
                scanConfiguration.setProxyCred(proxyCred);
            }
        }

        // proxy-file
        if (cmd.hasOption("proxy-file")) {
            String proxyFile = cmd.getOptionValue("proxy-file").trim();
            if (!proxyFile.isEmpty()) {
                scanConfiguration.setProxyFile(proxyFile);
            }
        }

        // proxy-freq
        if (cmd.hasOption("proxy-freq")) {
            String proxyFreq = cmd.getOptionValue("proxy-freq").trim();
            if (!proxyFreq.isEmpty()) {
                scanConfiguration.setProxyFreq(Integer.parseInt(proxyFreq));
            }
        }

        // tor
        if (cmd.hasOption("tor")) {
            scanConfiguration.setTor(true);
        }

        // tor-port
        if (cmd.hasOption("tor-port")) {
            String torPort = cmd.getOptionValue("tor-port").trim();
            if (!torPort.isEmpty()) {
                scanConfiguration.setTorPort(Integer.parseInt(torPort));
            }
        }

        // tor-type
        if (cmd.hasOption("tor-type")) {
            String torType = cmd.getOptionValue("tor-type").trim();
            if (!torType.isEmpty()) {
                scanConfiguration.setTorType(torType);
            }
        }

        // check-tor
        if (cmd.hasOption("check-tor")) {
            scanConfiguration.setCheckTor(true);
        }

        // delay
        if (cmd.hasOption("delay")) {
            String delay = cmd.getOptionValue("delay").trim();
            if (!delay.isEmpty()) {
                scanConfiguration.setDelay(Float.parseFloat(delay));
            }
        }

        // timeout
        if (cmd.hasOption("timeout")) {
            String timeout = cmd.getOptionValue("timeout").trim();
            if (!timeout.isEmpty()) {
                scanConfiguration.setTimeout(Float.parseFloat(timeout));
            }
        }

        // retries
        if (cmd.hasOption("retries")) {
            String retries = cmd.getOptionValue("retries").trim();
            if (!retries.isEmpty()) {
                scanConfiguration.setRetries(Integer.parseInt(retries));
            }
        }

        // retry-on
        if (cmd.hasOption("retry-on")) {
            String retryOn = cmd.getOptionValue("retry-on").trim();
            if (!retryOn.isEmpty()) {
                scanConfiguration.setRetryOn(retryOn);
            }
        }

        // randomize
        if (cmd.hasOption("randomize")) {
            String randomize = cmd.getOptionValue("randomize").trim();
            if (!randomize.isEmpty()) {
                scanConfiguration.setRParam(randomize);
            }
        }

        // safe-url
        if (cmd.hasOption("safe-url")) {
            String safeUrl = cmd.getOptionValue("safe-url").trim();
            if (!safeUrl.isEmpty()) {
                scanConfiguration.setSafeUrl(safeUrl);
            }
        }

        // safe-post
        if (cmd.hasOption("safe-post")) {
            String safePost = cmd.getOptionValue("safe-post").trim();
            if (!safePost.isEmpty()) {
                scanConfiguration.setSafePost(safePost);
            }
        }

        // safe-req
        if (cmd.hasOption("safe-req")) {
            String safeReq = cmd.getOptionValue("safe-req").trim();
            if (!safeReq.isEmpty()) {
                scanConfiguration.setSafeReqFile(safeReq);
            }
        }


        // safe-freq
        if (cmd.hasOption("safe-freq")) {
            String safeFreq = cmd.getOptionValue("safe-freq").trim();
            if (!safeFreq.isEmpty()) {
                scanConfiguration.setSafeFreq(Integer.parseInt(safeFreq));
            }
        }

        // skip-urlencode
        if (cmd.hasOption("skip-urlencode")) {
            scanConfiguration.setSkipUrlEncode(true);
        }

        // csrf-token
        if (cmd.hasOption("csrf-token")) {
            String csrfToken = cmd.getOptionValue("csrf-token").trim();
            if (!csrfToken.isEmpty()) {
                scanConfiguration.setCsrfToken(csrfToken);
            }
        }

        // csrf-url
        if (cmd.hasOption("csrf-url")) {
            String csrfUrl = cmd.getOptionValue("csrf-url").trim();
            if (!csrfUrl.isEmpty()) {
                scanConfiguration.setCsrfUrl(csrfUrl);
            }
        }

        // csrf-method
        if (cmd.hasOption("csrf-method")) {
            String csrfMethod = cmd.getOptionValue("csrf-method").trim();
            if (!csrfMethod.isEmpty()) {
                scanConfiguration.setCsrfMethod(csrfMethod);
            }
        }

        // csrf-data
        if (cmd.hasOption("csrf-data")) {
            String csrfData = cmd.getOptionValue("csrf-data").trim();
            if (!csrfData.isEmpty()) {
                scanConfiguration.setCsrfData(csrfData);
            }
        }


        // csrf-retries
        if (cmd.hasOption("csrf-retries")) {
            String csrfRetries = cmd.getOptionValue("csrf-retries").trim();
            if (!csrfRetries.isEmpty()) {
                scanConfiguration.setCsrfRetries(Integer.parseInt(csrfRetries));
            }
        }

        // force-ssl
        if (cmd.hasOption("force-ssl")) {
            scanConfiguration.setForceSSL(true);
        }

        // chunked
        if (cmd.hasOption("chunked")) {
            scanConfiguration.setChunked(true);
        }

        // hpp
        if (cmd.hasOption("hpp")) {
            scanConfiguration.setHpp(true);
        }

        /*
        --eval=EVALCODE     Evaluate provided Python code before the request (e.g.
                        "import hashlib;id2=hashlib.md5(id).hexdigest()")

        // python code
             request.add_argument("--eval", dest="evalCode",
            help="Evaluate provided Python code before the request (e.g. \"import hashlib;id2=hashlib.md5(id).hexdigest()\")")
        */

        if (cmd.hasOption("eval")) {
            String evalCode = cmd.getOptionValue("eval").trim();
            if (!evalCode.isEmpty()) {
                scanConfiguration.setEvalCode(evalCode);
            }
        }

        /*
        Optimization:
            These options can be used to optimize the performance of sqlmap

            -o                  Turn on all optimization switches
            --predict-output    Predict common queries output
            --keep-alive        Use persistent HTTP(s) connections
            --null-connection   Retrieve page length without actual HTTP response body
            --threads=THREADS   Max number of concurrent HTTP(s) requests (default 1)
        */
        if (cmd.hasOption("optimize")) {
            scanConfiguration.setOptimize(true);
        }

        //  predict-output
        if (cmd.hasOption("predict-output")) {
            scanConfiguration.setPredictOutput(true);
        }

        // keep-alive
        if (cmd.hasOption("keep-alive")) {
            scanConfiguration.setKeepAlive(true);
        }

        if (cmd.hasOption("null-connection")) {
            scanConfiguration.setNullConnection(true);
        }

        // threads
        if (cmd.hasOption("threads")) {
            String threads = cmd.getOptionValue("threads").trim();
            if (!threads.isEmpty() && MyStringUtil.isTruePortNumber(threads)) {
                scanConfiguration.setThreads(Integer.parseInt(threads));
            }
        }

        // testParameter
        if (cmd.hasOption("testParameter")) {
            String testParameter = cmd.getOptionValue("testParameter").trim();
            if (!testParameter.isEmpty()) {
                scanConfiguration.setTestParameter(testParameter);
            }
        }

        // skip
        if (cmd.hasOption("skip")) {
            String skip = cmd.getOptionValue("skip").trim();
            if (!skip.isEmpty()) {
                scanConfiguration.setSkip(skip);
            }
        }

        // skip-static
        if (cmd.hasOption("skip-static")) {
            scanConfiguration.setSkipStatic(true);
        }

        // param-exclude
        if (cmd.hasOption("param-exclude")) {
            String paramExclude = cmd.getOptionValue("param-exclude").trim();
            if (!paramExclude.isEmpty()) {
                scanConfiguration.setParamExclude(paramExclude);
            }
        }

        // param-filter
        if (cmd.hasOption("param-filter")) {
            String paramFilter = cmd.getOptionValue("param-filter").trim();
            if (!paramFilter.isEmpty()) {
                scanConfiguration.setParamFilter(paramFilter);
            }
        }

        // dbms
        if (cmd.hasOption("dbms")) {
            String dbms = cmd.getOptionValue("dbms").trim();
            if (!dbms.isEmpty()) {
                scanConfiguration.setDbms(dbms);
            }
        }

        // dbms-cred
        if (cmd.hasOption("dbms-cred")) {
            String dbmsCred = cmd.getOptionValue("dbms-cred").trim();
            if (!dbmsCred.isEmpty()) {
                scanConfiguration.setDbmsCred(dbmsCred);
            }
        }

        // os
        if (cmd.hasOption("os")) {
            String os = cmd.getOptionValue("os").trim();
            if (!os.isEmpty()) {
                scanConfiguration.setOs(os);
            }
        }

        // invalid-bignum
        if (cmd.hasOption("invalid-bignum")) {
            scanConfiguration.setInvalidBignum(true);
        }

        // invalid-logical
        if (cmd.hasOption("invalid-logical")) {
            scanConfiguration.setInvalidLogical(true);
        }

        // invalid-string
        if (cmd.hasOption("invalid-string")) {
            scanConfiguration.setInvalidString(true);
        }

        // no-cast
        if (cmd.hasOption("no-cast")) {
            scanConfiguration.setNoCast(true);
        }

        // no-escape
        if (cmd.hasOption("no-escape")) {
            scanConfiguration.setNoEscape(true);
        }

        // prefix
        if (cmd.hasOption("prefix")) {
            String prefix = cmd.getOptionValue("prefix").trim();
            if (!prefix.isEmpty()) {
                scanConfiguration.setPrefix(prefix);
            }
        }

        // suffix
        if (cmd.hasOption("suffix")) {
            String suffix = cmd.getOptionValue("suffix").trim();
            if (!suffix.isEmpty()) {
                scanConfiguration.setSuffix(suffix);
            }
        }

        // tamper
        if (cmd.hasOption("tamper")) {
            String tamper = cmd.getOptionValue("tamper").trim();
            if (!tamper.isEmpty()) {
                scanConfiguration.setTamper(tamper);
            }
        }


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

        if (cmd.hasOption("level")) {
            String level = cmd.getOptionValue("level").trim();
            if (!level.isEmpty() && MyStringUtil.isPositiveInteger(level)) {
                scanConfiguration.setLevel(Integer.parseInt(level));
            }
        }

        if (cmd.hasOption("risk")) {
            String risk = cmd.getOptionValue("risk").trim();
            if (!risk.isEmpty() && MyStringUtil.isPositiveInteger(risk)) {
                scanConfiguration.setRisk(Integer.parseInt(risk));
            }
        }

        if (cmd.hasOption("string")) {
            String string = cmd.getOptionValue("string").trim();
            if (!string.isEmpty()) {
                scanConfiguration.setString(string);
            }
        }

        if (cmd.hasOption("not-string")) {
            String notString = cmd.getOptionValue("not-string").trim();
            if (!notString.isEmpty()) {
                scanConfiguration.setNotString(notString);
            }
        }

        if (cmd.hasOption("regexp")) {
            String regexp = cmd.getOptionValue("regexp").trim();
            if (!regexp.isEmpty()) {
                scanConfiguration.setRegexp(regexp);
            }
        }

        // code
        if (cmd.hasOption("code")) {
            String code = cmd.getOptionValue("code").trim();
            if (!code.isEmpty() && MyStringUtil.isPositiveInteger(code)) {
                scanConfiguration.setCode(Integer.parseInt(code));
            }
        }

        // smart
        if (cmd.hasOption("smart")) {
            scanConfiguration.setSmart(true);
        }

        // text-only
        if (cmd.hasOption("text-only")) {
            scanConfiguration.setTextOnly(true);
        }

        // titles
        if (cmd.hasOption("titles")) {
            scanConfiguration.setTitles(true);
        }

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

        // technique
        if (cmd.hasOption("technique")) {
            String techniques = cmd.getOptionValue("technique").trim();
            if (!techniques.isEmpty()) {
                scanConfiguration.setTechnique(techniques);
            }
        }

        // time-sec
        if (cmd.hasOption("time-sec")) {
            String timeSec = cmd.getOptionValue("time-sec").trim();
            if (!timeSec.isEmpty() && MyStringUtil.isPositiveInteger(timeSec)) {
                scanConfiguration.setTimeSec(Integer.parseInt(timeSec));
            }
        }

        // union-cols
        if (cmd.hasOption("union-cols")) {
            String unionCols = cmd.getOptionValue("union-cols").trim();
            if (!unionCols.isEmpty()) {
                scanConfiguration.setUCols(unionCols);
            }
        }

        // union-char
        if (cmd.hasOption("union-char")) {
            String unionChar = cmd.getOptionValue("union-char").trim();
            if (!unionChar.isEmpty()) {
                scanConfiguration.setUChar(unionChar);
            }
        }

        // union-from
        if (cmd.hasOption("union-from")) {
            String unionFrom = cmd.getOptionValue("union-from").trim();
            if (!unionFrom.isEmpty()) {
                scanConfiguration.setUFrom(unionFrom);
            }
        }

        // dns-domain
        if (cmd.hasOption("dns-domain")) {
            String dnsDomain = cmd.getOptionValue("dns-domain").trim();
            if (!dnsDomain.isEmpty()) {
                scanConfiguration.setDnsDomain(dnsDomain);
            }
        }

        // second-url
        if (cmd.hasOption("second-url")) {
            String secondUrl = cmd.getOptionValue("second-url").trim();
            if (!secondUrl.isEmpty()) {
                scanConfiguration.setSecondUrl(secondUrl);
            }
        }

        // second-req
        if (cmd.hasOption("second-req")) {
            String secondReq = cmd.getOptionValue("second-req").trim();
            if (!secondReq.isEmpty()) {
                scanConfiguration.setSecondReq(secondReq);
            }
        }

        /*
           Fingerprint:
              -f, --fingerprint   Perform an extensive DBMS version fingerprint
         */

        // fingerprint
        if (cmd.hasOption("fingerprint")) {
            scanConfiguration.setExtensiveFp(true);
        }


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

        // all
        if (cmd.hasOption("all")) {
            scanConfiguration.setGetAll(true);
        }

        // banner
        if (cmd.hasOption("banner")) {
            scanConfiguration.setGetBanner(true);
        }

        // urrent-user
        if (cmd.hasOption("current-user")) {
            scanConfiguration.setGetCurrentUser(true);
        }

        // current-db
        if (cmd.hasOption("current-db")) {
            scanConfiguration.setGetCurrentDb(true);
        }

        // hostname
        if (cmd.hasOption("hostname")) {
            scanConfiguration.setGetHostname(true);
        }

        // is-dba
        if (cmd.hasOption("is-dba")) {
            scanConfiguration.setIsDba(true);
        }

        // users
        if (cmd.hasOption("users")) {
            scanConfiguration.setGetUsers(true);
        }

        // passwords
        if (cmd.hasOption("passwords")) {
            scanConfiguration.setGetPasswordHashes(true);
        }

        // privileges
        if (cmd.hasOption("privileges")) {
            scanConfiguration.setGetPrivileges(true);
        }

        // roles
        if (cmd.hasOption("roles")) {
            scanConfiguration.setGetRoles(true);
        }

        // dbs
        if (cmd.hasOption("dbs")) {
            scanConfiguration.setGetDbs(true);
        }

        // tables
        if (cmd.hasOption("tables")) {
            scanConfiguration.setGetTables(true);
        }

        // columns
        if (cmd.hasOption("columns")) {
            scanConfiguration.setGetColumns(true);
        }

        // schema
        if (cmd.hasOption("schema")) {
            scanConfiguration.setGetSchema(true);
        }


        // count
        if (cmd.hasOption("count")) {
            scanConfiguration.setGetCount(true);
        }

        // dump
        if (cmd.hasOption("dump")) {
            scanConfiguration.setDumpTable(true);
        }

        // dump-all
        if (cmd.hasOption("dump-all")) {
            scanConfiguration.setDumpAll(true);
        }

        // search
        if (cmd.hasOption("search")) {
            scanConfiguration.setSearch(true);
        }

        // comments
        if (cmd.hasOption("comments")) {
            scanConfiguration.setGetComments(true);
        }

        // statements
        if (cmd.hasOption("statements")) {
            scanConfiguration.setGetStatements(true);
        }

        // db
        if (cmd.hasOption("db")) {
            scanConfiguration.setDb(cmd.getOptionValue("db"));
        }

        // tbl
        if (cmd.hasOption("tbl")) {
            String tbl = cmd.getOptionValue("tbl").trim();
            if (!tbl.isEmpty()) {
                scanConfiguration.setTbl(tbl);
            }
        }

        // col
        if (cmd.hasOption("col")) {
            String col = cmd.getOptionValue("col").trim();
            if (!col.isEmpty()) {
                scanConfiguration.setCol(col);
            }
        }

        // exclude
        if (cmd.hasOption("exclude")) {
            String exclude = cmd.getOptionValue("exclude").trim();
            if (!exclude.isEmpty()) {
                scanConfiguration.setExclude(exclude);
            }
        }

        // user
        if (cmd.hasOption("user")) {
            String user = cmd.getOptionValue("user").trim();
            if (!user.isEmpty()) {
                scanConfiguration.setUser(user);
            }
        }

        // exclude-sysdbs
        if (cmd.hasOption("exclude-sysdbs")) {
            scanConfiguration.setExcludeSysDbs(true);
        }

        // pivot-column
        if (cmd.hasOption("pivot-column")) {
            String pivotColumn = cmd.getOptionValue("pivot-column").trim();
            if (!pivotColumn.isEmpty()) {
                scanConfiguration.setPivotColumn(pivotColumn);
            }
        }

        // where
        if (cmd.hasOption("where")) {
            String where = cmd.getOptionValue("where").trim();
            if (!where.isEmpty()) {
                scanConfiguration.setDumpWhere(where);
            }
        }

        // start
        if (cmd.hasOption("start")) {
            String start = cmd.getOptionValue("start").trim();
            if (!start.isEmpty() && MyStringUtil.isPositiveInteger(start)) {
                scanConfiguration.setLimitStart(Integer.parseInt(start));
            }
        }

        // stop
        if (cmd.hasOption("stop")) {
            String stop = cmd.getOptionValue("stop").trim();
            if (!stop.isEmpty() && MyStringUtil.isPositiveInteger(stop)) {
                scanConfiguration.setLimitStop(Integer.parseInt(stop));
            }
        }

        // first
        if (cmd.hasOption("first")) {
            String first = cmd.getOptionValue("first").trim();
            if (!first.isEmpty() && MyStringUtil.isPositiveInteger(first)) {
                scanConfiguration.setFirstChar(Integer.parseInt(first));
            }
        }

        // last
        if (cmd.hasOption("last")) {
            String last = cmd.getOptionValue("last").trim();
            if (!last.isEmpty() && MyStringUtil.isPositiveInteger(last)) {
                scanConfiguration.setLastChar(Integer.parseInt(last));
            }
        }

        // sql-query
        if (cmd.hasOption("sql-query")) {
            String sqlQuery = cmd.getOptionValue("sql-query").trim();
            if (!sqlQuery.isEmpty()) {
                scanConfiguration.setSqlQuery(sqlQuery);
            }
        }

        // sql-file
        if (cmd.hasOption("sql-file")) {
            String sqlFile = cmd.getOptionValue("sql-file").trim();
            if (!sqlFile.isEmpty()) {
                scanConfiguration.setSqlFile(sqlFile);
            }
        }


        /*
          Brute force:
              These options can be used to run brute force checks

              --common-tables     Check existence of common tables
              --common-columns    Check existence of common columns
              --common-files      Check existence of common files
         */

        // common-tables
        if (cmd.hasOption("common-tables")) {
            scanConfiguration.setCommonTables(true);
        }

        // common-columns
        if (cmd.hasOption("common-columns")) {
            scanConfiguration.setCommonColumns(true);
        }

        // common-files
        if (cmd.hasOption("common-files")) {
            scanConfiguration.setCommonFiles(true);
        }


        /*
           User-defined function injection:
              These options can be used to create custom user-defined functions

              --udf-inject        Inject custom user-defined functions
              --shared-lib=SHLIB  Local path of the shared library
         */

        // udf-inject
        if (cmd.hasOption("udf-inject")) {
            scanConfiguration.setUdfInject(true);
        }

        // shared-lib
        if (cmd.hasOption("shared-lib")) {
            String sharedLib = cmd.getOptionValue("shared-lib");
            if (sharedLib != null && !sharedLib.isEmpty()) {
                scanConfiguration.setShLib(sharedLib);
            }
        }


        /*
            File system access:
              These options can be used to access the back-end database management
              system underlying file system

              --file-read=FILE..  Read a file from the back-end DBMS file system
              --file-write=FIL..  Write a local file on the back-end DBMS file system
              --file-dest=FILE..  Back-end DBMS absolute filepath to write to
         */

        //  file-read
        if (cmd.hasOption("file-read")) {
            String fileRead = cmd.getOptionValue("file-read");
            if (fileRead != null && !fileRead.isEmpty()) {
                scanConfiguration.setFileRead(fileRead);
            }
        }

        //  file-write
        if (cmd.hasOption("file-write")) {
            String fileWrite = cmd.getOptionValue("file-write");
            if (fileWrite != null && !fileWrite.isEmpty()) {
                scanConfiguration.setFileWrite(fileWrite);
            }
        }

        //   file-dest
        if (cmd.hasOption("file-dest")) {
            String fileDest = cmd.getOptionValue("file-dest");
            if (fileDest != null && !fileDest.isEmpty()) {
                scanConfiguration.setFileDest(fileDest);
            }
        }


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

        // os-cmd
        if (cmd.hasOption("os-cmd")) {
            String osCmd = cmd.getOptionValue("os-cmd");
            if (osCmd != null && !osCmd.isEmpty()) {
                scanConfiguration.setOsCmd(osCmd);
            }
        }

        //  os-shell
        if (cmd.hasOption("os-shell")) {
            scanConfiguration.setOsShell(true);
        }

        // os-pwn
        if (cmd.hasOption("os-pwn")) {
            String osPwn = cmd.getOptionValue("os-pwn").trim();
            if (!osPwn.isEmpty()) {
                scanConfiguration.setOsPwn(osPwn);
            }
        }

        // os-smbrelay
        if (cmd.hasOption("os-smbrelay")) {
            scanConfiguration.setOsSmb(true);
        }

        // os-bof
        if (cmd.hasOption("os-bof")) {
            scanConfiguration.setOsBof(true);
        }

        //
        if (cmd.hasOption("priv-esc")) {
            scanConfiguration.setPrivEsc(true);
        }

        //
        if (cmd.hasOption("msf-path")) {
            String msfPath = cmd.getOptionValue("msf-path").trim();
            if (!msfPath.isEmpty()) {
                scanConfiguration.setMsfPath(msfPath);
            }
        }

        //
        if (cmd.hasOption("tmp-path")) {
            String tmpPath = cmd.getOptionValue("tmp-path").trim();
            if (!tmpPath.isEmpty()) {
                scanConfiguration.setTmpPath(tmpPath);
            }
        }


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

        // reg-read
        if (cmd.hasOption("reg-read")) {
            scanConfiguration.setRegRead(true);
        }

        // reg-add
        if (cmd.hasOption("reg-add")) {
            scanConfiguration.setRegAdd(true);
        }

        // reg-del
        if (cmd.hasOption("reg-del")) {
            scanConfiguration.setRegDel(true);
        }

        // reg-key
        if (cmd.hasOption("reg-key")) {
            String regKey = cmd.getOptionValue("reg-key").trim();
            if (!regKey.isEmpty()) {
                scanConfiguration.setRegKey(regKey);
            }
        }

        // reg-value
        if (cmd.hasOption("reg-value")) {
            String regVal = cmd.getOptionValue("reg-value").trim();
            if (!regVal.isEmpty()) {
                scanConfiguration.setRegVal(regVal);
            }
        }

        // reg-data
        if (cmd.hasOption("reg-data")) {
            String regData = cmd.getOptionValue("reg-data").trim();
            if (!regData.isEmpty()) {
                scanConfiguration.setRegData(regData);
            }
        }

        // reg-type
        if (cmd.hasOption("reg-type")) {
            String regType = cmd.getOptionValue("reg-type").trim();
            if (!regType.isEmpty()) {
                scanConfiguration.setRegType(regType);
            }
        }


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

        // -s SESSIONFILE
        if (cmd.hasOption("sessionFile")) {
            String sessionFile = cmd.getOptionValue("sessionFile").trim();
            if (!sessionFile.isEmpty()) {
                scanConfiguration.setSessionFile(sessionFile);
            }
        }

        // -t TRAFFICFILE
        if (cmd.hasOption("trafficFile")) {
            String trafficFile = cmd.getOptionValue("trafficFile").trim();
            if (!trafficFile.isEmpty()) {
                scanConfiguration.setTrafficFile(trafficFile);
            }
        }

        // --answers=ANSWERS
        if (cmd.hasOption("answers")) {
            String answers = cmd.getOptionValue("answers").trim();
            if (!answers.isEmpty()) {
                scanConfiguration.setAnswers(answers);
            }
        }

        // --base64=BASE64P..
        if (cmd.hasOption("base64")) {
            String base64Params = cmd.getOptionValue("base64").trim();
            if (!base64Params.isEmpty()) {
                scanConfiguration.setBase64Parameter(base64Params);
            }
        }

        // --base64-safe
        if (cmd.hasOption("base64-safe")) {
            scanConfiguration.setBase64Safe(true);
        }

        // --batch
        if (cmd.hasOption("batch")) {
            scanConfiguration.setBatch(true);
        }

        // --binary-fields=..
        if (cmd.hasOption("binary-fields")) {
            String binaryFields = cmd.getOptionValue("binary-fields").trim();
            if (!binaryFields.isEmpty()) {
                scanConfiguration.setBinaryFields(binaryFields);
            }
        }

        // --check-internet
        if (cmd.hasOption("check-internet")) {
            scanConfiguration.setCheckInternet(true);
        }

        // --cleanup
        if (cmd.hasOption("cleanup")) {
            scanConfiguration.setCleanup(true);
        }

        // --crawl=CRAWLDEPTH
        if (cmd.hasOption("crawl")) {
            String crawlDepth = cmd.getOptionValue("crawl").trim();
            if (!crawlDepth.isEmpty() && MyStringUtil.isPositiveInteger(crawlDepth)) {
                scanConfiguration.setCrawlDepth(Integer.parseInt(crawlDepth));
            }
        }

        // --crawl-exclude=..
        if (cmd.hasOption("crawl-exclude")) {
            String crawlExclude = cmd.getOptionValue("crawl-exclude").trim();
            if (!crawlExclude.isEmpty()) {
                scanConfiguration.setCrawlExclude(crawlExclude);
            }
        }

        // --csv-del=CSVDEL
        if (cmd.hasOption("csv-del")) {
            String csvDelimiter = cmd.getOptionValue("csv-del").trim();
            if (!csvDelimiter.isEmpty()) {
                scanConfiguration.setCsvDel(csvDelimiter);
            }
        }

        // --charset=CHARSET
        if (cmd.hasOption("charset")) {
            String charset = cmd.getOptionValue("charset").trim();
            if (!charset.isEmpty()) {
                scanConfiguration.setCharset(charset);
            }
        }

        // --dump-file=DUMP..
        if (cmd.hasOption("dump-file")) {
            String dumpFile = cmd.getOptionValue("dump-file").trim();
            if (!dumpFile.isEmpty()) {
                scanConfiguration.setDumpFile(dumpFile);
            }
        }

        // --dump-format=DU..
        if (cmd.hasOption("dump-format")) {
            String dumpFormat = cmd.getOptionValue("dump-format").trim();
            if (!dumpFormat.isEmpty()) {
                scanConfiguration.setDumpFormat(dumpFormat);
            }
        }

        // --encoding=ENCOD..
        if (cmd.hasOption("encoding")) {
            String encoding = cmd.getOptionValue("encoding").trim();
            if (!encoding.isEmpty()) {
                scanConfiguration.setEncoding(encoding);
            }
        }

        // --eta
        if (cmd.hasOption("eta")) {
            scanConfiguration.setEta(true);
        }

        // --flush-session
        if (cmd.hasOption("flush-session")) {
            scanConfiguration.setFlushSession(true);
        }

        // --forms
        if (cmd.hasOption("forms")) {
            scanConfiguration.setForms(true);
        }

        // --fresh-queries
        if (cmd.hasOption("fresh-queries")) {
            scanConfiguration.setFreshQueries(true);
        }

        // --gpage=GOOGLEPAGE
        if (cmd.hasOption("gpage")) {
            String googlePage = cmd.getOptionValue("gpage").trim();
            if (!googlePage.isEmpty() && MyStringUtil.isPositiveInteger(googlePage)) {
                scanConfiguration.setGooglePage(Integer.parseInt(googlePage));
            }
        }

        // --har=HARFILE
        if (cmd.hasOption("har")) {
            String harFile = cmd.getOptionValue("har").trim();
            if (!harFile.isEmpty()) {
                scanConfiguration.setHarFile(harFile);
            }
        }

        // --hex
        if (cmd.hasOption("hex")) {
            scanConfiguration.setHexConvert(true);
        }

        // --output-dir=OUT..
        if (cmd.hasOption("output-dir")) {
            String outputDir = cmd.getOptionValue("output-dir").trim();
            if (!outputDir.isEmpty()) {
                scanConfiguration.setOutputDir(outputDir);
            }
        }

        // --parse-errors
        if (cmd.hasOption("parse-errors")) {
            scanConfiguration.setParseErrors(true);
        }

        // --preprocess=PRE..
        if (cmd.hasOption("preprocess")) {
            String preprocessScripts = cmd.getOptionValue("preprocess").trim();
            if (!preprocessScripts.isEmpty()) {
                scanConfiguration.setPreprocess(preprocessScripts);
            }
        }

        // --postprocess=PO..
        if (cmd.hasOption("postprocess")) {
            String postprocessScripts = cmd.getOptionValue("postprocess").trim();
            if (!postprocessScripts.isEmpty()) {
                scanConfiguration.setPostprocess(postprocessScripts);
            }
        }

        // --repair
        if (cmd.hasOption("repair")) {
            scanConfiguration.setRepair(true);
        }

        // --save=SAVECONFIG
        if (cmd.hasOption("save")) {
            String saveConfig = cmd.getOptionValue("save").trim();
            if (!saveConfig.isEmpty()) {
                scanConfiguration.setSaveConfig(saveConfig);
            }
        }

        // --scope=SCOPE
        if (cmd.hasOption("scope")) {
            String scope = cmd.getOptionValue("scope").trim();
            if (!scope.isEmpty()) {
                scanConfiguration.setScope(scope);
            }
        }

        // --skip-heuristics
        if (cmd.hasOption("skip-heuristics")) {
            scanConfiguration.setSkipHeuristics(true);
        }

        // --skip-waf
        if (cmd.hasOption("skip-waf")) {
            scanConfiguration.setSkipWaf(true);
        }

        // --table-prefix=T..
        if (cmd.hasOption("table-prefix")) {
            String tablePrefix = cmd.getOptionValue("table-prefix").trim();
            if (!tablePrefix.isEmpty()) {
                scanConfiguration.setTestFilter(tablePrefix);
            }
        }

        // --test-filter=TE..
        if (cmd.hasOption("test-filter")) {
            String testFilter = cmd.getOptionValue("test-filter").trim();
            if (!testFilter.isEmpty()) {
                scanConfiguration.setTestFilter(testFilter);
            }
        }

        // --test-skip=TEST..
        if (cmd.hasOption("test-skip")) {
            String testSkip = cmd.getOptionValue("test-skip").trim();
            if (!testSkip.isEmpty()) {
                scanConfiguration.setTestSkip(testSkip);
            }
        }

        // --web-root=WEBROOT
        if (cmd.hasOption("web-root")) {
            String webRoot = cmd.getOptionValue("web-root").trim();
            if (!webRoot.isEmpty()) {
                scanConfiguration.setWebRoot(webRoot);
            }
        }


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

        // -z MNEMONICS
        if (cmd.hasOption("mnemonics")) {
            String mnemonics = cmd.getOptionValue("mnemonics").trim();
            if(!mnemonics.isEmpty()){
                scanConfiguration.setMnemonics(mnemonics);
            }
        }

        // --alert=ALERT
        if (cmd.hasOption("alert")) {
            String alert = cmd.getOptionValue("alert").trim();
            if (!alert.isEmpty()){
                scanConfiguration.setAlert(alert);
            }
        }

        // --beep
        if (cmd.hasOption("beep")) {
            scanConfiguration.setBeep(true);
        }

        // --dependencies
        if (cmd.hasOption("dependencies")) {
            scanConfiguration.setDependencies(true);
        }

        // --disable-coloring
        if (cmd.hasOption("disable-coloring")) {
            scanConfiguration.setDisableColoring(true);
        }

        // --list-tampers
        if (cmd.hasOption("list-tampers")) {
            scanConfiguration.setListTampers(true);
        }

        // --no-logging
        if (cmd.hasOption("no-logging")) {
            scanConfiguration.setNoLogging(true);
        }

        // --offline
        if (cmd.hasOption("offline")) {
            scanConfiguration.setOffline(true);
        }

        // --purge
        if (cmd.hasOption("purge")) {
           scanConfiguration.setPurge(true);
        }

        // --results-file=RESULTFILE
        if (cmd.hasOption("results-file")) {
            String resultsFile = cmd.getOptionValue("results-file").trim();
            if (!resultsFile.isEmpty()){
                scanConfiguration.setResultsFile(resultsFile);
            }
        }

        // --shell
        if (cmd.hasOption("shell")) {
            scanConfiguration.setShell(true);
        }

        // --tmp-dir=TMPDIR
        if (cmd.hasOption("tmp-dir")) {
            String tmpDir = cmd.getOptionValue("tmp-dir").trim();
            if (!tmpDir.isEmpty()){
                scanConfiguration.setTmpDir(tmpDir);
            }
        }

        // --unstable
        if (cmd.hasOption("unstable")) {
            scanConfiguration.setUnstable(true);
        }

        // --update
        if (cmd.hasOption("update")) {
            scanConfiguration.setUpdateAll(true);
        }

//        // --wizard
//        if (cmd.hasOption("wizard")) {
//            scanConfiguration.setWizard(true);
//        }

        BurpExtender.stdout.println("ScanConfigurationHelper.CommandLineToScanConfiguration Scan configuration: " + JSON.toJSONString(scanConfiguration));

        return scanConfiguration;
    }
}
