package modelo;
import javax.swing.table.AbstractTableModel;
import java.util.*;
import controle.FuncionarioDAO;

public class HVendasTableModel extends AbstractTableModel {
    private List<Venda> listaVendas = new ArrayList<>();
    private String[] colunas = { "ID", "Data", "Hora", "Funcionário", "Total" };
    private Map<Long, String> funcionariosMap = new HashMap<>();

    public HVendasTableModel(List<Venda> vendas) {
        this.listaVendas = vendas;
        carregarFuncionarios(); // popula o mapa uma vez
    }

    private void carregarFuncionarios() {
        FuncionarioDAO dao = new FuncionarioDAO();
        for (Funcionario f : dao.selecionarFuncionarios()) {
            funcionariosMap.put(f.getId(), f.getNome());
        }
    }

    @Override
    public int getRowCount() {
        return listaVendas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
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
                return funcionariosMap.getOrDefault(venda.getIdFuncionario(), "Desconhecido");
            case 4:
                return "R$ " + String.format("%.2f", venda.getTotal());
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    public void setLista(List<Venda> lista) {
        this.listaVendas = lista;
        carregarFuncionarios();
        fireTableDataChanged(); // útil se quiser recarregar a tabela
    }
    
    
}