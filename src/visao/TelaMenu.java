package visao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.sql.SQLException;

import net.miginfocom.swing.MigLayout;

import modelo.Carrinho;
import modelo.Funcionario;
import modelo.Produto;

public class TelaMenu extends JFrame {
    public JButton btnFuncionarios;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Produto prod = new Produto();
                Funcionario funcionario = new Funcionario();
                String mensagem = "Bem-vindo ao sistema!";
                TelaMenu frame = new TelaMenu(prod, funcionario, mensagem);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaMenu(Produto prod, Funcionario func, String mensagem) {
        setTitle("MENU");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1215, 850);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(230, 230, 230));

        JPanel topPanel = new JPanel(new MigLayout("", "[87px][][::1000px,grow][160px]", "[][][][][][][][]"));
        topPanel.setBackground(new Color(33, 64, 154));
        topPanel.setPreferredSize(new Dimension(600, 100));

        JLabel lblIconeUser = new JLabel();
        lblIconeUser.setIcon(loadImage("/img/user.png"));
        topPanel.add(lblIconeUser, "cell 0 0 1 6,alignx left,aligny top");

        JLabel msg1 = new JLabel("Bem vindo!", SwingConstants.CENTER);
        msg1.setForeground(Color.WHITE);
        msg1.setFont(new Font("Arial", Font.BOLD, 30));
        topPanel.add(msg1, "cell 1 0");

        JLabel lblSeta = new JLabel();
        lblSeta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSeta.setIcon(resizeImage("/img/icone_logout.png", 70, 70));
        topPanel.add(lblSeta, "cell 3 0 1 2,alignx right");
        lblSeta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TelaLogin tela = new TelaLogin(prod, mensagem, func);
                dispose();
                tela.setVisible(true);
                tela.setSize(1500, 1000);
                tela.setResizable(false);
            }
        });

        JLabel lblNome = new JLabel(func.getNome());
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNome.setForeground(Color.WHITE);
        topPanel.add(lblNome, "cell 1 1,alignx left");

        JPanel buttonPanel = new JPanel(new MigLayout("", "[30px][173px][173px][173px][173px][173px][173px]", "[100px,grow][100px,grow]"));
        buttonPanel.setBackground(new Color(230, 230, 230));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //JButton btnProdutos = new ImageButton(loadImage("/img/icone_produtos.png"));
        JButton btnProdutos = new ImageButton(getClass().getResource("/img/icone_produtos.png"));
        btnProdutos.addActionListener(e -> {
            dispose();
            TelaProdutos telaProdutos = new TelaProdutos(prod, func, mensagem);
            telaProdutos.setVisible(true);
            telaProdutos.setSize(1215, 850);
            telaProdutos.setLocationRelativeTo(null);
        });

        //JButton btnFornecedores = new ImageButton(loadImage("/img/icone_fornecedores.png"));
        JButton btnFornecedores = new ImageButton(getClass().getResource("/img/icone_fornecedores.png"));

        btnFornecedores.addActionListener(e -> {
            dispose();
            TelaFornecedores telaFornecedores = new TelaFornecedores(prod, func, mensagem);
            telaFornecedores.setVisible(true);
        });

        //JButton btnClientes = new ImageButton(loadImage("/img/icone_clientes.png"));
        JButton btnClientes = new ImageButton(getClass().getResource("/img/icone_clientes.png"));

        btnClientes.addActionListener(e -> {
            dispose();
            TelaClientes telaClientes = new TelaClientes(prod, func, mensagem);
            telaClientes.setVisible(true);
            telaClientes.setSize(800, 600);
        });

        //JButton btnHistorico = new ImageButton(loadImage("/img/icone_historico.png"));
        JButton btnHistorico = new ImageButton(getClass().getResource("/img/icone_historico.png"));

        btnHistorico.addActionListener(e -> {
            dispose();
            TelaHistoricoVendas telaHistoricoVendas = new TelaHistoricoVendas(func, mensagem, prod);
            telaHistoricoVendas.setVisible(true);
        });

      //  JButton btnVendas = new ImageButton(loadImage("/img/icone_vendas.png"));
        JButton btnVendas = new ImageButton(getClass().getResource("/img/icone_vendas.png"));

        btnVendas.addActionListener(e -> {
            dispose();
            TelaVendas telaVendas = new TelaVendas(prod, func, mensagem);
            telaVendas.setVisible(true);
        });

       // btnFuncionarios = new ImageButton(loadImage("/img/icone_funcionarios.png"));
        JButton btnFuncionarios = new ImageButton(getClass().getResource("/img/icone_funcionarios.png"));

        btnFuncionarios.addActionListener(e -> {
            if ("Admin".equals(func.getPerfil())) {
                dispose();
                try {
                    TelaFuncionarios telaFuncionarios = new TelaFuncionarios(prod, func, mensagem);
                    telaFuncionarios.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                new TelaErro("Seu perfil não tem permissão para acessar a tela de funcionários.", 0);
                dispose();
                TelaMenu telaMenu = new TelaMenu(prod, func, mensagem);
                telaMenu.setVisible(true);
                telaMenu.setSize(1215, 850);
                telaMenu.setLocationRelativeTo(null);
            }
        });

        // Adicionando botões no grid
        buttonPanel.add(btnProdutos, "cell 1 0 2 1,grow");
        buttonPanel.add(btnFornecedores, "cell 3 0 2 1,grow");
        buttonPanel.add(btnHistorico, "cell 5 0 2 1,grow");

        buttonPanel.add(btnClientes, "cell 1 1 2 1,grow");
        buttonPanel.add(btnFuncionarios, "cell 3 1 2 1,grow");
        buttonPanel.add(btnVendas, "cell 5 1 2 1,grow");

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
    }

    // Carrega imagem do classpath
    private ImageIcon loadImage(String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Imagem não encontrada: " + path);
            return new ImageIcon(); // ou um ícone padrão
        }
    }

    // Redimensiona ícone ao carregar
    private ImageIcon resizeImage(String path, int width, int height) {
        ImageIcon icon = loadImage(path);
        if (icon.getImage() != null) {
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return icon;
    }
}