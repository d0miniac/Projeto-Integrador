package visao;

import controle.ClienteDAO;
import modelo.Clientes;
import modelo.Funcionario;
import modelo.Produto;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import net.miginfocom.swing.MigLayout;

public class TelaClientes extends JFrame {
    private JTextField txtFiltro;
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;
    private TableRowSorter<DefaultTableModel> sorter;

    private ClienteDAO dao = new ClienteDAO();
    private List<Clientes> listaClientes;

    private Produto prod;
    private Funcionario func;
    private String mensagem;

    public TelaClientes(Produto prod, Funcionario func, String mensagem) {
        this.prod = prod;
        this.func = func;
        this.mensagem = mensagem;

        setTitle("Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1215, 850);
        setLocationRelativeTo(null);

        try {
            listaClientes = dao.listarTodos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar clientes: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
            listaClientes = List.of();
        }

        initComponents();
        carregarTabela();
        setVisible(true);
    }

    private void initComponents() {
        // --- Fundo ---
        ImagePanel painelFundo = new ImagePanel("/img/Fundo.png");
        painelFundo.setLayout(new BorderLayout());
        setContentPane(painelFundo);

        // --- Topo (barra azul) ---
        JPanel topPanel = new JPanel(new MigLayout("", "[87px][][grow][160px]", "[grow]"));
        topPanel.setBackground(new Color(33, 64, 154));
        topPanel.setPreferredSize(new Dimension(0, 100));

        // ícone usuário à direita
        JLabel lblUser = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/icone.png"))
                .getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)
        ));
        topPanel.add(lblUser, "cell 3 0,alignx right,aligny center, w 70!, h 70!");

        // título central
        JLabel lblTitulo = new JLabel("Clientes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo, "cell 1 0 2 1,alignx center,aligny center");

        // botão voltar (menu)
        JLabel lblVoltar = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/de-volta.png"))
                .getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)
        ));
        lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblVoltar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new TelaMenu(prod, func, mensagem)
                    .setVisible(true);
            }
        });
        topPanel.add(lblVoltar, "cell 0 0,alignx left,aligny center, w 70!, h 70!");

        painelFundo.add(topPanel, BorderLayout.NORTH);

        // --- Centro (filtro + tabela) ---
        JPanel center = new JPanel(new MigLayout("", "[grow]", "[60px][grow]"));
        center.setOpaque(false);

        // filtro
        txtFiltro = new JTextField();
        txtFiltro.setUI(new HintTextFieldUI("Filtrar por nome...", true));
        center.add(txtFiltro, "cell 0 0, split 2, growx");
        JButton btnPesquisa = new JButton("Pesquisar");
        styleButton(btnPesquisa);
        center.add(btnPesquisa, "wrap");
        btnPesquisa.addActionListener(e -> aplicarFiltro());

        // tabela
        modeloTabela = new DefaultTableModel(new Object[]{"ID","Nome","Email","Telefone"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tabelaClientes.setBackground(new Color(123, 150, 212));
        tabelaClientes.setForeground(Color.WHITE);
        JTableHeader hd = tabelaClientes.getTableHeader();
        hd.setFont(new Font("Tahoma", Font.PLAIN, 20));
        sorter = new TableRowSorter<>(modeloTabela);
        tabelaClientes.setRowSorter(sorter);

        center.add(new JScrollPane(tabelaClientes), "cell 0 1,grow");
        painelFundo.add(center, BorderLayout.CENTER);

        // --- Rodapé (botões) ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setOpaque(false);

        JButton btnAdd = new JButton("Cadastrar");
        styleButton(btnAdd);
        btnAdd.addActionListener(e -> abrirCadastro());

        JButton btnEdit = new JButton("Alterar");
        styleButton(btnEdit);
        btnEdit.addActionListener(e -> abrirEdicao());

        JButton btnDel = new JButton("Deletar");
        styleButton(btnDel);
        btnDel.addActionListener(e -> deletarCliente());

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDel);
        painelFundo.add(btnPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton b) {
        b.setBackground(new Color(243, 244, 240));
        b.setFont(new Font("Tahoma", Font.PLAIN, 24));
        b.setBorder(BorderFactory.createLineBorder(new Color(123, 150, 212), 2, true));
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        for (Clientes c : listaClientes) {
            modeloTabela.addRow(new Object[]{
                c.getidCliente(), c.getNome_Clientes(), c.getEmail(), c.getTelefone()
            });
        }
    }

    private void aplicarFiltro() {
        String txt = txtFiltro.getText().trim();
        if (txt.isEmpty()) sorter.setRowFilter(null);
        else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(txt), 1));
    }

    
    private void abrirCadastro() {
        new TelaCadastroClientes(prod, func, mensagem) {
            @Override
            protected void onClienteSalvo() {
                try {
                    listaClientes = dao.listarTodos();
                    carregarTabela();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, 
                    		"Erro ao regarregar clientes:  " + ex.getMessage(),
                    		"Erro",JOptionPane.ERROR_MESSAGE);
                }
            }
        }.setVisible(true);
    }

    
    private void abrirEdicao() {
        int row = tabelaClientes.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Selecione um cliente para alterar.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int modelRow = tabelaClientes.convertRowIndexToModel(row);
        Clientes c = listaClientes.get(modelRow);
        new TelaEditarClientes(prod, func, mensagem, c) {
            @Override
            protected void onClienteSalvo() {
                try {
                    listaClientes = dao.listarTodos();
                    carregarTabela();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,
                        "Erro ao recarregar clientes: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.setVisible(true);
    }

    private void deletarCliente() {
        int row = tabelaClientes.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Selecione um cliente para excluir.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int modelRow = tabelaClientes.convertRowIndexToModel(row);
        Clientes c = listaClientes.get(modelRow);
        int opt = JOptionPane.showConfirmDialog(this,
            "Excluir este cliente?", "Confirmação",
            JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            try {
                dao.deletar(c.getidCliente());
                listaClientes = dao.listarTodos();
                carregarTabela();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao excluir: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}