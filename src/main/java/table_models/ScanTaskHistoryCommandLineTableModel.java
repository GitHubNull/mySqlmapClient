package table_models;

import entities.ScanTaskHistoryCommandLineColumnName;
import entities.ScanTaskHistoryCommandLineColumnNameIndex;
import entities.ScanTaskHistoryCommandLine;
import util.GlobalEnv;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ScanTaskHistoryCommandLineTableModel extends AbstractTableModel {
    List<ScanTaskHistoryCommandLine> scanTaskHistoryCommandLineList = GlobalEnv.scanTaskHistoryCommandLineList;
    static final int STATIC_COLUMN_COUNT = 4;

    public void setScanTaskArgsList(List<ScanTaskHistoryCommandLine> scanTaskHistoryCommandLineList) {
        this.scanTaskHistoryCommandLineList = scanTaskHistoryCommandLineList;
    }

    @Override
    public int getRowCount() {
        return scanTaskHistoryCommandLineList.size();
    }

    @Override
    public int getColumnCount() {
        return STATIC_COLUMN_COUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (0 == scanTaskHistoryCommandLineList.size() || (0 > rowIndex || rowIndex >= scanTaskHistoryCommandLineList.size()) || (0 > columnIndex || STATIC_COLUMN_COUNT <= columnIndex)) {
            return null;
        }

        ScanTaskHistoryCommandLine scanTaskHistoryCommandLine = scanTaskHistoryCommandLineList.get(rowIndex);
        switch (columnIndex) {
            case ScanTaskHistoryCommandLineColumnNameIndex.ID_INDEX:
                return scanTaskHistoryCommandLine.getId();
            case ScanTaskHistoryCommandLineColumnNameIndex.COMMAND_LINE_STR_INDEX:
                return scanTaskHistoryCommandLine.getCommandLineStr();
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
            case ScanTaskHistoryCommandLineColumnNameIndex.ID_INDEX:
                return ScanTaskHistoryCommandLineColumnName.ID.toString();
            case ScanTaskHistoryCommandLineColumnNameIndex.COMMAND_LINE_STR_INDEX:
                return ScanTaskHistoryCommandLineColumnName.COMMAND_LINE_STR.toString();
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
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public void setValueAt(Object obj, int row, int col) {
        if (null == obj || 0 > row || scanTaskHistoryCommandLineList.size() < row || 0 >= col || STATIC_COLUMN_COUNT <= col) {
            return;
        }

        ScanTaskHistoryCommandLine scanTaskHistoryCommandLine = scanTaskHistoryCommandLineList.get(row);
        switch (col) {
            case ScanTaskHistoryCommandLineColumnNameIndex.COMMAND_LINE_STR_INDEX:
                SwingUtilities.invokeLater(() -> {
                    scanTaskHistoryCommandLine.setCommandLineStr((String) obj);
                    fireTableCellUpdated(row, col);
                });
                break;
            default:
                break;
        }

    }

    public void addOptionsCommandLine(ScanTaskHistoryCommandLine scanTaskHistoryCommandLine) {
        if (null == scanTaskHistoryCommandLine) {
            return;
        }
        int id = scanTaskHistoryCommandLine.getId();
        SwingUtilities.invokeLater(() -> {
            scanTaskHistoryCommandLineList.add(scanTaskHistoryCommandLine);
            fireTableRowsInserted(id, id);
        });
    }

    public void addOptionsCommandLine(String argsStr) {
        SwingUtilities.invokeLater(() -> {
            int id = scanTaskHistoryCommandLineList.size();
            scanTaskHistoryCommandLineList.add(new ScanTaskHistoryCommandLine(id, argsStr));
            fireTableRowsInserted(id, id);
        });
    }

    public synchronized void deleteOptionsCommandLineById(int id) {
        if (0 == scanTaskHistoryCommandLineList.size() || (0 > id || id > scanTaskHistoryCommandLineList.size())) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            scanTaskHistoryCommandLineList.remove(id);
            fireTableRowsDeleted(id, id);
        });
    }





    public ScanTaskHistoryCommandLine getOptionsCommandLineById(int id) {
        if (0 == scanTaskHistoryCommandLineList.size() || (0 > id || id >= scanTaskHistoryCommandLineList.size())) {
            return null;
        }

        return scanTaskHistoryCommandLineList.get(id);
    }

    public List<ScanTaskHistoryCommandLine> getOptionsCommandLineList() {
        return scanTaskHistoryCommandLineList;
    }
}
