import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class GestionUsuarios {
    public static void gestionMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        String usuario;
        while (opcion != -1){

            System.out.print(" 1 - Login | ");
            System.out.print(" 2 - Nuevo usuario |");
            System.out.print(" 3 - Borrar usuario | ");
            System.out.print(" 4 - Listar usuarios | ");
            System.out.print("-1 - Salir");

            System.out.println();

            opcion = sc.nextInt();
            if (opcion == 1){
                usuario = existeUsuario();

                if (!usuario.isEmpty()){
                    Main.usuario = usuario;
                    System.out.println("Bienvenido " + usuario);
                    break;
                } else {
                    System.out.println("Usuario no encontrado");
                }
            } else if (opcion == 2){
                usuario = insertarUsuario();
                Main.usuario = usuario;
                break;

            } else if (opcion == 3){
                borrarUsuario();

            } else if (opcion == 4){
                consultarUsuarios();
            }
        }
    }
    public static String existeUsuario() throws SQLException {
        java.sql.Connection con = Main.connection;

        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce tu nombre de usuario: ");
        String usuario = sc.nextLine();
        System.out.println("Introduce tu password: ");
        String password = sc.nextLine();

        PreparedStatement st = con.prepareStatement("SELECT * FROM usuarios WHERE nombre = ? AND password = ?");
        st.setString(1, usuario);
        st.setString(2, password);
        ResultSet rs = st.executeQuery();
        if (rs.next()){
            Main.id_usuario = rs.getInt(1);
            return rs.getString(2);
        }
        return "";
    }
    public static String insertarUsuario() throws SQLException {
        java.sql.Connection con = Main.connection;
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce tu nombre de usuario: ");
        String usuario = sc.nextLine();

        System.out.println("Introduce tus apellidos: ");
        String apellidos = sc.nextLine();

        System.out.println("Introduce tu password: ");
        int password = sc.nextInt();

        PreparedStatement st = con.prepareStatement("INSERT INTO usuarios(nombre, apellidos, password) VALUES(?,?,?)");

        st.setString(1, usuario);
        st.setString(2, apellidos);
        st.setInt(3, password);
        st.executeUpdate();

        return usuario;
    }
    public static void borrarUsuario() throws SQLException {
        java.sql.Connection con = Main.connection;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce tu nombre de usuario: ");
        String usuario = sc.nextLine();
        System.out.println("Introduce tu password: ");
        int password = sc.nextInt();

        PreparedStatement st = con.prepareStatement("DELETE FROM usuarios WHERE nombre = ? AND password = ?");
        st.setString(1, usuario);
        st.setInt(2, password);
        st.executeUpdate();

        System.out.println("Usuario borrado con éxito.");

        Main.usuario = "";
    }

    public static void consultarUsuarios() throws SQLException{
        java.sql.Connection con = Main.connection;
        Statement st = null;
        ResultSet rs = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM usuarios");
            System.out.println("ID \tNombre \tApellido");
            System.out.println("--------------------");
            while (rs.next()){
                System.out.print(rs.getString(1) + "\t");
                System.out.print(rs.getString(2) + "\t");
                System.out.println(rs.getString(3) + "\t");

            }
        } catch (SQLException e) {
            System.out.println("Se ha producido un error al leer los datos." + e.getMessage());
        }
    }
}