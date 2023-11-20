package table_models;

import entities.CommonCommandLineColumnName;
import entities.CommonCommandLineColumnNameIndex;
import entities.OptionsCommandLine;
import lombok.Getter;
import util.GlobalEnv;


import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public class CommandLineTableModel extends AbstractTableModel {
    private static int id = 1;
//    List<OptionsCommandLine> optionsCommandLineList = GlobalEnv.OPTIONS_COMMANDLINE_LIST;
    static final int STATIC_COLUMN_COUNT = 4;

//    public void setScanTaskArgsList(List<OptionsCommandLine> optionsCommandLineList) {
//        GlobalEnv.OPTIONS_COMMANDLINE_LIST = optionsCommandLineList;
//    }

    @Override
    public int getRowCount() {
        return GlobalEnv.OPTIONS_COMMANDLINE_LIST.size();
    }

    @Override
    public int getColumnCount() {
        return STATIC_COLUMN_COUNT;
    }

    public int generateId() {
        int generatedId = id;
        id = (id % 12) + 1;
        return generatedId;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (GlobalEnv.OPTIONS_COMMANDLINE_LIST.isEmpty() || (0 > rowIndex || rowIndex >= GlobalEnv.OPTIONS_COMMANDLINE_LIST.size()) || (0 > columnIndex || STATIC_COLUMN_COUNT <= columnIndex)) {
            return null;
        }

        OptionsCommandLine optionsCommandLine = GlobalEnv.OPTIONS_COMMANDLINE_LIST.get(rowIndex);
        switch (columnIndex) {
            case CommonCommandLineColumnNameIndex.ID_INDEX:
                return optionsCommandLine.getId();
            case CommonCommandLineColumnNameIndex.WAS_DEFAULT_INDEX:
                return optionsCommandLine.getWasDefault();
            case CommonCommandLineColumnNameIndex.TAG_INDEX:
                return optionsCommandLine.getTag();
            case 3:
                return optionsCommandLine.getCommandLineStr();
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
            case CommonCommandLineColumnNameIndex.ID_INDEX:
                return CommonCommandLineColumnName.ID.toString();
            case CommonCommandLineColumnNameIndex.WAS_DEFAULT_INDEX:
                return CommonCommandLineColumnName.WAS_DEFAULT.toString();
            case CommonCommandLineColumnNameIndex.TAG_INDEX:
                return CommonCommandLineColumnName.TAG.toString();
            case CommonCommandLineColumnNameIndex.COMMAND_LINE_STR_INDEX:
                return CommonCommandLineColumnName.COMMAND_LINE_STR.toString();

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
                return Boolean.class;
            case 2:
            case 3:
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
        if (null == obj || 0 > row || GlobalEnv.OPTIONS_COMMANDLINE_LIST.size() < row || 0 >= col || STATIC_COLUMN_COUNT <= col) {
            return;
        }

        OptionsCommandLine optionsCommandLine = GlobalEnv.OPTIONS_COMMANDLINE_LIST.get(row);
        switch (col) {
            case CommonCommandLineColumnNameIndex.WAS_DEFAULT_INDEX:
                SwingUtilities.invokeLater(() -> {
                    optionsCommandLine.setWasDefault((Boolean) obj);
                    fireTableCellUpdated(row, col);
                });
                break;
            case CommonCommandLineColumnNameIndex.TAG_INDEX:
                SwingUtilities.invokeLater(() -> {
                    optionsCommandLine.setTag((String) obj);
                    fireTableCellUpdated(row, col);
                });
                break;
            case CommonCommandLineColumnNameIndex.COMMAND_LINE_STR_INDEX:
                SwingUtilities.invokeLater(() -> {
                    optionsCommandLine.setCommandLineStr((String) obj);
                    fireTableCellUpdated(row, col);
                });
                break;
            default:
                break;
        }

    }

    public void addOptionsCommandLine(OptionsCommandLine optionsCommandLine) {
        if (null == optionsCommandLine) {
            return;
        }
        GlobalEnv.OPTIONS_COMMANDLINE_LIST.add(optionsCommandLine);
        SwingUtilities.invokeLater(this::fireTableDataChanged);
    }

    public synchronized void addOptionsCommandLine(String tag, String argsStr) {
        if (null == tag || null == argsStr){
            return;
        }
        int current_id = generateId();
        if (GlobalEnv.OPTIONS_COMMANDLINE_LIST.size() > GlobalEnv.MAX_HISTORY_COMMAND_LINE_LIST_LEN * 2){
            GlobalEnv.OPTIONS_COMMANDLINE_LIST.remove(0);
        }
        GlobalEnv.OPTIONS_COMMANDLINE_LIST.add(new OptionsCommandLine(current_id, tag, argsStr, false));
        SwingUtilities.invokeLater(this::fireTableDataChanged);
    }

    public synchronized void deleteOptionsCommandLineById(int id) {
        if (GlobalEnv.OPTIONS_COMMANDLINE_LIST.isEmpty() || (0 > id || id > GlobalEnv.OPTIONS_COMMANDLINE_LIST.size())) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            GlobalEnv.OPTIONS_COMMANDLINE_LIST.remove(id);
            fireTableRowsDeleted(id, id);
        });
    }

    public void updateWasDefaultById(int id, Boolean wasDefault) {
        if (GlobalEnv.OPTIONS_COMMANDLINE_LIST.isEmpty() || (0 > id || id >= GlobalEnv.OPTIONS_COMMANDLINE_LIST.size())) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            GlobalEnv.OPTIONS_COMMANDLINE_LIST.get(id).setWasDefault(wasDefault);
            fireTableCellUpdated(id, 1);
        });

    }

    public void updateTagById(int id, String tag) {
        if (GlobalEnv.OPTIONS_COMMANDLINE_LIST.isEmpty() || (0 > id || id >= GlobalEnv.OPTIONS_COMMANDLINE_LIST.size()) || (null == tag || tag.trim().isEmpty())) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            GlobalEnv.OPTIONS_COMMANDLINE_LIST.get(id).setTag(tag.trim());
            fireTableCellUpdated(id, 2);
        });

    }

    public void updateCommandLinesById(int id, String commandLineStr) {
        if (GlobalEnv.OPTIONS_COMMANDLINE_LIST.isEmpty() || (0 > id || id >= GlobalEnv.OPTIONS_COMMANDLINE_LIST.size()) || (null == commandLineStr || commandLineStr.trim().isEmpty())) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            GlobalEnv.OPTIONS_COMMANDLINE_LIST.get(id).setCommandLineStr(commandLineStr);
            fireTableCellUpdated(id, 3);
        });
    }

    public OptionsCommandLine getOptionsCommandLineById(int id) {
        if (GlobalEnv.OPTIONS_COMMANDLINE_LIST.isEmpty() || (0 > id || id >= GlobalEnv.OPTIONS_COMMANDLINE_LIST.size())) {
            return null;
        }

        return GlobalEnv.OPTIONS_COMMANDLINE_LIST.get(id);
    }

    public boolean isTagExist(String tagStr) {
        for (OptionsCommandLine optionsCommandLine : GlobalEnv.OPTIONS_COMMANDLINE_LIST) {
            if (optionsCommandLine.getTag().equals(tagStr)) {
                return true;
            }
        }
        return false;
    }


}
