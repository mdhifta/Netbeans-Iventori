package Database;
    
    /*Import Driver Connection Database*/
    import java.sql.DriverManager;
    import java.sql.Connection;
    import java.sql.SQLException;

/**
 *
 * @author Bubui
 */
public class Koneksi {
    public Connection connect;
    
    public Connection getKoneksi(){
    
        try{
            /*Setting Host, User & Password Database*/
            String hostname = "jdbc:mysql://localhost:3306/tugas_besar";
            String username = "root";
            String password = "";
            
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connect = DriverManager.getConnection(hostname, username, password);
            System.out.println("Koneksi Database Berhasil");
            
        } catch (SQLException error){
            System.out.println("Koneksi Database Gagal");
        }
        
        return connect;
    }
}
