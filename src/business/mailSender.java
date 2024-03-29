package business;
import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class mailSender {
	public void sendPlainTextEmail(String Senderhost, String Senderport, final String userName, final String password,
			String toAddress, String subject, String message) throws AddressException, MessagingException {

		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host",Senderhost);
		properties.put("mail.smtp.port",Senderport);
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable","true");

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};

		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message emailMessage = new MimeMessage(session);

		emailMessage.setFrom(new InternetAddress(userName));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		emailMessage.setRecipients(Message.RecipientType.TO, toAddresses);
		emailMessage.setSubject(subject);
		emailMessage.setSentDate(new Date(0));
		// set plain text message
		emailMessage.setText(message);
		// sends the e-mail
		Transport.send(emailMessage);
		System.out.println("Invoice approved and Mail Sent!!");
	}

}
