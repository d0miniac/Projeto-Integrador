package visao;

import modelo.Clientes;
import modelo.Funcionario;
import modelo.Produto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import net.miginfocom.swing.MigLayout;

public class TelaCadastroClientes extends JFrame {
    private JTextField txtNome, txtEmail, txtTelefone;
    private Produto prod;
    private Funcionario func;
    private String mensagem;

    public TelaCadastroClientes(Produto prod, Funcionario func, String mensagem) {
        this.prod = prod;
        this.func = func;
        this.mensagem = mensagem;

        setTitle("Cadastrar Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(657, 425);
        setLocationRelativeTo(null);
        setResizable(false);

        // Fundo
        ImagePanel painelFundo = new ImagePanel("/img/bgCadastro.png");
        painelFundo.setLayout(new BorderLayout());
        setContentPane(painelFundo);

        // Topo (voltar)
        JPanel topPanel = new JPanel(new MigLayout("", "[87px][][grow][160px]", "[grow]"));
        topPanel.setOpaque(false);

        // Ícone voltar
        JLabel lblVoltar = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/de-volta.png"))
                .getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)
        ));
        lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblVoltar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new TelaClientes(prod, func, mensagem).setVisible(true);
            }
        });
        topPanel.add(lblVoltar, "cell 0 0,alignx left,aligny center");

        // Título
        JLabel lblTitulo = new JLabel("Cadastrar Cliente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        topPanel.add(lblTitulo, "cell 1 0 2 1,alignx center,aligny center");

        // Ícone usuário (mesmo padrão)
        JLabel lblUser = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/icone.png"))
                .getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)
        ));
        topPanel.add(lblUser, "cell 3 0,alignx right,aligny center");

        painelFundo.add(topPanel, BorderLayout.NORTH);

        // Formulário
        JPanel formPanel = new JPanel(new MigLayout("wrap 2", "[right][grow]", "[][][]"));
        formPanel.setOpaque(false);
        formPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        formPanel.add(txtNome, "growx");
        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail, "growx");
        formPanel.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        formPanel.add(txtTelefone, "growx");

        // Botões
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setOpaque(false);
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        btnPanel.add(btnSalvar);
        btnPanel.add(btnCancelar);

        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();
            if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // opcional: persistir com ClienteDAO
            // new ClienteDAO().inserir(new Clientes(nome, email, telefone));
            dispose();
            TelaClientes tela = new TelaClientes(prod, func, mensagem);
            tela.setVisible(true);
        });

        btnCancelar.addActionListener(e -> {
            dispose();
            new TelaClientes(prod, func, mensagem).setVisible(true);
        });

        // Montagem
        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(formPanel, BorderLayout.CENTER);
        center.add(btnPanel, BorderLayout.SOUTH);

        painelFundo.add(center, BorderLayout.CENTER);
    }
}