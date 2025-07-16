package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ReportView {
    public JFrame frame; // Tornar frame público para acesso em JOptionPane
    private JTextArea reportArea;
    private JButton closeButton;
    private JButton exportButton; // Novo botão de exportar
    private JButton backToMenuButton; // Novo botão para voltar ao menu

    public ReportView() {
        frame = new JFrame("Relatório - Sistema Zoológico");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(700, 500); // Aumentado o tamanho
        frame.setLayout(new BorderLayout());

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Relatório"));

        closeButton = new JButton("Fechar");
        exportButton = new JButton("Exportar Relatório"); // Novo botão
        backToMenuButton = new JButton("Voltar ao Menu"); // Novo botão

        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        closeButton.setFont(buttonFont);
        exportButton.setFont(buttonFont);
        backToMenuButton.setFont(buttonFont);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(backToMenuButton); // Adicionado
        buttonPanel.add(exportButton); // Adicionado
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
        reportArea.setCaretPosition(0); // Rola para o topo
    }

    public String getCurrentReport() {
        return reportArea.getText();
    }

    public void addCloseListener(ActionListener listener) {
        closeButton.addActionListener(listener);
    }

    // Novo método para adicionar listener ao botão de exportar
    public void addExportListener(ActionListener listener) {
        exportButton.addActionListener(listener);
    }

    // Novo método para adicionar listener ao botão de voltar ao menu
    public void addBackToMenuListener(ActionListener listener) {
        backToMenuButton.addActionListener(listener);
    }

    // Métodos para exibir mensagens
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    // Método para obter nome do arquivo para exportação (usado pelo MenuController)
    public String getExportFileName() {
        return JOptionPane.showInputDialog(frame, "Digite o nome do arquivo para exportar (ex: relatorio.txt):");
    }

    public void displayReport(String relatórioGeral, String string) {

    }
}