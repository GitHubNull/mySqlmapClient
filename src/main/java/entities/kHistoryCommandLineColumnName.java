package entities;

public enum kHistoryCommandLineColumnName {
    ID("序号"),

    COMMAND_LINE_STR("参数(s)字符串");

    private final String text;

    kHistoryCommandLineColumnName(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
