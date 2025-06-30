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
import java.util.List;

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
        this.setTitle("Pagamento");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        ImagePanel painelFundo = new ImagePanel(getClass().getResource("/img/Fundo.png"));
        painelFundo.setLayout(new BorderLayout());
        setContentPane(painelFundo);

        criarBarraTopo(painelFundo);
        criarFormulario(painelFundo);
        criarRodape(painelFundo);
    }

    private void criarBarraTopo(JPanel parent) {
        ImageIcon imgBarra = new ImageIcon(getClass().getResource("/img/barraParteDeCima.png"));
        JLabel barraTopo = new JLabel(imgBarra);
        barraTopo.setLayout(new MigLayout("", "10[]10[grow]10[]10", "10[80]10"));
        barraTopo.setPreferredSize(new Dimension(0, 100));

        JLabel lblVoltar = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/de-volta.png"))
                    .getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)
        ));
        lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblVoltar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new TelaCarrinho(carrinho, funcionario).setVisible(true);
            }
        });
        barraTopo.add(lblVoltar, "cell 0 0,alignx left,aligny center");

        JLabel lblTitulo = new JLabel("Pagamento");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        barraTopo.add(lblTitulo, "growx,alignx center");

        JLabel lblLogo = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/img/armariodigital.png"))
                    .getImage().getScaledInstance(150, 80, Image.SCALE_SMOOTH)
        ));
        barraTopo.add(lblLogo, "cell 3 0,alignx right,aligny center");

        parent.add(barraTopo, BorderLayout.NORTH);
    }

    private void criarFormulario(JPanel parent) throws SQLException {
        JPanel content = new JPanel(new MigLayout("", "[grow]", "[grow]"));
        content.setOpaque(false);

        JPanel form = new JPanel(new MigLayout("", "[grow][grow]", "[][]20[][]20[][]20[][]20[][]"));
        form.setOpaque(false);
        form.setBorder(new MatteBorder(0,0,5,0,new Color(32,60,115,124)));

        adicionarCampo(form, "Cliente:", comboClientes = new JComboBox<>());
        adicionarCampo(form, "Número do Cartão:", txtCartao = new JTextField());
        adicionarCampo(form, "Nome no Cartão:", txtNome = new JTextField());
        adicionarCampo(form, "Validade (MM/AA):", txtValidade = new JTextField());
        adicionarCampo(form, "CVV:", txtCVV = new JTextField());
        adicionarCampoLabel(form, "Data de Pagamento:", lblDataPagamento =
            new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        adicionarCampoLabel(form, "Total:", lblTotal =
            new JLabel("R$ " + calcularTotal().toString().replace(".", ",")));

        lblDataPagamento.setForeground(Color.WHITE);
        lblTotal.setForeground(Color.WHITE);

        content.add(form, "center");
        parent.add(content, BorderLayout.CENTER);

        carregarClientes();
        configurarListeners();
    }

    private void criarRodape(JPanel parent) {
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        rodape.setOpaque(false);
        btnPagar = new JButton("FINALIZAR PAGAMENTO");
        btnPagar.setFont(new Font("Arial", Font.BOLD, 16));
        btnPagar.setBackground(new Color(32, 60, 115));
        btnPagar.setForeground(Color.WHITE);
        btnPagar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPagar.setEnabled(false);
        btnPagar.addActionListener(e -> processarPagamento());
        rodape.add(btnPagar);
        parent.add(rodape, BorderLayout.SOUTH);
    }

    private void adicionarCampo(JPanel form, String rotulo, JComponent comp) {
        JLabel lbl = new JLabel(rotulo);
        configurarLabel(lbl);
        form.add(lbl);
        form.add(comp, "growx");
    }

    private void adicionarCampoLabel(JPanel form, String rotulo, JLabel lbl) {
        JLabel l = new JLabel(rotulo);
        configurarLabel(l);
        form.add(l);
        form.add(lbl, "growx");
    }

    private void configurarLabel(JLabel l) {
        l.setFont(new Font("Tahoma", Font.BOLD, 14));
        l.setForeground(Color.WHITE);
    }

    private void configurarListeners() {
        DocumentListener dl = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { atualizarBotao(); }
            public void removeUpdate(DocumentEvent e) { atualizarBotao(); }
            public void changedUpdate(DocumentEvent e) { atualizarBotao(); }
        };
        txtCartao.getDocument().addDocumentListener(dl);
        txtNome.getDocument().addDocumentListener(dl);
        txtValidade.getDocument().addDocumentListener(dl);
        txtCVV.getDocument().addDocumentListener(dl);
    }

    private void atualizarBotao() {
        boolean ok = comboClientes.getItemCount()>0 &&
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
        BigDecimal soma = BigDecimal.ZERO;
        for (ItemVenda item : carrinho.getItens()) {
            if (item.getFoto()!=null && item.getFoto().getPreco()!=null) {
                soma = soma.add(item.getFoto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));
            }
        }
        return soma;
    }

    private void processarPagamento() {
        if (!validarCampos()) return;
        try {
            Clientes sel = (Clientes) comboClientes.getSelectedItem();
            VendaDAO vdao = new VendaDAO();
            int idVenda = vdao.inserirVenda(LocalDate.now(), calcularTotal(), funcionario.getId());
            for (ItemVenda iv : carrinho.getItens())
                vdao.inserirItemCarrinho(idVenda, iv.getIdProduto(), iv.getQuantidade());

            Pagamento p = new Pagamento();
            p.setIdCliente(sel.getidCliente());
            p.setNumeroCartao(txtCartao.getText().trim());
            p.setNomeCartao(txtNome.getText().trim());
            p.setValidade(txtValidade.getText().trim());
            p.setCvv(txtCVV.getText().trim());
            p.setValorTotal(calcularTotal());
            p.setDataPagamento(LocalDate.now());
            p.setIdVenda(idVenda);
            new PagamentoDAO().salvarPagamento(p);

            Carrinho.getInstancia().limpar();
            JOptionPane.showMessageDialog(this, "Pagamento efetuado com sucesso!");
            dispose();
            new TelaMenu(null, funcionario, "Compra finalizada com sucesso").setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao registrar pagamento: " + ex.getMessage());
        }
    }

    private boolean validarCampos() {
        String c = txtCartao.getText().trim(),
               n = txtNome.getText().trim(),
               v = txtValidade.getText().trim(),
               cv = txtCVV.getText().trim();

        if (!c.matches("\\d{16}"))      { mostrarErro("Cartão deve ter 16 dígitos."); return false; }
        if (!cv.matches("\\d{3}"))     { mostrarErro("CVV deve ter 3 dígitos."); return false; }
        if (!v.matches("\\d{2}/\\d{2}")){ mostrarErro("Validade no formato MM/AA."); return false; }

        int m = Integer.parseInt(v.split("/")[0]);
        int a = Integer.parseInt("20"+v.split("/")[1]);
        if (m<1 || m>12)               { mostrarErro("Mês inválido."); return false; }

        LocalDate hoje = LocalDate.now(),
                  venc = LocalDate.of(a, m, 1).withDayOfMonth(1);
        if (venc.isBefore(hoje.withDayOfMonth(1))) { mostrarErro("Cartão vencido."); return false; }

        return true;
    }

    private void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
