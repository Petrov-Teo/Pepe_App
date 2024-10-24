package PetrovTodor.PepeMedicalKids.services.emeil;

import PetrovTodor.PepeMedicalKids.payload.calendar.EventoGenericoDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendPasswordResetConfirmation(String to) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Password Reset Confirmation");
        helper.setText("<h1>La tua password è stata reimpostata con successo.</h1>", true);

        mailSender.send(message);
    }

    public void sendHtmlMessage(String to, String subject, String htmlText) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlText, true); // Imposta il secondo parametro su 'true' per indicare che il testo è in HTML

        mailSender.send(message);
    }

    public void sendEventNotification(String to, EventoGenericoDTO event) throws MessagingException {
        String subject = "Nuovo Evento: " + event.nome();
        String htmlBody = createHtmlEmailBody(event);  // Metodo per creare il corpo dell'email con i dettagli dell'evento
        sendHtmlMessage(to, subject, htmlBody);
    }

    private String createHtmlEmailBody(EventoGenericoDTO body) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return "<html>" +
                "<body>" +
                "<h1>Nuovo Evento: " + body.nome() + "</h1>" +
                "<p>Data Inizio: " + body.dataInizio().format(formatter) + "</p>" +
                "<p>Data Fine Ricorrenza: " + body.dataFineRicorrenza().format(formatter) + "</p>" +
                "<p>Ora Inizio: " + body.oraInizio() + "</p>" +
                "<p>Ora Fine: " + body.oraFine() + "</p>" +
                "<p>Luogo: " + body.luogo() + "</p>" +
                "<p>Note: " + body.note() + "</p>" +
                "</body>" +
                "</html>";
    }
}