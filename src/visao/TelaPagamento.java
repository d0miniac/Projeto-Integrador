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
        setSize(600, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setLayout(new BorderLayout());

        // Header com título
        JPanel panelTopo = new JPanel();
        panelTopo.setBackground(new Color(32, 60, 115));
        JLabel titulo = new JLabel("PAGAMENTO");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        panelTopo.add(titulo);
        getContentPane().add(panelTopo, BorderLayout.NORTH);

        // Painel principal com formulário
        JPanel panelCentro = new JPanel(new MigLayout("", "[][grow]", "[][][][][][][][]"));
        panelCentro.setBackground(new Color(32, 60, 115));
        getContentPane().add(panelCentro, BorderLayout.CENTER);

        // Criação de cada campo
        JLabel lblCliente = new JLabel("Cliente:");
        configurarLabel(lblCliente);
        panelCentro.add(lblCliente, "cell 0 0");
        comboClientes = new JComboBox<>();
        panelCentro.add(comboClientes, "cell 1 0, growx");

        JLabel lblCartao = new JLabel("Número do Cartão:");
        configurarLabel(lblCartao);
        panelCentro.add(lblCartao, "cell 0 1");
        txtCartao = new JTextField();
        panelCentro.add(txtCartao, "cell 1 1, growx");

        JLabel lblNome = new JLabel("Nome no Cartão:");
        configurarLabel(lblNome);
        panelCentro.add(lblNome, "cell 0 2");
        txtNome = new JTextField();
        panelCentro.add(txtNome, "cell 1 2, growx");

        JLabel lblValidade = new JLabel("Validade (MM/AA):");
        configurarLabel(lblValidade);
        panelCentro.add(lblValidade, "cell 0 3");
        txtValidade = new JTextField();
        panelCentro.add(txtValidade, "cell 1 3, growx");

        JLabel lblCVV = new JLabel("CVV:");
        configurarLabel(lblCVV);
        panelCentro.add(lblCVV, "cell 0 4");
        txtCVV = new JTextField();
        panelCentro.add(txtCVV, "cell 1 4, growx");

        JLabel lblData = new JLabel("Data de Pagamento:");
        configurarLabel(lblData);
        panelCentro.add(lblData, "cell 0 5");
        lblDataPagamento = new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lblDataPagamento.setForeground(Color.WHITE);
        panelCentro.add(lblDataPagamento, "cell 1 5");

        JLabel lblValor = new JLabel("Total:");
        configurarLabel(lblValor);
        panelCentro.add(lblValor, "cell 0 6");
        lblTotal = new JLabel("R$ " + calcularTotal().toString().replace(".", ","));
        lblTotal.setForeground(Color.WHITE);
        panelCentro.add(lblTotal, "cell 1 6");

        // Botão pagar
        btnPagar = new JButton("Pagar");
        btnPagar.setFont(new Font("Arial", Font.BOLD, 16));
        btnPagar.setBackground(new Color(33, 150, 83));
        btnPagar.setForeground(Color.WHITE);
        btnPagar.setEnabled(false);
        panelCentro.add(btnPagar, "cell 1 7, align right");

        carregarClientes();
        configurarListeners();
    }

    private void configurarLabel(JLabel label) {
        label.setFont(new Font("Tahoma", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
    }

    private void configurarListeners() {
        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { verificarCampos(); }
            public void removeUpdate(DocumentEvent e) { verificarCampos(); }
            public void changedUpdate(DocumentEvent e) { verificarCampos(); }
        };

        txtCartao.getDocument().addDocumentListener(listener);
        txtNome.getDocument().addDocumentListener(listener);
        txtValidade.getDocument().addDocumentListener(listener);
        txtCVV.getDocument().addDocumentListener(listener);

        btnPagar.addActionListener(e -> {
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
                pagamento.setValorTotal(calcularTotal());
                pagamento.setDataPagamento(LocalDate.now());

                new PagamentoDAO().salvarPagamento(pagamento);
                Carrinho.getInstancia().limpar();

                JOptionPane.showMessageDialog(null, "Pagamento realizado com sucesso para o cliente: " + clienteSelecionado.getNome_Clientes());
                SwingUtilities.invokeLater(() -> new TelaMenu(null, funcionario, "Compra finalizada com sucesso").setVisible(true));
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar pagamento: " + ex.getMessage());
                ex.printStackTrace();
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
        String cartao = txtCartao.getText().trim();
        String nome = txtNome.getText().trim();
        String validade = txtValidade.getText().trim();
        String cvv = txtCVV.getText().trim();

        if (!cartao.matches("\\d{16}")) {
            JOptionPane.showMessageDialog(null, "Número do cartão deve conter exatamente 16 dígitos numéricos.");
            return false;
        }

        if (!cvv.matches("\\d{3}")) {
            JOptionPane.showMessageDialog(null, "CVV deve conter exatamente 3 dígitos.");
            return false;
        }

        if (!validade.matches("\\d{2}/\\d{2}")) {
            JOptionPane.showMessageDialog(null, "Formato da validade deve ser MM/AA.");
            return false;
        }

        String[] partes = validade.split("/");
        int mes = Integer.parseInt(partes[0]);
        int ano = Integer.parseInt("20" + partes[1]);

        if (mes < 1 || mes > 12) {
            JOptionPane.showMessageDialog(null, "Mês da validade inválido.");
            return false;
        }

        LocalDate hoje = LocalDate.now();
        LocalDate validadeCartao = LocalDate.of(ano, mes, 1).withDayOfMonth(1);

        if (validadeCartao.isBefore(hoje.withDayOfMonth(1))) {
            JOptionPane.showMessageDialog(null, "Cartão vencido.");
            return false;
        }

        return true;
    }

    private BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemVenda item : carrinho.getItens()) {
            Produto produto = item.getFoto();
            if (produto != null && produto.getPreco() != null) {
                BigDecimal preco = produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
                total = total.add(preco);
            }
        }
        return total;
    }
}