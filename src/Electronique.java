public class Electronique extends Produit {
    private String marque;
    private int garantie; // en mois

    public Electronique(String id, String nom, double prix, int quantite, String marque, int garantie) {
        super(id, nom, prix, quantite);
    }

    // Constructeur, getters et setters
}
