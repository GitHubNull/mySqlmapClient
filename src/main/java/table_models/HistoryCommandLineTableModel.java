package table_models;

import entities.kHistoryCommandLineColumnName;
import entities.HistoryCommandLineColumnNameIndex;
import entities.HistoryCommandLine;
import util.GlobalEnv;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class HistoryCommandLineTableModel extends AbstractTableModel {
    List<HistoryCommandLine> historyCommandlineList = GlobalEnv.HISTORY_COMMANDLINE_LIST;
    static final int STATIC_COLUMN_COUNT = 2;

    public synchronized void setScanTaskArgsList(List<HistoryCommandLine> historyCommandLineList) {
        this.historyCommandlineList = historyCommandLineList;
    }

    public synchronized void addScanTaskHistoryCommandLine(String CommandLine) {
        int id = getRowCount();
        HistoryCommandLine historyCommandLine = new HistoryCommandLine(id, CommandLine);
        historyCommandlineList.add(historyCommandLine);
        SwingUtilities.invokeLater(() ->{
            fireTableRowsInserted(id, id);
        });
    }

    @Override
    public synchronized int getRowCount() {
        return historyCommandlineList.size();
    }

    @Override
    public int getColumnCount() {
        return STATIC_COLUMN_COUNT;
    }

    @Override
    public synchronized Object getValueAt(int rowIndex, int columnIndex) {
        if (0 == historyCommandlineList.size() || (0 > rowIndex || rowIndex >= historyCommandlineList.size()) || (0 > columnIndex || STATIC_COLUMN_COUNT <= columnIndex)) {
            return null;
        }

        HistoryCommandLine historyCommandLine = historyCommandlineList.get(rowIndex);
        switch (columnIndex) {
            case HistoryCommandLineColumnNameIndex.ID_INDEX:
                return historyCommandLine.getId();
            case HistoryCommandLineColumnNameIndex.COMMAND_LINE_STR_INDEX:
                return historyCommandLine.getCommandLineStr();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        if (0 > column || column >= STATIC_COLUMN_COUNT) {
            return null;
        }

        switch (column) {
            case HistoryCommandLineColumnNameIndex.ID_INDEX:
                return kHistoryCommandLineColumnName.ID.toString();
            case HistoryCommandLineColumnNameIndex.COMMAND_LINE_STR_INDEX:
                return kHistoryCommandLineColumnName.COMMAND_LINE_STR.toString();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (0 > columnIndex || columnIndex >= STATIC_COLUMN_COUNT) {
            return null;
        }

        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object obj, int row, int col) {
        if (null == obj || 0 > row || historyCommandlineList.size() < row || 0 >= col || STATIC_COLUMN_COUNT <= col) {
            return;
        }

        HistoryCommandLine historyCommandLine = historyCommandlineList.get(row);
        switch (col) {
            case HistoryCommandLineColumnNameIndex.COMMAND_LINE_STR_INDEX:
                SwingUtilities.invokeLater(() -> {
                    historyCommandLine.setCommandLineStr((String) obj);
                    fireTableCellUpdated(row, col);
                });
                break;
            default:
                break;
        }

    }


    public synchronized void deleteHistoryCommandLineById(int id) {
        if (0 == historyCommandlineList.size() || (0 > id || id > historyCommandlineList.size())) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            historyCommandlineList.remove(id);
            fireTableRowsDeleted(id, id);
        });
    }

    public HistoryCommandLine getHistoryCommandLineById(int id) {
        if (0 == historyCommandlineList.size() || (0 > id || id >= historyCommandlineList.size())) {
            return null;
        }

        return historyCommandlineList.get(id);
    }
}
