import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestionDB {

    private Connection connection;

    public GestionDB() throws SQLException {
        this.connection = DatabaseSingleton.getInstance().getConnection();
    }

    public void createTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS personne ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "nom VARCHAR(50), "
                + "genre VARCHAR(10))";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Table 'personne' created.");
        }
    }
    public void insertPerson(String nom, String genre) throws SQLException {
        String insertSQL = "INSERT INTO personne (nom, genre) VALUES (?, ?)";
        try (PreparedStatement pstmt =
                     connection.prepareStatement(insertSQL)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, genre);
            pstmt.executeUpdate();
            System.out.println("Person inserted.");
        }
    }
    public void selectPersons() throws SQLException {
        String selectSQL = "SELECT * FROM personne";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String genre = rs.getString("genre");
                System.out.println("ID: " + id + ", Nom: " + nom + ", Genre: " + genre);
            }
        }
    }
    public void updatePerson(int id, String nom, String genre)
            throws SQLException {
        String updateSQL = "UPDATE personne SET nom = ?, genre = ? WHERE id = ?" ;

        try (PreparedStatement pstmt =
                     connection.prepareStatement(updateSQL)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, genre);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            System.out.println("Person updated.");
        }
    }
    public void deletePerson(int id) throws
            SQLException {

        String deleteSQL = "DELETE FROM personne WHERE id = ?" ;

        try (PreparedStatement pstmt =

                     connection.prepareStatement(deleteSQL)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Person deleted.");
        }
    }
}