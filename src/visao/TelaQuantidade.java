package visao;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import modelo.Carrinho;
import modelo.Funcionario;
import modelo.ItemVenda;
import modelo.Produto;
import net.miginfocom.swing.MigLayout;

public class TelaQuantidade extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private int quantidade;
    private JLabel lblQuantidade;
    private Carrinho carrinho;

    public TelaQuantidade(Funcionario f, Produto p, TelaProdutos tp, String mensagem) {
        carrinho = Carrinho.getInstancia();
        quantidade = 1;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 319, 330);
        setLocationRelativeTo(null);
        setTitle("Selecionar Quantidade");

        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[grow][][][][grow]", "[][][][][][][][]"));

        // Título
        JLabel lblTitulo = new JLabel("Produto: " + p.getNome());
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblTitulo, "cell 1 0 3 1,alignx center");

        // Imagem
        ImageIcon imageIcon = new ImageIcon(p.getFoto());
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel lblFoto = new JLabel(new ImageIcon(image));
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        contentPane.add(lblFoto, "cell 1 1 3 2,alignx center");

        // Informações adicionais
        JLabel lblDescricao = new JLabel(p.getCategoria() + " - " + p.getMarca() + " - " + p.getCor() + " - " + p.getTamanho());
        lblDescricao.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblDescricao, "cell 1 3 3 1,alignx center");

        // Label "Quantidade"
        JLabel label = new JLabel("Quantidade:");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label, "cell 1 4 3 1,alignx center");

        // Botão "-"
        JButton btnMenos = new JButton("-");
        btnMenos.addActionListener(e -> {
            if (quantidade > 1) {
                quantidade--;
                lblQuantidade.setText(String.valueOf(quantidade));
            }
        });
        contentPane.add(btnMenos, "cell 1 5,alignx center");

        // Quantidade atual
        lblQuantidade = new JLabel(String.valueOf(quantidade));
        lblQuantidade.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblQuantidade, "cell 2 5,alignx center");

        // Botão "+"
        JButton btnMais = new JButton("+");
        btnMais.addActionListener(e -> {
            if (quantidade < p.getQuantidade()) {
                quantidade++;
                lblQuantidade.setText(String.valueOf(quantidade));
            }
        });
        contentPane.add(btnMais, "cell 3 5,alignx center");

        // Botão "Adicionar"
        JButton btnAdicionar = new JButton("ADICIONAR");
        btnAdicionar.addActionListener(e -> {
            ItemVenda item = new ItemVenda();
            item.setFoto(p);
            item.setQuantidade(quantidade);
            carrinho.adicionarItem(item);

            dispose();
            tp.dispose();
            new TelaVendas(p, f, mensagem).setVisible(true);
        });
        contentPane.add(btnAdicionar, "cell 1 6 3 1,alignx center");
    }
}
