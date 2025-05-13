package controle;

import modelo.Clientes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public ClienteDAO()  {
    }

    private Connection getConnection() throws SQLException {
        return ConexaoBD.getConexaoMySQL();
    }

    public void inserir(Clientes c) throws SQLException {
        // Removido o campo idClientes do INSERT
        String sql = "INSERT INTO clientes (Nome_Clientes, email, telefone) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getNome_Clientes());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefone());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setidCliente(rs.getInt(1)); 
                }
            }
        }
    }

    public List<Clientes> listarTodos() throws SQLException {
        List<Clientes> lista = new ArrayList<>();
        String sql = "SELECT idCliente, Nome_Clientes, email, telefone FROM clientes";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Clientes c = new Clientes(
                    rs.getInt("idCliente"),
                    rs.getString("Nome_Clientes"),
                    rs.getString("email"),
                    rs.getString("telefone")
                );
                lista.add(c);
            }
        }
        return lista;
    }

    public void atualizar(Clientes c) throws SQLException {
        String sql = "UPDATE clientes SET Nome_Clientes = ?, email = ?, telefone = ? WHERE idCliente = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNome_Clientes());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefone());
            ps.setInt(4, c.getidCliente());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE idCliente = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}