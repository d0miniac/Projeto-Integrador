package visao;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import controle.ProdutoDAO;
import modelo.Carrinho;
import modelo.Funcionario;
import modelo.ItemVenda;
import modelo.Produto;
import modelo.ProdutoTableModel;
import net.miginfocom.swing.MigLayout;

public class TelaProdutos extends JFrame {

	private JPanel contentPane;
	private JTextField txtFiltro;
	private JTable tableProdutos;
	private ArrayList<Produto> listaProdutos;
	private ProdutoTableModel ptm;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Produto prod = new Produto();
				Funcionario funcionario = new Funcionario();
				String mensagem = "Bem-vindo ao sistema!";
				TelaProdutos frame = new TelaProdutos(prod, funcionario, mensagem);
				frame.setVisible(true);
				frame.setSize(1215, 850);
				frame.setLocationRelativeTo(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public TelaProdutos(Produto prod, Funcionario func, String mensagem) {
		listaProdutos = new ArrayList<>();
		ProdutoDAO p = new ProdutoDAO();
		listaProdutos = p.selecionarProdutos();

		setTitle("Produtos");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//contentPane = new ImagePanel("src/img/bgProdutos.png");
		contentPane = new ImagePanel(getClass().getResource("/img/bgProdutos.png"));

		contentPane.setBackground(new Color(243, 244, 240));
		setContentPane(contentPane);
		setSize(1215, 850);
		setLocationRelativeTo(null);
		setResizable(false);

		contentPane.setLayout(new MigLayout("", "[grow,fill]", "[120px][grow]"));

		JPanel panelVazio = new JPanel();
		panelVazio.setBackground(new Color(0, 0, 0));
		panelVazio.setOpaque(false);
		contentPane.add(panelVazio, "cell 0 0,grow");
		panelVazio.setLayout(null);

		JPanel panelComponentes = new JPanel();
		panelComponentes.setBackground(new Color(255, 0, 0));
		panelComponentes.setOpaque(false);
		contentPane.add(panelComponentes, "cell 0 1,grow");
		panelComponentes.setLayout(new MigLayout("", "[][][][][grow][]", "[][][grow]"));

		txtFiltro = new JTextField();
		txtFiltro.setUI(new HintTextFieldUI("Pesquise por categoria, marca, cor ou tamanho"));
		txtFiltro.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtFiltro.setBorder(new LineBorder(new Color(123, 150, 212), 2, true));
		panelComponentes.add(txtFiltro, "flowx,cell 4 0,alignx left");
		txtFiltro.setColumns(90);
		txtFiltro.setPreferredSize(new Dimension(450, 45));

		JButton btnAdd = new JButton("Cadastrar");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				TelaCadastroProdutos tela;
				try {
					tela = new TelaCadastroProdutos(prod, func, mensagem);
					tela.setVisible(true);
					tela.setSize(657, 425);
					tela.setLocationRelativeTo(null);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setBackground(new Color(243, 244, 240));
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnAdd.setMinimumSize(new Dimension(150, 30));
		btnAdd.setMaximumSize(new Dimension(150, 30));
		btnAdd.setBorder(new LineBorder(new Color(123, 150, 212), 2, true));
		panelComponentes.add(btnAdd, "flowx,cell 4 1,alignx left,aligny center");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(123, 150, 212), 2, true));
		panelComponentes.add(scrollPane, "cell 4 2,grow");

		JLabel lblSeta = new JLabel("");
		lblSeta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblSeta.setIcon(new ImageIcon(TelaCadastroProdutos.class.getResource("/img/de-volta.png")));
		lblSeta.setBounds(0, 0, 110, 100);
		ImageIcon seta = new ImageIcon(TelaCadastroProdutos.class.getResource("/img/de-volta.png"));
		Image voltar = seta.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		lblSeta.setIcon(new ImageIcon(voltar));
		panelVazio.add(lblSeta);
		lblSeta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaMenu tela = new TelaMenu(prod, func, mensagem);
				dispose();
				tela.setVisible(true);
			}
		});
		tableProdutos = new JTable();
		tableProdutos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableProdutos.setGridColor(new Color(0, 0, 0));
		tableProdutos.setBackground(new Color(123, 150, 212));
		tableProdutos.setForeground(new Color(255, 255, 255));

		ptm = new ProdutoTableModel(listaProdutos);
		tableProdutos.setModel(ptm);

		theader();

		scrollPane.setViewportView(tableProdutos);

		JButton btnUpdate = new JButton("Alterar");
		btnUpdate.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
        		try {
            		int i = tableProdutos.getSelectedRow();
           			 if (i != -1) {
              			 Produto produto = listaProdutos.get(i); // <- produto selecionado corretamente
                		 TelaEditarProdutos telaEditar = new TelaEditarProdutos(produto, func); // <- passa o produto correto
                		 dispose();
                    	 telaEditar.setVisible(true);
                		 telaEditar.setSize(657, 425);
                		 telaEditar.setLocationRelativeTo(null);
             		} else {
                		new TelaErro("Selecione um produto para alterar.", 0).setVisible(true);
           		    }
       			 } catch (Exception ex) {
           			 ex.printStackTrace();
            		 new TelaErro("Erro ao abrir a tela de edição do produto!", 0).setVisible(true);
        }
    }
});


		btnUpdate.setBackground(new Color(243, 244, 240));
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnUpdate.setMinimumSize(new Dimension(150, 30));
		btnUpdate.setMaximumSize(new Dimension(150, 30));
		btnUpdate.setBorder(new LineBorder(new Color(123, 150, 212), 2, true));
		panelComponentes.add(btnUpdate, "cell 4 1");

		JButton btnDelete = new JButton("Deletar");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaErro telaErro = new TelaErro("Deseja realmente excluir esse produto?");
				int resposta = telaErro.getResposta();

				if (resposta == 0) {
					int i = tableProdutos.getSelectedRow();
					if (i != -1) {
						Long id = (Long) tableProdutos.getModel().getValueAt(i, 0);

						try {
							p.excluirProdutos(id);
							listaProdutos = p.selecionarProdutos();
							ptm = new ProdutoTableModel(listaProdutos);
							tableProdutos.setModel(ptm);
							new TelaErro("Produto excluído com sucesso!", 3);

						} catch (SQLException e1) {
							e1.printStackTrace();
							new TelaErro("Erro ao excluir produto!", 0);
						}
					} else {
						new TelaErro("Selecione um produto para excluir!", 1);
					}
				}
			}
		});

		btnDelete.setBackground(new Color(243, 244, 240));
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnDelete.setMinimumSize(new Dimension(150, 30));
		btnDelete.setMaximumSize(new Dimension(150, 30));
		btnDelete.setBorder(new LineBorder(new Color(123, 150, 212), 2, true));
		panelComponentes.add(btnDelete, "cell 4 1");

		JButton btnPesquisa = new JButton("PESQUISAR");
		btnPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!txtFiltro.getText().trim().isEmpty()) {
					String filtro = txtFiltro.getText();
					listaProdutos = p.pesquisarProdutos(filtro);
					ptm = new ProdutoTableModel(listaProdutos);
					tableProdutos.setModel(ptm);
				} else {
					listaProdutos = p.selecionarProdutos();
					ptm = new ProdutoTableModel(listaProdutos);
					tableProdutos.setModel(ptm);
				}
			}
		});
		panelComponentes.add(btnPesquisa, "cell 4 0,alignx center,aligny center");
		btnPesquisa.setBackground(new Color(243, 244, 240));
		btnPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnPesquisa.setMinimumSize(new Dimension(150, 30));
		btnPesquisa.setMaximumSize(new Dimension(150, 30));
		btnPesquisa.setBorder(new LineBorder(new Color(123, 150, 212), 2, true));
	}

	
	public void MostrarProdutos(Funcionario func) {
	    for (Produto produto : listaProdutos) {
	        // Criar um botão para cada produto
	        JButton btnProduto = new JButton();
	        btnProduto.setPreferredSize(new Dimension(350, 400));
	        btnProduto.setMaximumSize(new Dimension(350, 400));
	        btnProduto.setMinimumSize(new Dimension(350, 400));
	        btnProduto.setLayout(new BorderLayout());

	        // Definir imagem do produto
	        ImageIcon imageIcon = new ImageIcon(produto.getFoto());
	        Image image = imageIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
	        JLabel lblImage = new JLabel(new ImageIcon(image));
	        btnProduto.add(lblImage, BorderLayout.CENTER);

	       

	        // Botão de adicionar ao carrinho
	        JButton btnAdicionarCarrinho = new JButton("Adicionar ao Carrinho");
	        btnAdicionarCarrinho.setFont(new Font("Tahoma", Font.PLAIN, 12));
	        btnProduto.add(btnAdicionarCarrinho, BorderLayout.SOUTH);

	        // Adicionar evento de clique para adicionar ao carrinho
	        btnAdicionarCarrinho.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Adicionar o produto ao carrinho
	                adicionarAoCarrinho(produto);
	            }
	        });

	        Container panelProdutos = null;
			panelProdutos.add(btnProduto);
	        btnProduto.setBackground(new Color(243, 244, 240));
	    }
	}

	private void adicionarAoCarrinho(Produto produto) {
	    Carrinho carrinho = Carrinho.getInstancia();

	    // Verifica se o produto já está no carrinho
	    ItemVenda itemExistente = null;
	    for (ItemVenda item : carrinho.getItens()) {
	        if (item.getIdProduto() == produto.getId()) {
	            itemExistente = item;
	            break;
	        }
	    }

	    // Se o produto já estiver no carrinho, apenas aumenta a quantidade
	    if (itemExistente != null) {
	        itemExistente.setQuantidade(itemExistente.getQuantidade() + 1); // Adiciona uma unidade
	    } else {
	        // Caso contrário, cria um novo ItemVenda e adiciona ao carrinho
	        ItemVenda novoItem = new ItemVenda();
	        novoItem.setFoto(produto);
	        novoItem.setQuantidade(1); // Inicializa com 1 unidade
	        carrinho.adicionarItem(novoItem);
	    }

	    Funcionario func = null;
		// Atualiza a exibição da tela de vendas
	    TelaVendas telaVendas = new TelaVendas(produto, func, "Carrinho atualizado!");
	    telaVendas.setVisible(true);
	    dispose();  // Fecha a tela de produtos
	}

	

	private void theader() {
		JTableHeader thead = tableProdutos.getTableHeader();
		thead.setForeground(new Color(123, 150, 212));
		thead.setBackground(new Color(255, 255, 255));
		thead.setFont(new Font("Tahoma", Font.PLAIN, 20));
	}
}

	