import controller.AnimalController;
import model.AnimalModel;
import view.AnimalView;
import view.LoginView;
import view.RegisterView;
import view.ReportView;

public class Main {
    public static void main(String[] args) {
        AnimalModel model = new AnimalModel();
        LoginView loginView = new LoginView();
        RegisterView registerView = new RegisterView();
        AnimalView animalView = new AnimalView();
        ReportView reportView = new ReportView();
        AnimalController controller = new AnimalController(model, animalView, loginView, registerView, reportView);

        // Inicialmente, a tela de gerenciamento de animais deve estar oculta
        animalView.setVisible(false);
        registerView.setVisible(false);
        reportView.setVisible(false);
    }
}