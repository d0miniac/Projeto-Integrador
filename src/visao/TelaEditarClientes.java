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

        // Topo
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

        
        //Icone à direita
        JLabel lblUser = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/armariodigital.png"))
                  .getImage().getScaledInstance(150, 80, Image.SCALE_SMOOTH)
        ));
        barraTopo.add(lblUser, "cell 3 0,alignx right,aligny center");

        painelFundo.add(barraTopo, BorderLayout.NORTH);

        // Formulário
        JPanel formPanel = new JPanel(new MigLayout("wrap 2", "[right][grow]", "[][][]"));
        formPanel.setOpaque(false);
        formPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField(cliente.getNome_Clientes());
        formPanel.add(txtNome, "growx");
        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField(cliente.getEmail());
        formPanel.add(txtEmail, "growx");
        formPanel.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField(cliente.getTelefone());
        formPanel.add(txtTelefone, "growx");

        // Botões
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setOpaque(false);
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setForeground(new Color(255, 255, 255));
		btnSalvar.setBackground(new Color(32, 60, 115));
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(255, 0, 0));
		btnCancelar.setForeground(Color.WHITE);
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

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(formPanel, BorderLayout.CENTER);
        center.add(btnPanel, BorderLayout.SOUTH);

        painelFundo.add(center, BorderLayout.CENTER);
    }

    /** Será chamado após atualizar para recarregar a lista na TelaClientes */
    protected abstract void onClienteSalvo();
}