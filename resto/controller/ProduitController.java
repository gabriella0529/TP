package controller;

import model.dao.ProduitDAO;
import model.Produit;
import view.ProduitPanel;
import javax.swing.JOptionPane;

import java.sql.SQLException;
import java.util.List;

public class ProduitController {
    private ProduitPanel view;
    private ProduitDAO dao;

    public ProduitController(ProduitPanel view, ProduitDAO dao) {
        this.view = view;
        this.dao = dao;
        this.chargerProduits();

        this.view.getBtnAjouter().addActionListener(e -> ouvrirFormulaireAjout());
        this.view.getBtnSupprimer().addActionListener(e -> supprimerProduitSelectionne());
    }

    public void chargerProduits() {
        view.viderTableau();
        List<Produit> produits = dao.listerTous();
        for (Produit p : produits) {
            view.ajouterLigne(new Object[] {
                    p.getId(), p.getNom(), p.getIdCategorie(), p.getPrix(), p.getStock()
            });
        }
    }

    private void ouvrirFormulaireAjout() {
        // Ici, on pourrait ouvrir une JDialog pour saisir les infos
        // Exemple de validation selon les règles de gestion
        try {
            String nom = JOptionPane.showInputDialog("Nom du produit :");
            if (nom == null || nom.trim().isEmpty())
                throw new Exception("Nom requis.");

            double prix = Double.parseDouble(JOptionPane.showInputDialog("Prix :"));
            if (prix <= 0)
                throw new Exception("Le prix doit être > 0.");

            // Si OK, appel au DAO
            // dao.ajouter(...)
            chargerProduits(); // Rafraîchir le tableau
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage(), "Validation", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerProduitSelectionne() {
        // 1. Récupérer la ligne sélectionnée dans le tableau
        int fila = view.getTableProduits().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un produit dans le tableau.");
            return;
        }

        // Récupérer l'ID (colonne 0) et le nom (colonne 1)
        int idProd = (int) view.getTableProduits().getValueAt(fila, 0);
        String nom = (String) view.getTableProduits().getValueAt(fila, 1);

        // 2. Demander confirmation
        int confirm = JOptionPane.showConfirmDialog(view,
                "Êtes-vous sûr de vouloir supprimer le produit : " + nom + " ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // 3. Appel au DAO
                dao.supprimer(idProd);

                // 4. Rafraîchir l'affichage
                chargerProduits();
                JOptionPane.showMessageDialog(view, "Produit supprimé avec succès.");

            } catch (SQLException ex) {
                // Gestion de l'exception avec message compréhensible
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Erreur de dépendance", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}