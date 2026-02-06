package controller;

import model.dao.CategorieDAO;
import view.CategoriePanel;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.Map;

public class CategorieController {
    private CategoriePanel view;
    private CategorieDAO dao;

    public CategorieController(CategoriePanel view) {
        this.view = view;
        this.dao = new CategorieDAO();

        chargerCategories();
        initListeners();
    }

    private void chargerCategories() {
        view.viderTable();
        Map<Integer, String> categories = dao.obtenirToutes();
        for (Map.Entry<Integer, String> entry : categories.entrySet()) {
            view.ajouterLigne(new Object[] { entry.getKey(), entry.getValue() });
        }
    }

    private void initListeners() {
        view.getBtnAjouter().addActionListener(e -> {
            String libelle = view.getLibelle();
            if (libelle.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Le libellé est obligatoire.");
                return;
            }
            try {
                if (dao.ajouter(libelle)) {
                    JOptionPane.showMessageDialog(view, "Catégorie ajoutée !");
                    view.setLibelle("");
                    chargerCategories();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getBtnSupprimer().addActionListener(e -> {
            int id = view.getSelectedId();
            if (id == -1) {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner une catégorie.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(view, "Supprimer cette catégorie ?", "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    dao.supprimer(id);
                    JOptionPane.showMessageDialog(view, "Catégorie supprimée !");
                    chargerCategories();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
