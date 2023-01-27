package uz.suxa.connecterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class ConnecterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConnecterServiceApplication.class, args);
    }
}
