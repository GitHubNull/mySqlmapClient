package entities;

public enum ScanTaskHistoryCommandLineColumnName {
    ID("序号"),

    COMMAND_LINE_STR("参数(s)字符串");

    private final String text;

    ScanTaskHistoryCommandLineColumnName(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
