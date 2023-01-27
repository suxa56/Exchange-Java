package uz.suxa.retrieveapiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RetrieveApiService1Application {

    public static void main(String[] args) {
        SpringApplication.run(RetrieveApiService1Application.class, args);
    }
}
