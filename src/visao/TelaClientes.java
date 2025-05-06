package visao;

import modelo.Clientes;
import modelo.Funcionario;
import modelo.Produto;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import net.miginfocom.swing.MigLayout;

public class TelaClientes extends JFrame {
    private static final String ARQUIVO_CLIENTES =
        System.getProperty("user.home") + File.separator + "clientes.dat";

    private JTextField txtNome, txtEmail, txtTelefone, txtFiltro;
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;
    private TableRowSorter<DefaultTableModel> sorter;
    private List<Clientes> listaClientes = new ArrayList<>();

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

        // 1) Carrega dados gravados e atualiza tabela
        loadClientes();

        // 2) Fundo geral
        ImagePanel painelFundo = new ImagePanel("/img/Fundo.png");
        painelFundo.setLayout(new BorderLayout());
        setContentPane(painelFundo);

        // 3) Barra superior (imagem + ícones + título)
        ImageIcon barraRaw = new ImageIcon(getClass().getResource("/img/barraParteDeCima.png"));
        Image barraImg = barraRaw.getImage().getScaledInstance(1215, 100, Image.SCALE_SMOOTH);
        JLabel barraLabel = new JLabel(new ImageIcon(barraImg));
        barraLabel.setLayout(new MigLayout("", "[87px][][grow][160px]", "[grow]"));

