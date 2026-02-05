package model;

public class Produit {
    private int id;
    private String nom;
    private double prix;
    private int stock;
    private int idCategorie;

    // Constructeur complet
    public Produit(int id, String nom, double prix, int stock, int idCategorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.stock = stock;
        this.idCategorie = idCategorie;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public int getStock() {
        return stock;
    }
}