package com.heinunez.kafka.consumer;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseConsumer {

  private static final Logger log = LogManager.getLogger(BaseConsumer.class);
  private final Class<?> keyDeserializer;
  private final Class<?> valueDeserializer;

  public BaseConsumer(Class<?> keyDeserializer, Class<?> valueDeserializer) {
    this.keyDeserializer = keyDeserializer;
    this.valueDeserializer = valueDeserializer;
  }

  public <K, V> void runConsumer(
      final Map<String, Object> configs,
      final String topic,
      final Consumer<ConsumerRecords<K, V>> recordsHandler) {
    runConsumer(configs, Collections.singletonList(topic), recordsHandler);
  }

  public <K, V> void runConsumer(
      final Map<String, Object> configs,
      final List<String> topics,
      final Consumer<ConsumerRecords<K, V>> recordsHandler) {
    var genericConfigs = overrideConfigs(configs);

    log.info("getting ready to consume records");

    boolean notDoneConsuming = true;
    int noRecordsCount = 0;

    try (final var consumer = new KafkaConsumer<K, V>(genericConfigs)) {
      consumer.subscribe(topics);
      while (notDoneConsuming) {
        var consumerRecords = consumer.poll(Duration.ofSeconds(10));

        if (consumerRecords.isEmpty()) {
          noRecordsCount++;
        }
        recordsHandler.accept(consumerRecords);

        if (noRecordsCount >= 2) {
          notDoneConsuming = false;
          log.info("two passes and no records, setting quit flag");
        }
      }
      log.info("all done consumig records now");
    }
  }

  public Map<String, Object> overrideConfigs(final Map<String, Object> overrideConfigs) {
    return consumerConfig(overrideConfigs);
  }

  private Map<String, Object> consumerConfig(final Map<String, Object> overrides) {
    final var consumerProps = new HashMap<String, Object>();
    consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    consumerProps.put(
        AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
    consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
    consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
    consumerProps.putAll(overrides);

    return consumerProps;
  }
}
