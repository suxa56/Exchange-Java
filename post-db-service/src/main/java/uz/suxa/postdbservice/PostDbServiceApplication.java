package uz.suxa.postdbservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import uz.suxa.postdbservice.service.KafkaService;

@SpringBootApplication
@EnableAsync
public class PostDbServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostDbServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunnerBean(KafkaService kafkaService) {
        return (args) -> kafkaService.consumeAsync();
    }
}
