package com.heinunez.ecommerce.producer;

import com.heinunez.ecommerce.avro.OrderAvro;
import com.heinunez.ecommerce.avro.PaymentAvro;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

record TransRecord(
    ProducerRecord<String, SpecificRecord> order, ProducerRecord<String, SpecificRecord> payment) {}

public class TransactionalProducer {

  private static final Logger log = LogManager.getLogger(TransactionalProducer.class);

  public void sendTransactions() {
    var records = createRecords();
    try (var producer = new KafkaProducer<String, SpecificRecord>(config())) {
      log.info("init transactions");
      producer.initTransactions();
      producer.beginTransaction();
      for (var r : records) {
        producer.send(r.order());
        producer.send(r.payment());
      }
      producer.commitTransaction();
      log.info("commit");
    }
  }

  private List<TransRecord> createRecords() {
    var o1 =
        OrderAvro.newBuilder()
            .setUserId("u1")
            .setOrderId("o1")
            .setAmount(999.9)
            .setTimestamp(System.currentTimeMillis())
            .build();
    var p1 =
        PaymentAvro.newBuilder()
            .setOrderId(o1.getOrderId())
            .setStatus("ok")
            .setPaymentId("p1")
            .setTimestamp(System.currentTimeMillis())
            .build();

    var o2 =
        OrderAvro.newBuilder()
            .setUserId("u1")
            .setOrderId("o2")
            .setAmount(999.9)
            .setTimestamp(System.currentTimeMillis())
            .build();
    var p2 =
        PaymentAvro.newBuilder()
            .setOrderId(o2.getOrderId())
            .setStatus("ok")
            .setPaymentId("p2")
            .setTimestamp(System.currentTimeMillis())
            .build();
    var o3 =
        OrderAvro.newBuilder()
            .setUserId("u3")
            .setOrderId("o3")
            .setAmount(999.9)
            .setTimestamp(System.currentTimeMillis())
            .build();
    var p3 =
        PaymentAvro.newBuilder()
            .setOrderId(o3.getOrderId())
            .setStatus("ok")
            .setPaymentId("p3")
            .setTimestamp(System.currentTimeMillis())
            .build();

    return Arrays.asList(
        new TransRecord(new ProducerRecord<>("orders", o1), new ProducerRecord<>("payments", p1)),
        new TransRecord(new ProducerRecord<>("orders", o2), new ProducerRecord<>("payments", p2)),
        new TransRecord(new ProducerRecord<>("orders", o3), new ProducerRecord<>("payments", p3)));
  }

  private Map<String, Object> config() {
    final Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
    props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "payment-trans");
    props.put(KafkaAvroSerializerConfig.AUTO_REGISTER_SCHEMAS, false);
    props.put(KafkaAvroSerializerConfig.USE_LATEST_VERSION, true);
    props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

    return props;
  }

  public static void main(String[] args) {
    var producer = new TransactionalProducer();
    producer.sendTransactions();
  }
}
