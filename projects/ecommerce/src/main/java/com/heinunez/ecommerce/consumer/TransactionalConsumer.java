package com.heinunez.ecommerce.consumer;

import com.heinunez.kafka.consumer.BaseConsumer;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransactionalConsumer extends BaseConsumer {
  private static final Logger log = LogManager.getLogger(TransactionalConsumer.class);

  public TransactionalConsumer() {
    super(StringDeserializer.class, KafkaAvroDeserializer.class);
  }

  public static void main(String[] args) {
    var topics = Arrays.asList("orders", "payments");
    final var consumer = new TransactionalConsumer();
    final var configs = new HashMap<String, Object>();

    configs.put(ConsumerConfig.GROUP_ID_CONFIG, "ecommerce-transaction-group");
    configs.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
    configs.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
    final var consumerBuilder = new StringBuilder();

    Consumer<ConsumerRecords<String, GenericRecord>> recordsHandler =
        consumerRecords ->
            consumerRecords.forEach(
                cr -> {
                  final var genericRecord = cr.value();
                  if (genericRecord.hasField("userId")) {
                    consumerBuilder.append("order " + genericRecord.get("orderId"));
                  }
                  if (genericRecord.hasField("paymentId")) {
                    consumerBuilder.append("payment " + genericRecord.get("paymentId"));
                  }
                  log.info(consumerBuilder.toString());
                  consumerBuilder.setLength(0);
                });

    consumer.runConsumer(configs, topics, recordsHandler);
  }
}
