package com.heinunez.analytics.producer;

import com.heinunez.analytics.avro.UserActivityAvro;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserActivityProducer extends BaseProducer<String, UserActivityAvro> {

  private static final Logger log = LogManager.getLogger(UserActivityProducer.class);

  public UserActivityProducer() {
    super(StringSerializer.class, KafkaAvroSerializer.class);
  }

  @Override
  public List<UserActivityAvro> getRecords() {
    final var acts = new ArrayList<UserActivityAvro>();

    IntStream.range(1, 11)
        .forEach(
            a -> {
              final var act =
                  UserActivityAvro.newBuilder()
                      .setUserId("u" + a)
                      .setPage("/home" + a)
                      .setTimestamp(System.currentTimeMillis())
                      .build();

              acts.add(act);
            });

    log.info("created {} activities", acts.size());

    return acts;
  }

  public static void main(String[] args) {
    var producer = new UserActivityProducer();
    log.info("sending activities");
    producer.send("user-activity");
    log.info("done sending activities");
  }
}
