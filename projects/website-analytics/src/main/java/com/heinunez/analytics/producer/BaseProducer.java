package com.heinunez.analytics.producer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;

public abstract class BaseProducer<K, V> {
  private final Class<?> keySerializer;
  private final Class<?> valueSerializer;
  private Map<String, Object> overrideConfigs = new HashMap<>();

  public BaseProducer(final Class<?> keySerializer, final Class<?> valueSerializer) {
    this.keySerializer = keySerializer;
    this.valueSerializer = valueSerializer;
  }

  public abstract List<V> getRecords();

  public void overrideConfigs(final Map<String, Object> overrideConfigs) {
    this.overrideConfigs = overrideConfigs;
  }

  public void send(final String topicName) {
    var records = getRecords();
    var producerConfigs = producerConfig(overrideConfigs);
    try (final var producer = new KafkaProducer<K, V>(producerConfigs)) {
      records.forEach(r -> producer.send(new ProducerRecord<>(topicName, r)));
    }
  }

  private Map<String, Object> producerConfig(final Map<String, Object> overrides) {
    final Map<String, Object> producerProps = new HashMap<>();
    producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
    producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
    producerProps.put(KafkaAvroSerializerConfig.AUTO_REGISTER_SCHEMAS, false);
    producerProps.put(KafkaAvroSerializerConfig.USE_LATEST_VERSION, true);
    producerProps.put(
        AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

    producerProps.putAll(overrides);
    return producerProps;
  }
}
