package view;

import javax.swing.*;
import java.awt.*;

public class ReportView {
    private JFrame frame;
    private JTextArea reportArea;

    public ReportView() {
        frame = new JFrame("Relatório de Animais");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);
        frame.add(scrollPane, BorderLayout.CENTER);
    }

    public void setReport(String report) {
        reportArea.setText(report);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}