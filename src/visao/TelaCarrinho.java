package visao;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import modelo.Carrinho;
import modelo.ItemVenda;
import modelo.Produto;
import modelo.Funcionario;
import net.miginfocom.swing.MigLayout;

public class TelaCarrinho extends JFrame {

    private JPanel contentPane;
    private Carrinho carrinho;
    private ArrayList<ItemVenda> listaItens;
    private Funcionario funcionario;
    private Produto produto;
    private String mensagem;

    public TelaCarrinho(Carrinho c, Funcionario funcionario) {
        this.carrinho = c;
        this.listaItens = new ArrayList<>(c.getItens());
        this.funcionario = funcionario;

        setTitle("Carrinho de Compras");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1215, 850);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(32, 60, 115));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        criarCabecalho();
        criarPainelItens();
        criarRodape();
    }

    private void criarCabecalho() {
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTopo.setBackground(new Color(32, 60, 115));

        JLabel lblSeta = new JLabel();
        lblSeta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        try {
            ImageIcon seta = new ImageIcon(getClass().getResource("/img/de-volta.png"));
            Image voltar = seta.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            lblSeta.setIcon(new ImageIcon(voltar));
        } catch (Exception e) {
            lblSeta.setText("Voltar");
        }

        lblSeta.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new TelaVendas(produto, funcionario, mensagem).setVisible(true);
            }
        });

        JLabel lblTitulo = new JLabel("CARRINHO");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        painelTopo.add(lblSeta);
        painelTopo.add(lblTitulo);
        contentPane.add(painelTopo, BorderLayout.NORTH);
    }

    private void criarPainelItens() {
        JPanel painelCentro = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        painelCentro.add(scrollPane, BorderLayout.CENTER);

        JPanel painelItens = new JPanel();
        painelItens.setBackground(Color.WHITE);
        painelItens.setLayout(new MigLayout("wrap 3", "[grow]", "[]"));
        scrollPane.setViewportView(painelItens);

        for (ItemVenda item : listaItens) {
            JPanel cardItem = new JPanel();
            cardItem.setPreferredSize(new Dimension(350, 400));
            cardItem.setBackground(Color.WHITE);
            cardItem.setLayout(new BorderLayout());
            cardItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            // Imagem do produto com tratamento
            try {
                Produto produto = item.getFoto();
                Image image;

                if (produto.getFoto() != null && !produto.getFoto().isEmpty()) {
                    java.net.URL caminho = getClass().getResource("/" + produto.getFoto());
                    if (caminho != null) {
                        image = new ImageIcon(caminho).getImage().getScaledInstance(350, 300, Image.SCALE_SMOOTH);
                    } else {
                        image = new ImageIcon(getClass().getResource("/img/sem-foto.png"))
                                    .getImage().getScaledInstance(350, 300, Image.SCALE_SMOOTH);
                    }
                } else {
                    image = new ImageIcon(getClass().getResource("/img/sem-foto.png"))
                                .getImage().getScaledInstance(350, 300, Image.SCALE_SMOOTH);
                }

                JLabel lblFoto = new JLabel(new ImageIcon(image));
                cardItem.add(lblFoto, BorderLayout.CENTER);

            } catch (Exception ex) {
                ex.printStackTrace();
                JLabel lblErro = new JLabel("Imagem indisponível", SwingConstants.CENTER);
                lblErro.setPreferredSize(new Dimension(350, 300));
                cardItem.add(lblErro, BorderLayout.CENTER);
            }

            // Detalhes do produto
            JPanel painelDetalhes = new JPanel();
            painelDetalhes.setLayout(new BoxLayout(painelDetalhes, BoxLayout.Y_AXIS));
            painelDetalhes.setBackground(Color.WHITE);
            painelDetalhes.setBorder(new EmptyBorder(5, 10, 5, 10));

            JLabel lblNome = new JLabel("Produto: " + item.getNome());
            JLabel lblQuantidade = new JLabel("Quantidade: " + item.getQuantidade());
            painelDetalhes.add(lblNome);
            painelDetalhes.add(lblQuantidade);

            cardItem.add(painelDetalhes, BorderLayout.SOUTH);
            painelItens.add(cardItem);
        }

        contentPane.add(painelCentro, BorderLayout.CENTER);
    }

    private void criarRodape() {
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelRodape.setBackground(new Color(32, 60, 115));

        JButton btnFinalizar = new JButton("Finalizar Compra");
        btnFinalizar.setPreferredSize(new Dimension(150, 30));
        btnFinalizar.addActionListener(e -> {
            try {
                new TelaPagamento(carrinho, funcionario).setVisible(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            dispose();
        });

        painelRodape.add(btnFinalizar);
        contentPane.add(painelRodape, BorderLayout.SOUTH);
    }
}