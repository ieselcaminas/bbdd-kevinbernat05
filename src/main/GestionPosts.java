import javax.xml.transform.Result;
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

    public static void listAllPosts() throws SQLException {
        Connection con = Main.connection;

        PreparedStatement ps = con.prepareStatement("SELECT p.id, p.texto, p.likes, p.fecha, u.nombre FROM posts as p " +
                "INNER JOIN usuarios as u ON p.id_usuario = u.id");

        ResultSet rs = ps.executeQuery();
        System.out.println("ID \t Usuario \tPost \tLikes \tFecha" );
        System.out.println("---------------------------------");
        while (rs.next()) {
            printPost(rs);
            printComments(rs.getInt(1));
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
    public static void printComments(int idPost) throws SQLException {
        Connection con = Main.connection;

        PreparedStatement st = con.prepareStatement("SELECT c.id, c.texto, c.fecha, u.nombre" +
                " FROM comentarios as c " +
                " INNER JOIN usuarios as u O N c.id_usuario = u.id " +
                " INNER JOIN posts as p ON c.id_post = p.id" +
                " WHERE p.id = ?");

        st.setInt(1, idPost);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            System.out.println("\t\t\t" + rs.getString(2) + " - " +
                    rs.getDate(3 ) + " - " + rs.getString(4));
        }
    }
    public static void printPost(ResultSet rs) throws SQLException {
        System.out.println(rs.getInt(1) + " " +
                rs.getString(2) + " likes:" +
                rs.getInt(3) + " " + rs.getDate(4) +
                " " + rs.getString(5));

    }

}
