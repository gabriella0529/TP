import model.dao.Database;
import java.sql.*;

public class DatabaseTest {
    public static void main(String[] args) {
        System.out.println("Tentative de connexion à la base de données...");
        try (Connection conn = Database.getConnection()) {
            if (conn != null) {
                System.out.println("Succès ! Connexion établie.");

                String sql = "SELECT * FROM Utilisateur";
                try (Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql)) {
                    System.out.println("Liste des utilisateurs :");
                    boolean hasUsers = false;
                    while (rs.next()) {
                        hasUsers = true;
                        System.out.println(
                                "- Login: " + rs.getString("login") + ", Password: " + rs.getString("password"));
                    }
                    if (!hasUsers) {
                        System.out.println("Aucun utilisateur trouvé dans la table 'Utilisateur'.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Une erreur SQL est survenue :");
            e.printStackTrace();
        }
    }
}
