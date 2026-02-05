import javax.swing.UIManager;
import controller.LoginController;
import model.dao.UtilisateurDAO;
import view.LoginView;

public class Main {
    public static void main(String[] args) {
        // 1. Appliquer le style visuel du système (Ergonomie)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Initialiser le Modèle (DAO)
        UtilisateurDAO userDAO = new UtilisateurDAO();

        // 3. Initialiser la Vue (Fenêtre de login)
        LoginView loginView = new LoginView();

        // 4. Initialiser le Contrôleur (fait le lien entre les deux)
        new LoginController(loginView, userDAO);

        // 5. Afficher la fenêtre au centre de l'écran
        loginView.setVisible(true);
    }
}