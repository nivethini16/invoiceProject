package business;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
 
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
 
class mail{
    private String saveDirectory;
 
    /**
     * Sets the directory where attached files will be stored.
     * @param dir absolute path of the directory
     */
    public void setSaveDirectory(String dir) {
        this.saveDirectory = dir;
    }
 
    /**
     * Downloads new messages and saves attachments to disk if any.
     * @param host
     * @param port
     * @param userName
     * @param password
     */
    public void downloadEmailAttachments(String host, String port,
            String userName, String password) {
    	Properties mailProperties = new Properties();
		mailProperties.put("mail.store.protocol", "pop3");
		mailProperties.put("mail.pop3.host", host);
		mailProperties.put("mail.pop3.port", "995");
		mailProperties.put("mail.pop3.starttls.enable", "true");
		Session session = Session.getInstance(mailProperties);
		Folder emailFolder = null;
		Store store = null;
		String fileName = "";
		try {
			store = session.getStore("pop3s");
			store.connect(host, userName, password);
			emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);
			Message[] messages = emailFolder.getMessages();
			if(messages.length<=0) 
				System.out.println("No unread Message..");
			for (int i = 0; i < 3; i++) {
				Message message = messages[i];
				Address[] toAddress = message.getRecipients(Message.RecipientType.TO);
				System.out.println("---------------------------------");
				System.out.println("Details of Email Message " + (i + 1) + " :");
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("To: ");
				for (int j = 0; j < toAddress.length; j++) {
					System.out.println(toAddress[j].toString());
				}
				Object content = message.getContent();
				if (content instanceof Multipart) {
					Multipart multipart = (Multipart) message.getContent();
					for (int k = 0; k < multipart.getCount(); k++) {
						BodyPart bodyPart = multipart.getBodyPart(k);
						if (bodyPart.getDisposition() != null
								&& bodyPart.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)) {
							System.out.println("file name " + bodyPart.getFileName());
							fileName = bodyPart.getFileName();
							System.out.println("size " + bodyPart.getSize());
							System.out.println("content type " + bodyPart.getContentType());
							InputStream stream = (InputStream) bodyPart.getInputStream();
							File targetFile = new File("D:\\" + bodyPart.getFileName());
							java.nio.file.Files.copy(stream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
						}
					}
					 }if (content instanceof String)  
				{  
				    String body = (String)content;  
				    System.out.println(body);
				} 
			}
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				emailFolder.close(false);
				store.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

   }
}
 
    /**
     * Runs this program with Gmail POP3 server
     */
    public class mailReceiver extends mail{
    	public mailReceiver(){
   		String saveDirectory = "D:/";
        mail receiver = new mail();
        receiver.setSaveDirectory(saveDirectory);
        
        
    	}
    }

 
