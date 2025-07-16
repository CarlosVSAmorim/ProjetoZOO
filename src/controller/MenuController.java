package controller;
import view.*;

public class MenuController {
    private MenuView menuView;
    private AnimalView animalView;
    private UserView userView;
    private CuidadorView CuidadorView; // Nova view
    private ReportView reportView;
    private LoginView loginView;
    private AnimalController animalController;
    private UserController userController;
    private CuidadorController CuidadorController; // Novo controller

    public MenuController(MenuView menuView, AnimalView animalView, UserView userView,
                          CuidadorView CuidadorView, ReportView reportView, LoginView loginView,
                          AnimalController animalController, UserController userController,
                          CuidadorController CuidadorController) {
        this.menuView = menuView;
        this.animalView = animalView;
        this.userView = userView;
        this.CuidadorView = CuidadorView; // Inicializar nova view
        this.reportView = reportView;
        this.loginView = loginView;
        this.animalController = animalController;
        this.userController = userController;
        this.CuidadorController = CuidadorController; // Inicializar novo controller
        setupListeners();
    }

    private void setupListeners() {
        // Listeners para MenuView
        menuView.addAnimalMenuListener(e -> showAnimalMenu());
        menuView.addUserMenuListener(e -> showUserMenu());
        menuView.addCuidadorMenuListener(e -> showCuidadorMenu());
        menuView.addReportMenuListener(e -> showReportMenu());
        menuView.addLogoutListener(e -> logout());
        menuView.addExitListener(e -> exitApplication());

        // Listeners para AnimalView
        animalView.addBackToMenuListener(e -> showMainMenu());
        animalView.addCreateAnimalListener(e -> animalController.createAnimal());
        animalView.addEditAnimalListener(e -> animalController.editAnimal());
        animalView.addDeleteAnimalListener(e -> animalController.deleteAnimal());
        animalView.addListAnimalsListener(e -> animalController.listAnimals());
        animalView.addSearchAnimalListener(e -> animalController.searchAnimal());

        // Listeners para UserView
        userView.addBackToMenuListener(e -> showMainMenu());
        userView.addCreateUserListener(e -> userController.createUser());
        userView.addEditUserListener(e -> userController.editUser());
        userView.addDeleteUserListener(e -> userController.deleteUser());
        userView.addListUsersListener(e -> userController.listUsers());
        userView.addSearchUserListener(e -> userController.searchUser());

        // Listeners para CuidadorView
        CuidadorView.addBackToMenuListener(e -> showMainMenu());
        CuidadorView.addCreateCuidadorListener(e -> CuidadorController.createCuidador());
        CuidadorView.addEditCuidadorListener(e -> CuidadorController.editCuidador());
        CuidadorView.addDeleteCuidadorListener(e -> CuidadorController.deleteCuidador());
        CuidadorView.addListCuidadorsListener(e -> CuidadorController.listCuidadors());
        CuidadorView.addSearchCuidadorListener(e -> CuidadorController.searchCuidador());
        CuidadorView.addAssignAnimalListener(e -> CuidadorController.assignAnimalToCuidador());
        CuidadorView.addUnassignAnimalListener(e -> CuidadorController.unassignAnimalFromCuidador());

        // Listeners para ReportView
        reportView.addBackToMenuListener(e -> showMainMenu());
        reportView.addGenerateAnimalReportListener(e -> generateAnimalReport());
        reportView.addGenerateUserReportListener(e -> generateUserReport());
        reportView.addGenerateCuidadorReportListener(e -> generateCuidadorReport());
        reportView.addGenerateGeneralReportListener(e -> generateGeneralReport());
        reportView.addExportReportListener(e -> exportReport());

        // Listeners para LoginView
        loginView.addLoginListener(e -> performLogin());
        loginView.addExitListener(e -> exitApplication());
    }

    // Métodos para navegação entre menus
    public void showMainMenu() {
        menuView.setVisible(true);
        animalView.setVisible(false);
        userView.setVisible(false);
        CuidadorView.setVisible(false);
        reportView.setVisible(false);
        loginView.setVisible(false);
    }

