package controle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.ItemVenda;
import modelo.Produto;

public class ItemVendaDAO {

    public List<ItemVenda> buscarItensPorVenda(int idVenda) {
        List<ItemVenda> itens = new ArrayList<>();

        String sql = "SELECT c.idProduto, c.quantidade, p.preco, p.Marca_idMarca, p.Categoria_idCategoria, p.nomeArquivoFoto " +
                     "FROM carrinho c " +
                     "JOIN produtos p ON c.idProduto = p.idProduto " +
                     "WHERE c.idVenda = ?";

        try (Connection conn = ConexaoBD.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVenda);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Produto produto = new Produto();
                    produto.setId(rs.getLong("idProduto"));
                    produto.setPreco(rs.getBigDecimal("preco"));
                    produto.setFoto(rs.getString("nomeArquivoFoto"));
                    // ⚠️ Aqui você pode carregar marca, categoria, etc. se quiser

                    ItemVenda item = new ItemVenda();
                    item.setIdProduto(rs.getInt("dProduto"));
                    item.setQuantidade(rs.getInt("quantidade"));
                    item.setFoto(produto); // se no seu modelo o "produto" é referenciado via getFoto()

                    itens.add(item);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar itens da venda: " + e.getMessage());
        }

        return itens;
    }
}