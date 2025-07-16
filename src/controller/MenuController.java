package controller;
import view.*;
import model.*; // Importar modelos para acesso a métodos de autenticação e relatórios

import javax.swing.JOptionPane; // Para showConfirmDialog

public class MenuController {
    private MenuView menuView;
    private AnimalView animalView;
    private UserView userView;
    private CuidadorView cuidadorView;
    private ReportView reportView;
    private LoginView loginView;

    private AnimalController animalController;
    private UserController userController;
    private CuidadorController cuidadorController;

    // Construtor ajustado para receber todos os controladores necessários
    public MenuController(MenuView menuView, AnimalView animalView, UserView userView,
                          CuidadorView cuidadorView, ReportView reportView, LoginView loginView,
                          AnimalController animalController, UserController userController,
                          CuidadorController cuidadorController) {
        this.menuView = menuView;
        this.animalView = animalView;
        this.userView = userView;
        this.cuidadorView = cuidadorView;
        this.loginView = loginView;
        this.animalController = animalController;
        this.userController = userController;
        this.cuidadorController = cuidadorController;
        setupListeners();
    }

    private void setupListeners() {
        // Listeners para MenuView
        menuView.addAnimalButtonListener(e -> showAnimalMenu());
        menuView.addUserButtonListener(e -> showUserMenu());
        menuView.addCuidadorButtonListener(e -> showCuidadorMenu());
        menuView.addLogoutButtonListener(e -> logout());

        menuView.addExitListener(e -> exitApplication());


        animalView.addBackListener(e -> showMainMenu());

        userView.addBackListener(e -> showMainMenu());

        // Listeners para CuidadorView
        cuidadorView.addBackToMenuListener(e -> showMainMenu());

        // Listeners para LoginView
        loginView.addLoginListener(e -> performLogin());
            loginView.setVisible(false);
        loginView.addExitListener(e -> exitApplication());
    }

    // Métodos para navegação entre menus
    public void showMainMenu() {
        hideAllViews();
        menuView.setVisible(true);
    }

    public void showAnimalMenu() {
        hideAllViews();
        animalView.setVisible(true);
        animalController.updateAnimalArea(); // Atualiza a área de animais ao mostrar a tela
    }

    public void showUserMenu() {
        hideAllViews();
        userView.setVisible(true);
        userController.updateUserArea(); // Atualiza a área de usuários ao mostrar a tela
    }

    public void showCuidadorMenu() {
        hideAllViews();
        cuidadorView.setVisible(true);
        cuidadorController.updateCuidadorArea(); // Atualiza a área de cuidadores ao mostrar a tela
    }

    public void showReportMenu() {
        hideAllViews();
        reportView.setVisible(true);
        // Ao abrir a tela de relatório, podemos gerar um relatório padrão ou deixar o usuário escolher
        // generateGeneralReport(); // Opcional: gerar relatório geral ao abrir
    }

    public void showLoginMenu() {
        hideAllViews();
        loginView.setVisible(true);
        loginView.clearFields(); // Limpa os campos de login ao voltar
    }

    // Método auxiliar para ocultar todas as views
    private void hideAllViews() {
        menuView.setVisible(false);
        animalView.setVisible(false);
        userView.setVisible(false);
        cuidadorView.setVisible(false);
        loginView.setVisible(false);
    }

    // Métodos para ações específicas
    private void performLogin() {
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        // A autenticação é feita no UserModel.
        // O UserController deve ter um método para validar o usuário.
        if (userController.validateUser(username, password)) {
            loginView.showMessage("Login realizado com sucesso!");
            showMainMenu();
        } else {
            loginView.showError("Usuário ou senha inválidos!");
        }
    }

    // Este método deve ser implementado no UserController
    // private boolean validateLogin(String username, String password) {
    //     // Validação com verificação de nulos
    //     if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
    //         return false;
    //     }
    //     return userController.validateUser(username, password); // Chama o método do UserController
    // }

    private void logout() {
        int confirm = menuView.showConfirmDialog("Tem certeza que deseja sair?");
        if (confirm == JOptionPane.YES_OPTION) { // 0 = Yes
            showLoginMenu();
        }
    }

    private void exitApplication() {
        int confirm = menuView.showConfirmDialog("Tem certeza que deseja encerrar a aplicação?");
        if (confirm == JOptionPane.YES_OPTION) { // 0 = Yes
            System.exit(0);
        }
    }

