package model.dao;

import java.sql.*;
import java.util.List;
import model.LigneCommande;

public class CommandeDAO {
    public void enregistrerCommande(double total, List<LigneCommande> lignes) {
        Connection conn = null;
        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false); // Début de la transaction

            // 1. Insérer la commande
            String sqlCmd = "INSERT INTO Commande (total, etat) VALUES (?, 'VALIDÉE')";
            PreparedStatement psCmd = conn.prepareStatement(sqlCmd, Statement.RETURN_GENERATED_KEYS);
            psCmd.setDouble(1, total);
            psCmd.executeUpdate();

            // Récupérer l'ID de la commande générée
            ResultSet rs = psCmd.getGeneratedKeys();
            if (rs.next()) {
                int idCmd = rs.getInt(1);

                // 2. Insérer les lignes de commande
                String sqlLigne = "INSERT INTO LigneCommande (id_cmd, id_prod, quantite, prix_unitaire) VALUES (?, ?, ?, ?)";
                PreparedStatement psLigne = conn.prepareStatement(sqlLigne);

                for (LigneCommande ligne : lignes) {
                    psLigne.setInt(1, idCmd);
                    psLigne.setInt(2, ligne.getIdProd());
                    psLigne.setInt(3, ligne.getQuantite());
                    psLigne.setDouble(4, ligne.getPrixUnitaire());
                    psLigne.addBatch(); // Optimisation
                }
                psLigne.executeBatch();
            }

            conn.commit(); // Valider tout d'un coup
            System.out.println("Commande enregistrée avec succès !");
        } catch (SQLException e) {
            if (conn != null)
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            e.printStackTrace();
        }
    }

    public void annulerCommande(int idCmd) throws SQLException {
        Connection conn = null;
        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false); // Début de la transaction

            // 1. Passer l'état à 'ANNULÉE'
            String sqlUpdateCmd = "UPDATE Commande SET etat = 'ANNULÉE' WHERE id_cmd = ?";
            PreparedStatement psCmd = conn.prepareStatement(sqlUpdateCmd);
            psCmd.setInt(1, idCmd);
            psCmd.executeUpdate();

            // 2. Récupérer les lignes pour remettre en stock
            String sqlLignes = "SELECT id_prod, quantite FROM LigneCommande WHERE id_cmd = ?";
            PreparedStatement psLignes = conn.prepareStatement(sqlLignes);
            psLignes.setInt(1, idCmd);
            ResultSet rs = psLignes.executeQuery();

            while (rs.next()) {
                int idProd = rs.getInt("id_prod");
                int qte = rs.getInt("quantite");

                // 3. Remettre en stock dans la table Produit
                String sqlRestock = "UPDATE Produit SET stock_actuel = stock_actuel + ? WHERE id_prod = ?";
                PreparedStatement psRestock = conn.prepareStatement(sqlRestock);
                psRestock.setInt(1, qte);
                psRestock.setInt(2, idProd);
                psRestock.executeUpdate();

                // 4. Enregistrer le mouvement de stock inverse (ENTRÉE)
                String sqlMvt = "INSERT INTO MouvementStock (type_mvt, id_prod, quantite, motif) VALUES ('ENTRÉE', ?, ?, ?)";
                PreparedStatement psMvt = conn.prepareStatement(sqlMvt);
                psMvt.setInt(1, idProd);
                psMvt.setInt(2, qte);
                psMvt.setString(3, "ANNULATION Commande #" + idCmd);
                psMvt.executeUpdate();
            }

            conn.commit(); // Valider toutes les opérations
        } catch (SQLException e) {
            if (conn != null)
                conn.rollback();
            throw e;
        }
    }
}