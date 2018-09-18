package coupon.sys.core.dao.db.utils;

import java.util.Properties;
import coupon.sys.core.configuration.Configuration;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This class responsible to generate and to send an email associated with the DailyCouponExpirationTask.
 * In case of successful process and a success message will be sent with the deleted coupons
 * In case of failure process and failure message will be sent  
 * @author Yehuda.Hizmi
 *
 */
public class EmailingProcess {
	
	
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	static Configuration config = Configuration.getInstance();

	/**
	 * This method build the message and sent it
	 * @param subject - The relevant subject (success or failure)
	 * @param message - The relevant message (success or failure)
	 * @param to - The to recipients 
	 * @param cc - The cc recipients
	 * @throws AddressException - The exception thrown when a wrongly formatted address is encountered. 
	 * @throws MessagingException - The base class for all exceptions thrown by the Messaging classes
	 */
	public static void generateAndSendEmail(String subject, String message, String to, String cc) throws AddressException, MessagingException {
	
	// Step1
	mailServerProperties = System.getProperties();
	mailServerProperties.put("mail.smtp.port", "587");
	mailServerProperties.put("mail.smtp.auth", "true");
	mailServerProperties.put("mail.smtp.starttls.enable", "true");

	// Step2
	//Get Mail Session
	getMailSession = Session.getDefaultInstance(mailServerProperties, null);
	generateMailMessage = new MimeMessage(getMailSession);
	generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
	generateMailMessage.setSubject(subject);
	generateMailMessage.setContent(message, "text/html");

	//Step3
	//Get Session and Send mail
	Transport transport = getMailSession.getTransport("smtp");

	//Step 4
	//Connect to specific email account
	transport.connect("smtp.gmail.com", config.SENDER, config.SENDER_PASSWORD);
	transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
	transport.close();

	}

}
