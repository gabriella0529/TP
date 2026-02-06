package controller;

import model.dao.StatistiquesDAO;
import view.DashboardPanel;
import java.time.LocalDate;
import java.util.List;

public class DashboardController {
    private DashboardPanel view;
    private StatistiquesDAO dao;

    public DashboardController(DashboardPanel view) {
        this.view = view;
        this.dao = new StatistiquesDAO();
        rafraichirDonnees();
    }

    public void rafraichirDonnees() {
        // 1. Calcul du CA du jour
        String aujourdhui = LocalDate.now().toString();
        double ca = dao.obtenirChiffreAffaireJour(aujourdhui);
        view.setChiffreAffaire(ca);

        // 2. Chargement des alertes de stock
        view.viderAlertes();
        List<Object[]> alertes = dao.obtenirAlertesStock();
        for (Object[] ligne : alertes) {
            view.ajouterAlerte(ligne);
        }

        // 3. Chargement du Top Ventes
        view.viderTopVentes();
        List<Object[]> top = dao.obtenirTopProduitsVendus();
        for (Object[] ligne : top) {
            view.ajouterTopVente(ligne);
        }
    }
}