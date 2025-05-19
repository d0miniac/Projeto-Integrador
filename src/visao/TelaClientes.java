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
		setResizable(false);


        try {
            listaClientes = dao.listarTodos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            listaClientes = List.of();
        }

        initComponents();
        carregarTabela();
        setVisible(true);
    }

    private void initComponents() {
        // --- Fundo ---
        ImagePanel contentPane = new ImagePanel("/img/Fundo.png");
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

     // --- Topo com imagem de barra ---
        ImageIcon imgBarra = new ImageIcon(getClass().getResource("/img/barraParteDeCima.png"));
        JLabel barraTopo = new JLabel(imgBarra);
        barraTopo.setLayout(new MigLayout("", "10[]10[grow]10[]10", "10[80]10"));
        barraTopo.setPreferredSize(new Dimension(0, 100));

        // Botão voltar
        JLabel btnVoltar = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/img/de-volta.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        btnVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVoltar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new TelaMenu(prod, func, mensagem).setVisible(true);
            }
        });
        barraTopo.add(btnVoltar);

        // Título centralizado
        JLabel lblTitulo = new JLabel("Clientes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE); // ou qualquer cor que contraste bem com sua imagem
        barraTopo.add(lblTitulo, "growx, alignx center");

        // Ícone do usuário à direita
        JLabel iconeUsuario = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/img/armariodigital.png")).getImage().getScaledInstance(150, 80, Image.SCALE_SMOOTH)));
        barraTopo.add(iconeUsuario);

        contentPane.add(barraTopo, BorderLayout.NORTH);

        // --- Centro (Filtro + Tabela) ---
        JPanel painelCentro = new JPanel(new MigLayout("", "10[450,grow]10[160]10", "10[45]10[45]10[fill,grow]10"));
        painelCentro.setOpaque(false);

        txtFiltro = new JTextField();
        txtFiltro.setUI(new HintTextFieldUI("Pesquise por nome, email ou telefone", true));
        txtFiltro.setFont(new Font("Tahoma", Font.PLAIN, 18));
        txtFiltro.setBorder(BorderFactory.createLineBorder(new Color(123, 150, 212), 2, true));
        txtFiltro.setPreferredSize(new Dimension(450, 50));
        txtFiltro.setColumns(90);
        painelCentro.add(txtFiltro, "cell 0 0, alignx left"); // Alinha à esquerda e encaixa melhor no MigLayout
        
        
        JButton btnPesquisar = new JButton("PESQUISAR");
        styleButton(btnPesquisar);
        painelCentro.add(btnPesquisar, "cell 1 0");
        btnPesquisar.addActionListener(e -> aplicarFiltro());

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "Email", "Telefone"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaClientes = new JTable(modeloTabela);
        sorter = new TableRowSorter<>(modeloTabela);
        tabelaClientes.setRowSorter(sorter);
        tabelaClientes.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tabelaClientes.setRowHeight(28);
        tabelaClientes.setBackground(new Color(123, 150, 212));
        tabelaClientes.setForeground(Color.WHITE);

        JTableHeader header = tabelaClientes.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 18));
        header.setBackground(new Color(243, 244, 240));
        header.setForeground(new Color(180, 196, 250));

        painelCentro.add(new JScrollPane(tabelaClientes), "cell 0 2 2 1, grow, push");

        contentPane.add(painelCentro, BorderLayout.CENTER);

        // --- Rodapé (Botões) ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        painelBotoes.setOpaque(false);

        JButton btnCadastrar = new JButton("Cadastrar");
        styleButton(btnCadastrar);
        btnCadastrar.addActionListener(e -> abrirCadastro());

        JButton btnAlterar = new JButton("Alterar");
        styleButton(btnAlterar);
        btnAlterar.addActionListener(e -> abrirEdicao());

        JButton btnDeletar = new JButton("Deletar");
        styleButton(btnDeletar);
        btnDeletar.addActionListener(e -> deletarCliente());

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnDeletar);

        // Adiciona os botões abaixo da barra de pesquisa (nova linha do MigLayout)
        painelCentro.add(painelBotoes, "cell 0 1 2 1, alignx left, wrap");

    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btn.setBackground(new Color(243, 244, 240));
        btn.setBorder(BorderFactory.createLineBorder(new Color(123, 150, 212), 2, true));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(130, 36));
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
        String texto = txtFiltro.getText().trim();
        if (texto.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 1));
        }
    }

    private void abrirCadastro() {
        new TelaCadastroClientes(prod, func, mensagem) {
            @Override
            protected void onClienteSalvo() {
                try {
                    listaClientes = dao.listarTodos();
                    carregarTabela();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao recarregar clientes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.setVisible(true);
    }

    private void abrirEdicao() {
        int row = tabelaClientes.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para alterar.", "Aviso", JOptionPane.WARNING_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "Erro ao recarregar clientes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.setVisible(true);
    }

    private void deletarCliente() {
        int row = tabelaClientes.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int modelRow = tabelaClientes.convertRowIndexToModel(row);
        Clientes c = listaClientes.get(modelRow);
        int opt = JOptionPane.showConfirmDialog(this, "Excluir este cliente?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            try {
                dao.deletar(c.getidCliente());
                listaClientes = dao.listarTodos();
                carregarTabela();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}