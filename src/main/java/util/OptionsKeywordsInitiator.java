package util;

import burp.BurpExtender;

import java.util.List;

public class OptionsKeywordsInitiator {
//    public static List<String> optionsKeywords = GlobalEnv.OPTIONS_KEYWORDS;
    public  static void init() {
        BurpExtender.stdout.println("OptionsKeywordsInitiator.init() begin...");
//        List<String> optionsKeywords = GlobalEnv.OPTIONS_KEYWORDS;

        String[] params;
        params = new String[]{
                "--current-user",
                "--current-db",
                "--hostname",
                "--is-dba",
                "--users",
                "--passwords",
                "--privileges",
                "--roles",
                "--dbs",
                "--tables",
                "--columns",
                "--schema",
                "--count",
                "--dump",
                "--dump-all",
                "--search",
                "--comments",
                "--statements",
//                "-D DB",
//                "-T TBL",
//                "-C COL",
//                "-X EXCLUDE",
//                "-U USER",
                "--exclude-sysdbs",
                "--pivot-column=P..",
                "--where=DUMPWHERE",
                "--start=LIMITSTART",
                "--stop=LIMITSTOP",
                "--first=FIRSTCHAR",
                "--last=LASTCHAR",
                "--sql-query=SQLQ..",
                "--sql-shell",
                "--sql-file=SQLFILE",
                "--common-tables",
                "--common-columns",
                "--common-files",
                "--udf-inject",
                "--shared-lib=SHLIB",
                "--file-read=FILE..",
                "--file-write=FIL..",
                "--file-dest=FILE..",
                "--os-cmd=OSCMD",
                "--os-shell",
                "--os-pwn",
                "--os-smbrelay",
                "--os-bof",
                "--priv-esc",
                "--msf-path=MSFPATH",
                "--tmp-path=TMPPATH",
                "--reg-read",
                "--reg-add",
                "--reg-del",
                "--reg-key=REGKEY",
                "--reg-value=REGVAL",
                "--reg-data=REGDATA",
                "--reg-type=REGTYPE",
//                "-s SESSIONFILE",
//                "-t TRAFFICFILE",
                "--answers=ANSWERS",
                "--base64=BASE64P..",
                "--base64-safe",
                "--batch",
                "--binary-fields=..",
                "--check-internet",
                "--cleanup",
                "--crawl=CRAWLDEPTH",
                "--crawl-exclude=..",
                "--csv-del=CSVDEL",
                "--charset=CHARSET",
                "--dump-file=DUMP..",
                "--dump-format=DU..",
                "--encoding=ENCOD..",
                "--eta",
                "--flush-session",
                "--forms",
                "--fresh-queries",
                "--gpage=GOOGLEPAGE",
                "--har=HARFILE",
                "--hex",
                "--output-dir=OUT..",
                "--parse-errors",
                "--preprocess=PRE..",
                "--postprocess=PO..",
                "--repair",
                "--save=SAVECONFIG",
                "--scope=SCOPE",
                "--skip-heuristics",
                "--skip-waf",
                "--table-prefix=T..",
                "--test-filter=TE..",
                "--test-skip=TEST..",
                "--web-root=WEBROOT",
//                "-z MNEMONICS",
                "--alert=ALERT",
                "--beep",
                "--dependencies",
                "--disable-coloring",
                "--list-tampers",
                "--no-logging",
                "--offline",
                "--purge",
                "--results-file=R..",
                "--shell",
                "--tmp-dir=TMPDIR",
                "--unstable",
                "--update"
//                "--wizard"
        };

        for (String param : params) {
            if (param.length() >= 3) {
                GlobalEnv.OPTIONS_KEYWORDS.add(param);
            }
        }

        BurpExtender.stdout.println("OptionsKeywordsInitiator.init() end...");
    }
}
