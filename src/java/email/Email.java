package email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import models.ToDoList;
import models.User;

/**
 * This class sends email to users. Referred from:
 * https://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
 *
 * @author Suguru
 */
public class Email {

    private String body;

    public final String username = "suguru.tokuda@gmail.com";
    public final String password = "sfst0812";

    private Properties properties;
    private Session session;

    private Message message;

    public Email() {
        
    }

    public boolean sendInvitationEmail(User sender, User receiver, ToDoList toDoList) {
        properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiver.getEmail()));
            message.setSubject("ToDoApp: You've got an Invitation");

            body = "<html>\n"
                    + "<body>\n"
                    + "<p>Dear " + receiver.getFirstname() + ",</p>\n"
                    + "<p>" + sender.getFirstname() + " has sent you an invitation for " + toDoList.getTodolistname() + ". "
                    + "Please confirm the invitatoin from the link below:</p><br>"
                    + "<a href=\"http://localhost:8080/ToDoAppClient/confirminvitation/" + toDoList.getId() + "\">http://localhost:8080/ToDoAppClient/confirminvitation/" + toDoList.getId() + "</a><br>\n"
                    + "<p>Regards,<br> Suguru Tokuda</p>\n"
                    + "</body>\n"
                    + "<html>";
            
            message.setContent(body, "text/html");
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sendConfirmationEmail(User user) {
        properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));
            message.setSubject("ToDoApp: Confirmation Email");
            body = "<html>\n"
                    + "<body>\n"
                    + "<p>Dear " + user.getFirstname() + ",</p>\n"
                    + "<p>Thanks for sign up. You have another step before starting using the app. "
                    + "Please confirm sign up from the link below:</p><br>"
                    + "<a href=\"http://localhost:8080/ToDoAppClient/confirmregistration/" + user.getId() + "\">http://localhost:8080/ToDoAppClient/confirmregistration/" + user.getId() + "</a><br>\n"
                    + "<p>Regards,<br> Suguru Tokuda</p>\n"
                    + "</body>\n"
                    + "<html>\n";
            message.setContent(body, "text/html");
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