    // Métodos para geração de relatórios (chamados pelos listeners da ReportView)
    // Estes métodos devem ser chamados por botões na ReportView, não diretamente aqui.
    // O MenuController apenas orquestra a exibição da ReportView.
    // A ReportView deve ter listeners para chamar esses métodos nos respectivos controladores.
    public void generateAnimalReport() {
        try {
            animalController.generateReport(); // O AnimalController já exibe o relatório na ReportView
        } catch (Exception e) {
            reportView.showError("Erro ao gerar relatório de animais: " + e.getMessage());
        }
    }

    public void generateUserReport() {
        try {
            userController.generateReport(); // O UserController deve ter um método para gerar e exibir o relatório
        } catch (Exception e) {
            reportView.showError("Erro ao gerar relatório de usuários: " + e.getMessage());
        }
    }

    public void generateCuidadorReport() {
        try {
            cuidadorController.generateReport(); // O CuidadorController já exibe o relatório na ReportView
        } catch (Exception e) {
            reportView.showError("Erro ao gerar relatório de cuidadores: " + e.getMessage());
        }
    }

    public void generateGeneralReport() {
        try {
            StringBuilder generalReport = new StringBuilder();
            generalReport.append("=== RELATÓRIO GERAL DO SISTEMA ===\n\n");

            // Chamar os métodos de geração de relatório de cada controlador
            // Estes métodos devem retornar a string do relatório para serem concatenados aqui
            // ou os controladores devem ter um método para "obter" o relatório formatado.
            // Para simplificar, vamos fazer os controladores retornarem a string do relatório.
            // NOTA: Os métodos generateReport() nos controladores atuais exibem o relatório diretamente.
            // Precisamos ajustar isso para que eles retornem a string.

            // Temporariamente, vamos chamar os métodos que já exibem e depois obter o texto da ReportView
            // Isso não é o ideal, mas funciona com a estrutura atual.
            // O ideal seria que generateReport() retornasse um String.

            // Opção 1: Se generateReport() nos controllers retornasse String
            // generalReport.append("--- ANIMAIS ---\n");
            // generalReport.append(animalController.getFormattedReport()); // Novo método
            // generalReport.append("\n\n--- USUÁRIOS ---\n");
            // generalReport.append(userController.getFormattedReport()); // Novo método
            // generalReport.append("\n\n--- CUIDADORES ---\n");
            // generalReport.append(cuidadorController.getFormattedReport()); // Novo método

            // Opção 2: Adaptando ao que já existe (menos ideal)
            // Para o contexto atual, onde generateReport() já exibe,
            // vamos apenas chamar e depois tentar pegar o texto da ReportView.
            // Isso é problemático se a ReportView não estiver visível ou se o texto for sobrescrito.
            // A melhor abordagem é que os controllers retornem a string do relatório.

            // Para este exercício, vamos assumir que os controllers terão um método que retorna a string do relatório.
            // Isso exigirá modificações nos AnimalController, UserController e CuidadorController.

            // Exemplo de como seria se os controllers retornassem String:
            generalReport.append("--- ANIMAIS ---\n");
            generalReport.append(animalController.getReportAsString()); // Método a ser criado
            generalReport.append("\n\n--- USUÁRIOS ---\n");
            generalReport.append(userController.getReportAsString()); // Método a ser criado
            generalReport.append("\n\n--- CUIDADORES ---\n");
            generalReport.append(cuidadorController.getReportAsString()); // Método a ser criado

            reportView.displayReport("Relatório Geral", generalReport.toString());
        } catch (Exception e) {
            reportView.showError("Erro ao gerar relatório geral: " + e.getMessage());
        }
    }

    public void exportReport() {
        try {
            String currentReport = reportView.getCurrentReport();
            if (currentReport != null && !currentReport.isEmpty()) {
                String fileName = JOptionPane.showInputDialog(reportView.frame, "Digite o nome do arquivo para exportar (ex: relatorio.txt):");
                if (fileName != null && !fileName.trim().isEmpty()) {
                    exportToFile(currentReport, fileName);
                    reportView.showMessage("Relatório exportado com sucesso para " + fileName + "!");
                } else {
                    reportView.showMessage("Nome do arquivo inválido.");
                }
            } else {
                reportView.showError("Nenhum relatório para exportar!");
            }
        } catch (Exception e) {
            reportView.showError("Erro ao exportar relatório: " + e.getMessage());
        }
    }

    private void exportToFile(String content, String fileName) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(fileName);
            writer.write(content);
            writer.close();
        } catch (java.io.IOException e) {
            throw new RuntimeException("Erro ao escrever arquivo: " + e.getMessage());
        }
    }

    // Método para inicialização da aplicação
    public void start() {
        showLoginMenu();
    }
}
