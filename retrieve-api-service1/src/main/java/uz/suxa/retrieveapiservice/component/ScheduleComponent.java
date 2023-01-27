package uz.suxa.retrieveapiservice.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.suxa.retrieveapiservice.service.ApiService;
import uz.suxa.retrieveapiservice.service.KafkaService;

import java.util.concurrent.TimeUnit;

@Component
public class ScheduleComponent {

    private final ApiService apiService;
    private final KafkaService kafkaService;

    public ScheduleComponent(ApiService apiService, KafkaService kafkaService) {
        this.apiService = apiService;
        this.kafkaService = kafkaService;
    }

//    Prod
//    @Scheduled(fixedDelay = 3L, timeUnit = TimeUnit.HOURS)
//    Test
    @Scheduled(fixedDelay = 3L, timeUnit = TimeUnit.MINUTES)
    public void retrieve()  {
        apiService.retrieveApi().forEach(kafkaService::send);
    }
}
