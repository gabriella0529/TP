package controller;

import view.CommandePanel;
import model.dao.CommandeDAO;
import model.dao.ProduitDAO;
import model.Produit;
import model.LigneCommande;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandeController {
    private CommandePanel view;
    private CommandeDAO commandeDAO;
    private ProduitDAO produitDAO;
    private double totalCommande = 0;
    private Map<String, Produit> produitsMap; // Pour retrouver les produits par nom

    public CommandeController(CommandePanel view) {
        this.view = view;
        this.commandeDAO = new CommandeDAO();
        this.produitDAO = new ProduitDAO();
        this.produitsMap = new HashMap<>();

        chargerProduits();
        initListeners();
    }

    private void chargerProduits() {
        try {
            List<Produit> produits = produitDAO.listerTous();
            view.getComboProduits().removeAllItems();

            for (Produit p : produits) {
                if (p.getStock() > 0) { // Afficher uniquement les produits en stock
                    view.getComboProduits().addItem(p.getNom());
                    produitsMap.put(p.getNom(), p);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erreur lors du chargement des produits : " + e.getMessage());
        }
    }

    private void initListeners() {
        // Ajouter un produit au panier
        view.getBtnAjouterLigne().addActionListener(e -> {
            try {
                String nomProduit = (String) view.getComboProduits().getSelectedItem();
                if (nomProduit == null) {
                    throw new Exception("Veuillez sélectionner un produit");
                }

                int qte = Integer.parseInt(view.getTxtQuantite().getText().trim());
                if (qte <= 0)
                    throw new Exception("La quantité doit être > 0");

                // Récupérer le produit réel
                Produit produit = produitsMap.get(nomProduit);
                if (produit == null) {
                    throw new Exception("Produit introuvable");
                }

                // Vérifier le stock disponible
                if (qte > produit.getStock()) {
                    throw new Exception("Stock insuffisant ! Disponible : " + produit.getStock());
                }

                // Calculer le montant
                double montantLigne = qte * produit.getPrix();

                // Ajouter la ligne au tableau
                view.getTableModel().addRow(new Object[] {
                        produit.getId(),
                        produit.getNom(),
                        produit.getPrix(),
                        qte,
                        montantLigne
                });

                calculerTotal();

                // Réinitialiser le champ quantité
                view.getTxtQuantite().setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Erreur : Quantité invalide");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage());
            }
        });

        // Supprimer une ligne du panier
        view.getBtnSupprimerLigne().addActionListener(e -> {
            int selectedRow = view.getTablePanier().getSelectedRow();

            if (selectedRow >= 0) {
                view.getTableModel().removeRow(selectedRow);
                calculerTotal();
                JOptionPane.showMessageDialog(view, "Ligne supprimée du panier");
            } else {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner une ligne à supprimer");
            }
        });

        // Valider la commande
        view.getBtnValider().addActionListener(e -> {
            if (view.getTableModel().getRowCount() == 0) {
                JOptionPane.showMessageDialog(view, "Le panier est vide !");
                return;
            }

            try {
                // Créer la liste des lignes de commande
                List<LigneCommande> lignes = new ArrayList<>();

                for (int i = 0; i < view.getTableModel().getRowCount(); i++) {
                    int idProd = (int) view.getTableModel().getValueAt(i, 0);
                    int qte = (int) view.getTableModel().getValueAt(i, 3);
                    double prix = (double) view.getTableModel().getValueAt(i, 2);

                    lignes.add(new LigneCommande(idProd, qte, prix));
                }

                // Enregistrer en base de données
                commandeDAO.enregistrerCommande(totalCommande, lignes);

                JOptionPane.showMessageDialog(view, "Commande validée avec succès !");
                viderPanier();
                chargerProduits(); // Recharger pour mettre à jour les stocks

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erreur lors de la validation : " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // Annuler la commande
        view.getBtnAnnuler().addActionListener(e -> {
            if (view.getTableModel().getRowCount() == 0) {
                JOptionPane.showMessageDialog(view, "Le panier est déjà vide !");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(view,
                    "Voulez-vous vraiment vider le panier ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                viderPanier();
                JOptionPane.showMessageDialog(view, "Panier vidé.");
            }
        });
    }

    private void calculerTotal() {
        totalCommande = 0;
        for (int i = 0; i < view.getTableModel().getRowCount(); i++) {
            totalCommande += (double) view.getTableModel().getValueAt(i, 4);
        }
        view.setTotal(totalCommande);
    }

    private void viderPanier() {
        view.getTableModel().setRowCount(0);
        view.setTotal(0);
        view.getTxtQuantite().setText("");
    }
}