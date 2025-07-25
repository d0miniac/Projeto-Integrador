package visao;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import controle.ProdutoDAO;
import modelo.*;

import net.miginfocom.swing.MigLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TelaVendas extends JFrame {

    private JPanel contentPane_1, panelVazio, panelProdutos;
    private ArrayList<Produto> listaProdutos;
    private ProdutoDAO produtoDAO;
    private JComboBox<String> comboCategorias;
    private Funcionario funcionario;
    private Produto produto;
    private String mensagem;

    public TelaVendas(Produto prod, Funcionario func, String msg) {
        this.produto = prod;
        this.funcionario = func;
        this.mensagem = msg;

        Carrinho carrinho = Carrinho.getInstancia();
        produtoDAO = new ProdutoDAO();
        listaProdutos = produtoDAO.selecionarProdutos();
        carrinho.getItens();

        setTitle("Carrinho de Produtos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1215, 850);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPane_1 = new JPanel(new BorderLayout());
        setContentPane(contentPane_1);

        criarPainelSuperior();
        criarPainelProdutos(listaProdutos);
    }

    private void criarPainelSuperior() {
        panelVazio = new JPanel(new BorderLayout());
        panelVazio.setPreferredSize(new Dimension(1215, 100));
        panelVazio.setBackground(new Color(32, 60, 115));

        JPanel painelEsquerda = new JPanel(new MigLayout("insets 15, align left", "[]10[]", "[]"));
        painelEsquerda.setOpaque(false);

        JLabel lblSeta = new JLabel();
        lblSeta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        try {
            ImageIcon seta = new ImageIcon(getClass().getResource("/img/de-volta.png"));
            Image voltar = seta.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            lblSeta.setIcon(new ImageIcon(voltar));
        } catch (Exception e) {
            lblSeta.setText("<");
            lblSeta.setForeground(Color.WHITE);
            lblSeta.setFont(new Font("Arial", Font.BOLD, 40));
        }

        lblSeta.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new TelaMenu(produto, funcionario, mensagem).setVisible(true);
            }
        });

        JLabel lblTitulo = new JLabel("VENDAS");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 40));
        lblTitulo.setForeground(Color.WHITE);

        painelEsquerda.add(lblSeta);
        painelEsquerda.add(lblTitulo);

        JPanel painelDireita = new JPanel(new MigLayout("insets 10, fillx", "[][]10[]10[]10[]10[]push[]", "[]"));
        painelDireita.setOpaque(false);

        comboCategorias = new JComboBox<>();
        comboCategorias.addItem("Todas categorias");
        for (Categoria categoria : Categoria.values()) {
            comboCategorias.addItem(categoria.getDescricao());
        }

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setPreferredSize(new Dimension(120, 30));
        btnFiltrar.addActionListener(e -> aplicarFiltro());

        JLabel lblLogo = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/img/logo.png"));
            Image logoImage = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(logoImage));
        } catch (Exception e) {
            lblLogo.setText("Logo");
            lblLogo.setForeground(Color.WHITE);
            lblLogo.setFont(new Font("Arial", Font.BOLD, 20));
        }

        JLabel label = new JLabel("Categoria:");
        label.setFont(new Font("Tahoma", Font.BOLD, 15));
        label.setForeground(Color.WHITE);

        painelDireita.add(label, "cell 0 0");
        painelDireita.add(comboCategorias, "cell 2 0");
        painelDireita.add(btnFiltrar, "cell 3 0");

        JButton btnVerCarrinho = new JButton("Ver Carrinho");
        btnVerCarrinho.setPreferredSize(new Dimension(120, 30));
        btnVerCarrinho.addActionListener(e -> new TelaCarrinho(Carrinho.getInstancia(), funcionario).setVisible(true));
        painelDireita.add(btnVerCarrinho, "cell 4 0");
        painelDireita.add(lblLogo, "cell 6 0");

        panelVazio.add(painelEsquerda, BorderLayout.WEST);
        panelVazio.add(painelDireita, BorderLayout.EAST);

        contentPane_1.add(panelVazio, BorderLayout.NORTH);
    }

    private void criarPainelProdutos(List<Produto> produtos) {
        JPanel panelProdutosComScroll = new JPanel(new BorderLayout());
        panelProdutosComScroll.setPreferredSize(new Dimension(1215, 750));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(new LineBorder(Color.GRAY, 1));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panelProdutosComScroll.add(scrollPane, BorderLayout.CENTER);

        panelProdutos = new JPanel();
        panelProdutos.setBackground(Color.WHITE);
        panelProdutos.setLayout(new MigLayout("wrap 3", "[grow,fill]", "[]"));
        scrollPane.setViewportView(panelProdutos);

        contentPane_1.add(panelProdutosComScroll, BorderLayout.CENTER);
        exibirProdutos(produtos);
    }

    private void exibirProdutos(List<Produto> produtos) {
        panelProdutos.removeAll();

        for (Produto prod : produtos) {
            JButton btnProduto = new JButton();
            btnProduto.setPreferredSize(new Dimension(350, 400));
            btnProduto.setLayout(new BorderLayout());
            btnProduto.setBackground(Color.WHITE);

            JLabel lblImage;
            try {
                String nomeImagem = prod.getFoto();
                File imagemArquivo = new File("src/img/" + nomeImagem);

                if (nomeImagem == null || nomeImagem.isBlank() || !imagemArquivo.exists()) {
                    imagemArquivo = new File("src/img/imagem_padrao.png");
                }

                BufferedImage imagem = ImageIO.read(imagemArquivo);
                Image imagemRedimensionada = imagem.getScaledInstance(350, 350, Image.SCALE_SMOOTH);
                lblImage = new JLabel(new ImageIcon(imagemRedimensionada));
            } catch (IOException e) {
                e.printStackTrace();
                lblImage = new JLabel("Imagem não encontrada");
                lblImage.setHorizontalAlignment(SwingConstants.CENTER);
            }

            btnProduto.add(lblImage, BorderLayout.CENTER);

            JPanel panelDetalhes = new JPanel();
            panelDetalhes.setLayout(new BoxLayout(panelDetalhes, BoxLayout.Y_AXIS));
            panelDetalhes.setBackground(Color.WHITE);
            panelDetalhes.add(new JLabel("Preço: R$ " + prod.getPreco()));
            panelDetalhes.add(new JLabel("Categoria: " + prod.getCategoria().getDescricao()));

            btnProduto.add(panelDetalhes, BorderLayout.SOUTH);
            btnProduto.addActionListener(e -> adicionarAoCarrinho(prod));
            panelProdutos.add(btnProduto);
        }

        panelProdutos.revalidate();
        panelProdutos.repaint();
    }

    private void aplicarFiltro() {
        String categoriaSelecionada = (String) comboCategorias.getSelectedItem();
        List<Produto> filtrados;

        if ("Todas categorias".equals(categoriaSelecionada)) {
            filtrados = listaProdutos;
        } else {
            filtrados = listaProdutos.stream()
                    .filter(p -> p.getCategoria() != null &&
                            p.getCategoria().getDescricao().equalsIgnoreCase(categoriaSelecionada))
                    .collect(Collectors.toList());
        }

        exibirProdutos(filtrados);
    }

    private void adicionarAoCarrinho(Produto produto) {
        Carrinho carrinho = Carrinho.getInstancia();
        ItemVenda itemExistente = carrinho.getItens().stream()
                .filter(item -> item.getIdProduto() == produto.getId())
                .findFirst().orElse(null);

        if (itemExistente != null) {
            itemExistente.setQuantidade(itemExistente.getQuantidade() + 1);
        } else {
            ItemVenda novoItem = new ItemVenda();
            novoItem.setFoto(produto);
            novoItem.setQuantidade(1);
            carrinho.adicionarItem(novoItem);
        }

        JOptionPane.showMessageDialog(this, "Produto adicionado ao carrinho!");
    }
}