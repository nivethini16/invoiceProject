package presentation;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import business.mailReceiver;
import business.mailSender;
import database.ExtractByArea;

public class Main {
	public static Properties loadProperties() throws Exception {

		Properties prop = new Properties();
		InputStream in = new FileInputStream("C:\\Users\\npanneerselvam\\workspace\\project\\resources\\details.properties");
		prop.load(in);
		in.close();
		return prop;
	}

	public static void main(String[] args) throws Exception {
		Properties prop;
		 
			prop = loadProperties();
			String host = prop.getProperty("host");
		     String port = prop.getProperty("port");
		     String userName = prop.getProperty("username");
		     String password = prop.getProperty("password");	 
		 
		mailReceiver receiver=new mailReceiver();
		 receiver.downloadEmailAttachments(host, port, userName, password);
		ExtractByArea eba=new ExtractByArea();
		eba.extract();
		String Senderhost = prop.getProperty("Senderhost");
        String Senderport = prop.getProperty("Senderport");
        String mailFrom = prop.getProperty("username");
        String Senderpassword = prop.getProperty("password");
        String mailTo = "nivsps@gmail.com";
        String subject = "Approval";
        String message = "Your invoice has been approved";
   
    	mailSender msend=new mailSender();
		msend.sendPlainTextEmail(Senderhost, Senderport, mailFrom, Senderpassword, mailTo,
                subject, message);
		
		
	}

}
