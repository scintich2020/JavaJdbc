import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try{
            String jdbcUrl = "jdbc:mysql://localhost:3306/Acces";
            String username = "root";
            String password = "";
            DAOAcces daoAcces = new DAOAcces(jdbcUrl, username, password);

            daoAcces.SelectElements("SELECT * FROM Acces");


            String tableName = "Acces";
            String[] colonnes = {"id", "prenom", "login", "password", "statut", "age"};
            Object[] valeurs = {1, "Eunice", "lamere", "dusoleil", "Etudiante", 22};
            daoAcces.InsertElement(tableName, colonnes, valeurs);

            daoAcces.closeConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}