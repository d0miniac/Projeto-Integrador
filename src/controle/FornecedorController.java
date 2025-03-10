package controle;

import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Fornecedor;
import controle.FornecedorDAO;

public class FornecedorController {
    
    public FornecedorController() {
    }

    public ArrayList<Fornecedor> listarFornecedores() throws SQLException {
        FornecedorDAO dao = new FornecedorDAO();
        return dao.selecionarFornecedores();
    }

    public void salvar(Fornecedor fornecedor) throws SQLException {
        FornecedorDAO dao = new FornecedorDAO();
        dao.cadastrarFornecedor(fornecedor);
    }
}
