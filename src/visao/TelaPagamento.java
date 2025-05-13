package visao;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.*;
import java.time.YearMonth;

public class TelaPagamento extends JFrame {

    private JPanel contentPane;
    private JLabel lblTotal;
    private JButton btnPagar;
    private JTextField txtCartao, txtNome, txtValidade, txtCVV;

    public TelaPagamento(Funcionario funcionario) {
        setTitle("Pagamento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        Carrinho carrinho = Carrinho.getInstancia();

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);

        JLabel lblTitulo = new JLabel("Finalizar Pagamento", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCentro.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Campos de entrada
        txtCartao = new JTextField();
        txtNome = new JTextField();
        txtValidade = new JTextField();
        txtCVV = new JTextField();

        // Adiciona listeners de validação
        DocumentListener docListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validarCampos(); }
            public void removeUpdate(DocumentEvent e) { validarCampos(); }
            public void changedUpdate(DocumentEvent e) { validarCampos(); }
        };

        txtCartao.getDocument().addDocumentListener(docListener);
        txtNome.getDocument().addDocumentListener(docListener);
        txtValidade.getDocument().addDocumentListener(docListener);
        txtCVV.getDocument().addDocumentListener(docListener);

        panelCentro.add(new JLabel("Número do Cartão:"));
        panelCentro.add(txtCartao);

        panelCentro.add(new JLabel("Nome no Cartão:"));
        panelCentro.add(txtNome);

        panelCentro.add(new JLabel("Validade (MM/AA):"));
        panelCentro.add(txtValidade);

        panelCentro.add(new JLabel("CVV:"));
        panelCentro.add(txtCVV);

        panelCentro.add(new JLabel("Total da Compra:"));
        lblTotal = new JLabel("R$ " + String.format("%.2f", calcularTotal(carrinho)), SwingConstants.LEFT);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        panelCentro.add(lblTotal);

        contentPane.add(panelCentro, BorderLayout.CENTER);

        btnPagar = new JButton("Pagar");
        btnPagar.setFont(new Font("Arial", Font.BOLD, 16));
        btnPagar.setBackground(new Color(0, 153, 76));
        btnPagar.setForeground(Color.WHITE);
        btnPagar.setFocusPainted(false);
        btnPagar.setEnabled(false); // Começa desativado

        btnPagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                carrinho.limpar();
                JOptionPane.showMessageDialog(null, "Pagamento realizado com sucesso!");
                new TelaMenu(null, funcionario, "Compra finalizada com sucesso").setVisible(true);
                dispose();
            }
        });

        contentPane.add(btnPagar, BorderLayout.SOUTH);
    }

    private void validarCampos() {
        String numeroCartao = txtCartao.getText().trim();
        String nomeCartao = txtNome.getText().trim();
        String validade = txtValidade.getText().trim();
        String cvv = txtCVV.getText().trim();

        boolean camposValidos =
                numeroCartao.matches("\\d{16}") &&
                !nomeCartao.isEmpty() &&
                validade.matches("\\d{2}/\\d{2}") &&
                validadeValida(validade) &&
                cvv.matches("\\d{3}");

        btnPagar.setEnabled(camposValidos);
    }

    private boolean validadeValida(String validade) {
        try {
            String[] partes = validade.split("/");
            int mes = Integer.parseInt(partes[0]);
            int ano = Integer.parseInt(partes[1]) + 2000;

            if (mes < 1 || mes > 12) return false;

            YearMonth validadeCartao = YearMonth.of(ano, mes);
            YearMonth agora = YearMonth.now();

            return validadeCartao.isAfter(agora) || validadeCartao.equals(agora);
        } catch (Exception e) {
            return false;
        }
    }

    private double calcularTotal(Carrinho carrinho) {
        return carrinho.getItens().stream()
            .mapToDouble(item -> item.getFoto().getPreco() * item.getQuantidade())
            .sum();
    }
}
