package visao;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import controle.FuncionarioDAO;
import modelo.Funcionario;
import net.miginfocom.swing.MigLayout;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class TelaEsqueciSenha extends JFrame {

	private JPanel contentPane;
	private JTextField txtEmail;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaEsqueciSenha frame = new TelaEsqueciSenha();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaEsqueciSenha() {
		setTitle("Recuperação de Senha");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1215, 850);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane = new ImagePanel("src/img/bgSenha.png");
		setContentPane(contentPane);

		contentPane.setLayout(new MigLayout("", "[200px,grow][grow][grow]",
				"[50px][100px][100px][][][][][][][][][][][][][][50px][][][]"));

		JLabel lblEqueceuSenha = new JLabel("Esqueceu sua Senha?");
		lblEqueceuSenha.setFont(new Font("Tahoma", Font.BOLD, 50));
		contentPane.add(lblEqueceuSenha, "cell 1 5 2 1");

		JLabel lblRedefinir1 = new JLabel("Para redefinir sua senha, precisamos do");
		lblRedefinir1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		contentPane.add(lblRedefinir1, "cell 1 9 2 1");

		JLabel lblRedefinir2 = new JLabel("e-mail utilizado para a criação da conta.");
		lblRedefinir2.setFont(new Font("Tahoma", Font.PLAIN, 30));
		contentPane.add(lblRedefinir2, "cell 1 10 2 1");

		JLabel lblInsiraEmail = new JLabel("Insira seu e-mail:");
		lblInsiraEmail.setFont(new Font("Tahoma", Font.PLAIN, 25));
		contentPane.add(lblInsiraEmail, "cell 1 14");

		txtEmail = new JTextField();
		txtEmail.setBackground(new Color(162, 208, 255));
		contentPane.add(txtEmail, "cell 1 16,grow");
		txtEmail.setColumns(10);

		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setForeground(new Color(255, 255, 255));
		btnConfirmar.setBackground(new Color(32, 60, 115));
		btnConfirmar.setFont(new Font("Tahoma", Font.PLAIN, 30));
		contentPane.add(btnConfirmar, "cell 1 19,alignx center");

		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String emailUsuario = txtEmail.getText();

				FuncionarioDAO dao = new FuncionarioDAO();
				Funcionario funcionario = dao.buscarPorEmail(emailUsuario);

				if (funcionario != null) {
					String senhaUsuario = funcionario.getSenha();
					enviarEmail(emailUsuario, senhaUsuario);
					JOptionPane.showMessageDialog(null, "E-mail enviado com sucesso!");
				} else {

					new TelaErro("O e-mail informado não foi encontrado.", 0);
				}
			}

			private void enviarEmail(String emailDestino, String senhaUsuario) {
				Properties properties = new Properties();
				String host = "smtp.gmail.com";
				final String emailRemetente = "leticiagehrke714@gmail.com";
				final String senhaRemetente = "10022007";
				properties.put("mail.smtp.host", host);
				properties.put("mail.smtp.port", "465");
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.smtp.socketFactory.port", "465");
				properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

				Session session = Session.getInstance(properties, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(emailRemetente, senhaRemetente);
					}
				});

				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress(emailRemetente, "Nome Remetente"));
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDestino));
					message.setSubject("Recuperação de Senha");
					message.setText("Sua senha é: " + senhaUsuario);

					Transport.send(message);
					System.out.println("Email enviado com sucesso...");
				} catch (MessagingException mex) {

					new TelaErro("Erro ao enviar o e-mail. Tente novamente mais tarde.", 0);
					mex.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
}
