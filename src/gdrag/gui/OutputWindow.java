package gdrag.gui;

import javax.swing.*;
import java.awt.*;

public class OutputWindow extends JFrame {
    private JTextArea textArea;

    public OutputWindow(String title) {
        super(title);
        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    public void println(String text) {
        textArea.append(text + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
