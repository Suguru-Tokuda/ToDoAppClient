package email;

import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This class sends email to users. Referred from:
 * https://www.javatpoint.com/example-of-sending-email-using-java-mail-api
 * https://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
 *
 * @author Suguru
 */
public class Email {

    private String to;
    private String from;
    private String subject;
    private String body;
    private String host;

    public final String username = "suguru.tokuda@gmail.com";
    public final String password = "sfst0812";

    private Properties properties;
    private Session session;

    private Message message;

    public Email(String mailHost) {
        properties = System.getProperties();
        properties.setProperty(mailHost, host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public boolean sendInvitationEmail(String senderUsername, String receiverUsername, String toDoListName, String toDoListId) {
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(getFrom()));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(getTo()));
            message.setSubject(subject);            
            
            body = "<html>\n" +
                    "<body>\n" +
                    "<p>Dear " + receiverUsername +  ",</p>\n" +
                    "<p>" + senderUsername + " has sent you an invitation for the list, " + toDoListName + ". " +
                    "Please confirm the invitatoin from the link below:</p><br>" +
                    "<a href=\"http://localhost:8080/ToDoAppClient/confirminvitation/\"" + toDoListId + "\"><br>\n" +
                    "<p>Regards,<br> Suguru Tokuda</p>\n" +
                    "</body>\n" +
                    "<html>";
            message.setContent(body, "text/html");
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sendConfirmationEmail(String username, String userid) {
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(getFrom()));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(getTo()));
            message.setSubject(subject);            
            body = "<html>\n" +
                    "<body>\n" +
                    "<p>Dear " + username +  ",</p>\n" +
                    "<p>Thanks for sign up. You have another step before starting using the app. " +
                    "Please confirm sign up from the link below:</p><br>" +
                    "<a href=\"http://localhost:8080/ToDoAppClient/confirmsignup/\"" + userid + "\"><br>\n" +
                    "<p>Regards,<br> Suguru Tokuda</p>\n" +
                    "</body>\n" +
                    "<html>\n";
            message.setContent(body, "text/html");
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

}
