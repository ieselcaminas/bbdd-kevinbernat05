import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class GestionPosts {
    public static void gestionMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        while (opcion != -1) {
            if (!Main.usuario.isEmpty()) {
                System.out.print(Main.usuario + " | ");
            }
            System.out.print(" 1 - Nuevo post | ");
            System.out.print(" 2 - Listar todos los posts | ");
            System.out.print("-1 - Salir");

            System.out.println();

            opcion = sc.nextInt();
            if (opcion == 1) {
                newPost();
            } else if (opcion == 2) {
                listAllPosts();
            }
        }
    }

    private static void listAllPosts() throws SQLException {
        Connection con = Main.connection;

        PreparedStatement ps = con.prepareStatement("Select *, usuarios.nombre FROM posts INNER JOIN usuarios ON posts.id_usuario = usuarios.id");
        ResultSet rs = ps.executeQuery();
        System.out.println("Usuario \tPost \tLikes \tFecha" );
        System.out.println("---------------------------------");
        while (rs.next()) {
            System.out.print(rs.getString("nombre") + "\t");
            System.out.print(rs.getString(2) + "\t");
            System.out.print(rs.getInt(3) + "\t");
            System.out.println(rs.getDate(4));

        }
        System.out.println("---------------------------------");
    }

    public static void newPost() throws SQLException {
        if (Main.id_usuario == -1) {
            System.out.println("Debes logearte antes");
            GestionUsuarios.existeUsuario();
            return;
        }

        Connection con = Main.connection;

        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca el texto de su post: ");
        String texto = sc.nextLine();
        java.sql.Date fecha = new java.sql.Date(new Date().getTime());

        PreparedStatement pst = con.prepareStatement("INSERT INTO posts (texto,likes,fecha,id_usuario) VALUES (?,?,?,?)");
        pst.setString(1, texto);
        pst.setInt(2, 0);
        pst.setDate(3, fecha);
        pst.setInt(4, Main.id_usuario);
        pst.executeUpdate();
    }

}
