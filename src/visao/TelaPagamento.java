package visao;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import net.miginfocom.swing.MigLayout;

public class TelaPagamento extends JFrame {

    private JPanel contentPane;
    private JLabel lblTotal;
    private JButton btnPagar;
    private JTextField txtCartao, txtNome, txtValidade, txtCVV;
    private JLabel lblDataPagamento;


    public TelaPagamento(Funcionario funcionario) {
        setTitle("Pagamento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        Carrinho carrinho = Carrinho.getInstancia();

        contentPane = new JPanel();
        contentPane.setBackground(new Color(33, 64, 154));
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[452px]", "[26px][348px][][27px]"));

        JLabel lblTitulo = new JLabel("Finalizar Pagamento", SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(255, 255, 255));
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        contentPane.add(lblTitulo, "cell 0 0,growx,aligny top");

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(new Color(255, 255, 255));
        panelCentro.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Campos de entrada
        txtCartao = new JTextField();
        txtCartao.setForeground(new Color(0, 0, 0));
        txtCartao.setBackground(new Color(209, 209, 233));
        txtNome = new JTextField();
        txtNome.setBackground(new Color(209, 209, 233));
        txtValidade = new JTextField();
        txtValidade.setBackground(new Color(209, 209, 233));
        txtCVV = new JTextField();
        txtCVV.setBackground(new Color(209, 209, 233));

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
        panelCentro.setLayout(new MigLayout("", "[217px][][][217px]", "[57px][57px][57px][57px][57px][]"));
        
      
        JLabel label = new JLabel("Número do Cartão:");
        label.setFont(new Font("Tahoma", Font.BOLD, 15));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panelCentro.add(label, "cell 0 0,grow");
        panelCentro.add(txtCartao, "cell 3 0,grow");

        JLabel label_1 = new JLabel("Nome no Cartão:");
        label_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        panelCentro.add(label_1, "cell 0 1,grow");
        panelCentro.add(txtNome, "cell 3 1,grow");

        JLabel label_2 = new JLabel("Validade (MM/AA):");
        label_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        panelCentro.add(label_2, "cell 0 2,grow");
        panelCentro.add(txtValidade, "cell 3 2,grow");

        JLabel label_3 = new JLabel("CVV:");
        label_3.setFont(new Font("Tahoma", Font.BOLD, 15));
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        panelCentro.add(label_3, "cell 0 3,grow");
        panelCentro.add(txtCVV, "cell 3 3,grow");

        JLabel label_4 = new JLabel("Total da Compra:");
        label_4.setFont(new Font("Tahoma", Font.BOLD, 15));
        label_4.setHorizontalAlignment(SwingConstants.CENTER);
        panelCentro.add(label_4, "cell 0 4,grow");
        lblTotal = new JLabel("R$ " + String.format("%.2f", calcularTotal(carrinho)), SwingConstants.LEFT);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        panelCentro.add(lblTotal, "cell 3 4,grow");
        
        JLabel labelData = new JLabel("Data de Pagamento:");
        labelData.setFont(new Font("Tahoma", Font.BOLD, 15));
        labelData.setHorizontalAlignment(SwingConstants.CENTER);
        panelCentro.add(labelData, "cell 0 5,grow");

        lblDataPagamento = new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lblDataPagamento.setFont(new Font("Arial", Font.BOLD, 14));
        panelCentro.add(lblDataPagamento, "cell 3 5,grow");


        contentPane.add(panelCentro, "cell 0 1,alignx left,growy");
                
                        btnPagar = new JButton("Pagar");
                        panelCentro.add(btnPagar, "cell 2 6");
                        btnPagar.setFont(new Font("Arial", Font.BOLD, 16));
                        btnPagar.setBackground(new Color(209, 209, 233));
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
