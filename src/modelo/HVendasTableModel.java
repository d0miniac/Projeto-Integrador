package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import controle.FuncionarioDAO;

public class HVendasTableModel extends AbstractTableModel{
	private List<Venda> listaVendas;
	private String[] colunas = { "ID", "Data", "Hora", "Funcionário", "Total"  };

	public HVendasTableModel(List<Venda> vendas) {
		this.listaVendas = vendas;
	}
	public int getRowCount() {
		
		return listaVendas.size();
	}
	public int getColumnCount() {
		return colunas.length;
	}
	public Object getValueAt(int rowIndex, int columnIndex) {
		Venda venda = listaVendas.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return venda.getId();
		case 1:
			return venda.getData();
		case 2:
			return venda.getHorario();
		case 3:
			venda.getIdFuncionario();
			FuncionarioDAO funcionario = new FuncionarioDAO();
			ArrayList<Funcionario> funcionariosLista = funcionario.selecionarFuncionarios();
			String vendedor="";
			for (Funcionario f : funcionariosLista) {
				if(venda.getIdFuncionario()==f.getId()) {
					vendedor = f.getNome();
				}
			}
			return vendedor;
		case 4:
			return "R$ "+ String.format("%.2f",venda.getTotal());
		default:
			return null;
		}
	}
	public String getColumnName(int column) {
		return colunas[column];
	}
}
