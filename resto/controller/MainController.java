package controller;

import view.MainView;
import view.ProduitForm;
import view.ProduitPanel;
import model.Produit;
import model.dao.ProduitDAO;
import model.dao.CategorieDAO;

import java.util.Map;

import javax.swing.JOptionPane;

public class MainController {
    private MainView mainView;

    public MainController(MainView mainView) {
        this.mainView = mainView;
        initListeners();
        mainView.setSize(1000, 700); // Redimensionner la fenêtre
        // Afficher le dashboard par défaut
        afficherDashboard();
    }

    private void basculerVue(javax.swing.JPanel pnl) {
        mainView.getMainContainer().removeAll();
        mainView.getMainContainer().add(pnl);
        mainView.getMainContainer().revalidate();
        mainView.getMainContainer().repaint();
    }

    private void afficherDashboard() {
        view.DashboardPanel pnl = new view.DashboardPanel();
        new controller.DashboardController(pnl);
        basculerVue(pnl);
    }

    private void initListeners() {
        mainView.getBtnDashboard().addActionListener(e -> afficherDashboard());

        mainView.getBtnCategories().addActionListener(e -> {
            view.CategoriePanel pnl = new view.CategoriePanel();
            new controller.CategorieController(pnl);
            basculerVue(pnl);
        });

        // Gestion des événements (clics boutons)
        mainView.getBtnProduits().addActionListener(e -> {
            ProduitPanel pnl = new ProduitPanel();
            ProduitDAO dao = new ProduitDAO();
            new ProduitController(pnl, dao);
            mainView.getMainContainer().removeAll();
            mainView.getMainContainer().add(pnl);
            mainView.getMainContainer().revalidate();
            mainView.getMainContainer().repaint();
        });

        mainView.getBtnCommandes().addActionListener(e -> {
            view.CommandePanel pnl = new view.CommandePanel();
            new controller.CommandeController(pnl);
            basculerVue(pnl);
        });

        mainView.getBtnStocks().addActionListener(e -> {
            view.StockPanel pnl = new view.StockPanel();
            new controller.StockController(pnl);

            mainView.getMainContainer().removeAll();
            mainView.getMainContainer().add(pnl);
            mainView.getMainContainer().revalidate();
            mainView.getMainContainer().repaint();
        });
    }

    public void afficher() {
        mainView.setVisible(true);
    }
}