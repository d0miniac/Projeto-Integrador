package modelo;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import modelo.Fornecedor;

public class FornecedorTableModel extends AbstractTableModel {
	private List<Fornecedor> fornecedores;
	private String[] colunas = { "ID", "Email", "Nome Fornecedor", "Nome p/ contato", "Telefone" };

	public FornecedorTableModel(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	@Override
	public int getRowCount() {
		return fornecedores.size();
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Fornecedor fornecedor = fornecedores.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return fornecedor.getIdFornecedor();
		case 1:
			return fornecedor.getEmail();
		case 2:
			return fornecedor.getNomeFornecedor();
		case 3:
			return fornecedor.getNomeCtt();
		case 4:
			return fornecedor.getTelefone();
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}
}
