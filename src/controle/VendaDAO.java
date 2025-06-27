package controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import modelo.Venda;

public class VendaDAO {

    // ✅ Inserir venda e retornar o idVenda gerado
    public int inserirVenda(LocalDate data, java.math.BigDecimal total, long idFuncionario) throws SQLException {
        int idVendaGerada = -1;
        Connection conn = ConexaoBD.getConexaoMySQL();

        String sql = "INSERT INTO armariodigital.vendas (Data_venda, Total, Funcionario_idFuncionario, Hora_Venda) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, java.sql.Date.valueOf(data));
            stmt.setBigDecimal(2, total);
            stmt.setLong(3, idFuncionario);
            stmt.setTime(4, java.sql.Time.valueOf(LocalTime.now()));

            int res = stmt.executeUpdate();

            if (res > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idVendaGerada = rs.getInt(1);
                    }
                }
            }
        }

        conn.close();
        return idVendaGerada;
    }

    // ✅ Inserir item do carrinho vinculado a uma venda
    public void inserirItemCarrinho(int idVenda, Long idProduto, int quantidade) throws SQLException {
        String sql = "INSERT INTO armariodigital.carrinho (Venda_idVenda, Produto_idProduto, Quantidade) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBD.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVenda);
            stmt.setLong(2, idProduto);
            stmt.setInt(3, quantidade);
            stmt.executeUpdate();
        }
    }

    // ✅ Selecionar vendas para exibir no histórico
    public ArrayList<Venda> selecionarVendas() {
        ArrayList<Venda> listaVendas = new ArrayList<>();
        String sql = "SELECT * FROM armariodigital.vendas";

        try (Connection conn = ConexaoBD.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Venda v = new Venda();
                v.setId(rs.getLong("idVenda"));
                v.setData(rs.getString("Data_venda"));
                v.setHorario(rs.getString("Hora_Venda"));
                v.setIdFuncionario(rs.getLong("Funcionario_idFuncionario"));
                v.setTotal(rs.getFloat("Total"));
                listaVendas.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaVendas;
    }
}