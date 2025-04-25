package com.heinunez.iot.stream;

import com.heinunez.iot.avro.SensorAggregateAvro;
import com.heinunez.iot.avro.SensorDataAvro;
import org.apache.kafka.streams.kstream.Aggregator;

public class SensorAggregator implements Aggregator<String, SensorDataAvro, SensorAggregateAvro> {

  @Override
  public SensorAggregateAvro apply(String key, SensorDataAvro data, SensorAggregateAvro agg) {
    agg.setSum(agg.getSum() + data.getTemperature());
    agg.setCount(agg.getCount() + 1);

    agg.setAvg(agg.getSum() / agg.getCount());

    return agg;
  }
}
