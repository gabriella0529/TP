package controller;

import model.MouvementStock;
import model.Produit;
import model.dao.MouvementStockDAO;
import model.dao.ProduitDAO;
import view.StockPanel;
import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockController {
    private StockPanel view;
    private MouvementStockDAO mvtDao;
    private ProduitDAO prodDao;
    private Map<String, Produit> produitsMap = new HashMap<>();

    public StockController(StockPanel view) {
        this.view = view;
        this.mvtDao = new MouvementStockDAO();
        this.prodDao = new ProduitDAO();

        chargerProduits();
        initListeners();
    }

    private void chargerProduits() {
        List<Produit> produits = prodDao.listerTous();
        view.getComboProduits().removeAllItems();
        view.getComboProduits().addItem("-- Sélectionnez un produit --");
        for (Produit p : produits) {
            view.getComboProduits().addItem(p.getNom());
            produitsMap.put(p.getNom(), p);
        }
    }

    private void initListeners() {
        view.getComboProduits().addActionListener(e -> rafraichirHistorique());
        view.getBtnEntree().addActionListener(e -> enregistrerMouvement(MouvementStock.Type.ENTREE));
        view.getBtnSortie().addActionListener(e -> enregistrerMouvement(MouvementStock.Type.SORTIE));
    }

    private void rafraichirHistorique() {
        String nom = (String) view.getComboProduits().getSelectedItem();
        Produit p = produitsMap.get(nom);
        view.getTableModel().setRowCount(0);

        if (p != null) {
            // Recharger le produit pour avoir le stock à jour
            p = prodDao.trouverParId(p.getId());
            view.setStockActuel(p.getStock());

            List<MouvementStock> mvts = mvtDao.listerParProduit(p.getId());
            for (MouvementStock m : mvts) {
                view.getTableModel().addRow(new Object[] {
                        m.getDate(), m.getType(), m.getQuantite(), m.getMotif()
                });
            }
        } else {
            view.setStockActuel(0);
        }
    }

    private void enregistrerMouvement(MouvementStock.Type type) {
        String nom = (String) view.getComboProduits().getSelectedItem();
        Produit p = produitsMap.get(nom);
        if (p == null) {
            JOptionPane.showMessageDialog(view, "Sélectionnez un produit.");
            return;
        }

        try {
            String qteStr = JOptionPane.showInputDialog(view, "Quantité :");
            if (qteStr == null)
                return;
            int qte = Integer.parseInt(qteStr);
            if (qte <= 0)
                throw new Exception("La quantité doit être positive.");

            // Si sortie, vérifier si stock suffisant
            if (type == MouvementStock.Type.SORTIE && qte > p.getStock()) {
                throw new Exception("Stock insuffisant ! Disponible : " + p.getStock());
            }

            String motif = JOptionPane.showInputDialog(view, "Motif :");
            if (motif == null)
                motif = "Manuel";

            MouvementStock mvt = new MouvementStock(0, type, p.getId(), qte, LocalDateTime.now(), motif);
            mvtDao.enregistrer(mvt);

            JOptionPane.showMessageDialog(view, "Mouvement enregistré !");
            produitsMap.put(p.getNom(), prodDao.trouverParId(p.getId())); // Update local map
            rafraichirHistorique();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Format numérique invalide.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage());
        }
    }
}
