package uz.suxa.postdbservice.service;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Service
public class KafkaService {

    @Value("${bootstrap.servers}")
    private String BOOTSTRAP_SERVERS;
    private static final String TOPIC = "currency-rate";

    private volatile boolean keepConsuming = true;

    private final CurrencyRateService currencyRateService;

    public KafkaService(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    @Async
    public void consumeAsync() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(setupProperties());
        consumer.subscribe(List.of(TOPIC));

        while (keepConsuming) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(250));
            consumerRecords.forEach(record -> currencyRateService.save(record.value()));
            consumer.commitAsync();
        }
        consumer.close();
    }


    private Properties setupProperties() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "postgresDB");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return properties;
    }
}
