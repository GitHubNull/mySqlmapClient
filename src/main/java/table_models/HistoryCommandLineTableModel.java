package table_models;

import entities.kHistoryCommandLineColumnName;
import entities.HistoryCommandLineColumnNameIndex;
import entities.HistoryCommandLine;
import util.GlobalEnv;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class HistoryCommandLineTableModel extends AbstractTableModel {
    //    private List<HistoryCommandLine> historyCommandlineList;
    static final int STATIC_COLUMN_COUNT = 2;

//    public HistoryCommandLineTableModel() {
//        historyCommandlineList = GlobalEnv.HISTORY_COMMANDLINE_LIST;
//    }

//    public synchronized void setScanTaskArgsList(List<HistoryCommandLine> historyCommandLineList) {
//        this.historyCommandlineList = historyCommandLineList;
//    }

    public synchronized void addScanTaskHistoryCommandLine(String CommandLine) {
        int currentId = generateId();
        HistoryCommandLine historyCommandLine = new HistoryCommandLine(currentId, CommandLine);
        if (GlobalEnv.HISTORY_COMMANDLINE_LIST.size() >= GlobalEnv.MAX_HISTORY_COMMAND_LINE_LIST_LEN) {
            GlobalEnv.HISTORY_COMMANDLINE_LIST.remove(0);
        }
        GlobalEnv.HISTORY_COMMANDLINE_LIST.add(historyCommandLine);
        SwingUtilities.invokeLater(this::fireTableDataChanged);
    }

    private int generateId() {
        GlobalEnv.HistoryCommandLineTableModelId += 1;
        return GlobalEnv.HistoryCommandLineTableModelId;
    }

    @Override
    public synchronized int getRowCount() {
        return GlobalEnv.HISTORY_COMMANDLINE_LIST.size();
    }

    @Override
    public int getColumnCount() {
        return STATIC_COLUMN_COUNT;
    }

    @Override
    public synchronized Object getValueAt(int rowIndex, int columnIndex) {
        if (GlobalEnv.HISTORY_COMMANDLINE_LIST.isEmpty() || (0 > rowIndex || rowIndex >= GlobalEnv.HISTORY_COMMANDLINE_LIST.size()) || (0 > columnIndex || STATIC_COLUMN_COUNT <= columnIndex)) {
            return null;
        }

        HistoryCommandLine historyCommandLine = GlobalEnv.HISTORY_COMMANDLINE_LIST.get(rowIndex);
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
        if (null == obj || 0 > row || GlobalEnv.HISTORY_COMMANDLINE_LIST.size() < row || 0 >= col || STATIC_COLUMN_COUNT <= col) {
            return;
        }

        HistoryCommandLine historyCommandLine = GlobalEnv.HISTORY_COMMANDLINE_LIST.get(row);
        if (col == HistoryCommandLineColumnNameIndex.COMMAND_LINE_STR_INDEX) {
            SwingUtilities.invokeLater(() -> {
                historyCommandLine.setCommandLineStr((String) obj);
                fireTableCellUpdated(row, col);
            });
        }

    }


    public synchronized void deleteHistoryCommandLineById(int id) {
        if (GlobalEnv.HISTORY_COMMANDLINE_LIST.isEmpty() || (0 > id || id > GlobalEnv.HISTORY_COMMANDLINE_LIST.size())) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            GlobalEnv.HISTORY_COMMANDLINE_LIST.remove(id);
            fireTableRowsDeleted(id, id);
        });
    }

    public synchronized void deleteHistoryCommandLineByRowNumber(int rowNumber) {
        if (GlobalEnv.HISTORY_COMMANDLINE_LIST.isEmpty() || (0 > rowNumber || rowNumber > GlobalEnv.HISTORY_COMMANDLINE_LIST.size())) {
            return;
        }

        GlobalEnv.HISTORY_COMMANDLINE_LIST.remove(rowNumber);
        SwingUtilities.invokeLater(() -> {
            fireTableRowsDeleted(rowNumber, rowNumber);
        });
    }

    public void deleteHistoryCommandLineByRowNumbers(int[] rowNumbers) {
        if (GlobalEnv.HISTORY_COMMANDLINE_LIST.isEmpty() || (null == rowNumbers || 0 == rowNumbers.length || rowNumbers.length > GlobalEnv.HISTORY_COMMANDLINE_LIST.size())) {
            return;
        }

        for (int rowNumber : rowNumbers) {
            deleteHistoryCommandLineById(rowNumber);
        }
    }

    public synchronized void clearAll() {
        GlobalEnv.HISTORY_COMMANDLINE_LIST.clear();
        GlobalEnv.HistoryCommandLineTableModelId = 0;
        SwingUtilities.invokeLater(() -> {
            fireTableDataChanged();
        });
    }

    public HistoryCommandLine getHistoryCommandLineById(int id) {
        if (GlobalEnv.HISTORY_COMMANDLINE_LIST.isEmpty() || (0 > id || id >= GlobalEnv.HISTORY_COMMANDLINE_LIST.size())) {
            return null;
        }

        return GlobalEnv.HISTORY_COMMANDLINE_LIST.get(id);
    }
}
