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

    public MainController(MainView view) {
        this.mainView = view;
        initListeners();
    }

    private void initListeners() {
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
            // Logique pour créer une commande
            System.out.println("Navigation vers Commandes");
        });

        mainView.getBtnStocks().addActionListener(e -> {
            // Ouvrir le formulaire d'ajout de produit
            ouvrirFormulaireAjout();
        });
    }

    private void ouvrirFormulaireAjout() {
        // 1. Récupérer les catégories depuis le DAO pour le formulaire
        CategorieDAO categorieDAO = new CategorieDAO();
        Map<Integer, String> categories = categorieDAO.obtenirToutes();

        ProduitForm form = new ProduitForm(null, categories);
        form.setVisible(true);

        if (form.isConfirmed()) {
            try {
                // Validation des données
                String nom = form.getNom();
                if (nom.isEmpty())
                    throw new Exception("Le nom est obligatoire.");

                double prix = Double.parseDouble(form.getPrix());
                if (prix <= 0)
                    throw new Exception("Le prix doit être positif.");

                int stock = Integer.parseInt(form.getStock());
                if (stock < 0)
                    throw new Exception("Le stock ne peut pas être négatif.");

                // Récupérer l'ID de la catégorie à partir de son libellé
                int idCat = categories.entrySet().stream()
                        .filter(e -> e.getValue().equals(form.getCategorieSelectionnee()))
                        .map(Map.Entry::getKey).findFirst().orElse(-1);

                // 2. Appel au DAO pour l'insertion
                ProduitDAO dao = new ProduitDAO();
                dao.ajouter(new Produit(0, nom, prix, stock, idCat));

                JOptionPane.showMessageDialog(mainView, "Produit ajouté avec succès !");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainView, "Veuillez saisir des nombres valides.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainView, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void afficher() {
        mainView.setVisible(true);
    }
}