package com.heinunez.iot.stream;

import com.heinunez.iot.avro.SensorAggregateAvro;
import com.heinunez.iot.avro.SensorDataAvro;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SensorDataStream {

  private static final Logger log = LogManager.getLogger(SensorDataStream.class);

  private Properties properties() {
    var properties = new Properties();
    properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "sensor_stream_app");
    properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

    return properties;
  }

  private Topology topology(Properties properties) {

    var builder = new StreamsBuilder();
    var serdeConfig =
        Collections.singletonMap(
            AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

    var sensorDataSerde = new SpecificAvroSerde<SensorDataAvro>();
    var aggregateSerde = new SpecificAvroSerde<SensorAggregateAvro>();
    var initialAggregate = SensorAggregateAvro.newBuilder().build();

    sensorDataSerde.configure(serdeConfig, false);
    aggregateSerde.configure(serdeConfig, false);

    var sensorStream =
        builder.stream("sensor-data", Consumed.with(Serdes.String(), sensorDataSerde));

    sensorStream
        .groupBy((k, v) -> v.getRoom().toString(), Grouped.with(Serdes.String(), sensorDataSerde))
        .aggregate(
            () -> initialAggregate,
            new SensorAggregator(),
            Materialized.with(Serdes.String(), aggregateSerde))
        .toStream()
        .print(Printed.toSysOut());

    return builder.build(properties);
  }

  public void start() throws InterruptedException {
    var props = properties();
    var topology = topology(props);
    log.info("topology desc {}", topology.describe());

    var doneLatch = new CountDownLatch(1);
    try (var streams = new KafkaStreams(topology, props)) {
      streams.start();
      doneLatch.await(10, TimeUnit.SECONDS);
      log.info("shutting down");
    }
  }

  public static void main(String[] args) throws InterruptedException {
    var streamApp = new SensorDataStream();
    streamApp.start();
  }
}
