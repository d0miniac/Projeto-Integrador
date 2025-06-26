package visao;

import java.awt.EventQueue;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import modelo.Funcionario;
import modelo.Produto;
import net.miginfocom.swing.MigLayout;
import java.awt.Window.Type;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Toolkit;
import controle.FuncionarioDAO;
import javax.swing.border.LineBorder;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.JCheckBox;

public class TelaCadastroFuncionario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JFormattedTextField txtCpf;  
	private JTextField txtEmail;
	private JTextField txtSenha;
	private JTextField txtConfirma;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Produto prod = new Produto();
					Funcionario funcionario = new Funcionario();
					String mensagem = "Bem-vindo ao sistema!";
					TelaCadastroFuncionario frame = new TelaCadastroFuncionario(prod,mensagem, funcionario);
					frame.setVisible(true);
					frame.setSize(1215, 850);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaCadastroFuncionario(Produto prod,String mensagem, Funcionario func) {

		setTitle("Cadastro de Funcionários");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//contentPane = new ImagePanel("src/img/background3.png");
		contentPane = new ImagePanel(getClass().getResource("/img/background3.png"));

		setContentPane(contentPane);

		setSize(1215, 850);
		setLocationRelativeTo(null);
		setResizable(false);

		contentPane.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel PainelVazio = new JPanel();
		PainelVazio.setBackground(new Color(0, 0, 0, 0));
		contentPane.add(PainelVazio);

		JPanel PainelComponentes = new JPanel();
		PainelComponentes.setBackground(new Color(243, 244, 240));
		contentPane.add(PainelComponentes);
		PainelComponentes.setLayout(new MigLayout("", "[46px,grow]", "[20px][14px][][30px][][][][][][][][][][][][][][]"));

		JLabel lblNewLabel = new JLabel("<html>Cadastre um <br>novo funcionário");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 60));
		PainelComponentes.add(lblNewLabel, "cell 0 1,alignx left,aligny top");

		JLabel lblNewLabel_6 = new JLabel("Já tem uma conta?");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PainelComponentes.add(lblNewLabel_6, "flowx,cell 0 2");

		JLabel lblNewLabel_7 = new JLabel("<html><u>Login</u></html>");
		lblNewLabel_7.setForeground(new Color(32, 60, 115));
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_7.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				TelaLogin tela = new TelaLogin(prod,mensagem, func);
				tela.setSize(1215, 850);
				tela.setResizable(false);
				tela.setVisible(true);
			}
		});
		PainelComponentes.add(lblNewLabel_7, "cell 0 2");

		JPanel panelAleatorio = new JPanel();
		PainelComponentes.add(panelAleatorio, "cell 0 2");
		panelAleatorio.setOpaque(false);

		JLabel lblNewLabel_1 = new JLabel("Nome Completo:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		PainelComponentes.add(lblNewLabel_1, "cell 0 4");

		txtNome = new JTextField(25);
		txtNome.setBorder(new LineBorder(new Color(171, 173, 179), 1, true));
		txtNome.setBackground(new Color(209, 209, 233));
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 25));
		PainelComponentes.add(txtNome, "cell 0 5,alignx left");
		txtNome.setColumns(25);

		JLabel lblNewLabel_2 = new JLabel("CPF:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 30));
		PainelComponentes.add(lblNewLabel_2, "cell 0 6");

		
		try {
			MaskFormatter format_textField4 = new MaskFormatter("###.###.###-##");
			txtCpf = new JFormattedTextField(format_textField4);
			txtCpf.setFont(new Font("Tahoma", Font.PLAIN, 25));
			txtCpf.setBorder(new LineBorder(new Color(171, 173, 179), 1, true));
			txtCpf.setBackground(new Color(209, 209, 233));
			txtCpf.setColumns(25);
			PainelComponentes.add(txtCpf, "cell 0 7,alignx left");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JLabel lblNewLabel_3 = new JLabel("E-mail:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 30));
		PainelComponentes.add(lblNewLabel_3, "cell 0 8");

		txtEmail = new JTextField(25);
		txtEmail.setBorder(new LineBorder(new Color(171, 173, 179), 1, true));
		txtEmail.setBackground(new Color(209, 209, 233));
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 25));
		PainelComponentes.add(txtEmail, "cell 0 9,alignx left");
		txtEmail.setColumns(25);

		JLabel lblNewLabel_4 = new JLabel("Senha:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 30));
		PainelComponentes.add(lblNewLabel_4, "cell 0 10");

		txtSenha = new JTextField(25);
		txtSenha.setBorder(new LineBorder(new Color(171, 173, 179), 1, true));
		txtSenha.setBackground(new Color(209, 209, 233));
		txtSenha.setFont(new Font("Tahoma", Font.PLAIN, 25));
		PainelComponentes.add(txtSenha, "cell 0 11,alignx left");
		txtSenha.setColumns(25);

		JLabel lblNewLabel_5 = new JLabel("Confirme a Senha:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 30));
		PainelComponentes.add(lblNewLabel_5, "cell 0 12");

		txtConfirma = new JTextField(25);
		txtConfirma.setBorder(new LineBorder(new Color(171, 173, 179), 1, true));
		txtConfirma.setBackground(new Color(209, 209, 233));
		txtConfirma.setFont(new Font("Tahoma", Font.PLAIN, 25));
		PainelComponentes.add(txtConfirma, "cell 0 13,alignx left");
		txtConfirma.setColumns(25);

		JCheckBox checkBox = new JCheckBox("Administrador?");
		checkBox.setBackground(new Color(243, 244, 240));
		checkBox.setFont(new Font("Tahoma", Font.PLAIN, 28));
		PainelComponentes.add(checkBox, "flowx,cell 0 15,alignx left");

		JButton btnCadastro = new JButton("Cadastrar");
		btnCadastro.setForeground(new Color(255, 255, 255));
		btnCadastro.setBackground(new Color(32, 60, 115));
		btnCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || txtSenha.getText().isEmpty()) {
			        new TelaErro("Preencha todos os campos!", 2);
			        return;
			    }

			    String strCpf;
			    Funcionario novo = new Funcionario();
			    novo.setNome(txtNome.getText());
			    novo.setEmail(txtEmail.getText());
			    
			    strCpf = txtCpf.getText();
			    strCpf = strCpf.replaceAll("[^0-9]", "");  

			    if (strCpf.length() != 11) {  
			        new TelaErro("O CPF deve conter exatamente 11 números!", 1);
			        return;
			    }

			    if (strCpf.isEmpty()) {
			        new TelaErro("Informe um CPF válido", 1);
			        return;
			    }

			    novo.setCpf(strCpf);
			    novo.setSenha(txtSenha.getText());
			    
		        if (txtSenha.getText().length() < 4) {
		            new TelaErro("A senha deve ter no mínimo 4 caracteres!", 1);
		            return;
		        }

			    if (txtSenha.getText().equals(txtConfirma.getText())) {
			        novo.setSenha(txtConfirma.getText());
			    } else {
			        new TelaErro("As senhas não coincidem!", 10);
			        return;
			    }

			    if (checkBox.isSelected()) {
			        novo.setPerfil("Admin");
			    } else {
			        novo.setPerfil("Comum");
			    }


			    FuncionarioDAO dao = new FuncionarioDAO();
			    ArrayList<Funcionario> listaF = dao.selecionarFuncionarios();
			    for (Funcionario funcionario : listaF) {
			        if (funcionario.getEmail().equals(novo.getEmail())) {
			            new TelaErro("Esse email já está cadastrado!", 2);
			            return;
			        }
			    }

			    
			    
			    dao.cadastrarFuncionario(novo);
			    new TelaErro("Funcionário cadastrado com sucesso!", 3);
			    dispose();
			    TelaLogin tela = new TelaLogin(prod,mensagem, func);
                tela.setVisible(true);
                tela.setSize(1215, 850);
                tela.setResizable(false);
			}
		});
		btnCadastro.setFont(new Font("Tahoma", Font.PLAIN, 28));
		PainelComponentes.add(btnCadastro, "cell 0 16,alignx left");
	}

}
