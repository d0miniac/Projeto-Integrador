package visao;

import modelo.Clientes;
import modelo.Funcionario;
import modelo.Produto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import net.miginfocom.swing.MigLayout;

public class TelaClientes extends JFrame {
    private JTextField txtNome, txtEmail, txtTelefone, txtFiltro;
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;
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
        setLayout(new BorderLayout());

        // 1) Fundo geral
        ImagePanel painelFundo = new ImagePanel("/img/Fundo.png");
        painelFundo.setLayout(new BorderLayout());
        setContentPane(painelFundo);

        // 2) Barra superior como imagem
        ImageIcon barraRaw = new ImageIcon(getClass().getResource("/img/barraParteDeCima.png"));
        Image barraImg = barraRaw.getImage().getScaledInstance(1215, 100, Image.SCALE_SMOOTH);
        JLabel barraLabel = new JLabel(new ImageIcon(barraImg));
        barraLabel.setLayout(new MigLayout(
            "", 
            "[87px][][grow][160px]",
            "[grow]"
        ));

        //Image usuário
        ImageIcon userRaw = new ImageIcon(getClass().getResource("/img/icone.png"));
        Image userImg = userRaw.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        JLabel lblIconeUser = new JLabel(new ImageIcon(userImg));
        barraLabel.add(lblIconeUser, "cell 0 0,alignx right,aligny center, w 70!, h 70!");

        //Texto cadastro
        JLabel lblBemVindo = new JLabel("Cadastro de Clientes " , SwingConstants.CENTER);
        lblBemVindo.setFont(new Font("Arial", Font.BOLD, 30));
        lblBemVindo.setForeground(Color.WHITE);
        barraLabel.add(lblBemVindo, "cell 1 0 2 1,alignx center,aligny center");

        // Botao voltar ao login(arrumar pra voltar ao menu
        ImageIcon outRaw = new ImageIcon(getClass().getResource("/img/de-volta.png"));
        Image outImg = outRaw.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        JLabel lblLogout = new JLabel(new ImageIcon(outImg));
        lblLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                TelaLogin telaLogin = new TelaLogin(prod, mensagem, func);
                telaLogin.setVisible(true);
                telaLogin.setSize(1215, 850);
                telaLogin.setLocationRelativeTo(null);
            }
        });
        barraLabel.add(lblLogout, "cell 0 0,alignx left,aligny center, w 70!, h 70!");

        painelFundo.add(barraLabel, BorderLayout.NORTH);

        // 3) Painel principal com conteúdo
        JPanel painelPrincipal = new JPanel(new MigLayout("wrap 1", "[grow]", "[][grow]"));
        painelPrincipal.setOpaque(false);

        // 3a) Campo filtro
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
        btnPesquisa.addActionListener(e -> {
            // implementar busca em listaClientes e chamar atualizarTabela()
        });

        // 3b) Formulário de dados
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
        painelPrincipal.add(painelFormulario, "growx");

        // 3c) Botões de ação
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        painelBotoes.setOpaque(false);
        JButton btnAdicionar = new JButton("Cadastrar");
        JButton btnEditar    = new JButton("Alterar");
        JButton btnExcluir   = new JButton("Deletar");
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelPrincipal.add(painelBotoes, "wrap");

        // 3d) Tabela de clientes
        modeloTabela = new DefaultTableModel(new Object[]{"Nome", "Email", "Telefone"}, 0);
        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.setRowHeight(30);
        tabelaClientes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTableHeader header = tabelaClientes.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(123, 150, 212));
        header.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        painelPrincipal.add(scrollPane, "grow");

        // 4) Ligações de ações
        btnAdicionar.addActionListener(e -> adicionarCliente());
        btnEditar.addActionListener(e   -> editarCliente());
        btnExcluir.addActionListener(e  -> excluirCliente());
        tabelaClientes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                carregarClienteSelecionado();
            }
        });

        painelFundo.add(painelPrincipal, BorderLayout.CENTER);
        setVisible(true);
    }

    // Métodos de CRUD (mantidos iguais ao anterior)
    private void adicionarCliente() { /* ... */ }
    private void editarCliente()     { /* ... */ }
    private void excluirCliente()    { /* ... */ }
    private void carregarClienteSelecionado() { /* ... */ }
    private void atualizarTabela()   { /* ... */ }
    private void limparCampos()      { /* ... */ }
}