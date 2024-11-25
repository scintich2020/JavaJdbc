import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Delete {



    public static void main(String[] args) {

        try {
            String strClassName = "com.mysql.jdbc.Driver";
            String dbName= "Acces";
            String login= "root";
            String motdepasse= "";
            String strUrl = "jdbc:mysql://localhost:3306/" +  dbName;

            Class.forName(strClassName);
            Connection conn = DriverManager.getConnection(strUrl, login, motdepasse);
            Statement stAddUser = conn.createStatement();
            stAddUser.executeUpdate("DELETE  from Acces where id = 5");

            conn.close();
        }
        catch(ClassNotFoundException e) {
            System.err.println("Driver non chargé !");  e.printStackTrace();
        } catch(SQLException e) {
            System.err.println("Autre erreur !");  e.printStackTrace();
        }



    }
}
