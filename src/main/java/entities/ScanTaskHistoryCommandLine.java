package entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScanTaskHistoryCommandLine implements Comparable<ScanTaskHistoryCommandLine>, Serializable {
    private final static long serialVersionUID = 1;
    int id;
    String commandLineStr;

    public ScanTaskHistoryCommandLine(int id, String commandLineStr) {
        this.id = id;
        this.commandLineStr = commandLineStr;
    }

    @Override
    public int compareTo(ScanTaskHistoryCommandLine o) {
        Integer id = this.getId();
        return id.compareTo(o.getId());
    }
}
