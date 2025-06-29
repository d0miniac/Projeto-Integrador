package visao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controle.ItemVendaDAO;
import modelo.Funcionario;
import modelo.ItemVenda;
import modelo.Produto;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;

public class TelaDetalhesVenda extends JFrame {

    private JTable tabela;
    private List<ItemVenda> itensVenda;

    public TelaDetalhesVenda(Long idVenda, Produto prod, Funcionario func, String mensagem) {
        setTitle("Detalhes da Venda #" + idVenda);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        ImagePanel contentPane = new ImagePanel(getClass().getResource("/img/Fundo.png"));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // --- Barra superior ---
        ImageIcon imgBarra = new ImageIcon(getClass().getResource("/img/barraParteDeCima.png"));
        JLabel barraTopo = new JLabel(imgBarra);
        barraTopo.setLayout(new MigLayout("", "10[]10[grow]10[]10", "10[80]10"));
        barraTopo.setPreferredSize(new Dimension(0, 100));

        JLabel btnVoltar = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/img/de-volta.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        btnVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVoltar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        barraTopo.add(btnVoltar);

        JLabel lblTitulo = new JLabel("Itens da Venda #" + idVenda);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE);
        barraTopo.add(lblTitulo, "growx, alignx center");

        JLabel iconeUsuario = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/img/armariodigital.png")).getImage().getScaledInstance(150, 80, Image.SCALE_SMOOTH)));
        barraTopo.add(iconeUsuario);

        contentPane.add(barraTopo, BorderLayout.NORTH);

        // --- Painel Central com MigLayout ---
        JPanel painelCentro = new JPanel(new MigLayout("", "10[grow]10", "10[grow]10"));
        painelCentro.setOpaque(false);

        tabela = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabela);
        painelCentro.add(scrollPane, "grow, push");

        contentPane.add(painelCentro, BorderLayout.CENTER);

        // --- Rodapé ---
        JPanel painelRodape = new JPanel();
        painelRodape.setOpaque(false);

        JButton btnFechar = new JButton("Fechar");
        styleButton(btnFechar);
        btnFechar.addActionListener(e -> dispose());
        painelRodape.add(btnFechar);

        contentPane.add(painelRodape, BorderLayout.SOUTH);

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

            modelo.addRow(new Object[]{
                nome,
                quantidade,
                "R$ " + precoUnitario.setScale(2),
                "R$ " + subtotal.setScale(2)
            });
        }

        tabela.setModel(modelo);
        tabela.setFont(new Font("Tahoma", Font.PLAIN, 15));
        tabela.setRowHeight(20);
        tabela.setBackground(new Color(123, 150, 212));
        tabela.setForeground(Color.WHITE);
        tabela.setGridColor(new Color(123, 150, 212));

        JTableHeader header = tabela.getTableHeader();
        header.setFont(new Font("Tahoma", Font.PLAIN, 18));
        header.setBackground(Color.WHITE);
        header.setForeground(new Color(123, 150, 212));
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btn.setBackground(new Color(243, 244, 240));
        btn.setBorder(BorderFactory.createLineBorder(new Color(123, 150, 212), 2, true));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(130, 36));
    }
}