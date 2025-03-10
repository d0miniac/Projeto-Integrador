package modelo;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ProdutoTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] colunas = {"ID", "Categoria", "Marca", "Cor", "Tamanho", "Quantidade", "Pre\u00E7o"};
	private ArrayList<Produto> produtosLista;
	public ProdutoTableModel(ArrayList<Produto> produtos) {
		this.produtosLista = produtos;
	}
	
	public int getRowCount() {
		return produtosLista.size();
	}
	public int getColumnCount() {
		return colunas.length;
	}
	
	public Object getValueAt (int rowIndex, int columnIndex) {
		Produto produto = produtosLista.get(rowIndex);
		if(columnIndex == 0) {
			return produto.getId();
		}
		else if(columnIndex ==1) {
			return produto.getCategoria();
		}
		else if(columnIndex ==2) {
			return produto.getMarca();
		}
		else if(columnIndex ==3) {
			return produto.getCor();
		}
		else if(columnIndex ==4) {
			return produto.getTamanho();
		}
		else if(columnIndex ==5) {
			return produto.getQuantidade();
		}
		else if(columnIndex ==6) {
			return String.format("R$ %.2f", produto.getPreco());
		}
		
		else {
			return null;
		}
		
	}
	
	public String getColumnName (int coluna) {
		return colunas [coluna];
	}
	
}
