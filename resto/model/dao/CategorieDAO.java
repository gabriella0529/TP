package model.dao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CategorieDAO {

    // 1. Ajouter une catégorie (avec vérification d'existence)
    public boolean ajouter(String libelle) throws SQLException {
        String sql = "INSERT INTO Categorie (libelle) VALUES (?)";
        try (Connection conn = Database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, libelle);
            return ps.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            // Gère la règle de gestion : Libellé unique
            throw new SQLException("Cette catégorie existe déjà.");
        }
    }

    // 2. Récupérer toutes les catégories (utile pour le JComboBox du formulaire)
    public Map<Integer, String> obtenirToutes() {
        Map<Integer, String> categories = new HashMap<>();
        String sql = "SELECT * FROM Categorie ORDER BY libelle";

        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                categories.put(rs.getInt("id_cat"), rs.getString("libelle"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // 3. Supprimer une catégorie (Gérer les produits rattachés)
    public void supprimer(int idCat) throws SQLException {
        // Le SQL lèvera une erreur si des produits sont rattachés (Foreign Key)
        String sql = "DELETE FROM Categorie WHERE id_cat = ?";
        try (Connection conn = Database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCat);
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLException("Impossible de supprimer : des produits sont rattachés à cette catégorie.");
        }
    }
}
