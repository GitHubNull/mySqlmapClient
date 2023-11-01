package util;

import burp.BurpExtender;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;

public class Autocomplete implements DocumentListener {

    private enum Mode {
        INSERT,
        COMPLETION
    }

    private JTextField textField;
    private final List<String> keywords;
    private Mode mode = Mode.INSERT;

    public Autocomplete(JTextField textField, List<String> keywords) {
        this.textField = textField;
//        textField.getDocument().addDocumentListener(this);
        this.keywords = keywords;
        Collections.sort(keywords);
    }

    @Override
    public void changedUpdate(DocumentEvent ev) {
    }

    @Override
    public void removeUpdate(DocumentEvent ev) {
    }

    @Override
    public void insertUpdate(DocumentEvent ev) {
        BurpExtender.stdout.println("[line: 41] - Autocomplete.insertUpdate() begin..");
        if (ev.getLength() != 1){
            BurpExtender.stdout.println("[line: 43] - Autocomplete.insertUpdate()");
            return;
        }

        int pos = ev.getOffset();
        String content = null;
        try {
            content = textField.getText(0, pos + 1);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        // Find where the word starts
        int w;
        for (w = pos; w >= 0; w--) {
            assert content != null;
            if (!Character.isLetter(content.charAt(w))) {
                BurpExtender.stdout.println("[line: 60] - Autocomplete.insertUpdate()");
                break;
            }
        }

        // Too few chars
        if (pos - w < 2){
            BurpExtender.stdout.println("[line: 67] - Autocomplete.insertUpdate()");
            return;
        }


        String prefix = content.substring(w + 1).toLowerCase();
        int n = Collections.binarySearch(keywords, prefix);
        if (n < 0 && -n <= keywords.size()) {
            String match = keywords.get(-n - 1);
            if (match.startsWith(prefix)) {
                BurpExtender.stdout.println("[line: 77] - Autocomplete.insertUpdate()");
                // A completion is found
                String completion = match.substring(pos - w);
                // We cannot modify Document from within notification,
                // so we submit a task that does the change later
                SwingUtilities.invokeLater(new CompletionTask(completion, pos + 1));
            }
        } else {
            // Nothing found
            BurpExtender.stdout.println("[line: 86] - Autocomplete.insertUpdate() -- Nothing found");
            mode = Mode.INSERT;
        }
        BurpExtender.stdout.println("CommitAction.insertUpdate() end...");
    }

    public class CommitAction extends AbstractAction {
        /**
         *
         */
        private static final long serialVersionUID = 5794543109646743416L;

        @Override
        public void actionPerformed(ActionEvent ev) {
            BurpExtender.stdout.println("CommitAction.CommitAction() begin...");
            if (mode == Mode.COMPLETION) {
                int pos = textField.getSelectionEnd();
                StringBuilder sb = new StringBuilder(textField.getText());
                sb.insert(pos, " ");
                textField.setText(sb.toString());
                textField.setCaretPosition(pos + 1);
                mode = Mode.INSERT;
            } else {
                textField.replaceSelection("\t");
            }
            BurpExtender.stdout.println("CommitAction.CommitAction() end...");
        }
    }

    private class CompletionTask implements Runnable {
        private String completion;
        private int position;

        CompletionTask(String completion, int position) {
            this.completion = completion;
            this.position = position;
        }

        public void run() {
            BurpExtender.stdout.println("Autocomplete.CompletionTask.run() begin...");
            StringBuilder sb = new StringBuilder(textField.getText());
            sb.insert(position, completion);
            textField.setText(sb.toString());
            textField.setCaretPosition(position + completion.length());
            textField.moveCaretPosition(position);
            mode = Mode.COMPLETION;
            BurpExtender.stdout.println("Autocomplete.CompletionTask.run() end...");
        }
    }
}
