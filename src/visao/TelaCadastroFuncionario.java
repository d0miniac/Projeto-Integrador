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

public class TelaCadastroFuncionario extends JFrame {

    private JTextField txtNome, txtEmail, txtSenha, txtConfirma, txtID;
    private JFormattedTextField txtCpf;
    private JCheckBox checkBoxAdmin;

    public TelaCadastroFuncionario(Produto prod, String mensagem, Funcionario func) {
        setTitle("Cadastro de Funcionários");
        setSize(657, 425);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel de fundo
        ImagePanel painelFundo = new ImagePanel(getClass().getResource("/img/bgCadastroFornecedores.png"));
        painelFundo.setLayout(new BorderLayout());
        setContentPane(painelFundo);

        // Barra superior
        JPanel barraTopo = new JPanel(new BorderLayout());
        barraTopo.setPreferredSize(new Dimension(0, 100));
        barraTopo.setOpaque(false);
        painelFundo.add(barraTopo, BorderLayout.NORTH);

        JLabel bgBarra = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/barraParteDeCima.png"))
                .getImage().getScaledInstance(657, 100, Image.SCALE_SMOOTH)
        ));
        bgBarra.setLayout(new BorderLayout());
        barraTopo.add(bgBarra, BorderLayout.CENTER);

        JPanel esquerda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        esquerda.setOpaque(false);
        JLabel lblVoltar = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/de-volta.png"))
                .getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)
        ));
        lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        esquerda.add(lblVoltar);
        bgBarra.add(esquerda, BorderLayout.WEST);

        lblVoltar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    dispose();
                    new TelaFuncionarios(prod, func, mensagem).setVisible(true);
                } catch (SQLException ex) {
                    new TelaErro("Erro ao retornar!", 0).setVisible(true);
                }
            }
        });

        JLabel lblTitulo = new JLabel("Cadastro de Funcionário");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        bgBarra.add(lblTitulo, BorderLayout.CENTER);

        JPanel direita = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        direita.setOpaque(false);
        JLabel lblLogo = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/armariodigital.png"))
                .getImage().getScaledInstance(150, 80, Image.SCALE_SMOOTH)
        ));
        direita.add(lblLogo);
        bgBarra.add(direita, BorderLayout.EAST);

        // Painel central com MigLayout
        JPanel painelCentral = new JPanel(new MigLayout("", "[grow]", "[70px][][grow][][100px][150px][150px][100px]"));
        painelCentral.setOpaque(false);
        painelFundo.add(painelCentral, BorderLayout.CENTER);

        JPanel vazio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        vazio.setOpaque(false);
        painelCentral.add(vazio, "cell 0 0,grow");

        JLabel lblSubtitulo = new JLabel("Informações do funcionário");
        lblSubtitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblSubtitulo.setForeground(new Color(153, 162, 209));
        painelCentral.add(lblSubtitulo, "cell 0 3");

        // Painel topo: ID, Nome, Email
        JPanel topo = new JPanel(new MigLayout("", "[grow][grow][grow]", "[][]"));
        topo.setOpaque(false);
        topo.setBorder(new MatteBorder(0, 0, 5, 0, new Color(32, 60, 115, 124)));
        painelCentral.add(topo, "cell 0 5,grow");

        topo.add(new JLabel("ID"), "flowx,cell 0 0");
        txtID = new JTextField(10);
        txtID.setEnabled(false);
        topo.add(txtID, "cell 0 0,growx");

        topo.add(new JLabel("NOME"), "flowx,cell 1 0");
        txtNome = new JTextField(10);
        topo.add(txtNome, "cell 1 0,growx");

        topo.add(new JLabel("EMAIL"), "flowx,cell 2 0");
        txtEmail = new JTextField(10);
        topo.add(txtEmail, "cell 2 0,growx");

        // Painel meio: CPF, Senhas, CheckBox
        JPanel meio = new JPanel(new MigLayout("", "[grow][grow][grow][grow]", "[][]10[]"));
        meio.setOpaque(false);
        meio.setBorder(new MatteBorder(0, 0, 5, 0, new Color(32, 60, 115, 124)));
        painelCentral.add(meio, "cell 0 6,grow");

        meio.add(new JLabel("CPF"), "cell 0 0");
        try {
            txtCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
            meio.add(txtCpf, "cell 0 1,growx");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        meio.add(new JLabel("SENHA"), "cell 1 0");
        txtSenha = new JPasswordField(10);
        meio.add(txtSenha, "cell 1 1,growx");

        meio.add(new JLabel("CONFIRMAR SENHA"), "cell 2 0");
        txtConfirma = new JPasswordField(10);
        meio.add(txtConfirma, "cell 2 1,growx");

        // Checkbox linha nova
        checkBoxAdmin = new JCheckBox("Administrador?");
        checkBoxAdmin.setOpaque(false);
        checkBoxAdmin.setForeground(Color.BLACK);
        checkBoxAdmin.setFont(new Font("Tahoma", Font.BOLD, 14));
        checkBoxAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        meio.add(checkBoxAdmin, "cell 0 2 4 1, align left");

        // Painel inferior com botões
        JPanel inferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        inferior.setOpaque(false);
        painelCentral.add(inferior, "cell 0 7,grow");

        JButton btnCadastrar = new JButton("CADASTRAR");
        btnCadastrar.setBackground(new Color(32, 60, 115));
        btnCadastrar.setForeground(Color.WHITE);
        inferior.add(btnCadastrar);

        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBackground(new Color(255, 0, 0));
        btnCancelar.setForeground(Color.WHITE);
        inferior.add(btnCancelar);

        btnCancelar.addActionListener(e -> dispose());

        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String cpf = txtCpf.getText().replaceAll("[^0-9]", "");
            String senha = txtSenha.getText().trim();
            String confirmar = txtConfirma.getText().trim();

            if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty() || confirmar.isEmpty()) {
                new TelaErro("Preencha todos os campos obrigatórios!", 0).setVisible(true);
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
            for (Funcionario f : dao.selecionarFuncionarios()) {
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
                new TelaFuncionarios(prod, func, mensagem).setVisible(true);
            } catch (SQLException ex) {
                new TelaErro("Erro ao retornar!", 0).setVisible(true);
            }
        });
    }
}