        // 3a) Botão Voltar (esquerda)
        ImageIcon voltarRaw = new ImageIcon(getClass().getResource("/img/de-volta.png"));
        Image voltarImg = voltarRaw.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        JLabel lblVoltar = new JLabel(new ImageIcon(voltarImg));
        lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblVoltar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); // também grava ao descartar
                new TelaMenu(prod, func, mensagem)
                    .setVisible(true);
            }
        });
        barraLabel.add(lblVoltar, "cell 0 0,alignx left,aligny center, w 70!, h 70!");

        // 3b) Título central
        JLabel lblTitulo = new JLabel("Cadastro de Clientes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        barraLabel.add(lblTitulo, "cell 1 0 2 1,alignx center,aligny center");

        // 3c) Ícone Usuário (direita)
        ImageIcon userRaw = new ImageIcon(getClass().getResource("/img/icone.png"));
        Image userImg = userRaw.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        JLabel lblIconeUser = new JLabel(new ImageIcon(userImg));
        barraLabel.add(lblIconeUser, "cell 3 0,alignx right,aligny center, w 70!, h 70!");

        painelFundo.add(barraLabel, BorderLayout.NORTH);

        // 4) Painel principal com formulário, botões e tabela
        JPanel painelPrincipal = new JPanel(new MigLayout("wrap 1", "[grow]", "[][grow]"));
        painelPrincipal.setOpaque(false);

        // 4a) Filtro + Pesquisar
        txtFiltro = new JTextField("Filtrar por nome...");
        txtFiltro.setForeground(Color.GRAY);
        txtFiltro.setPreferredSize(new Dimension(300, 40));
        txtFiltro.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (txtFiltro.getText().equals("Filtrar por nome...")) {
                    txtFiltro.setText("");
                    txtFiltro.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (txtFiltro.getText().isEmpty()) {
                    txtFiltro.setText("Filtrar por nome...");
                    txtFiltro.setForeground(Color.GRAY);
                }
            }
        });
        painelPrincipal.add(txtFiltro, "growx, split 2");
        JButton btnPesquisa = new JButton("PESQUISAR");
        painelPrincipal.add(btnPesquisa, "wrap");
        btnPesquisa.addActionListener(e -> aplicarFiltro());

        // 4b) Tabela configurada com sorter
        modeloTabela = new DefaultTableModel(new Object[]{"Nome", "Email", "Telefone"}, 0);
        tabelaClientes = new JTable(modeloTabela);
        sorter = new TableRowSorter<>(modeloTabela);
        tabelaClientes.setRowSorter(sorter);
        tabelaClientes.setRowHeight(30);
        tabelaClientes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTableHeader header = tabelaClientes.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(123, 150, 212));
        header.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);

        // 4c) Formulário de dados
        JPanel painelFormulario = new JPanel(new MigLayout("wrap 2", "[right][grow]"));
        painelFormulario.setOpaque(false);
        painelFormulario.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelFormulario.add(txtNome, "growx");
        painelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        painelFormulario.add(txtEmail, "growx");
        painelFormulario.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        painelFormulario.add(txtTelefone, "growx");

        // 4d) Botões CRUD
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        painelBotoes.setOpaque(false);
        JButton btnAdicionar = new JButton("Cadastrar");
        JButton btnEditar    = new JButton("Alterar");
        JButton btnExcluir   = new JButton("Deletar");
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);

        // Montagem final
        painelPrincipal.add(painelFormulario, "growx");
        painelPrincipal.add(painelBotoes, "wrap");
        painelPrincipal.add(scrollPane, "grow");
        painelFundo.add(painelPrincipal, BorderLayout.CENTER);

        // Preenche tabela com dados carregados
        atualizarTabela();

        // Listeners CRUD e seleção
        btnAdicionar.addActionListener(e -> adicionarCliente());
        btnEditar   .addActionListener(e -> editarCliente());
        btnExcluir  .addActionListener(e -> excluirCliente());
        tabelaClientes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                carregarClienteSelecionado();
            }
        });

        setVisible(true);
    }

    // Override para garantir persistência sempre que a janela for descartada
    @Override
    public void dispose() {
        saveClientes();
        super.dispose();
    }

    private void aplicarFiltro() {
        String text = txtFiltro.getText().trim();
        if (text.isEmpty() || text.equals("Filtrar por nome...")) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(text), 0));
        }
    }

    private void adicionarCliente() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String telefone = txtTelefone.getText().trim();
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Clientes c = new Clientes(nome, email, telefone);
        listaClientes.add(c);
        modeloTabela.addRow(new Object[]{nome, email, telefone});
        limparCampos();
    }

    private void editarCliente() {
        int viewRow = tabelaClientes.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int modelRow = tabelaClientes.convertRowIndexToModel(viewRow);
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String telefone = txtTelefone.getText().trim();
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Clientes c = listaClientes.get(modelRow);
        c.setNome(nome);
        c.setEmail(email);
        c.setTelefone(telefone);
        modeloTabela.setValueAt(nome, modelRow, 0);
        modeloTabela.setValueAt(email, modelRow, 1);
        modeloTabela.setValueAt(telefone, modelRow, 2);
        limparCampos();
    }

    private void excluirCliente() {
        int viewRow = tabelaClientes.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int modelRow = tabelaClientes.convertRowIndexToModel(viewRow);
        if (JOptionPane.showConfirmDialog(this, "Excluir este cliente?", "Confirmação",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            listaClientes.remove(modelRow);
            modeloTabela.removeRow(modelRow);
            limparCampos();
        }
    }

    private void carregarClienteSelecionado() {
        int viewRow = tabelaClientes.getSelectedRow();
        if (viewRow >= 0) {
            int modelRow = tabelaClientes.convertRowIndexToModel(viewRow);
            Clientes c = listaClientes.get(modelRow);
            txtNome.setText(c.getNome());
            txtEmail.setText(c.getEmail());
            txtTelefone.setText(c.getTelefone());
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Clientes c : listaClientes) {
            modeloTabela.addRow(new Object[]{c.getNome(), c.getEmail(), c.getTelefone()});
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        tabelaClientes.clearSelection();
    }

    @SuppressWarnings("unchecked")
    private void loadClientes() {
        File f = new File(ARQUIVO_CLIENTES);
        if (!f.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            listaClientes = (List<Clientes>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveClientes() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_CLIENTES))) {
            out.writeObject(listaClientes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}