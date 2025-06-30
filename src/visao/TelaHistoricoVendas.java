package visao;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;

import controle.VendaDAO;
import modelo.Funcionario;
import modelo.HVendasTableModel;
import modelo.Produto;
import modelo.Venda;
import net.miginfocom.swing.MigLayout;

public class TelaHistoricoVendas extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private HVendasTableModel vtm;
    private ArrayList<Venda> listaVendas;
    private Funcionario funcionario;
    private Produto produto;
    private String mensagem;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Produto prod = new Produto();
                Funcionario funcionario = new Funcionario();
                String mensagem = "Bem-vindo ao sistema!";
                TelaHistoricoVendas frame = new TelaHistoricoVendas(funcionario, mensagem, prod);
                frame.setVisible(true);
            } catch (Exception e) {
                TelaErro telaErro = new TelaErro("Erro crítico: " + e.getMessage());
                telaErro.setVisible(true);
            }
        });
    }

    public TelaHistoricoVendas(Funcionario func, String mensagem, Produto prod) {
        this.funcionario = func;
        this.produto = prod;
        this.mensagem = mensagem;

        setTitle("Histórico de Vendas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1190, 750);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPane = new ImagePanel(getClass().getResource("/img/bgTelaHistorico.png"));
        contentPane.setBackground(new Color(243, 244, 240));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[183px][276px][4px][422px][4px][292px]", "[120px][][][][25px][523px]"));

        JPanel panelVazio = new JPanel();
        panelVazio.setOpaque(false);
        contentPane.add(panelVazio, "cell 0 0,grow");
        panelVazio.setLayout(null);

        JLabel lblSeta = new JLabel();
        lblSeta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSeta.setIcon(new ImageIcon(new ImageIcon(TelaCadastroProdutos.class.getResource("/img/de-volta.png"))
                .getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
        lblSeta.setBounds(0, 0, 110, 100);
        panelVazio.add(lblSeta);
        lblSeta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TelaMenu tela = new TelaMenu(produto, funcionario, mensagem);
                dispose();
                tela.setVisible(true);
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(new LineBorder(new Color(123, 150, 212), 2, true));
        contentPane.add(scrollPane, "cell 0 5 6 1,grow");

        VendaDAO vdao = new VendaDAO();
        listaVendas = vdao.selecionarVendas();
        vtm = new HVendasTableModel(listaVendas);

        table = new JTable();
        table.setBackground(new Color(243, 244, 240));
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        table.setModel(vtm);
        scrollPane.setViewportView(table);

        estilizarTabela();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = table.getSelectedRow();
                if (linha >= 0) {
                    Long idVenda = (Long) table.getValueAt(linha, 0);
                    new TelaDetalhesVenda(idVenda, prod, func, mensagem).setVisible(true);
                }
            }
        });
    }

    private void estilizarTabela() {
        JTableHeader thead = table.getTableHeader();
        thead.setForeground(new Color(123, 150, 212));
        thead.setBackground(Color.WHITE);
        thead.setFont(new Font("Tahoma", Font.PLAIN, 20));
    }
}