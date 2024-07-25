import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GestionMagasinDB {
    private Connection connection;
    private HashMap<String, Produit> produits;

    public GestionMagasinDB() throws SQLException {
        this.connection = DatabaseSingleton.getInstance().getConnection();
        this.produits = new HashMap<>();
    }

    // Méthodes pour ajouter, supprimer, modifier, rechercher, lister, etc.
    public void ajouterProduit(Produit produit) throws SQLException {
        String insertSQL = "INSERT INTO produits (id, nom, prix, quantite) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, produit.getId());
            pstmt.setString(2, produit.getNom());
            pstmt.setDouble(3, produit.getPrix());
            pstmt.setInt(4, produit.getQuantite());
            pstmt.executeUpdate();
            produits.put(produit.getId(), produit);
        }
    }

    public void supprimerProduit(String id) throws SQLException {
        String deleteSQL = "DELETE FROM produits WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            produits.remove(id);
        }
    }

    public void modifierProduit(String id, Produit produitModifie) throws SQLException {
        String updateSQL = "UPDATE produits SET nom = ?, prix = ?, quantite = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, produitModifie.getNom());
            pstmt.setDouble(2, produitModifie.getPrix());
            pstmt.setInt(3, produitModifie.getQuantite());
            pstmt.setString(4, id);
            pstmt.executeUpdate();
            produits.put(id, produitModifie);
        }
    }

    public Produit rechercherProduitParNom(String nom) throws SQLException {
        String selectSQL = "SELECT * FROM produits WHERE nom = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, nom);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Produit produit = new Produit(rs.getString("id"), rs.getString("nom"), rs.getDouble("prix"), rs.getInt("quantite"));
                    return produit;
                }
            }
        }
        return null;
    }

    public List<Produit> listerProduitsParLettre(char lettre) throws SQLException {
        String selectSQL = "SELECT * FROM produits WHERE nom LIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, lettre + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                List<Produit> resultats = new ArrayList<>();
                while (rs.next()) {
                    Produit produit = new Produit(rs.getString("id"), rs.getString("nom"), rs.getDouble("prix"), rs.getInt("quantite"));
                    resultats.add(produit);
                }
                return resultats;
            }
        }
    }

    public int nombreDeProduitsEnStock() throws SQLException {
        String selectSQL = "SELECT COUNT(*) FROM produits";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public List<Produit> afficherProduitsParCategorie(Class<?> categorie) throws SQLException {
        String selectSQL = "SELECT * FROM produits WHERE categorie = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, categorie.getSimpleName());
            try (ResultSet rs = pstmt.executeQuery()) {
                List<Produit> resultats = new ArrayList<>();
                while (rs.next()) {
                    Produit produit = new Produit(rs.getString("id"), rs.getString("nom"), rs.getDouble("prix"), rs.getInt("quantite"));
                    resultats.add(produit);
                }
                return resultats;
            }
        }
    }

    public double valeurTotaleDuStock() throws SQLException {
        String selectSQL = "SELECT SUM(prix * quantite) FROM produits";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                return rs.getDouble(1);
            }
        }
    }

    public Produit rechercherProduitParId(String id) throws SQLException {
        String selectSQL = "SELECT * FROM produits WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Produit produit = new Produit(rs.getString("id"), rs.getString("nom"), rs.getDouble("prix"), rs.getInt("quantite"));
                    return produit;
                }
            }
        }
        return null;
    }

    public void sauvegarderProduits() throws SQLException {
        // Pas besoin de sauvegarder les produits car ils sont déjà enregistrés dans la base de données
    }

    public void chargerProduits() throws SQLException {
        String selectSQL = "SELECT * FROM produits";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Produit produit = new Produit(rs.getString("id"), rs.getString("nom"), rs.getDouble("prix"), rs.getInt("quantite"));
                    produits.put(produit.getId(), produit);
                }
            }
        }
    }

    public void fermer() throws SQLException {
        connection.close();
    }
}