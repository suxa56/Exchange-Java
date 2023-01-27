package uz.suxa.connecterservice.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.suxa.grpc.LogServiceGrpc;
import uz.suxa.grpc.LogServiceOuterClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Service
public class HistoryService {

    @GrpcClient("log")
    private LogServiceGrpc.LogServiceBlockingStub blockingStub;

    private static final List<String> logs = new ArrayList<>();

    @Value("${bootstrap.servers}")
    private String BOOTSTRAP_SERVERS;
    private static final String TOPIC_HISTORY = "history";

    @Async
    public CompletableFuture<List<String>> getLogs() {
        LogServiceOuterClass.LogRequest request = LogServiceOuterClass.LogRequest
                .newBuilder().setCommand("logs")
                .build();


        Iterator<LogServiceOuterClass.LogResponse> responses = blockingStub.receive(request);
        while (responses.hasNext()) {
            LogServiceOuterClass.LogResponse response = responses.next();
            logs.add(response.getLog());
        }
        return CompletableFuture.completedFuture(logs);
    }

    public void save(String history) {
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(setupProperties())) {
            ProducerRecord<String, String> producerRecord =
                    new ProducerRecord<>(TOPIC_HISTORY, history);
            producer.send(producerRecord);
        }
    }

    private Properties setupProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
