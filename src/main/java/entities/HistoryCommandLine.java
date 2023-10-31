package entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class HistoryCommandLine implements Comparable<HistoryCommandLine>, Serializable {
    private final static long serialVersionUID = 1;
    int id;
    String commandLineStr;

    public HistoryCommandLine(int id, String commandLineStr) {
        this.id = id;
        this.commandLineStr = commandLineStr;
    }

    @Override
    public int compareTo(HistoryCommandLine o) {
        Integer id = this.getId();
        return id.compareTo(o.getId());
    }
}
