package visao;

import controle.ClienteDAO;
import modelo.Clientes;
import modelo.Funcionario;
import modelo.Produto;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import net.miginfocom.swing.MigLayout;

public abstract class TelaEditarClientes extends JFrame {
    private JTextField txtNome, txtEmail, txtTelefone;
    private Produto prod;
    private Funcionario func;
    private String mensagem;
    private Clientes cliente;
    private ClienteDAO dao;

    public TelaEditarClientes(Produto prod, Funcionario func, String mensagem, Clientes cliente) {
        this.prod = prod;
        this.func = func;
        this.mensagem = mensagem;
        this.cliente = cliente;

        dao = new ClienteDAO();

        setTitle("Editar Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(657, 425);
        setLocationRelativeTo(null);
        setResizable(false);

        // Fundo
        ImagePanel painelFundo = new ImagePanel("/img/Fundo.png");
        painelFundo.setLayout(new BorderLayout());
        setContentPane(painelFundo);

        // Topo (Barra Azul)
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

        JLabel lblTitulo = new JLabel("Editar Cliente");
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
        JLabel lblInfo = new JLabel("Informações do cliente");
        lblInfo.setFont(new Font("Arial", Font.BOLD, 16));
        lblInfo.setForeground(new Color(153, 162, 209));
        contentPane.add(lblInfo, "cell 0 0,alignx center");

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

        // Linha separadora
        JSeparator separator = new JSeparator();
        contentPane.add(separator, BorderLayout.SOUTH);

        painelFundo.add(contentPane, BorderLayout.CENTER);

        // Botões
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        botoesPanel.setOpaque(false);

        JButton btnSalvar = new JButton("SALVAR");
        btnSalvar.setBackground(new Color(32, 60, 115));
        btnSalvar.setForeground(Color.WHITE);

        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBackground(new Color(255, 0, 0));
        btnCancelar.setForeground(Color.WHITE);

        botoesPanel.add(btnSalvar);
        botoesPanel.add(btnCancelar);

        painelFundo.add(botoesPanel, BorderLayout.SOUTH);

        // Ações dos botões
        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();
            if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            cliente.setNome(nome);
            cliente.setEmail(email);
            cliente.setTelefone(telefone);
            try {
                dao.atualizar(cliente);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dispose();
            onClienteSalvo();
        });

        btnCancelar.addActionListener(e -> {
            dispose();
        });
    }

    protected abstract void onClienteSalvo();
}