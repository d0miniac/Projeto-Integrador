package controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Funcionario;

public class FuncionarioDAO {

    public int cadastrarFuncionario(Funcionario funcionario) {
        String sql = "INSERT INTO funcionarios (CPF, NomeFuncionario, Email, Senha, Perfil) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getCpf());
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, funcionario.getEmail());
            stmt.setString(4, funcionario.getSenha());
            stmt.setString(5, funcionario.getPerfil());

            return stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar funcionário: " + e.getMessage());
            return 0;
        }
    }

    public Funcionario logarFuncionario(Funcionario login) {
        String sql = "SELECT * FROM funcionarios WHERE Email = ? AND Senha = ?";

        try (Connection conn = ConexaoBD.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login.getEmail());
            stmt.setString(2, login.getSenha());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Funcionario autenticado = new Funcionario();
                    autenticado.setId(rs.getLong("idFuncionario"));
                    autenticado.setCpf(rs.getString("CPF"));
                    autenticado.setNome(rs.getString("NomeFuncionario"));
                    autenticado.setEmail(rs.getString("Email"));
                    autenticado.setSenha(rs.getString("Senha"));
                    autenticado.setPerfil(rs.getString("Perfil"));
                    return autenticado;
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao logar funcionário: " + e.getMessage());
        }

        return null;
    }

    public Funcionario buscarPorEmail(String email) {
        String sql = "SELECT * FROM funcionarios WHERE Email = ?";

        try (Connection conn = ConexaoBD.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Funcionario funcionario = new Funcionario();
                    funcionario.setId(rs.getLong("idFuncionario"));
                    funcionario.setCpf(rs.getString("CPF"));
                    funcionario.setNome(rs.getString("NomeFuncionario"));
                    funcionario.setEmail(rs.getString("Email"));
                    funcionario.setSenha(rs.getString("Senha"));
                    funcionario.setPerfil(rs.getString("Perfil"));
                    return funcionario;
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por e-mail: " + e.getMessage());
        }

        return null;
    }

    public ArrayList<Funcionario> selecionarFuncionarios() {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios";

        try (Connection conn = ConexaoBD.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getLong("idFuncionario"));
                funcionario.setCpf(rs.getString("CPF"));
                funcionario.setNome(rs.getString("NomeFuncionario"));
                funcionario.setEmail(rs.getString("Email"));
                funcionario.setSenha(rs.getString("Senha"));
                funcionario.setPerfil(rs.getString("Perfil"));
                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao selecionar funcionários: " + e.getMessage());
        }

        return funcionarios;
    }

    public ArrayList<Funcionario> pesquisarFuncionarios(String filtro) {
        ArrayList<Funcionario> listaFuncionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios WHERE NomeFuncionario LIKE ? OR CPF LIKE ? OR Email LIKE ? OR idFuncionario LIKE ?";

        try (Connection conn = ConexaoBD.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 1; i <= 4; i++) {
                stmt.setString(i, "%" + filtro + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Funcionario funcionario = new Funcionario();
                    funcionario.setId(rs.getLong("idFuncionario"));
                    funcionario.setCpf(rs.getString("CPF"));
                    funcionario.setNome(rs.getString("NomeFuncionario"));
                    funcionario.setEmail(rs.getString("Email"));
                    funcionario.setSenha(rs.getString("Senha"));
                    funcionario.setPerfil(rs.getString("Perfil"));
                    listaFuncionarios.add(funcionario);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar funcionários: " + e.getMessage());
        }

        return listaFuncionarios;
    }

    public void alterarFuncionario(Funcionario funcionario) throws SQLException {
        String sql = "UPDATE funcionarios SET CPF = ?, NomeFuncionario = ?, Email = ?, Senha = ? WHERE idFuncionario = ?";

        try (Connection conn = ConexaoBD.getConexaoMySQL();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getCpf());
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, funcionario.getEmail());
            stmt.setString(4, funcionario.getSenha());
            stmt.setLong(5, funcionario.getId());

            stmt.executeUpdate();
            System.out.println("Funcionário atualizado com sucesso: " + funcionario.getId());

        } catch (SQLException e) {
            System.err.println("Erro ao alterar funcionário: " + e.getMessage());
            throw e;
        }
    }

    public boolean excluirFuncionario(Long idFuncionario) throws SQLException {
    String sql = "DELETE FROM funcionarios WHERE idFuncionario = ?";
    Connection conn = ConexaoBD.getConexaoMySQL();
         PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, idFuncionario);
        int linhas = stmt.executeUpdate();
        System.out.println("Linhas excluídas: " + linhas);
        return linhas > 0;
    
}

}