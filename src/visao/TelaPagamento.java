package visao;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controle.ClienteDAO;
import modelo.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.sql.SQLException;

import net.miginfocom.swing.MigLayout;

public class TelaPagamento extends JFrame {

	private JPanel contentPane;
	private JLabel lblTotal;
	private JButton btnPagar;
	private JTextField txtCartao, txtNome, txtValidade, txtCVV;
	private JLabel lblDataPagamento;
	private JComboBox<Clientes> comboClientes;

	public TelaPagamento(Funcionario funcionario) {
		setTitle("Pagamento");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 550);
		setLocationRelativeTo(null);
		setResizable(false);

		Carrinho carrinho = Carrinho.getInstancia();

		contentPane = new JPanel();
		contentPane.setBackground(new Color(33, 64, 154));
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[452px]", "[26px][400px][][27px]"));

		JLabel lblTitulo = new JLabel("Finalizar Pagamento", SwingConstants.CENTER);
		lblTitulo.setForeground(new Color(255, 255, 255));
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
		contentPane.add(lblTitulo, "cell 0 0,growx,aligny top");

		JPanel panelCentro = new JPanel();
		panelCentro.setBackground(new Color(255, 255, 255));
		panelCentro.setBorder(new EmptyBorder(10, 0, 10, 0));
		panelCentro.setLayout(new MigLayout("", "[217px][][][217px]", "[50px][50px][50px][50px][50px][50px][50px][]"));

		txtCartao = new JTextField();
		txtCartao.setBackground(new Color(209, 209, 233));

		txtNome = new JTextField();
		txtNome.setBackground(new Color(209, 209, 233));

		txtValidade = new JTextField();
		txtValidade.setBackground(new Color(209, 209, 233));

		txtCVV = new JTextField();
		txtCVV.setBackground(new Color(209, 209, 233));

		comboClientes = new JComboBox<>();
		carregarClientes(); // carrega do banco

		DocumentListener docListener = new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				validarCampos();
			}

			public void removeUpdate(DocumentEvent e) {
				validarCampos();
			}

			public void changedUpdate(DocumentEvent e) {
				validarCampos();
			}
		};

		txtCartao.getDocument().addDocumentListener(docListener);
		txtNome.getDocument().addDocumentListener(docListener);
		txtValidade.getDocument().addDocumentListener(docListener);
		txtCVV.getDocument().addDocumentListener(docListener);

		panelCentro.add(new JLabel("Número do Cartão:"), "cell 0 0,grow");
		panelCentro.add(txtCartao, "cell 3 0,grow");

		panelCentro.add(new JLabel("Nome no Cartão:"), "cell 0 1,grow");
		panelCentro.add(txtNome, "cell 3 1,grow");

		panelCentro.add(new JLabel("Validade (MM/AA):"), "cell 0 2,grow");
		panelCentro.add(txtValidade, "cell 3 2,grow");

		panelCentro.add(new JLabel("CVV:"), "cell 0 3,grow");
		panelCentro.add(txtCVV, "cell 3 3,grow");

		panelCentro.add(new JLabel("Cliente:"), "cell 0 4,grow");
		panelCentro.add(comboClientes, "cell 3 4,grow");

		panelCentro.add(new JLabel("Total da Compra:"), "cell 0 5,grow");
		lblTotal = new JLabel("R$ " + String.format("%.2f", calcularTotal(carrinho)), SwingConstants.LEFT);
		lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
		panelCentro.add(lblTotal, "cell 3 5,grow");

		panelCentro.add(new JLabel("Data de Pagamento:"), "cell 0 6,grow");
		lblDataPagamento = new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		lblDataPagamento.setFont(new Font("Arial", Font.BOLD, 14));
		panelCentro.add(lblDataPagamento, "cell 3 6,grow");

		btnPagar = new JButton("Pagar");
		btnPagar.setFont(new Font("Arial", Font.BOLD, 16));
		btnPagar.setBackground(new Color(33, 150, 83));
		btnPagar.setForeground(Color.WHITE);
		btnPagar.setFocusPainted(false);
		btnPagar.setEnabled(false);

		panelCentro.add(btnPagar, "cell 2 7");

		btnPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clienteSelecionado = (Clientes) comboClientes.getSelectedItem();
				carrinho.limpar();
				JOptionPane.showMessageDialog(null,
						"Pagamento realizado com sucesso para o cliente: " + clienteSelecionado.getNome_Clientes());
				new TelaMenu(null, funcionario, "Compra finalizada com sucesso").setVisible(true);
				dispose();
			}
		});

		contentPane.add(panelCentro, "cell 0 1,grow");
	}

	private void validarCampos() {
		String numeroCartao = txtCartao.getText().trim();
		String nomeCartao = txtNome.getText().trim();
		String validade = txtValidade.getText().trim();
		String cvv = txtCVV.getText().trim();
		Clientes cliente = (Clientes) comboClientes.getSelectedItem();

		boolean camposValidos = numeroCartao.matches("\\d{16}") && !nomeCartao.isEmpty()
				&& validade.matches("\\d{2}/\\d{2}") && validadeValida(validade) && cvv.matches("\\d{3}")
				&& cliente != null;

		btnPagar.setEnabled(camposValidos);
	}

	private boolean validadeValida(String validade) {
		try {
			String[] partes = validade.split("/");
			int mes = Integer.parseInt(partes[0]);
			int ano = Integer.parseInt(partes[1]) + 2000;

			if (mes < 1 || mes > 12)
				return false;

			YearMonth validadeCartao = YearMonth.of(ano, mes);
			YearMonth agora = YearMonth.now();

			return validadeCartao.isAfter(agora) || validadeCartao.equals(agora);
		} catch (Exception e) {
			return false;
		}
	}

	private double calcularTotal(Carrinho carrinho) {
		return carrinho.getItens().stream().mapToDouble(item -> item.getFoto().getPreco() * item.getQuantidade()).sum();
	}

	private void carregarClientes() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			List<Clientes> clientes = clienteDAO.listarTodos();
			for (Clientes cliente : clientes) {
				comboClientes.addItem(cliente);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + e.getMessage());
		}
	}
}
