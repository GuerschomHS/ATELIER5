import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            GestionMagasinDB gestionMagasinDB = new GestionMagasinDB();

            // Ajouter des produits
            Produit produit1 = new Produit("id1", "Produit 1", 10.0, 5);
            Produit produit2 = new Produit("id2", "Produit 2", 20.0, 10);
            gestionMagasinDB.ajouterProduit(produit1);
            gestionMagasinDB.ajouterProduit(produit2);

            // Rechercher un produit par nom
            Produit produitTrouve = gestionMagasinDB.rechercherProduitParNom("Produit 1");
            System.out.println("Produit trouvé : " + produitTrouve);

            // Lister les produits par lettre
            List<Produit> produitsParLettre = gestionMagasinDB.listerProduitsParLettre('P');
            System.out.println("Produits par lettre P : ");
            for (Produit produit : produitsParLettre) {
                System.out.println(produit);
            }

            // Modifier un produit
            produit1.setPrix(15.0);
            gestionMagasinDB.modifierProduit(produit1.getId(), produit1);

            // Supprimer un produit
            gestionMagasinDB.supprimerProduit(produit2.getId());

            // Afficher le nombre de produits en stock
            int nombreDeProduits = gestionMagasinDB.nombreDeProduitsEnStock();
            System.out.println("Nombre de produits en stock : " + nombreDeProduits);

            // Fermer la connexion
            gestionMagasinDB.fermer();
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }
}