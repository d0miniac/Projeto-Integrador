package visao;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import modelo.Carrinho;
import modelo.Funcionario;
import modelo.ItemVenda;
import modelo.Produto;

public class TelaDetalhesProduto extends JFrame {

    public TelaDetalhesProduto(ItemVenda item, Funcionario func, TelaVendas tv, String mensagem, Produto prod) {
        setTitle("Detalhes do Produto");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Exibe imagem do produto
        ImageIcon imageIcon = new ImageIcon(item.getFoto().getFoto());
        Image image = imageIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
        JLabel lblImage = new JLabel(new ImageIcon(image));
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lblImage, BorderLayout.NORTH);

        // Informações
        JPanel panelInfo = new JPanel(new GridLayout(0, 1));
        JLabel lblNome = new JLabel("Produto: " + item.getFoto().getNomeVisual());
        JLabel lblPreco = new JLabel("Preço: R$ " + item.getFoto().getPreco());
        JLabel lblQuantidade = new JLabel("Quantidade: " + item.getQuantidade());

        Font fonte = new Font("Tahoma", Font.BOLD, 16);
        lblNome.setFont(fonte);
        lblPreco.setFont(fonte);
        lblQuantidade.setFont(fonte);

        panelInfo.add(lblNome);
        panelInfo.add(lblPreco);
        panelInfo.add(lblQuantidade);
        getContentPane().add(panelInfo, BorderLayout.CENTER);

        // Botões
        JPanel panelBotao = new JPanel();
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> dispose());

        JButton btnRemove = new JButton("Remover");
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Carrinho carrinho = Carrinho.getInstancia();

                // Remove usando a própria instância do item
                carrinho.removerItem(item);

                // Fecha janelas e reabre a tela de vendas atualizada
                dispose();
                tv.dispose();
                new TelaVendas(prod, func, mensagem).setVisible(true);
            }
        });

        panelBotao.add(btnRemove);
        panelBotao.add(btnVoltar);
        getContentPane().add(panelBotao, BorderLayout.SOUTH);
    }
}
