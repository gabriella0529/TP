package controller;

import model.dao.ProduitDAO;
import model.Produit;
import view.ProduitForm;
import view.ProduitPanel;
import javax.swing.JOptionPane;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ProduitController {
    private ProduitPanel view;
    private ProduitDAO dao;
    private model.dao.CategorieDAO catDao;

    public ProduitController(ProduitPanel view, ProduitDAO dao) {
        this.view = view;
        this.dao = dao;
        this.catDao = new model.dao.CategorieDAO();
        this.chargerProduits();

        this.view.getBtnAjouter().addActionListener(e -> ouvrirFormulaire(null));
        this.view.getBtnModifier().addActionListener(e -> {
            int row = view.getTableProduits().getSelectedRow();
            if (row != -1) {
                int id = (int) view.getTableProduits().getValueAt(row, 0);
                Produit p = dao.trouverParId(id);
                ouvrirFormulaire(p);
            } else {
                JOptionPane.showMessageDialog(view, "Sélectionnez un produit à modifier.");
            }
        });
        this.view.getBtnSupprimer().addActionListener(e -> supprimerProduitSelectionne());
    }

    public void chargerProduits() {
        view.viderTableau();
        List<Produit> produits = dao.listerTous();
        Map<Integer, String> categories = catDao.obtenirToutes();
        for (Produit p : produits) {
            String libelleCat = categories.getOrDefault(p.getIdCategorie(), "Inconnue");
            view.ajouterLigne(new Object[] {
                    p.getId(), p.getNom(), libelleCat, p.getPrix(), p.getStock(), p.getSeuilAlerte()
            });
        }
    }

    private void ouvrirFormulaire(Produit p) {
        Map<Integer, String> categories = catDao.obtenirToutes();
        ProduitForm form = new ProduitForm(null, categories, p);
        form.setVisible(true);

        if (form.isConfirmed()) {
            try {
                String nom = form.getNom();
                if (nom.isEmpty())
                    throw new Exception("Le nom est requis.");

                double prix = Double.parseDouble(form.getPrix());
                if (prix <= 0)
                    throw new Exception("Le prix doit être positif.");

                int stock = Integer.parseInt(form.getStock());
                if (stock < 0)
                    throw new Exception("Le stock ne peut pas être négatif.");

                int seuil = Integer.parseInt(form.getSeuil());

                // Retrouver l'ID de la catégorie
                String libCat = form.getCategorieSelectionnee();
                int idCat = categories.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(libCat))
                        .map(Map.Entry::getKey).findFirst().orElse(-1);

                if (p == null) {
                    dao.ajouter(new Produit(0, nom, prix, stock, idCat, seuil));
                    JOptionPane.showMessageDialog(view, "Produit ajouté !");
                } else {
                    dao.modifier(new Produit(p.getId(), nom, prix, stock, idCat, seuil));
                    JOptionPane.showMessageDialog(view, "Produit modifié !");
                }
                chargerProduits();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Format numérique invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerProduitSelectionne() {
        int fila = view.getTableProduits().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(view, "Sélectionnez un produit.");
            return;
        }

        int idProd = (int) view.getTableProduits().getValueAt(fila, 0);
        String nom = (String) view.getTableProduits().getValueAt(fila, 1);

        int confirm = JOptionPane.showConfirmDialog(view, "Supprimer " + nom + " ?", "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                dao.supprimer(idProd);
                chargerProduits();
                JOptionPane.showMessageDialog(view, "Produit supprimé.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
