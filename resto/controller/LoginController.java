package controller;

import model.dao.UtilisateurDAO;
import view.LoginView;
import view.MainView;
import javax.swing.JOptionPane;

public class LoginController {
    private LoginView loginView;
    private UtilisateurDAO userDAO;

    public LoginController(LoginView view, UtilisateurDAO dao) {
        this.loginView = view;
        this.userDAO = dao;

        // Listener pour le bouton de connexion
        this.loginView.getBtnConnexion().addActionListener(e -> gererConnexion());
    }

    private void gererConnexion() {
        String login = loginView.getLogin();
        String password = loginView.getPassword();

        // Validation de base : pas de texte vide
        if (login.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginView, "Veuillez remplir tous les champs.", "Erreur",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Vérification BDD
        if (userDAO.authentifier(login, password)) {
            // Si réussi, on ferme le login et on ouvre le menu principal
            loginView.dispose();
            MainView mainView = new MainView();
            new MainController(mainView).afficher();
        } else {
            // Message d'erreur compréhensible
            JOptionPane.showMessageDialog(loginView, "Login ou mot de passe incorrect.", "Échec",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}