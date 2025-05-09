package controle;

import modelo.Clientes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private final String url = "jdbc:mysql://localhost:3306/seubanco";
    private final String user = "usuario";
    private final String pass = "senha";

    public ClienteDAO()  {
        
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    public void inserir(Clientes c) throws SQLException {
        String sql = "INSERT INTO clientes (nome, email, telefone) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefone());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
        }
    }

    public List<Clientes> listarTodos() throws SQLException {
        List<Clientes> lista = new ArrayList<>();
        String sql = "SELECT id, nome, email, telefone FROM clientes";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Clientes c = new Clientes(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("telefone")
                );
                lista.add(c);
            }
        }
        return lista;
    }

    public void atualizar(Clientes c) throws SQLException {
        String sql = "UPDATE clientes SET nome = ?, email = ?, telefone = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefone());
            ps.setInt(4, c.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}