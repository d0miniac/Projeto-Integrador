package visao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controle.ItemVendaDAO;
import modelo.ItemVenda;
import modelo.Produto;

import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class TelaDetalhesVenda extends JFrame {

    private JTable tabela;
    private List<ItemVenda> itensVenda;

    public TelaDetalhesVenda(Long idVenda) {
        setTitle("Detalhes da Venda #" + idVenda);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);

        JLabel titulo = new JLabel("Itens da Venda #" + idVenda);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(titulo, BorderLayout.NORTH);

        tabela = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabela);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnFechar.addActionListener(e -> dispose());

        JPanel rodape = new JPanel();
        rodape.add(btnFechar);
        contentPane.add(rodape, BorderLayout.SOUTH);

        carregarItens(idVenda);
    }

    private void carregarItens(Long idVenda) {
        ItemVendaDAO dao = new ItemVendaDAO();
        itensVenda = dao.buscarItensPorVenda(idVenda.intValue());

        String[] colunas = {"Produto", "Quantidade", "Preço Unitário", "Subtotal"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (ItemVenda item : itensVenda) {
            Produto produto = item.getFoto();
            String nome = produto.getMarca() + " " + produto.getCategoria() + " " +
                          produto.getTamanho() + " " + produto.getCor();

            int quantidade = item.getQuantidade();
            BigDecimal precoUnitario = produto.getPreco();
            BigDecimal subtotal = precoUnitario.multiply(BigDecimal.valueOf(quantidade));

            modelo.addRow(new Object[] {
                nome,
                quantidade,
                "R$ " + precoUnitario.setScale(2),
                "R$ " + subtotal.setScale(2)
            });
        }

        tabela.setModel(modelo);
        tabela.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tabela.setRowHeight(28);

        JTableHeader header = tabela.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 16));
    }
}