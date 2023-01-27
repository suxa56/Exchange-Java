package uz.suxa.retrieveapiservice.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class KafkaService {

//    private final Environment env;
//
//    public KafkaService(Environment env) {
//        this.env = env;
//    }
    @Value("${bootstrap.servers}")
    private String BOOTSTRAP_SERVERS;
    private static final String TOPIC_CURRENCY = "currency-rate";

    public void send(String currencyRate) {
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(setupProducer())) {
                ProducerRecord<String, String> producerRecord =
                        new ProducerRecord<>(TOPIC_CURRENCY, currencyRate);
                producer.send(producerRecord);
        }
    }

    private Properties setupProducer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
