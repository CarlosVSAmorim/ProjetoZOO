import controller.AnimalController;
import controller.UserController;
import controller.MenuController;
import model.AnimalModel;
import model.UserModel;
import view.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Tentar aplicar o visual do sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Não foi possível aplicar o Look and Feel do sistema.");
        }

        // Inicializar os modelos
        AnimalModel animalModel = new AnimalModel();
        UserModel userModel = new UserModel();

        // Inicializar as views
        LoginView loginView = new LoginView();
        RegisterView registerView = new RegisterView();
        AnimalView animalView = new AnimalView();
        UserView userView = new UserView();
        ReportView reportView = new ReportView();
        MenuView menuView = new MenuView();

        // Inicializar os controllers
        AnimalController animalController = new AnimalController(
                animalModel, animalView, loginView, registerView, reportView, menuView
        );
        UserController userController = new UserController(userModel, userView);
        MenuController menuController = new MenuController(
                menuView, animalView, userView, reportView, loginView, animalController, userController);

        // Conectar o MenuView ao UserController
        userController.setMenuView(menuView);

        // Iniciar com a tela de login
        SwingUtilities.invokeLater(() -> loginView.setVisible(true));
    }
}
