package visao;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class TelaCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCliente frame = new TelaCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow][grow]"));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[][]", "[][]"));
		
		JButton btnNewButton = new JButton("New button");
		panel.add(btnNewButton, "cell 0 1");
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panel.add(lblNewLabel_1, "cell 1 1");
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[grow][][][][][][][][]", "[][]"));
		
		textField = new JTextField();
		panel_1.add(textField, "cell 0 0,growx");
		textField.setColumns(10);
		
		JButton btnpesquisa = new JButton("PESQUISAR");
		panel_1.add(btnpesquisa, "cell 8 0");
		
		JButton btnCadastro = new JButton("Cadastrar");
		panel_1.add(btnCadastro, "cell 0 1");
		
		JButton btnAltera = new JButton("Alterar");
		panel_1.add(btnAltera, "cell 1 1");
		
		JButton btnDeletar = new JButton("Deletar");
		panel_1.add(btnDeletar, "cell 2 1");
	}

}
