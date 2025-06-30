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
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        ImagePanel contentPane = new ImagePanel(getClass().getResource("/img/bgCadastroFornecedores.png"));
        contentPane.setLayout(new MigLayout("insets 0, gap 0", "[grow]", "[100px][grow][100px]"));
        setContentPane(contentPane);

        criarBarraTopo(contentPane);
        criarPainelItens(contentPane);
        criarRodape(contentPane);
    }

    private void criarBarraTopo(JPanel parent) {
        ImagePanel barraTopo = new ImagePanel(getClass().getResource("/img/barraParteDeCima.png")) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(parent.getWidth(), 100);
            }
        };
        barraTopo.setLayout(new BorderLayout());
        barraTopo.setOpaque(false);

        JPanel esquerda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        esquerda.setOpaque(false);

        JLabel lblVoltar = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/de-volta.png"))
                .getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)
        ));
        lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblVoltar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new TelaVendas(produto, funcionario, mensagem).setVisible(true);
            }
        });

        JLabel lblTitulo = new JLabel("Carrinho de Compras");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(new EmptyBorder(0, 15, 0, 0));

        esquerda.add(lblVoltar);
        esquerda.add(lblTitulo);
        barraTopo.add(esquerda, BorderLayout.WEST);

        JPanel direita = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        direita.setOpaque(false);
        JLabel lblLogo = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/armariodigital.png"))
                .getImage().getScaledInstance(150, 80, Image.SCALE_SMOOTH)
        ));
        direita.add(lblLogo);
        barraTopo.add(direita, BorderLayout.EAST);

        parent.add(barraTopo, "cell 0 0, growx, pushx, h 100!");
    }

    private void criarPainelItens(JPanel parent) {
        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel painelItens = new JPanel();
        painelItens.setOpaque(false);
        painelItens.setLayout(new MigLayout("wrap 3", "[fill][fill][fill]", "20[]20"));

        if (listaItens.isEmpty()) {
            JLabel lblVazio = new JLabel("O carrinho está vazio!");
            lblVazio.setFont(new Font("Tahoma", Font.BOLD, 18));
            lblVazio.setForeground(Color.BLACK);
            lblVazio.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel vazioPanel = new JPanel();
            vazioPanel.setOpaque(false);
            vazioPanel.setLayout(new BoxLayout(vazioPanel, BoxLayout.Y_AXIS));
            vazioPanel.add(Box.createVerticalGlue());
            vazioPanel.add(lblVazio);
            vazioPanel.add(Box.createVerticalGlue());

            scrollPane.setViewportView(vazioPanel);
        } else {
            for (ItemVenda item : listaItens) {
                JPanel cardItem = new JPanel();
                cardItem.setPreferredSize(new Dimension(300, 380));
                cardItem.setBackground(Color.WHITE);
                cardItem.setLayout(new BorderLayout());
                cardItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                try {
                    Produto produto = item.getFoto();
                    Image image;

                    if (produto.getFoto() != null && !produto.getFoto().isEmpty()) {
                        java.net.URL caminho = getClass().getResource("/" + produto.getFoto());
                        image = caminho != null
                            ? new ImageIcon(caminho).getImage().getScaledInstance(300, 240, Image.SCALE_SMOOTH)
                            : new ImageIcon(getClass().getResource("/img/imagem_padrao.png")).getImage().getScaledInstance(300, 240, Image.SCALE_SMOOTH);
                    } else {
                        image = new ImageIcon(getClass().getResource("/img/imagem_padrao.png"))
                            .getImage().getScaledInstance(300, 240, Image.SCALE_SMOOTH);
                    }

                    JLabel lblFoto = new JLabel(new ImageIcon(image));
                    cardItem.add(lblFoto, BorderLayout.CENTER);

                } catch (Exception ex) {
                    JLabel lblErro = new JLabel("Imagem indisponível", SwingConstants.CENTER);
                    lblErro.setPreferredSize(new Dimension(300, 240));
                    cardItem.add(lblErro, BorderLayout.CENTER);
                }

                JPanel painelDetalhes = new JPanel();
                painelDetalhes.setLayout(new BoxLayout(painelDetalhes, BoxLayout.Y_AXIS));
                painelDetalhes.setBackground(Color.WHITE);
                painelDetalhes.setBorder(new EmptyBorder(5, 10, 10, 10));

                JLabel lblNome = new JLabel("Produto: " + item.getNome());
                JLabel lblQuantidade = new JLabel("Quantidade: " + item.getQuantidade());
                painelDetalhes.add(lblNome);
                painelDetalhes.add(lblQuantidade);

                JButton btnRemover = new JButton("Remover");
                btnRemover.setBackground(new Color(220, 53, 69));
                btnRemover.setForeground(Color.WHITE);
                btnRemover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btnRemover.setAlignmentX(Component.LEFT_ALIGNMENT);

                btnRemover.addActionListener(e -> {
                    int opcao = JOptionPane.showConfirmDialog(this,
                            "Deseja remover este item do carrinho?",
                            "Confirmação",
                            JOptionPane.YES_NO_OPTION);

                    if (opcao == JOptionPane.YES_OPTION) {
                        listaItens.remove(item);
                        carrinho.setItens(listaItens);
                        dispose();
                        new TelaCarrinho(carrinho, funcionario).setVisible(true);
                    }
                });

                painelDetalhes.add(Box.createVerticalStrut(8));
                painelDetalhes.add(btnRemover);

                cardItem.add(painelDetalhes, BorderLayout.SOUTH);
                painelItens.add(cardItem, "gapbottom 20");
            }

            scrollPane.setViewportView(painelItens);
        }

        painelCentro.add(scrollPane, BorderLayout.CENTER);
        parent.add(painelCentro, "cell 0 1, grow");
    }

    private void criarRodape(JPanel parent) {
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 20));
        painelRodape.setOpaque(false);

        JButton btnFinalizar = new JButton("FINALIZAR COMPRA");
        btnFinalizar.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnFinalizar.setPreferredSize(new Dimension(220, 45));
        btnFinalizar.setBackground(new Color(32, 60, 115));
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnFinalizar.addActionListener(e -> {
            try {
                if (funcionario == null || funcionario.getId() == null) {
                    JOptionPane.showMessageDialog(this, "Erro: Funcionário inválido. Faça login novamente.");
                    dispose();
                    new TelaLogin(new Produto(), "Retorne ao login", new Funcionario()).setVisible(true);
                    return;
                }

                if (listaItens == null || listaItens.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O carrinho está vazio! Adicione produtos antes de finalizar a compra.", "Carrinho Vazio", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                new TelaPagamento(carrinho, funcionario).setVisible(true);
                dispose();

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        painelRodape.add(btnFinalizar);
        parent.add(painelRodape, "cell 0 2, align right, growx");
    }
}