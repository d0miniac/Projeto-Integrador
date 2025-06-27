package controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.Pagamento;

public class PagamentoDAO {
	
	 private Connection getConnection() throws SQLException {
	        return ConexaoBD.getConexaoMySQL();
	    }
	
	public void salvarPagamento(Pagamento pagamento) throws SQLException{
        String sql = "INSERT INTO pagamentos (idCliente, numeroCartao, nomeCartao, validade, cvv, valorTotal, dataPagamento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try(Connection con = getConnection();
        		PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
        	
        	pstm.setInt(1, pagamento.getIdCliente());
        	pstm.setString(2, pagamento.getNumeroCartao());
        	pstm.setString(3, pagamento.getNomeCartao());
        	pstm.setString(4, pagamento.getValidade());
        	pstm.setString(5, pagamento.getCvv());
        	pstm.setBigDecimal(6, pagamento.getValorTotal());
        	pstm.setDate(7, java.sql.Date.valueOf(pagamento.getDataPagamento()));

        	pstm.executeUpdate();
        	
        }

	}

}
