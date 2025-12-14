package net.engineeringdigest.journalapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JournalApplication.class, args);
        System.out.println("present environments :- " +  Arrays.toString(context.getEnvironment().getActiveProfiles()));
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