package YouDrive_Team11.Servlets.CustomerCtrl;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendEmail {
 
	Session session;
	
	public SendEmail(){
 
		final String username = "youdrive.team11@gmail.com";
		final String password = "virtual11";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		
		
	}
	
	public void send(String recipient, String subject, String msg) throws UnsupportedEncodingException{
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("youdrive.team11@gmail.com", "YouDrive"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(recipient));
			message.setSubject(subject);
			message.setText(msg);
 
			Transport.send(message);
 
			System.out.println("Email sent to address: " + recipient);
 
		} catch (MessagingException e) {
			System.out.println("Couldn't send");
			throw new RuntimeException(e);
		}
	}
}



