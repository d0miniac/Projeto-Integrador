package controle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.ItemVenda;
import modelo.Produto;

public class ItemVendaDAO {

   public List<ItemVenda> buscarItensPorVenda(int idVenda) {
    List<ItemVenda> itens = new ArrayList<>();

    String sql = "SELECT * FROM vw_detalhes_venda WHERE idVenda = ?";

    try (Connection conn = ConexaoBD.getConexaoMySQL();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idVenda);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getLong("idProduto"));
                produto.setPreco(rs.getBigDecimal("precoUnitario"));
                produto.setFoto(rs.getString("imagem"));
                produto.setMarca(rs.getString("marca"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setTamanho(rs.getString("tamanho"));
                produto.setCor(rs.getString("cor"));

                ItemVenda item = new ItemVenda();
                item.setFoto(produto); // produto completo aqui
                item.setQuantidade(rs.getInt("quantidade"));

                itens.add(item);
            }
        }

    } catch (SQLException e) {
        System.err.println("Erro ao buscar itens da venda: " + e.getMessage());
    }

    return itens;
}
}