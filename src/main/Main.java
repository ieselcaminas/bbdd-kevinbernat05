import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static java.sql.Connection connection;
    static String usuario = "";
    static int id_usuario = -1;
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
        connection = getConnection();
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        while (opcion != -1){
            if (!usuario.isEmpty())
                System.out.print(usuario + " | ");


            System.out.print(" 1 - Usuarios |");
            System.out.print(" 2 - Posts |");
            System.out.print(" 3 - Comentarios |");
            System.out.print(" -1 - Salir ");

            System.out.println();

            opcion = sc.nextInt();
            if (opcion == 1){
                GestionUsuarios.gestionMenu();
            } else if (opcion == 2){
                GestionPosts.gestionMenu();
            }
        }
    }
}