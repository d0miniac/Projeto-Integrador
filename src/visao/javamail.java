package visao;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class javamail {
	
	public static void main(String[] args) {
		
	        Properties properties = System.getProperties();

	        String host = "smtp.gmail.com";
	        String email = "armariodigitalemail@gmail.com";
	        String password = "10022007";


	        properties.put("mail.smtp.host", host);
	        properties.put("mail.smtp.port", "465");
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.socketFactory.class",
	                "javax.net.ssl.SSLSocketFactory");

	        Session session = Session.getDefaultInstance(properties, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(email, password);
	            }
	        });

	        session.setDebug(true);

	        try {
	            MimeMessage message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(email, "Leticia Linda"));
	            //o segundo parametro após o email é o nome do remetente,
	            //vc pode customizar pra ficar tipo: Empresa xxx

	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
	            message.setSubject("Fala, bora trabalhar?");
	            message.setText("Lê essa msg maneira aqui...");

	            System.out.println("enviando...");
	            Transport.send(message);
	            System.out.println("Email enviado com sucesso...");
	        } catch (MessagingException mex) {
	            mex.printStackTrace();
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException(e);
	        }
	    }
}
