package controller;

import model.dao.*;
import model.LigneCommande;
import model.Produit;
import view.CommandeView;
import java.util.ArrayList;
import java.util.List;

public class RestaurationController {
    private ProduitDAO produitDAO = new ProduitDAO();
    private CommandeDAO commandeDAO = new CommandeDAO();
    private CommandeView view = new CommandeView();

    public void creerNouvelleCommande() {
        List<LigneCommande> panier = new ArrayList<>();
        double totalGlobal = 0;

        while (true) {
            int idProd = view.afficherMenuEtSaisirId();
            if (idProd == 0)
                break;

            // Vérifier si le produit existe et récupérer son prix
            Produit p = produitDAO.trouverParId(idProd);
            if (p != null) {
                int qte = view.saisirQuantite();
                if (qte > 0 && qte <= p.getStock()) { // Vérification stock
                    double montant = p.getPrix() * qte;
                    totalGlobal += montant;
                    // On ajoute au panier temporaire
                    panier.add(new LigneCommande(idProd, qte, p.getPrix()));
                    view.afficherMessage("Ajouté : " + p.getNom() + " (Total partiel : " + totalGlobal + "€)");
                } else {
                    view.afficherMessage("Erreur : Stock insuffisant !");
                }
            } else {
                view.afficherMessage("Produit introuvable.");
            }
        }

        if (!panier.isEmpty()) {
            commandeDAO.enregistrerCommande(totalGlobal, panier);
            view.afficherMessage("Commande validée en base de données.");
        }
    }
}