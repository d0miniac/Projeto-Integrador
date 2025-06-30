package visao;

import controle.*;
import modelo.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TelaPagamento extends JFrame {
    private JComboBox<Clientes> comboClientes;
    private JTextField txtCartao, txtNome, txtValidade, txtCVV;
    private JLabel lblDataPagamento, lblTotal;
    private JButton btnPagar;
    private Carrinho carrinho;
    private Funcionario funcionario;

    public TelaPagamento(Carrinho carrinho, Funcionario funcionario) throws SQLException {
        this.funcionario = funcionario;
        this.carrinho = carrinho;
        setTitle("Pagamento");
        setSize(657, 425);
        setLocationRelativeTo(null);
        setResizable(false);

        ImagePanel painelFundo = new ImagePanel(getClass().getResource("/img/bgCadastroFornecedores.png"));
        painelFundo.setLayout(new BorderLayout());
        setContentPane(painelFundo);

        criarPainelTopo(painelFundo);
        criarPainelMeio(painelFundo);
        criarPainelInferior(painelFundo);
    }

    private void criarPainelTopo(JPanel painel) {
        JPanel barraTopo = new JPanel(new BorderLayout());
        barraTopo.setPreferredSize(new Dimension(0, 100));
        barraTopo.setOpaque(false);
        painel.add(barraTopo, BorderLayout.NORTH);

        JLabel bgBarra = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/barraParteDeCima.png"))
                .getImage().getScaledInstance(657, 100, Image.SCALE_SMOOTH)
        ));
        bgBarra.setLayout(new BorderLayout());
        barraTopo.add(bgBarra, BorderLayout.CENTER);

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
                new TelaCarrinho(carrinho, funcionario).setVisible(true);
            }
        });
        esquerda.add(lblVoltar);
        bgBarra.add(esquerda, BorderLayout.WEST);

        JLabel lblTitulo = new JLabel("Pagamento");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        bgBarra.add(lblTitulo, BorderLayout.CENTER);

        JPanel direita = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        direita.setOpaque(false);
        JLabel lblLogo = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/armariodigital.png"))
                .getImage().getScaledInstance(150, 80, Image.SCALE_SMOOTH)
        ));
        direita.add(lblLogo);
        bgBarra.add(direita, BorderLayout.EAST);
    }
    private void criarPainelMeio(JPanel painel) throws SQLException {
        JPanel painelCentral = new JPanel(new MigLayout("", "[grow]", "[][grow]"));
        painelCentral.setOpaque(false);

        JLabel lblSubtitulo = new JLabel("Informações de Pagamento");
        lblSubtitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblSubtitulo.setForeground(new Color(153, 162, 209));
        painelCentral.add(lblSubtitulo, "cell 0 0");

        JPanel meio = new JPanel(new MigLayout("", "[grow][grow]", "[][][][][][]"));
        meio.setOpaque(false);
        meio.setBorder(new MatteBorder(0, 0, 5, 0, new Color(32, 60, 115, 124)));

        meio.add(criarLabel("Cliente:"), "cell 0 0");
        comboClientes = new JComboBox<>();
        meio.add(comboClientes, "cell 1 0,growx");

        meio.add(criarLabel("Número do Cartão:"), "cell 0 1");
        txtCartao = new JTextField();
        meio.add(txtCartao, "cell 1 1,growx");

        meio.add(criarLabel("Nome no Cartão:"), "cell 0 2");
        txtNome = new JTextField();
        meio.add(txtNome, "cell 1 2,growx");

        meio.add(criarLabel("Validade (MM/AA):"), "cell 0 3");
        txtValidade = new JTextField();
        meio.add(txtValidade, "cell 1 3,growx");

        meio.add(criarLabel("CVV:"), "cell 0 4");
        txtCVV = new JTextField();
        meio.add(txtCVV, "cell 1 4,growx");

        meio.add(criarLabel("Data de Pagamento:"), "cell 0 5");
        lblDataPagamento = new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lblDataPagamento.setForeground(Color.BLACK);
        meio.add(lblDataPagamento, "cell 1 5");

        meio.add(criarLabel("Total:"), "cell 0 6");
        lblTotal = new JLabel("R$ " + calcularTotal().toString().replace(".", ","));
        lblTotal.setForeground(Color.BLACK);
        meio.add(lblTotal, "cell 1 6");

        painelCentral.add(meio, "cell 0 1,grow");
        painel.add(painelCentral, BorderLayout.CENTER);

        carregarClientes();
        configurarListeners();
    }

    private void criarPainelInferior(JPanel painel) {
        JPanel inferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        inferior.setOpaque(false);

        btnPagar = new JButton("FINALIZAR PAGAMENTO");
        btnPagar.setBackground(new Color(32, 60, 115));
        btnPagar.setForeground(Color.WHITE);
        btnPagar.setFont(new Font("Arial", Font.BOLD, 14));
        btnPagar.setEnabled(false);
        btnPagar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPagar.addActionListener(e -> processarPagamento());

        inferior.add(btnPagar);
        painel.add(inferior, BorderLayout.SOUTH);
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Tahoma", Font.BOLD, 14));
        label.setForeground(Color.BLACK); // Rótulos com fonte preta ✔️
        return label;
    }

    private void configurarListeners() {
        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { atualizarBotao(); }
            public void removeUpdate(DocumentEvent e) { atualizarBotao(); }
            public void changedUpdate(DocumentEvent e) { atualizarBotao(); }
        };
        txtCartao.getDocument().addDocumentListener(listener);
        txtNome.getDocument().addDocumentListener(listener);
        txtValidade.getDocument().addDocumentListener(listener);
        txtCVV.getDocument().addDocumentListener(listener);
    }

    private void atualizarBotao() {
        boolean ok = comboClientes.getItemCount() > 0 &&
                     !txtCartao.getText().isEmpty() &&
                     !txtNome.getText().isEmpty() &&
                     !txtValidade.getText().isEmpty() &&
                     !txtCVV.getText().isEmpty();
        btnPagar.setEnabled(ok);
    }

    private void carregarClientes() throws SQLException {
        for (Clientes c : new ClienteDAO().listarTodos()) {
            comboClientes.addItem(c);
        }
    }

    private BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemVenda item : carrinho.getItens()) {
            if (item.getFoto() != null && item.getFoto().getPreco() != null) {
                total = total.add(item.getFoto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));
            }
        }
        return total;
    }

    private void processarPagamento() {
        if (!validarCampos()) return;

        try {
            Clientes clienteSelecionado = (Clientes) comboClientes.getSelectedItem();
            VendaDAO vendaDAO = new VendaDAO();
            int idVenda = vendaDAO.inserirVenda(LocalDate.now(), calcularTotal(), funcionario.getId());

            for (ItemVenda iv : carrinho.getItens()) {
                vendaDAO.inserirItemCarrinho(idVenda, iv.getIdProduto(), iv.getQuantidade());
            }

            Pagamento pagamento = new Pagamento();
            pagamento.setIdCliente(clienteSelecionado.getidCliente());
            pagamento.setNumeroCartao(txtCartao.getText().trim());
            pagamento.setNomeCartao(txtNome.getText().trim());
            pagamento.setValidade(txtValidade.getText().trim());
            pagamento.setCvv(txtCVV.getText().trim());
            pagamento.setValorTotal(calcularTotal());
            pagamento.setDataPagamento(LocalDate.now());
            pagamento.setIdVenda(idVenda);

            new PagamentoDAO().salvarPagamento(pagamento);
            Carrinho.getInstancia().limpar();

            new TelaErro("Pagamento efetuado com sucesso!", 3).setVisible(true);
            dispose();
            new TelaMenu(null, funcionario, "Compra finalizada com sucesso").setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            new TelaErro("Erro ao registrar pagamento: " + ex.getMessage(), 0).setVisible(true);
        }
    }

    private boolean validarCampos() {
        String c = txtCartao.getText().trim();
        String n = txtNome.getText().trim();
        String v = txtValidade.getText().trim();
        String cv = txtCVV.getText().trim();

        if (!c.matches("\\d{16}")) {
            new TelaErro("Cartão deve conter 16 dígitos numéricos.", 0).setVisible(true);
            return false;
        }
        if (!cv.matches("\\d{3}")) {
            new TelaErro("CVV deve conter 3 dígitos.", 0).setVisible(true);
            return false;
        }
        if (!v.matches("\\d{2}/\\d{2}")) {
            new TelaErro("Validade deve estar no formato MM/AA.", 0).setVisible(true);
            return false;
        }

        int mes = Integer.parseInt(v.split("/")[0]);
        int ano = Integer.parseInt("20" + v.split("/")[1]);

        if (mes < 1 || mes > 12) {
            new TelaErro("Mês de validade inválido.", 0).setVisible(true);
            return false;
        }

        LocalDate hoje = LocalDate.now();
        LocalDate vencimento = LocalDate.of(ano, mes, 1).withDayOfMonth(1);
        if (vencimento.isBefore(hoje.withDayOfMonth(1))) {
            new TelaErro("Cartão vencido.", 0).setVisible(true);
            return false;
        }

        return true;
    }
}