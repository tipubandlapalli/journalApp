package net.engineeringdigest.journalapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {
    private static final String EMAIL_FOOTER =
            "\n\n---\n" + // Separator
                    "This is an automated, computer-generated email from the JournalApp. " +
                    "Please do not reply to this message.";

    private final JavaMailSender javaMailSender;

    public void sendMail(String to, String subject, String body){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(to);
                simpleMailMessage.setSubject(subject);
                simpleMailMessage.setText(body + EMAIL_FOOTER);
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e){
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