    public void showAnimalMenu() {
        menuView.setVisible(false);
        animalView.setVisible(true);
        userView.setVisible(false);
        CuidadorView.setVisible(false);
        reportView.setVisible(false);
        loginView.setVisible(false);
    }

    public void showUserMenu() {
        menuView.setVisible(false);
        animalView.setVisible(false);
        userView.setVisible(true);
        CuidadorView.setVisible(false);
        reportView.setVisible(false);
        loginView.setVisible(false);
    }

    public void showCuidadorMenu() {
        menuView.setVisible(false);
        animalView.setVisible(false);
        userView.setVisible(false);
        CuidadorView.setVisible(true);
        reportView.setVisible(false);
        loginView.setVisible(false);
    }

    public void showReportMenu() {
        menuView.setVisible(false);
        animalView.setVisible(false);
        userView.setVisible(false);
        CuidadorView.setVisible(false);
        reportView.setVisible(true);
        loginView.setVisible(false);
    }

    public void showLoginMenu() {
        menuView.setVisible(false);
        animalView.setVisible(false);
        userView.setVisible(false);
        CuidadorView.setVisible(false);
        reportView.setVisible(false);
        loginView.setVisible(true);
    }

    // Métodos para ações específicas
    private void performLogin() {
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        if (validateLogin(username, password)) {
            loginView.showMessage("Login realizado com sucesso!");
            showMainMenu();
        } else {
            loginView.showError("Usuário ou senha inválidos!");
        }
    }

    private boolean validateLogin(String username, String password) {
        // Implementar validação de login
        // Por exemplo, verificar no banco de dados ou lista de usuários
        return userController.validateUser(username, password);
    }

    private void logout() {
        int confirm = menuView.showConfirmDialog("Tem certeza que deseja sair?");
        if (confirm == 0) { // 0 = Yes
            showLoginMenu();
        }
    }

    private void exitApplication() {
        int confirm = menuView.showConfirmDialog("Tem certeza que deseja encerrar a aplicação?");
        if (confirm == 0) { // 0 = Yes
            System.exit(0);
        }
    }

    // Métodos para geração de relatórios
    private void generateAnimalReport() {
        try {
            String report = animalController.generateReport();
            reportView.displayReport("Relatório de Animais", report);
        } catch (Exception e) {
            reportView.showError("Erro ao gerar relatório de animais: " + e.getMessage());
        }
    }

    private void generateUserReport() {
        try {
            String report = userController.generateReport();
            reportView.displayReport("Relatório de Usuários", report);
        } catch (Exception e) {
            reportView.showError("Erro ao gerar relatório de usuários: " + e.getMessage());
        }
    }

    private void generateCuidadorReport() {
        try {
            String report = CuidadorController.generateReport();
            reportView.displayReport("Relatório de Cuidadores", report);
        } catch (Exception e) {
            reportView.showError("Erro ao gerar relatório de cuidadores: " + e.getMessage());
        }
    }

    private void generateGeneralReport() {
        try {
            StringBuilder generalReport = new StringBuilder();
            generalReport.append("=== RELATÓRIO GERAL DO SISTEMA ===\n\n");

            generalReport.append("--- ANIMAIS ---\n");
            generalReport.append(animalController.generateReport());
            generalReport.append("\n\n--- USUÁRIOS ---\n");
            generalReport.append(userController.generateReport());
            generalReport.append("\n\n--- CUIDADORES ---\n");
            generalReport.append(CuidadorController.generateReport());

            reportView.displayReport("Relatório Geral", generalReport.toString());
        } catch (Exception e) {
            reportView.showError("Erro ao gerar relatório geral: " + e.getMessage());
        }
    }

    private void exportReport() {
        try {
            String currentReport = reportView.getCurrentReport();
            if (currentReport != null && !currentReport.isEmpty()) {
                String fileName = reportView.getExportFileName();
                if (fileName != null && !fileName.isEmpty()) {
                    exportToFile(currentReport, fileName);
                    reportView.showMessage("Relatório exportado com sucesso!");
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