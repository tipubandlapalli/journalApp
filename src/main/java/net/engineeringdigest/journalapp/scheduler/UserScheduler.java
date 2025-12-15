package net.engineeringdigest.journalapp.scheduler;

import net.engineeringdigest.journalapp.entity.Journal;
import net.engineeringdigest.journalapp.entity.UserEntity;
import net.engineeringdigest.journalapp.repository.user.UserEntityForSARepository;
import net.engineeringdigest.journalapp.service.EmailService;
import net.engineeringdigest.journalapp.service.SentimentAnalysisService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class UserScheduler {

    private final SentimentAnalysisService sentimentAnalysisService;
    private final EmailService emailService;
    private final UserEntityForSARepository userEntityForSARepository;


    public UserScheduler(SentimentAnalysisService sentimentAnalysisService, EmailService emailService, UserEntityForSARepository userEntityForSARepository) {
        this.sentimentAnalysisService = sentimentAnalysisService;
        this.emailService = emailService;
        this.userEntityForSARepository = userEntityForSARepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void sendEmails(){
        List<UserEntity> users = userEntityForSARepository.getAllUsersForSA();
        for(UserEntity user: users){
            String str = user.getJournals()
                    .stream()
                    .filter(j -> j.getLocalDateTime().isAfter(LocalDateTime.now().minusDays(7)))
                    .map(Journal::getContent)
                    .collect(Collectors.joining("; "));
            emailService.sendMail(user.getEmail(), " Sentiment Analysis", "last 7 days your sentiment" +  sentimentAnalysisService.fetch(str));
        }
    }
}
