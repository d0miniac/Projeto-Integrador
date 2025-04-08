
package visao;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import controle.FornecedorDAO;
import controle.ProdutoDAO;
import modelo.Categoria;
import modelo.Cor;
import modelo.Fornecedor;
import modelo.Funcionario;
import modelo.Marca;
import modelo.Produto;
import modelo.Tamanho;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaCadastroProdutos extends JFrame {

	private JPanel contentPane;
	private JTextField txtPreco;
	private JTextField txtQuantidade;
	JLabel lblimagem;
	Produto produto;
	ProdutoDAO dao;
	FornecedorDAO fdao;
	ArrayList<Fornecedor> listaFornecedores;
	private JTextField txtTitulo;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Produto prod = new Produto();
				Funcionario funcionario = new Funcionario();
				String mensagem = "Bem-vindo ao sistema!";
				TelaCadastroProdutos frame = new TelaCadastroProdutos(prod, funcionario, mensagem);
				frame.setVisible(true);
				frame.setSize(657, 425);
				frame.setLocationRelativeTo(null);
			} catch (Exception e) {

			}
		});
	}

	public TelaCadastroProdutos(Produto prod, Funcionario func, String mensagem) throws SQLException {
		produto = new Produto();
		setTitle("Cadastro de Produtos");
		contentPane = new ImagePanel("src/img/bgCadastroProdutos.png");
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[70px][100px][100px][200px]"));

		JPanel vazio = new JPanel();
		vazio.setBackground(new Color(255, 0, 0));
		contentPane.add(vazio, "cell 0 0,grow");
		vazio.setOpaque(false);
		vazio.setLayout(null);

		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_7.setIcon(new ImageIcon(TelaCadastroProdutos.class.getResource("/img/de-volta.png")));
		lblNewLabel_7.setBounds(0, 0, 40, 40);
		ImageIcon seta = new ImageIcon(TelaCadastroProdutos.class.getResource("/img/de-volta.png"));
		Image voltar = seta.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		lblNewLabel_7.setIcon(new ImageIcon(voltar));
		vazio.add(lblNewLabel_7);
		lblNewLabel_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaProdutos tela;
				tela = new TelaProdutos(prod, func, mensagem);
				dispose();
				tela.setSize(1215, 850);
				tela.setLocationRelativeTo(null);
				tela.setVisible(true);

			}
		});

		JPanel topo = new JPanel();
		topo.setBorder(new MatteBorder(0, 0, 5, 0, (Color) new Color(32, 60, 115, 124)));
		topo.setBackground(new Color(0, 128, 255));
		contentPane.add(topo, "cell 0 1,grow");
		topo.setOpaque(false);
		topo.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]", "[][]"));

		JLabel lblNewLabel_9 = new JLabel("FORNECEDOR");
		topo.add(lblNewLabel_9, "flowx,cell 0 0,alignx left");

		JLabel lblNewLabel_1 = new JLabel("PREÇO");
		topo.add(lblNewLabel_1, "flowx,cell 2 0,alignx center");

		try {
			// Configura um NumberFormat para moeda
			NumberFormat format = NumberFormat.getNumberInstance();
			format.setMaximumFractionDigits(2); // Até 2 casas decimais
			format.setMinimumFractionDigits(2); // Sempre mostrar 2 casas decimais
			format.setGroupingUsed(true); // Usar separador de milhares

			// Configura o NumberFormatter para o campo
			NumberFormatter formatter = new NumberFormatter(format);
			formatter.setValueClass(Double.class); // O campo aceitará valores do tipo Double
			formatter.setAllowsInvalid(false); // Impede a entrada de caracteres inválidos
			formatter.setMinimum(0.0); // Valor mínimo
			formatter.setMaximum(Double.MAX_VALUE); // Valor máximo

			txtPreco = new JFormattedTextField(formatter);
			txtPreco.setColumns(10); // Tamanho do campo
			topo.add(txtPreco, "cell 2 0,alignx center");
		} catch (Exception e) {
			e.printStackTrace();
		}

		txtPreco.setUI(new HintTextFieldUI("R$"));

		JLabel lblNewLabel_2 = new JLabel("MARCA");
		topo.add(lblNewLabel_2, "flowx,cell 4 0,alignx center");

		JComboBox cbxMarca = new JComboBox();
