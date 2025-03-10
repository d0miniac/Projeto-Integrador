package visao;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import controle.ProdutoDAO;
import controle.VendaDAO;
import modelo.Carrinho;
import modelo.Funcionario;
import modelo.ItemVenda;
import modelo.Produto;
import modelo.Venda;
import net.miginfocom.swing.MigLayout;

import java.util.ArrayList;

public class TelaVendas extends JFrame {

	private JPanel contentPane_1;
	private JPanel panelVazio;
	private JPanel panelProdutos;
	private ArrayList<Produto> listaProdutos;
	private ProdutoDAO produtoDAO;
	ArrayList<ItemVenda> listaItens;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				Produto prod = new Produto();
				Funcionario funcionario = new Funcionario();
				String mensagem = "Bem-vindo ao sistema!";
				TelaVendas frame = new TelaVendas(prod,funcionario, mensagem);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public TelaVendas(Produto prod,Funcionario func, String mensagem) {
		
		Carrinho carrinho = Carrinho.getInstancia();
		produtoDAO = new ProdutoDAO();
		listaProdutos = produtoDAO.selecionarProdutos();
		listaItens = new ArrayList<>();
		listaItens = carrinho.getItens();

		setTitle("Carrinho de Produtos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1215, 850);
		setLocationRelativeTo(null);
		setResizable(false);

		contentPane_1 = new ImagePanel("src/img/bgVendas.png");
		contentPane_1.setBackground(new Color(243, 244, 240));
		setContentPane(contentPane_1);

		contentPane_1.setLayout(new BoxLayout(contentPane_1, BoxLayout.Y_AXIS));

		panelVazio = new JPanel();
		panelVazio.setLayout(null);
		panelVazio.setOpaque(false);
		panelVazio.setBackground(Color.BLACK);

		Dimension dimVazio = new Dimension(getWidth(), (int) (getHeight() * 0.20));
		panelVazio.setPreferredSize(dimVazio);

		contentPane_1.add(panelVazio);
		
//		JButton btnNewButton = new JButton("Carrinho");
//		btnNewButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				Carrinho c;
//				TelaCarrinho carrinho = new TelaCarrinho(c);
//				carrinho.setVisible(true);
//				
//			}
//		});
//		btnNewButton.setBounds(1048, 36, 89, 23);
//		panelVazio.add(btnNewButton);

		JPanel panelProdutosComScroll = new JPanel();
		panelProdutosComScroll.setLayout(new BorderLayout());

		Dimension dimProdutos = new Dimension(getWidth(), (int) (getHeight() * 0.99));
		panelProdutosComScroll.setPreferredSize(dimProdutos);

		contentPane_1.add(panelProdutosComScroll);

		
		
		
		
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBorder(new LineBorder(new Color(123, 150, 212), 2, true));
			scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0)));
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panelProdutosComScroll.add(scrollPane, BorderLayout.CENTER);
			
			panelProdutos = new JPanel();
			scrollPane.setViewportView(panelProdutos);
			panelProdutos.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0)));
			panelProdutos.setBackground(new Color(243, 244, 240));
			panelProdutos.setLayout(new MigLayout("wrap 3", "[grow]", "[]"));
			
