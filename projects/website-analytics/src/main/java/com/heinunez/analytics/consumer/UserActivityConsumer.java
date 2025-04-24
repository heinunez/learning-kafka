package com.heinunez.analytics.consumer;

import com.heinunez.analytics.avro.UserActivityAvro;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import java.util.HashMap;
import java.util.function.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserActivityConsumer extends BaseConsumer {

  private static final Logger log = LogManager.getLogger(UserActivityConsumer.class);

  public UserActivityConsumer() {
    super(StringDeserializer.class, KafkaAvroDeserializer.class);
  }

  public static void main(String[] args) {
    final var topicName = "user-activity";
    final var consumer = new UserActivityConsumer();
    final var configs = new HashMap<String, Object>();

    configs.put(ConsumerConfig.GROUP_ID_CONFIG, "user-activity-group");
    configs.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

    var aggregate = new HashMap<CharSequence, Integer>();

    Consumer<ConsumerRecords<String, UserActivityAvro>> recordsHandler =
        (consumerRecords ->
            consumerRecords.forEach(
                cr -> {
                  var ua = cr.value();
                  log.info(
                      "found user activity {} - {} - {}",
                      ua.getUserId(),
                      ua.getPage(),
                      ua.getTimestamp());
                  aggregate.put(ua.getPage(), aggregate.getOrDefault(ua.getPage(), 0) + 1);
                }));

    consumer.runConsumer(configs, topicName, recordsHandler);
    log.info("Aggregated\n{}", aggregate);
  }
}
