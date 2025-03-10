package controle;

import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Fornecedor;
import modelo.Funcionario;

public class FuncionarioController {
	
	public FuncionarioController() {
	
	}
	
	public ArrayList<Funcionario> listarFuncionarios() throws SQLException {
		FuncionarioDAO dao = new FuncionarioDAO();
		return dao.selecionarFuncionarios();
	}
	
	public void salvar(String nome, String cpf, String email, String senha) throws SQLException {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setEmail(email);
		funcionario.setSenha(senha);
		
		FuncionarioDAO dao = new FuncionarioDAO();
		dao.cadastrarFuncionario(funcionario);
	}
	
	public ArrayList<Funcionario> listarFuncionario() throws SQLException {
		FuncionarioDAO dao = new FuncionarioDAO();
        return dao.selecionarFuncionarios();
    }

    public void salvar(Funcionario funcionario) throws SQLException {
    	FuncionarioDAO dao = new FuncionarioDAO();
        dao.cadastrarFuncionario(funcionario);
    }
}
