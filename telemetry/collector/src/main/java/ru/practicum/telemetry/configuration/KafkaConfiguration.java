package ru.practicum.telemetry.configuration;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfiguration {

    @Value("${my.kafka.bootstrap-server}")
    private String bootstrapServer;

    @Value("${my.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${my.kafka.producer.value-serializer}")
    private String valueSerializer;

    @Bean
    KafkaProducer<String, SpecificRecordBase> kafkaProducer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        return new KafkaProducer<>(properties);
    }
}
