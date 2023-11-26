package entities;

public enum kHistoryCommandLineColumnName {
    ID("#"),

    COMMAND_LINE_STR("command line");

    private final String text;

    kHistoryCommandLineColumnName(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
