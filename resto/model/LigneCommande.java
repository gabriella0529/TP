package model;

public class LigneCommande {
    private int idLigne;
    private int idCmd;
    private int idProd;
    private int quantite;
    private double prixUnitaire;
    private double montantLigne;

    // Constructeur utilisé lors de la création d'une nouvelle commande
    public LigneCommande(int idProd, int quantite, double prixUnitaire) {
        this.idProd = idProd;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        // Le montant est calculé automatiquement pour garantir la cohérence
        this.montantLigne = quantite * prixUnitaire;
    }

    // Getters
    public int getIdProd() {
        return idProd;
    }

    public int getQuantite() {
        return quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public double getMontantLigne() {
        return montantLigne;
    }

    public int getIdLigne() {
        return idLigne;
    }

    public int getIdCmd() {
        return idCmd;
    }

    // Setters (si besoin de modifier après coup)
    public void setQuantite(int quantite) {
        this.quantite = quantite;
        this.montantLigne = this.quantite * this.prixUnitaire;
    }

    public void setIdLigne(int idLigne) {
        this.idLigne = idLigne;
    }

    public void setIdCmd(int idCmd) {
        this.idCmd = idCmd;
    }
}