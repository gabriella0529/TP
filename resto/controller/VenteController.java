package controller;

import model.dao.UtilisateurDAO;

public class VenteController {
    private UtilisateurDAO userDAO = new UtilisateurDAO();

    public void demarrerSession(String login, String password) {
        if (userDAO.authentifier(login, password)) {
            System.out.println("Bienvenue, " + login + " !");
            // Appeler ici la vue pour prendre une commande
        } else {
            System.out.println("Échec de connexion.");
        }
    }
}