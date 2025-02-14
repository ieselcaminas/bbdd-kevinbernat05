import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConectarSQL {

    static java.sql.Connection connection;

    public static java.sql.Connection getConnection(){
        String host = "jdbc:sqlite:src/main/resources/network.sqlite";
        if (connection == null) {
            try {
                connection = java.sql.DriverManager.getConnection(host);
            }catch (SQLException sql){
                System.out.println(sql.getMessage());
                System.exit(0);
            }
        }
        return connection;
    }

    public static void main(String[] args) throws SQLException {
        Statement stmt = connection.createStatement();
        /*stmt.executeUpdate("CREATE TABLE T1 (c1 VARCHAR(20))");*/
        ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");

        while (rs.next()) {
            System.out.println(rs.getString(1));
            System.out.println("\t" + rs.getString(2));
            System.out.println("\t" + rs.getString(3));
        }
        stmt.close();
    }
    public static void insertUser(){
        Statement stmt = null;
        String sql = "INSERT INTO usuarios VALUES(nombre,apellidos) VALUES ('Noelia', 'Alvarez')";

        try {
            stmt = connection.createStatement();
            st

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}