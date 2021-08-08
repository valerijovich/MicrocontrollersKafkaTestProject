package com.valerijovich.sender.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

// Настройка продюсера кафки
@Configuration
public class KafkaProducer {

    // Сервер Kafka берётся из application.properties
    @Value("${kafka.server}")
    private String kafkaServer;

    // Айди продюсера (берётся из application.properties), по которому потом подписчик сможет получать из него сообщения
    @Value("${kafka.producer.id}")
    private String kafkaProducerId;

    // Записываем в мапу конфигурационные настройки для продюсера
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerId);
        return props;
    }

    // Передаём созданную мапу producerConfigs в конструктор producerUserFactory для создания фабрики
    @Bean
    public ProducerFactory<Long, Integer> producerMessageFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    // Создаём фабрику
    @Bean
    public KafkaTemplate<Long, Integer> kafkaTemplate() {
        KafkaTemplate<Long, Integer> template = new KafkaTemplate<>(producerMessageFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }
}