//			JPanel panel2 = new JPanel();
//			panelProdutosComScroll.add(panel2, BorderLayout.NORTH);
			if(listaItens.isEmpty()) {
				JLabel labelVazio = new JLabel("Carrinho Vazio, Adicione itens ao carrinho através da tela de produtos");
				labelVazio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		        labelVazio.setPreferredSize(new Dimension(350, 50));
		        labelVazio.setHorizontalAlignment(SwingConstants.CENTER);
		        labelVazio.setVerticalAlignment(SwingConstants.TOP);
		        panelProdutos.add(labelVazio,"grow,wrap");
				
			}
			else {
				MostrarProdutos(func);
				JButton btnConcluir = new JButton("Concluir");
				btnConcluir.setBackground(new Color(243, 244, 240));
				btnConcluir.setFont(new Font("Tahoma", Font.PLAIN, 24));
				btnConcluir.setMinimumSize(new Dimension(170, 30));
				btnConcluir.setMaximumSize(new Dimension(170, 30));
				btnConcluir.setBorder(new LineBorder(new Color(123, 150, 212), 2, true));
				JPanel panel = new JPanel();
				panelProdutosComScroll.add(panel, BorderLayout.NORTH);
				panel.add(btnConcluir);
				btnConcluir.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
					Float total=0f;
						for (ItemVenda itemVenda : listaItens) {
							total=total+(itemVenda.getPreco()*itemVenda.getQuantidade());
						}
						Venda venda = new Venda();
						venda.setTotal(total);
						venda.setIdFuncionario(func.getId());
						LocalDate dataAtual = LocalDate.now();
						LocalTime horaAtual = LocalTime.now();
						DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Apenas data
				        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
						String data = dataAtual.format(formatoData);
						String hora = horaAtual.format(formatoHora);
						venda.setData(data);
						venda.setHorario(hora);
						VendaDAO dao = new VendaDAO();
						TelaErro tela = new TelaErro("Concluir Venda?");
						
						if(tela.getResposta()==1) {
							dao.cadastrarVenda(venda);
							for (ItemVenda itemVenda : listaItens) {
								Long idV = itemVenda.getId();
								ProdutoDAO pDao = new ProdutoDAO();
								ArrayList<Produto> listaProd = pDao.selecionarProdutos();
								for (Produto produto : listaProd) {
									if(produto.getId()==idV) {
										
										int nQtde = produto.getQuantidade()-itemVenda.getQuantidade();
										produto.setQuantidade(nQtde);
										try {
											pDao.alterarProdutos(produto);
										} catch (SQLException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
								}
							}
							listaItens.clear();
							dispose();
							new TelaVendas(prod,func, mensagem).setVisible(true);
						}
						
						
						
					}
				});
			}
			
			
		
		
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
				TelaMenu tela = new TelaMenu(prod,func, mensagem);
				dispose();
				tela.setVisible(true);
				
			}
		});
		panelVazio.add(lblSeta);
		
	}
	
	public void MostrarProdutos(Funcionario func){
		for (ItemVenda item : listaItens) {
			JButton btnProduto = new JButton();
			
			Produto prod = new Produto();
			Funcionario funcionario = new Funcionario();
			String mensagem = "";
			
			  btnProduto.setPreferredSize(new Dimension(350, 400));
		        btnProduto.setMaximumSize(new Dimension(350, 400));
		        btnProduto.setMinimumSize(new Dimension(350, 400));
		        // Definindo layout para ocupar todo o botão com a imagem
		        btnProduto.setLayout(new BorderLayout());

		        // Carregando a imagem e ajustando para preencher todo o botão
		        ImageIcon imageIcon = new ImageIcon(item.getFoto());
		        Image image = imageIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
		        JLabel lblImage = new JLabel(new ImageIcon(image));
		        btnProduto.add(lblImage, BorderLayout.CENTER); // Imagem ocupa o centro do botão

		        // Definindo o título do produto abaixo da imagem
		        JLabel lblNome = new JLabel(item.getNome(), SwingConstants.CENTER);
		        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		        lblNome.setPreferredSize(new Dimension(350, 50)); // Espaço fixo para o título
		        btnProduto.add(lblNome, BorderLayout.SOUTH);

			btnProduto.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					TelaDetalhesProduto telaDetalhes = new TelaDetalhesProduto(item,func,TelaVendas.this, mensagem, prod);
					telaDetalhes.setVisible(true);
				}
			});

			
			
			panelProdutos.add(btnProduto);
			btnProduto.setBackground(new Color(243, 244, 240));
		}
	}
}
