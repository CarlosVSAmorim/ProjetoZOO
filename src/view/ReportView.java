package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ReportView {
    private JFrame frame;
    private JTextArea reportArea;
    private JButton closeButton;

    public ReportView() {
        frame = new JFrame("Relatório - Sistema Zoológico");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Relatório"));

        closeButton = new JButton("Fechar");
        closeButton.setFont(new Font("Arial", Font.BOLD, 12));
        closeButton.addActionListener(e -> frame.setVisible(false));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void setReport(String report) {
        reportArea.setText(report);
    }

    public void addCloseListener(ActionListener listener) {
        closeButton.addActionListener(listener);
    }
}