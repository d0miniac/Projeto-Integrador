package visao;

import java.awt.*;
import java.awt.event.*;
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

        contentPane_1 = new JPanel();
        contentPane_1.setLayout(new BorderLayout());
        setContentPane(contentPane_1);

        criarPainelSuperior();
        criarPainelProdutos(listaProdutos);
    }

    private void criarPainelSuperior() {
        panelVazio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelVazio.setPreferredSize(new Dimension(getWidth(), 100));
        JButton btnFinalizar = new JButton("Finalizar Compra");
        btnFinalizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaPagamento(funcionario).setVisible(true);
                dispose(); // Fecha a tela de vendas
            }
        });
        panelVazio.add(btnFinalizar);


        // Botão de voltar
        JLabel lblSeta = new JLabel();
        lblSeta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ImageIcon seta = new ImageIcon("src/img/de-volta.png");
        Image voltar = seta.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        lblSeta.setIcon(new ImageIcon(voltar));
        lblSeta.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new TelaMenu(produto, funcionario, mensagem).setVisible(true);
            }
        });

        // ComboBox de categorias
        comboCategorias = new JComboBox<>();
        comboCategorias.addItem("Todas categorias");
        for (Categoria categoria : Categoria.values()) {
            comboCategorias.addItem(categoria.getDescricao());
        }

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aplicarFiltro();
            }
        });

        panelVazio.add(lblSeta);
        panelVazio.add(new JLabel("Categoria:"));
        panelVazio.add(comboCategorias);
        panelVazio.add(btnFiltrar);

        contentPane_1.add(panelVazio, BorderLayout.NORTH);
    }

    private void criarPainelProdutos(List<Produto> produtos) {
        JPanel panelProdutosComScroll = new JPanel(new BorderLayout());
        panelProdutosComScroll.setPreferredSize(new Dimension(getWidth(), getHeight() - 100));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(new LineBorder(Color.GRAY, 1));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panelProdutosComScroll.add(scrollPane, BorderLayout.CENTER);

        panelProdutos = new JPanel();
        panelProdutos.setBackground(Color.WHITE);
        panelProdutos.setLayout(new MigLayout("wrap 3", "[grow]", "[]"));
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

            ImageIcon icon = new ImageIcon(prod.getFoto());
            Image image = icon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
            JLabel lblImage = new JLabel(new ImageIcon(image));
            btnProduto.add(lblImage, BorderLayout.CENTER);

            JPanel panelDetalhes = new JPanel();
            panelDetalhes.setLayout(new BoxLayout(panelDetalhes, BoxLayout.Y_AXIS));
            panelDetalhes.setBackground(Color.WHITE);
           // panelDetalhes.add(new JLabel("Produto: " + prod.getNome()));
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

        if (categoriaSelecionada.equals("Todas categorias")) {
            filtrados = listaProdutos; // Mostra todos os produtos
        } else {
            filtrados = listaProdutos.stream()
                .filter(p -> p.getCategoria() != null && p.getCategoria().getDescricao().equalsIgnoreCase(categoriaSelecionada))
                .collect(Collectors.toList());
        }

        exibirProdutos(filtrados);
    }
    private void adicionarAoCarrinho(Produto produto) {
        Carrinho carrinho = Carrinho.getInstancia();
        ItemVenda itemExistente = carrinho.getItens().stream()
            .filter(item -> item.getIdProduto() == produto.getId())
            .findFirst()
            .orElse(null);

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