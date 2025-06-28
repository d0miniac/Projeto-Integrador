package modelo;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class FuncionarioTableModel extends AbstractTableModel {

    private List<Funcionario> funcionarios;
    private final String[] colunas = { "ID", "Nome", "Email", "CPF", "Senha" };

    public FuncionarioTableModel(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    @Override
    public int getRowCount() {
        return funcionarios.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Funcionario funcionario = funcionarios.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return funcionario.getId();
            case 1:
                return funcionario.getNome();
            case 2:
                return funcionario.getEmail();
            case 3:
                return formatarCpf(funcionario.getCpf());
            case 4:
                return funcionario.getSenha();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    private String formatarCpf(String cpf) {
        if (cpf != null && cpf.length() == 11) {
            return String.format("%s.%s.%s-%s", 
                cpf.substring(0, 3), 
                cpf.substring(3, 6), 
                cpf.substring(6, 9), 
                cpf.substring(9, 11));
        }
        return cpf;
    }

    // NOVO: método para atualizar a lista de funcionários e notificar a tabela
    public void atualizarDados(List<Funcionario> novosFuncionarios) {
        this.funcionarios = novosFuncionarios;
        fireTableDataChanged();
    }

    // Opcional: se quiser acessar o funcionário diretamente
    public Funcionario getFuncionario(int rowIndex) {
        return funcionarios.get(rowIndex);
    }
}