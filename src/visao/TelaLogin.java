package visao;

import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import controle.FuncionarioDAO;
import modelo.Funcionario;
import modelo.Produto;

public class TelaLogin extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtEmail;
    private JPasswordField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	Produto prod = new Produto();
                Funcionario funcionario = new Funcionario();
                String mensagem = "Bem-vindo ao sistema!";
                TelaLogin frame = new TelaLogin(prod,mensagem, funcionario);
                frame.setVisible(true);
                frame.setSize(1200, 750);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaLogin(Produto prod,String mensagem, Funcionario func) {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(1, 2));
        setContentPane(contentPane);

       // ImagePanel panelDireito = new ImagePanel("src/img/background1D.png");
        //ImagePanel panelEsquerdo = new ImagePanel("src/img/background1E.png");
       ImagePanel panelDireito = new ImagePanel(getClass().getResource("/img/background1D.png"));
       ImagePanel panelEsquerdo = new ImagePanel(getClass().getResource("/img/background1E.png"));


        contentPane.add(panelEsquerdo);
        contentPane.add(panelDireito);

        panelDireito.setBorder(new EmptyBorder(5, 5, 5, 5));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        panelDireito.setLayout(new MigLayout("", "[grow][grow][grow 50,fill][grow]", "[bottom][][][][][][][][][][][][::100px][::100px][][][][40px][][70px][][][][][][][][][70px][][][][][][][][][][][][][70px][]"));

        JLabel lblBemVindo = new JLabel("Bem vindo");
        lblBemVindo.setForeground(Color.WHITE);
        lblBemVindo.setFont(new Font("Tahoma", Font.BOLD, 50));
        panelDireito.add(lblBemVindo, "cell 1 13,alignx left");

        JLabel lblNovamente = new JLabel("novamente!");
        lblNovamente.setForeground(Color.WHITE);
        lblNovamente.setFont(new Font("Tahoma", Font.BOLD, 50));
        panelDireito.add(lblNovamente, "cell 1 14,alignx left");

        JLabel lblCpf = new JLabel("Email:");
        lblCpf.setForeground(Color.WHITE);
        lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 25));
        panelDireito.add(lblCpf, "cell 1 18,alignx left");

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 30));
        panelDireito.add(txtEmail, "cell 1 19 2 1,grow");
        txtEmail.setColumns(10);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setForeground(Color.WHITE);
        lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 25));
        panelDireito.add(lblSenha, "cell 1 27,alignx left");

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 30));
        panelDireito.add(passwordField, "cell 1 28 2 1,grow");

        JCheckBox chckbxMostrarSenha = new JCheckBox("Mostrar senha");
        chckbxMostrarSenha.setForeground(Color.WHITE);
        chckbxMostrarSenha.setFont(new Font("Tahoma", Font.PLAIN, 20));
        chckbxMostrarSenha.setBackground(new Color(32, 60, 115));
        panelDireito.add(chckbxMostrarSenha, "cell 1 29,alignx left");

        chckbxMostrarSenha.addActionListener(e -> {
            if (chckbxMostrarSenha.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText();
            String senha = new String(passwordField.getPassword());

            if (email.isEmpty() || senha.isEmpty()) {
                TelaErro telaErro = new TelaErro("Preencha todos os campos!", 2);
                return;
            }

            if (!isValidEmail(email)) {
                TelaErro telaErro = new TelaErro("Email inválido!", 2);
                return;
            }

            Funcionario login = new Funcionario();
            login.setEmail(email);
            login.setSenha(senha);

            FuncionarioDAO dao = new FuncionarioDAO();
            Funcionario fAutenticado = dao.logarFuncionario(login);

            if (fAutenticado != null) {
            	dispose();
                TelaMenu tela = new TelaMenu(prod,fAutenticado, mensagem);
                tela.setVisible(true);
            } else {
                TelaErro telaErro = new TelaErro("Usuário ou senha inválidos!", 2);
            }
        });
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 30));
        btnLogin.setBackground(new Color(103, 203, 239));
        panelDireito.add(btnLogin, "cell 1 41 2 1,alignx right,grow");

        setLocationRelativeTo(null);
    }

  

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
}