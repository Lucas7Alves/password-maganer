package service;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import util.ConfigLoader;

/**
 * Serviço responsável pelo envio de e-mails com o token 2FA.
 */
public class EmailService {

    private final String from;
    private final String password;

    /**
     * Envia um e-mail com o token de autenticação 2FA.
     * @param to E-mail do destinatário
     * @param token Token a ser enviado
     */
    public void sendTokenEmail(String to, String token) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Seu código 2FA");
            message.setText("Seu código é: " + token);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Construtor que carrega as credenciais de e-mail do arquivo de configuração.
     */
    public EmailService() {
        Properties config = ConfigLoader.getProperties();
        this.from = config.getProperty("EMAIL_FROM");
        this.password = config.getProperty("EMAIL_PASSWORD");
    }
}
