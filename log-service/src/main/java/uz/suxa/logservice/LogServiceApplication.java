package uz.suxa.logservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uz.suxa.logservice.service.KafkaService;

@SpringBootApplication
public class LogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(KafkaService kafkaService) {
        return args -> {
            kafkaService.consumeAsync();
        };
    }

}
