
package visao;

import java.sql.Connection;
import java.sql.SQLException;

import controle.ConexaoBD;

public class TesteConexao {
	public static void main(String[] args) {
		try (Connection conn = ConexaoBD.getConexaoMySQL()) {
			if (conn != null) {
				System.out.println("Conexão bem-sucedida!");
			} else {
				System.out.println("Falha na conexão.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
