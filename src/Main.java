import controller.AnimalController;
import controller.UserController;
import controller.CuidadorController;
import controller.MenuController;
import model.AnimalModel;
import model.UserModel;
import model.CuidadorModel; // Importar CuidadorModel
import view.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Não foi possível aplicar o Look and Feel do sistema.");
        }

        // Inicializar os modelos (agora CuidadorModel também interage com DB)
        AnimalModel animalModel = new AnimalModel();
        UserModel userModel = new UserModel();
        CuidadorModel cuidadorModel = new CuidadorModel(); // Instanciar CuidadorModel para gerenciar DB

        // Inicializar as views
        LoginView loginView = new LoginView();
        RegisterView registerView = new RegisterView();
        AnimalView animalView = new AnimalView();
        UserView userView = new UserView();
        ReportView reportView = new ReportView();
        MenuView menuView = new MenuView();
        CuidadorView cuidadorView = new CuidadorView();

        // Inicializar os controllers
        AnimalController animalController = new AnimalController(animalModel, animalView, reportView);
        UserController userController = new UserController(userModel, userView, reportView);
        CuidadorController cuidadorController = new CuidadorController(cuidadorModel, cuidadorView, reportView);

        // Inicializar MenuController com todos os componentes necessários
        MenuController menuController = new MenuController(
                menuView, animalView, userView, cuidadorView, reportView, loginView,
                animalController, userController, cuidadorController
        );

        // Iniciar com a tela de login
        SwingUtilities.invokeLater(() -> menuController.start());
    }
}