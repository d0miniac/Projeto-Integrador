package visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Carrinho;
import modelo.Funcionario;
import modelo.ItemVenda;
import modelo.Produto;

public class TelaDetalhesProduto extends JFrame {

	public TelaDetalhesProduto(ItemVenda item,Funcionario func,TelaVendas tv, String mensagem, Produto prod) {
		setTitle("Detalhes do Produto");
		setSize(400, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		ImageIcon imageIcon = new ImageIcon();
		Image image = imageIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
		JLabel lblImage = new JLabel(new ImageIcon(image));
		getContentPane().add(lblImage, BorderLayout.NORTH);

		JPanel panelInfo = new JPanel(new GridLayout(0, 1));
		JLabel lblNome = new JLabel("Título: " + item.getNome());
		JLabel lblPreco = new JLabel("Preço: R$" + item.getPreco());
		JLabel lblQuantidade = new JLabel("Quantidade: "+item.getQuantidade());

		lblNome.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNome.setPreferredSize(new Dimension(200, 40));
		lblPreco.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblQuantidade.setFont(new Font("Tahoma", Font.BOLD, 16));

		panelInfo.add(lblNome);
		panelInfo.add(lblPreco);
		panelInfo.add(lblQuantidade);
		getContentPane().add(panelInfo, BorderLayout.CENTER);
		
		
		

		JPanel panelBotao = new JPanel();
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JButton btnRemove = new JButton("Remover");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Carrinho carrinho = Carrinho.getInstancia();
				System.out.println("id"+item.getId());
				carrinho.remover(item.getId());
				dispose();
				tv.dispose();
				TelaVendas tela = new TelaVendas(prod,func, mensagem);
				tela.setVisible(true);
				
				
				
				
			}
		});
		panelBotao.add(btnRemove);
		panelBotao.add(btnVoltar);
		getContentPane().add(panelBotao, BorderLayout.SOUTH);

		
	}
}