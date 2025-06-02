package controle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    public static Connection getConexaoMySQL() {
        Connection connection = null;
        String driverName = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Driver não encontrado");
        }
        String serverName = "localhost:3306";
        String bancoDados = "armariodigital";
        String url = "jdbc:mysql://" + serverName + "/" + bancoDados;
        String username = "root";
        String password = "admin";
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("BD não conectado");
        }
        return connection;
    }
}