//		cbxMarca.setModel(new DefaultComboBoxModel(new String[] {"NIKE", "ADIDAS", "LACOSTE", "GUCCI", "PUMA"}));
		cbxMarca.addItem(Marca.NIKE);
		cbxMarca.addItem(Marca.ADIDAS);
		cbxMarca.addItem(Marca.PUMA);

		topo.add(cbxMarca, "cell 4 0,alignx center");

		/*
		 * JComboBox<String> cbxFornecedor = new JComboBox<String>(); listaFornecedores
		 * = new ArrayList<>(); fdao = new FornecedorDAO(); listaFornecedores =
		 * fdao.selecionarFornecedores(); for (Fornecedor fornecedor :
		 * listaFornecedores) { cbxFornecedor.addItem(fornecedor.getNomeFornecedor()); }
		 */
		JComboBox<Fornecedor> cbxFornecedor = new JComboBox<Fornecedor>();
		listaFornecedores = new ArrayList<>();
		fdao = new FornecedorDAO();
		listaFornecedores = fdao.selecionarFornecedores();
		for (Fornecedor fornecedor : listaFornecedores) {
			cbxFornecedor.addItem(fornecedor);
		}

		topo.add(cbxFornecedor, "cell 0 0");

		/*
		 * txtTitulo = new JTextField(); topo.add(txtTitulo, "cell 1 0,aligny center");
		 * txtTitulo.setColumns(10);
		 */

		JPanel meio = new JPanel();
		meio.setBorder(new MatteBorder(0, 0, 5, 0, (Color) new Color(32, 60, 115, 124)));
		meio.setBackground(new Color(0, 255, 0));
		contentPane.add(meio, "cell 0 2,grow");
		meio.setOpaque(false);
		meio.setLayout(new MigLayout("", "[grow][grow][grow]", "[][][]"));

		JLabel lblNewLabel_3 = new JLabel("QUANTIDADE NO ESTOQUE");
		meio.add(lblNewLabel_3, "flowx,cell 0 0,alignx center");

		txtQuantidade = new JTextField();
		meio.add(txtQuantidade, "cell 0 0,alignx center");
		txtQuantidade.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("TAMANHO");
		meio.add(lblNewLabel_8, "flowx,cell 1 0,alignx center");

		JLabel lblNewLabel_4 = new JLabel("COR");
		meio.add(lblNewLabel_4, "flowx,cell 2 0,alignx center");

		JComboBox cbxCor = new JComboBox();
		cbxCor.addItem(Cor.AMARELO);
		cbxCor.addItem(Cor.AZUL);
		cbxCor.addItem(Cor.BRANCO);
		cbxCor.addItem(Cor.CINZA);
		cbxCor.addItem(Cor.LARANJA);
		cbxCor.addItem(Cor.MARROM);
		cbxCor.addItem(Cor.PRETO);
		cbxCor.addItem(Cor.ROSA);
		cbxCor.addItem(Cor.ROXO);
		cbxCor.addItem(Cor.VERDE);
		cbxCor.addItem(Cor.VERMELHO);

		meio.add(cbxCor, "cell 2 0,alignx center");

		JComboBox cbxTamanho = new JComboBox();
		cbxTamanho.addItem(Tamanho.PP);
		cbxTamanho.addItem(Tamanho.P);
		cbxTamanho.addItem(Tamanho.M);
		cbxTamanho.addItem(Tamanho.G);
		cbxTamanho.addItem(Tamanho.GG);
		cbxTamanho.addItem(Tamanho.XG);
		cbxTamanho.addItem(Tamanho.XGG);
		cbxTamanho.addItem(Tamanho.EG);
		cbxTamanho.addItem(Tamanho.EGG);
		meio.add(cbxTamanho, "cell 1 0");

		JPanel inferior = new JPanel();
		inferior.setBackground(new Color(128, 0, 255));
		contentPane.add(inferior, "cell 0 3,grow");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 568);
		inferior.setOpaque(false);
		inferior.setLayout(new MigLayout("", "[grow][grow][]", "[][][][][][]"));

		JLabel lblNewLabel_5 = new JLabel("CATEGORIA");
		inferior.add(lblNewLabel_5, "flowx,cell 0 0,alignx center");

		JComboBox<Categoria> cbxCategoria = new JComboBox<Categoria>();
		cbxCategoria.addItem(Categoria.BLUSA);
		cbxCategoria.addItem(Categoria.CALÇA);
		cbxCategoria.addItem(Categoria.CAMISA);
		cbxCategoria.addItem(Categoria.INTIMA);
		cbxCategoria.addItem(Categoria.JAQUETA);
		cbxCategoria.addItem(Categoria.VESTIDO);
		cbxCategoria.addItem(Categoria.SHORTS);
		inferior.add(cbxCategoria, "cell 0 0,alignx center");

		JLabel lblNewLabel_6 = new JLabel("IMAGEM DO PRODUTO");
		inferior.add(lblNewLabel_6, "cell 2 0,alignx center");

		lblimagem = new JLabel("");
		lblimagem.setIcon(new ImageIcon(TelaCadastroProdutos.class.getResource("/img/user.png")));
		inferior.add(lblimagem, "cell 2 3 1 2");

		JButton btnLoad = new JButton("Selecionar Imagem");
		inferior.add(btnLoad, "cell 2 5,alignx center");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser("C://Users//Aluno//Pictures");
				file.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int i = file.showSaveDialog(null);
				System.out.println(i);

				if (i == 1) {
					// JtextFieldLocal.setText("");
				} else {
					File arquivo = file.getSelectedFile();

					String caminhoOrigem = arquivo.getAbsolutePath();

					Date now = new Date();
					String nome_imagem = "ImagensProdutos/prod_" + now.getTime() + ".png";

					Path f = Paths.get(nome_imagem);
					String caminhoDestino = f.toAbsolutePath().toString();
					InputStream is = null;
					OutputStream os = null;
					try {
						is = new FileInputStream(caminhoOrigem);
						os = new FileOutputStream(caminhoDestino);
						byte[] buffer = new byte[1024];
						int length;
						while ((length = is.read(buffer)) > 0) {
							os.write(buffer, 0, length);
						}
						is.close();
						os.close();
						produto.setFoto(caminhoDestino);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						produto.setFoto(null);
					} finally {

					}

					produto.setFoto(nome_imagem);

					ImageIcon imagem = new ImageIcon(caminhoDestino);
					Image img = imagem.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
					System.out.println(arquivo);
					lblimagem.setIcon(new ImageIcon(img));

				}

			}
		});
		JButton btnNewButton = new JButton("SALVAR");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(32, 60, 115));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Fornecedor fnc = (Fornecedor) cbxFornecedor.getSelectedItem();
					produto.setFornecedor(fnc.getIdFornecedor());

					Float preco = Float.parseFloat(txtPreco.getText().replace("R$", "").replace(",", ".").trim());

					int quantidade;
					try {
						quantidade = Integer.parseInt(txtQuantidade.getText().trim());
					} catch (NumberFormatException ex) {
						new TelaErro("A quantidade deve ser um número inteiro válido!", 0);
						return;
					}

					if (quantidade <= 0) {
						new TelaErro("A quantidade deve ser maior que zero!", 0);
						return;
					}

					String cor;
					Cor corselecionada = (Cor) cbxCor.getSelectedItem();
					cor = corselecionada.getDescricao();

					String marca;
					Marca marcaselecionada = (Marca) cbxMarca.getSelectedItem();
					marca = marcaselecionada.getDescricao();

					String tamanho;
					Tamanho tamanhoselecionado = (Tamanho) cbxTamanho.getSelectedItem();
					tamanho = tamanhoselecionado.getDescricao();

					String categoria;
					String categoriaSelecionada = (String) cbxCategoria.getSelectedItem();
					categoria = categoriaSelecionada.getDescricao();

					produto.setCategoria(categoriaSelecionada);
					produto.setMarca(marcaselecionada);
					produto.setPreco(preco);
					produto.setQuantidade(quantidade);
					produto.setCor(corselecionada);
					produto.setTamanho(tamanhoselecionado);

					dao = new ProdutoDAO();
					int res1 = dao.cadastrarProduto(produto);

					if (res1 == 1) {
						new TelaErro("Produto cadastrado com sucesso!", 3);
						TelaProdutos telaProdutos = new TelaProdutos(prod, func, mensagem);
						dispose();
						telaProdutos.setVisible(true);
					} else {
						new TelaErro("Erro ao cadastrar o produto", 0);
					}
				} catch (Exception ex) {
					new TelaErro("Erro inesperado ao cadastrar o produto!", 0);
				}
			}
		});

		inferior.add(btnNewButton, "flowx,cell 0 5,alignx left,growy");

		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBackground(new Color(255, 0, 0));
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaProdutos tela = new TelaProdutos(prod, func, mensagem);
				dispose();
				tela.setVisible(true);
			}
		});
		inferior.add(btnCancelar, "cell 0 5");
	}

}
