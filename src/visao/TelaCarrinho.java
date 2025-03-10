package visao;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Carrinho;
import modelo.ItemVenda;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

public class TelaCarrinho extends JFrame {

	private JPanel contentPane;
	private Carrinho carrinho;
	ArrayList<ItemVenda> listaItens;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TelaCarrinho frame = new TelaCarrinho();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public TelaCarrinho(Carrinho c){
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(1215, 850);
		
		listaItens = new ArrayList<>();
		listaItens = c.getItens();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		
		
		
		panel.setLayout(null);
		JButton btnNewButton = new JButton("Voltar");
		btnNewButton.setBounds(90, 5, 77, 25);
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);
		panel_2.setBackground(new Color(243, 244, 240));
		panel_2.setLayout(new MigLayout("wrap 3", "[grow]", "[]"));
		for (ItemVenda item : listaItens) {
			ImageIcon imageIcon = new ImageIcon(item.getFoto());
	        Image image = imageIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
	        JLabel lblItemFoto = new JLabel(new ImageIcon(image));
			JLabel lblItemNome = new JLabel(item.getNome());
			contentPane.add(lblItemFoto);
			lblItemNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        lblItemNome.setPreferredSize(new Dimension(30, 30));
			contentPane.add(lblItemNome);
			JLabel lblItemQuantidade = new JLabel(String.valueOf(item.getQuantidade())+"x");
			contentPane.add(lblItemQuantidade);
		}
	}

}
