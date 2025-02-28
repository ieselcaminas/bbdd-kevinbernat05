import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class GestionComentarios {
    public static void gestionComments() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        while (opcion != -1) {
            if (!Main.usuario.isEmpty()) {
                System.out.print(Main.usuario + " | ");
            }
            System.out.print(" 1 - Nuevo comentario | ");
            System.out.print(" 2 - Listar todos los comentarios | ");
            System.out.print("-1 - Salir");

            System.out.println();

            opcion = sc.nextInt();
            if (opcion == 1) {
                newComment();
            } else if (opcion == 2) {
            }
        }
    }
    public static void newComment() throws SQLException {
        String comentario;

        if (Main.id_usuario == -1) {
            System.out.println("Debes logearte antes");
            GestionUsuarios.existeUsuario();
            return;
        }

        Connection con = Main.connection;

        Scanner sc = new Scanner(System.in);

        GestionPosts.listAllPosts();
        System.out.println();
        System.out.print("Introduzca el ID del post a comentar: ");
        int id = sc.nextInt();
        sc.nextLine();


        System.out.println("Introduzca su comentario: ");
        comentario = sc.nextLine();


        java.sql.Date fecha = new java.sql.Date(new Date().getTime());
        PreparedStatement pst = con.prepareStatement("INSERT INTO comentarios (id_post, id_usuario, texto, fecha) VALUES (?, ?, ?, ?)");
        pst.setInt(1, id);
        pst.setInt(2, Main.id_usuario);
        pst.setString(3, comentario);
        pst.setDate(4, fecha);
        pst.executeUpdate();
    }
}