package com.heinunez.iot.producer;

import com.heinunez.iot.avro.SensorDataAvro;
import com.heinunez.kafka.producer.BaseProducer;
import com.opencsv.CSVReaderBuilder;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SensorDataProducer extends BaseProducer<String, SensorDataAvro> {

  private static final Logger log = LogManager.getLogger(SensorDataProducer.class);

  public SensorDataProducer() {
    super(StringSerializer.class, KafkaAvroSerializer.class);
  }

  @Override
  public List<SensorDataAvro> getRecords() {
    return loadFromCsv();
  }

  private List<SensorDataAvro> loadFromCsv() {

    try (var fileReader =
            new FileReader(SensorDataProducer.class.getResource("/sensor_data.csv").getFile());
        var csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build()) {

      return csvReader.readAll().stream()
          .map(
              r ->
                  SensorDataAvro.newBuilder()
                      .setSensorId(r[0])
                      .setRoom(r[1])
                      .setType(r[2])
                      .setTemperature(Double.parseDouble(r[3]))
                      .setTimestamp(Long.parseLong(r[4]))
                      .build())
          .toList();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return Collections.emptyList();
  }

  public static void main(String[] args) {
    var producer = new SensorDataProducer();
    log.info("sending sensor data");
    producer.send("sensor-data");
    log.info("done sending sensor data");
  }
}
