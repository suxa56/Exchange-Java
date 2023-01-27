package uz.suxa.logservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.suxa.logservice.dto.ExchangeHistoryDto;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Service
public class KafkaService {

    @Value("${bootstrap.servers}")
    private String BOOTSTRAP_SERVERS;
    private static final String TOPIC = "history";
    private volatile boolean keepConsuming = true;

    private final HistoryService historyService;

    public KafkaService(HistoryService historyService) {
        this.historyService = historyService;
    }


    @Async
    public void consumeAsync() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(setupProperties());
        consumer.subscribe(List.of(TOPIC));

        while (keepConsuming) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(250));
            consumerRecords.forEach(record -> {
                ObjectMapper objectMapper = new ObjectMapper();

                try {
                    ExchangeHistoryDto dto = objectMapper.readValue(record.value(), ExchangeHistoryDto.class);
                    historyService.insert(dto);
                } catch (JsonProcessingException e) {
                    System.out.println("Cannot parse json");
                }
            });
            consumer.commitAsync();
        }
        consumer.close();
    }


    private Properties setupProperties() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "mongoDB");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return properties;
    }
}
