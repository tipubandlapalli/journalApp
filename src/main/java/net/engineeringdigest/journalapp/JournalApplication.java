package net.engineeringdigest.journalapp;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Arrays;

@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class JournalApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JournalApplication.class, args);
        log.info("present environments :- {}",  Arrays.toString(context.getEnvironment().getActiveProfiles()));
    }
    @Bean
    public PlatformTransactionManager add(MongoDatabaseFactory mongoDatabaseFactory){
        return new MongoTransactionManager(mongoDatabaseFactory);
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}