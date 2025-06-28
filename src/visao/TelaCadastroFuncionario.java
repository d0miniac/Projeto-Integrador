package visao;

import controle.FuncionarioDAO;
import modelo.Funcionario;
import modelo.Produto;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class TelaCadastroFuncionario extends JFrame {
    private JTextField txtNome, txtEmail, txtSenha, txtConfirma;
    private JFormattedTextField txtCpf;
    private JCheckBox checkBoxAdmin;

    public TelaCadastroFuncionario(Produto prod, String mensagem, Funcionario func) {
        setTitle("Cadastro de Funcionários");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(657, 525);
        setLocationRelativeTo(null);
        setResizable(false);

        ImagePanel painelFundo = new ImagePanel(getClass().getResource("/img/Fundo.png"));
        painelFundo.setLayout(new BorderLayout());
        setContentPane(painelFundo);

        // Barra Superior
        JLabel barraTopo = new JLabel(new ImageIcon(getClass().getResource("/img/barraParteDeCima.png")));
        barraTopo.setLayout(new MigLayout("", "10[]10[grow]10[]10", "10[80]10"));
        barraTopo.setPreferredSize(new Dimension(0, 100));

        JLabel lblVoltar = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/de-volta.png"))
                .getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
        lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblVoltar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    TelaFuncionarios tela = new TelaFuncionarios(prod, func, mensagem);
                    dispose();
                    tela.setVisible(true);
                } catch (SQLException ex) {
                    new TelaErro("Erro ao retornar para TelaFuncionarios!", 0).setVisible(true);
                    ex.printStackTrace();
                }
            }
        });
        barraTopo.add(lblVoltar, "cell 0 0");

        JLabel lblTitulo = new JLabel("Cadastro de Funcionários");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        barraTopo.add(lblTitulo, "growx, alignx center");

        JLabel lblLogo = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/armariodigital.png"))
                .getImage().getScaledInstance(150, 80, Image.SCALE_SMOOTH)));
        barraTopo.add(lblLogo, "cell 3 0");
        painelFundo.add(barraTopo, BorderLayout.NORTH);

        // Painel Central
        JPanel contentPane = new JPanel();
        contentPane.setOpaque(false);
        contentPane.setLayout(new MigLayout("", "[grow]", "[50px][240px][30px]"));
        painelFundo.add(contentPane, BorderLayout.CENTER);

        JLabel lblForm = new JLabel("Informações do funcionário");
        lblForm.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblForm.setForeground(new Color(153, 162, 209));
        contentPane.add(lblForm, "cell 0 0,alignx center");

        JPanel painelForm = new JPanel();
        painelForm.setOpaque(false);
        painelForm.setBorder(new MatteBorder(0, 0, 5, 0, new Color(32, 60, 115, 124)));
        painelForm.setLayout(new MigLayout("", "[grow]", "[][][][][][][]"));
        contentPane.add(painelForm, "cell 0 1,grow");

        painelForm.add(new JLabel("NOME"), "wrap");
        txtNome = new JTextField(25);
        painelForm.add(txtNome, "growx, wrap");

        painelForm.add(new JLabel("EMAIL"), "wrap");
        txtEmail = new JTextField(25);
        painelForm.add(txtEmail, "growx, wrap");

        painelForm.add(new JLabel("CPF"), "wrap");
        try {
            txtCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
            painelForm.add(txtCpf, "growx, wrap");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        painelForm.add(new JLabel("SENHA"), "wrap");
        txtSenha = new JTextField(25);
        painelForm.add(txtSenha, "growx, wrap");

        painelForm.add(new JLabel("CONFIRMAR SENHA"), "wrap");
        txtConfirma = new JTextField(25);
        painelForm.add(txtConfirma, "growx, wrap");

        checkBoxAdmin = new JCheckBox("Administrador?");
        checkBoxAdmin.setBackground(new Color(0, 0, 0, 0));
        painelForm.add(checkBoxAdmin, "wrap");

        // Botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setOpaque(false);
        painelBotoes.setLayout(new MigLayout("", "[100px][100px]", "[]"));
        contentPane.add(painelBotoes, "cell 0 2");

        JButton btnCadastrar = new JButton("CADASTRAR");
        btnCadastrar.setBackground(new Color(32, 60, 115));
        btnCadastrar.setForeground(Color.WHITE);
        painelBotoes.add(btnCadastrar, "cell 0 0");

        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBackground(Color.RED);
        btnCancelar.setForeground(Color.WHITE);
        painelBotoes.add(btnCancelar, "cell 1 0");

        btnCancelar.addActionListener(e -> dispose());

        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String cpf = txtCpf.getText().replaceAll("[^0-9]", "");
            String senha = txtSenha.getText().trim();
            String confirmar = txtConfirma.getText().trim();

            if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty() || confirmar.isEmpty()) {
                new TelaErro("Preencha todos os campos!", 0).setVisible(true);
                return;
            }

            if (!nome.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                new TelaErro("Nome inválido!", 0).setVisible(true);
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                new TelaErro("E-mail inválido!", 0).setVisible(true);
                return;
            }

            if (cpf.length() != 11) {
                new TelaErro("CPF inválido!", 0).setVisible(true);
                return;
            }

            if (!senha.equals(confirmar)) {
                new TelaErro("As senhas não coincidem!", 0).setVisible(true);
                return;
            }

            if (senha.length() < 4) {
                new TelaErro("A senha deve ter no mínimo 4 caracteres!", 0).setVisible(true);
                return;
            }

            FuncionarioDAO dao = new FuncionarioDAO();
            ArrayList<Funcionario> lista = dao.selecionarFuncionarios();
            for (Funcionario f : lista) {
                if (f.getEmail().equalsIgnoreCase(email)) {
                    new TelaErro("Este e-mail já está cadastrado!", 0).setVisible(true);
                    return;
                }
            }

            Funcionario novo = new Funcionario();
            novo.setNome(nome);
            novo.setEmail(email);
            novo.setCpf(cpf);
            novo.setSenha(senha);
            novo.setPerfil(checkBoxAdmin.isSelected() ? "Admin" : "Comum");
            dao.cadastrarFuncionario(novo);

            new TelaErro("Funcionário cadastrado com sucesso!", 3).setVisible(true);
            dispose();
            try {
                TelaFuncionarios tela = new TelaFuncionarios(prod, func, mensagem);
                tela.setVisible(true);
                tela.setSize(1215, 850);
                tela.setLocationRelativeTo(null);
            } catch (SQLException ex) {
                ex.printStackTrace();
                new TelaErro("Erro ao retornar!", 0).setVisible(true);
            }
        });
    }
}