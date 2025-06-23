package visao;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controle.ClienteDAO;
import controle.PagamentoDAO;
import modelo.Carrinho;
import modelo.Clientes;
import modelo.Funcionario;
import modelo.ItemVenda;
import modelo.Pagamento;
import modelo.Produto;
import net.miginfocom.swing.MigLayout;

public class TelaPagamento extends JFrame {

    private JComboBox<Clientes> comboClientes;
    private JTextField txtCartao, txtNome, txtValidade, txtCVV;
    private JLabel lblDataPagamento, lblTotal;
    private JButton btnPagar;
    private Funcionario funcionario;
    private Carrinho carrinho;

    public TelaPagamento(Carrinho carrinho, Funcionario funcionario) throws SQLException {
        this.funcionario = funcionario;
        this.carrinho = carrinho;
        setTitle("Pagamento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][]"));
        getContentPane().add(panelCentro);

        panelCentro.add(new JLabel("Cliente:"), "cell 0 0");
        comboClientes = new JComboBox<>();
        panelCentro.add(comboClientes, "cell 1 0, growx");

        panelCentro.add(new JLabel("Número do Cartão:"), "cell 0 1");
        txtCartao = new JTextField();
        panelCentro.add(txtCartao, "cell 1 1, growx");

        panelCentro.add(new JLabel("Nome no Cartão:"), "cell 0 2");
        txtNome = new JTextField();
        panelCentro.add(txtNome, "cell 1 2, growx");

        panelCentro.add(new JLabel("Validade (MM/AA):"), "cell 0 3");
        txtValidade = new JTextField();
        panelCentro.add(txtValidade, "cell 1 3, growx");

        panelCentro.add(new JLabel("CVV:"), "cell 0 4");
        txtCVV = new JTextField();
        panelCentro.add(txtCVV, "cell 1 4, growx");

        panelCentro.add(new JLabel("Data de Pagamento:"), "cell 0 5");
        lblDataPagamento = new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        panelCentro.add(lblDataPagamento, "cell 1 5, growx");

        panelCentro.add(new JLabel("Total:"), "cell 0 6");
        lblTotal = new JLabel("R$ " + calcularTotal(carrinho).toString().replace(".", ","));
        panelCentro.add(lblTotal, "cell 1 6, growx");

        btnPagar = new JButton("Pagar");
        btnPagar.setFont(new Font("Arial", Font.BOLD, 16));
        btnPagar.setBackground(new Color(33, 150, 83));
        btnPagar.setForeground(Color.WHITE);
        btnPagar.setEnabled(false);
        panelCentro.add(btnPagar, "cell 1 7");

        carregarClientes();
        configurarListeners();
    }

    private void configurarListeners() {
        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                verificarCampos();
            }

            public void removeUpdate(DocumentEvent e) {
                verificarCampos();
            }

            public void changedUpdate(DocumentEvent e) {
                verificarCampos();
            }
        };

        txtCartao.getDocument().addDocumentListener(listener);
        txtNome.getDocument().addDocumentListener(listener);
        txtValidade.getDocument().addDocumentListener(listener);
        txtCVV.getDocument().addDocumentListener(listener);

        btnPagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!validarCampos()) return;

                try {
                    Clientes clienteSelecionado = (Clientes) comboClientes.getSelectedItem();
                    if (clienteSelecionado == null) {
                        JOptionPane.showMessageDialog(null, "Selecione um cliente.");
                        return;
                    }

                    Pagamento pagamento = new Pagamento();
                    pagamento.setIdCliente(clienteSelecionado.getidCliente());
                    pagamento.setNumeroCartao(txtCartao.getText().trim());
                    pagamento.setNomeCartao(txtNome.getText().trim());
                    pagamento.setValidade(txtValidade.getText().trim());
                    pagamento.setCvv(txtCVV.getText().trim());
                    pagamento.setValorTotal(calcularTotal(carrinho));
                    pagamento.setDataPagamento(LocalDate.now());

                    PagamentoDAO pagamentoDAO = new PagamentoDAO();
                    pagamentoDAO.salvarPagamento(pagamento);

                    Carrinho.getInstancia().limpar();
                    JOptionPane.showMessageDialog(null, "Pagamento realizado com sucesso para o cliente: " + clienteSelecionado.getNome_Clientes());
                    new TelaMenu(null, funcionario, "Compra finalizada com sucesso").setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar pagamento: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }

    private void verificarCampos() {
        boolean habilitar = !txtCartao.getText().isEmpty() &&
                            !txtNome.getText().isEmpty() &&
                            !txtValidade.getText().isEmpty() &&
                            !txtCVV.getText().isEmpty();
        btnPagar.setEnabled(habilitar);
    }

    private void carregarClientes() throws SQLException {
        List<Clientes> clientes = new ClienteDAO().listarTodos();
        for (Clientes cliente : clientes) {
            comboClientes.addItem(cliente);
        }
    }

    private boolean validarCampos() {
        if (txtCartao.getText().isEmpty() || txtNome.getText().isEmpty() ||
            txtValidade.getText().isEmpty() || txtCVV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos do cartão.");
            return false;
        }
        return true;
    }

    private BigDecimal calcularTotal(Carrinho carrinho2) {
    	BigDecimal total = BigDecimal.ZERO;
    	for (ItemVenda item : Carrinho.getInstancia().getItens()) {
    	Produto produto = item.getFoto(); 
    	if (produto != null && produto.getPreco() != null) {
    	BigDecimal preco = produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
    	total = total.add(preco);
    }
    }
    	return total;
    	}
}