import javax.swing.UIManager;
import controller.LoginController;
import model.dao.UtilisateurDAO;
import view.LoginView;

public class Main {
    public static void main(String[] args) {
        // 1. Appliquer le style visuel du système (Ergonomie)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            augmenterPolice(14);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Initialiser le Modèle (DAO)
        UtilisateurDAO userDAO = new UtilisateurDAO();

        // 3. Initialiser la Vue (Fenêtre de login)
        LoginView loginView = new LoginView();

        // 4. initialiser le Contrôleur (fait le lien entre les deux)
        new LoginController(loginView, userDAO);

        // 5. Afficher la fenêtre au centre de l'écran
        loginView.setVisible(true);
    }

    private static void augmenterPolice(int size) {
        java.awt.Font font = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, size);
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, new javax.swing.plaf.FontUIResource(font));
            }
        }
    }
}