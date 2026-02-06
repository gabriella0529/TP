package view;

import model.Produit;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ProduitForm extends JDialog {
    private JTextField txtNom = new JTextField(20);
    private JComboBox<String> comboCategories = new JComboBox<>();
    private JTextField txtPrix = new JTextField(10);
    private JTextField txtStock = new JTextField(10);
    private JTextField txtSeuil = new JTextField(10);
    private JButton btnEnregistrer = new JButton("Enregistrer");
    private JButton btnAnnuler = new JButton("Annuler");
    private boolean isValide = false;
    private Produit produit;

    public ProduitForm(Frame parent, Map<Integer, String> categories, Produit p) {
        super(parent, p == null ? "Ajouter un Produit" : "Modifier le Produit", true);
        this.produit = p;
        setSize(400, 350);
        setLocationRelativeTo(parent);

        // Remplir le combo avec les libellés de catégories
        for (String libelle : categories.values()) {
            comboCategories.addItem(libelle);
        }

        // Pré-remplir si modification
        if (produit != null) {
            txtNom.setText(produit.getNom());
            txtPrix.setText(String.valueOf(produit.getPrix()));
            txtStock.setText(String.valueOf(produit.getStock()));
            txtSeuil.setText(String.valueOf(produit.getSeuilAlerte()));

            // Sélectionner la catégorie correspondante
            String libCat = categories.get(produit.getIdCategorie());
            if (libCat != null)
                comboCategories.setSelectedItem(libCat);
        } else {
            txtSeuil.setText("5"); // Valeur par défaut
        }

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nom du produit :"));
        panel.add(txtNom);
        panel.add(new JLabel("Catégorie :"));
        panel.add(comboCategories);
        panel.add(new JLabel("Prix de vente (FCFA) :"));
        panel.add(txtPrix);
        panel.add(new JLabel("Stock actuel :"));
        panel.add(txtStock);
        panel.add(new JLabel("Seuil d'alerte :"));
        panel.add(txtSeuil);

        panel.add(btnEnregistrer);
        panel.add(btnAnnuler);

        add(panel);

        btnAnnuler.addActionListener(e -> dispose());
        btnEnregistrer.addActionListener(e -> {
            isValide = true;
            dispose();
        });
    }

    // Getters pour récupérer les saisies
    public String getNom() {
        return txtNom.getText().trim();
    }

    public String getCategorieSelectionnee() {
        return (String) comboCategories.getSelectedItem();
    }

    public String getPrix() {
        return txtPrix.getText().trim();
    }

    public String getStock() {
        return txtStock.getText().trim();
    }

    public String getSeuil() {
        return txtSeuil.getText().trim();
    }

    public boolean isConfirmed() {
        return isValide;
    }
}
