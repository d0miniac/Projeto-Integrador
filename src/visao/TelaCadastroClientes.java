package visao;

import controle.ClienteDAO;
import modelo.Clientes;
import modelo.Funcionario;
import modelo.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import net.miginfocom.swing.MigLayout;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;

public abstract class TelaCadastroClientes extends JFrame {
    private JTextField txtID;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private Produto prod;
    private Funcionario func;
    private String mensagem;
    private ClienteDAO dao;

    public TelaCadastroClientes(Produto prod, Funcionario func, String mensagem) {
        this.prod = prod;
        this.func = func;
        this.mensagem = mensagem;
        this.dao = new ClienteDAO();

        setTitle("Cadastro de Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(657, 425);
        setLocationRelativeTo(null);
        setResizable(false);

        ImagePanel painelFundo = new ImagePanel("/img/Fundo.png");
        painelFundo.setLayout(new BorderLayout());
        setContentPane(painelFundo);

        ImageIcon imgBarra = new ImageIcon(getClass().getResource("/img/barraParteDeCima.png"));
        JLabel barraTopo = new JLabel(imgBarra);
        barraTopo.setLayout(new MigLayout("", "10[]10[grow]10[]10", "10[80]10"));
        barraTopo.setPreferredSize(new Dimension(0, 100));

        JLabel lblVoltar = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/de-volta.png"))
                  .getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)
        ));
        lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblVoltar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        barraTopo.add(lblVoltar, "cell 0 0,alignx left,aligny center");

        JLabel lblTitulo = new JLabel("Cadastro de Clientes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        barraTopo.add(lblTitulo, "growx, alignx center");

        JLabel lblUser = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/armariodigital.png"))
                  .getImage().getScaledInstance(150, 80, Image.SCALE_SMOOTH)
        ));
        barraTopo.add(lblUser, "cell 3 0,alignx right,aligny center");

        painelFundo.add(barraTopo, BorderLayout.NORTH);

        // Painel Central
        JPanel contentPane = new JPanel();
        contentPane.setOpaque(false);
        contentPane.setLayout(new MigLayout("", "[grow]", "[grow][100px][150px][150px][100px]"));
        painelFundo.add(contentPane, BorderLayout.CENTER);
        
        // Título "Informações do cliente"
        JLabel lblTituloForm = new JLabel("Informações do cliente");
        lblTituloForm.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTituloForm.setForeground(new Color(153, 162, 209));
        contentPane.add(lblTituloForm, "cell 0 0,alignx center");
        
        // Campos

        JPanel painelTopo = new JPanel();
        painelTopo.setOpaque(false);
        painelTopo.setBorder(new MatteBorder(0, 0, 5, 0, new Color(32, 60, 115, 124)));
        painelTopo.setLayout(new MigLayout("", "[grow][grow][grow]", "[][]"));
        contentPane.add(painelTopo, "cell 0 1,grow");

        JLabel lblNome = new JLabel("NOME");
        painelTopo.add(lblNome, "cell 0 0,alignx center");

        txtNome = new JTextField();
        txtNome.setColumns(10);
        painelTopo.add(txtNome, "cell 0 1,alignx center");

        JLabel lblEmail = new JLabel("EMAIL");
        painelTopo.add(lblEmail, "cell 1 0,alignx center");

        txtEmail = new JTextField();
        txtEmail.setColumns(10);
        painelTopo.add(txtEmail, "cell 1 1,alignx center");

        JLabel lblTelefone = new JLabel("TELEFONE");
        painelTopo.add(lblTelefone, "cell 2 0,alignx center");

        try {
            MaskFormatter formatTelefone = new MaskFormatter("(##) #####-####");
            txtTelefone = new JFormattedTextField(formatTelefone);
            txtTelefone.setColumns(10);
            painelTopo.add(txtTelefone, "cell 2 1,alignx center");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel painelInferior = new JPanel();
        painelInferior.setOpaque(false);
        painelInferior.setLayout(new MigLayout("", "[100px][100px]", "[]"));
        contentPane.add(painelInferior, "cell 0 4,grow");

        JButton btnCadastrar = new JButton("CADASTRAR");
        btnCadastrar.setBackground(new Color(32, 60, 115));
        btnCadastrar.setForeground(Color.WHITE);
        painelInferior.add(btnCadastrar, "cell 0 0");

        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBackground(new Color(255, 0, 0));
        btnCancelar.setForeground(Color.WHITE);
        painelInferior.add(btnCancelar, "cell 1 0");

        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim().replaceAll("[^\\d]", "");

            if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
                new TelaErro("Preencha todos os campos obrigatórios!", 0).setVisible(true);
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                new TelaErro("O e-mail deve conter '@' e '.'!", 0).setVisible(true);
                return;
            }

            if (!nome.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                new TelaErro("O nome deve conter apenas letras!", 0).setVisible(true);
                return;
            }

            if (telefone.length() != 11) {
                new TelaErro("O telefone deve conter exatamente 11 dígitos!", 0).setVisible(true);
                return;
            }

            Clientes c = new Clientes(nome, email, txtTelefone.getText());
            try {
                dao.inserir(c);
                new TelaErro("Cliente cadastrado com sucesso!", 3).setVisible(true);
                dispose();
                onClienteSalvo();
            } catch (SQLException ex) {
                new TelaErro("Erro ao salvar: " + ex.getMessage(), 0).setVisible(true);
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    protected abstract void onClienteSalvo();
}