import java.sql.*;

public class DAOAcces {

    private Connection connection;
    private Statement statement;

    public DAOAcces(String jdbcURL, String username, String password){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver chargé avec succès.");

            this.connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connexion établie avec succès.");

        } catch (ClassNotFoundException e) {
            System.err.println("Erreur de chargement du driver : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }

    public void closeConnection(){
        try{
            if (connection != null){
                connection.close();
            }
            System.out.println("connexion fermée.");
        }catch (SQLException e){
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }

    public void SelectElements(String requeteSQL){
        try{
            this.statement = this.connection.prepareStatement(requeteSQL);
            ResultSet resultSet = this.statement.executeQuery(requeteSQL);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()){
                for (int i = 1; i <= columnCount; i++){
                    System.out.print(metaData.getColumnName(i) + ": " + resultSet.getString(i) + "\t");
                }
                System.out.println();
            }
        }catch (SQLException e){
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
        }finally {
            try {
                if (this.statement != null) this.statement.close();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture du PreparedStatement : " + e.getMessage());
            }
        }
    }

    public void InsertElement(String tableName, String[] colonnes, Object[] valeurs)throws SQLException{
        if (colonnes.length != valeurs.length) {
            throw new IllegalArgumentException("Le nombre de colonnes doit correspondre au nombre de valeurs.");
        }

        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
        for (int i = 0; i < colonnes.length; i++) {
            sql.append(colonnes[i]);
            if (i < colonnes.length - 1) sql.append(", ");
            sql.append(") VALUES (");

            for (int e = 0; e < valeurs.length; e++) {
                sql.append("?");
                if (i < valeurs.length - 1) sql.append(", ");
            }
            sql.append(")");

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
                for (int l = 0; l < valeurs.length; l++) {
                    preparedStatement.setObject(i + 1, valeurs[i]);
                }
                int rowsInserted = preparedStatement.executeUpdate();
                System.out.println(rowsInserted + " ligne(s) insérée(s) dans la table " + tableName);
            }
        }
    }
}
