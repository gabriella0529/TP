package model.dao;

import java.sql.*;
import java.util.List;
import model.LigneCommande;

public class CommandeDAO {
    public void enregistrerCommande(double total, List<LigneCommande> lignes) throws SQLException {
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

                // 2. Insérer les lignes de commande et mettre à jour les stocks
                String sqlLigne = "INSERT INTO LigneCommande (id_cmd, id_prod, quantite, prix_unitaire, montant_ligne) VALUES (?, ?, ?, ?, ?)";
                String sqlStock = "UPDATE Produit SET stock_actuel = stock_actuel - ? WHERE id_prod = ?";
                String sqlMvt = "INSERT INTO MouvementStock (type_mvt, id_prod, quantite, motif) VALUES ('SORTIE', ?, ?, ?)";

                PreparedStatement psLigne = conn.prepareStatement(sqlLigne);
                PreparedStatement psStock = conn.prepareStatement(sqlStock);
                PreparedStatement psMvt = conn.prepareStatement(sqlMvt);

                for (LigneCommande ligne : lignes) {
                    // Ligne de commande
                    psLigne.setInt(1, idCmd);
                    psLigne.setInt(2, ligne.getIdProd());
                    psLigne.setInt(3, ligne.getQuantite());
                    psLigne.setDouble(4, ligne.getPrixUnitaire());
                    psLigne.setDouble(5, ligne.getMontantLigne());
                    psLigne.addBatch();

                    // Mise à jour stock
                    psStock.setInt(1, ligne.getQuantite());
                    psStock.setInt(2, ligne.getIdProd());
                    psStock.addBatch();

                    // Mouvement stock
                    psMvt.setInt(1, ligne.getIdProd());
                    psMvt.setInt(2, ligne.getQuantite());
                    psMvt.setString(3, "COMMANDE #" + idCmd);
                    psMvt.addBatch();
                }
                psLigne.executeBatch();
                psStock.executeBatch();
                psMvt.executeBatch();
            }

            conn.commit(); // Valider tout d'un coup
        } catch (SQLException e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            if (conn != null)
                conn.setAutoCommit(true);
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