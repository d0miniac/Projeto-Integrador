package controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Categoria;
import modelo.Cor;
import modelo.Marca;
import modelo.Produto;
import modelo.Tamanho;
import modelo.Venda;

public class VendaDAO {
	public int cadastrarVenda(Venda v) {
		PreparedStatement stmt1 = null;
		int res1 = 0;
		Connection conn = ConexaoBD.getConexaoMySQL();
		try {
			stmt1 = conn.prepareStatement("insert into armariodigital.vendas(Data_Venda,Total,Funcionario_idFuncionario,Hora_Venda) values (?,?,?,?);");
			stmt1.setString(1,v.getData());
			stmt1.setFloat(2,v.getTotal());
			stmt1.setLong(3,v.getIdFuncionario());
			stmt1.setString(4, v.getHorario());
			res1 = stmt1.executeUpdate();
			stmt1.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res1;
	}
	public ArrayList<Venda> selecionarVendas() {
		ArrayList<Venda> listaVendas = new ArrayList<>();
		PreparedStatement stmt1 = null;
		ResultSet rs = null;
		Connection conn = ConexaoBD.getConexaoMySQL();
		try {
			stmt1 = conn.prepareStatement("SELECT * FROM armariodigital.vendas;");
			rs = stmt1.executeQuery();
			while (rs.next()) {
				Venda v = new Venda();
				v.setId(rs.getLong("idVenda"));
				v.setData(rs.getString("Data_Venda"));
				v.setHorario(rs.getString("Hora_Venda"));
				v.setIdFuncionario(rs.getLong("Funcionario_idFuncionario"));
				v.setTotal(rs.getFloat("Total"));
				listaVendas.add(v);
				
				
				

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaVendas;

	}
}
