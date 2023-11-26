package entities;

public enum CommonCommandLineColumnName {
    ID("#"),
    WAS_DEFAULT("Is default"),
    TAG("tag"),

    COMMAND_LINE_STR("command line");

    private final String text;

    CommonCommandLineColumnName(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
