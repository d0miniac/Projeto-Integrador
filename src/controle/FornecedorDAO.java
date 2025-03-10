package controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.Fornecedor;

public class FornecedorDAO {

	public int cadastrarFornecedor(Fornecedor fornecedor) {
	    PreparedStatement stmt = null;
	    int resultado = 0;
	    Connection conn = ConexaoBD.getConexaoMySQL();
	    
	    try {
	        stmt = conn.prepareStatement(
	            "INSERT INTO fornecedores (Email, Nome_Fornecedor, Nome_Ctt, Telefone) VALUES (?, ?, ?, ?)",
	            PreparedStatement.RETURN_GENERATED_KEYS
	        );
	        
	        stmt.setString(1, fornecedor.getEmail());
	        stmt.setString(2, fornecedor.getNomeFornecedor());
	        stmt.setString(3, fornecedor.getNomeCtt());
	        stmt.setString(4, fornecedor.getTelefone());
	        
	        resultado = stmt.executeUpdate();
	        
	        if (resultado > 0) {
	            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    fornecedor.setIdFornecedor(generatedKeys.getLong(1));
	                }
	            }
	        }
	        
	        stmt.close();
	        conn.close();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return resultado;
	}

	public void excluirFornecedor(Long idFornecedor) throws SQLException {
	    ProdutoDAO produtoDAO = new ProdutoDAO();
	    produtoDAO.excluirProdutosPorFornecedor(idFornecedor);  

	    String sql = "DELETE FROM armariodigital.fornecedores WHERE idFornecedor = ?";
	    try (Connection conn = ConexaoBD.getConexaoMySQL(); PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setLong(1, idFornecedor);
	        stmt.executeUpdate();
	    }
	}

	public void alterarFornecedor(Fornecedor fornecedor) throws SQLException {
		String sql = "UPDATE armariodigital.fornecedores SET Email = ?, Nome_Fornecedor = ?, Nome_Ctt = ?, Telefone = ? WHERE idFornecedor = ?";
		try (Connection conn = ConexaoBD.getConexaoMySQL(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, fornecedor.getEmail());
			stmt.setString(2, fornecedor.getNomeFornecedor());
			stmt.setString(3, fornecedor.getNomeCtt());
			stmt.setString(4, fornecedor.getTelefone());
			stmt.setLong(5, fornecedor.getIdFornecedor());

			System.out.println("Alterando fornecedor com ID: " + fornecedor.getIdFornecedor());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Fornecedor> selecionarFornecedores() {
		ArrayList<Fornecedor> fornecedores = new ArrayList<>();
		String sql = "SELECT * FROM armariodigital.fornecedores";
		try (Connection conn = ConexaoBD.getConexaoMySQL();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setIdFornecedor(rs.getLong("idFornecedor"));
				fornecedor.setNomeCtt(rs.getString("Nome_Ctt"));
				fornecedor.setNomeFornecedor(rs.getString("Nome_Fornecedor"));
				fornecedor.setEmail(rs.getString("Email"));
				fornecedor.setTelefone(rs.getString("Telefone"));
				fornecedores.add(fornecedor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fornecedores;
	}

	public ArrayList<Fornecedor> pesquisarFornecedores(String filtro) {
		ArrayList<Fornecedor> listaFornecedores = new ArrayList<>();
		String sql = "SELECT * FROM armariodigital.fornecedores WHERE 1=1";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = ConexaoBD.getConexaoMySQL();

		try {

			if (filtro != null && !filtro.isEmpty()) {
				sql += " AND (Nome_Fornecedor LIKE ? OR Nome_Ctt LIKE ? OR Email LIKE ? OR Telefone LIKE ?OR idFornecedor LIKE ?)";
			}

			stmt = conn.prepareStatement(sql);

			if (filtro != null && !filtro.isEmpty()) {
				stmt.setString(1, "%" + filtro + "%");
				stmt.setString(2, "%" + filtro + "%");
				stmt.setString(3, "%" + filtro + "%");
				stmt.setString(4, "%" + filtro + "%");
				stmt.setString(5, "%" + filtro + "%");
			}

			rs = stmt.executeQuery();

			while (rs.next()) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setIdFornecedor(rs.getLong("idFornecedor"));
				fornecedor.setNomeFornecedor(rs.getString("Nome_Fornecedor"));
				fornecedor.setNomeCtt(rs.getString("Nome_Ctt"));
				fornecedor.setEmail(rs.getString("Email"));
				fornecedor.setTelefone(rs.getString("Telefone"));
				listaFornecedores.add(fornecedor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaFornecedores;
	}
}
