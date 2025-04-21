# IoT Home Data Pipeline

## Description
Simulate IoT sensor data streaming from home devices and process/aggregate readings with Kafka Streams. This project demonstrates how to build a scalable, backend-friendly IoT data pipeline using Java and Docker, suitable for a backend software engineer. The solution leverages the Java ecosystem and is fully testable on a local machine.

## Topics Covered
- Kafka fundamentals (brokers, topics, partitions)
- Java producer and consumer APIs
- Message serialization (JSON/Avro)
- Simulating IoT device data
- Kafka Streams for real-time processing and aggregation
- Consumer groups and offsets
- Monitoring with Kafka CLI tools
- Project structure and dependency management

## Tools
- Java (using the official Kafka client library: [org.apache.kafka:kafka-clients](https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients))
- Docker (for running Kafka and Zookeeper)
- Jackson ([com.fasterxml.jackson.core:jackson-databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind)) or Avro ([org.apache.avro:avro](https://mvnrepository.com/artifact/org.apache.avro/avro))
- Kafka Streams ([org.apache.kafka:kafka-streams](https://mvnrepository.com/artifact/org.apache.kafka/kafka-streams))
- Maven or Gradle (for dependency management)
- Kafka CLI tools (kafka-topics.sh, kafka-console-producer.sh, kafka-console-consumer.sh)

## Step-by-Step Guide (General)
1. Set up Kafka and Zookeeper using Docker.
2. Create a `sensor-data` topic.
3. Scaffold a Java Maven/Gradle project for the pipeline.
4. Add Kafka, Kafka Streams, and serialization libraries as dependencies.
5. Implement a Java producer to simulate multiple IoT sensors (e.g., temperature, humidity, motion) and send readings to Kafka.
6. Serialize sensor events as JSON or Avro.
7. Implement a Kafka Streams Java application to process and aggregate sensor data (e.g., average temperature per room).
8. Consume and display the aggregated results.
9. Monitor sensor event flow and topic stats with CLI tools.
10. (Optional) Add unit/integration tests for producer, processor, and consumer logic.

<details>
<summary>Detailed Guide (Spoiler)</summary>

### 1. Set up Kafka and Zookeeper with Docker
- Use a Docker Compose file as in the previous project.

### 2. Create the Topic
- Use CLI: `docker exec -it <kafka_container_id> kafka-topics --create --topic sensor-data --bootstrap-server localhost:9092 --partitions 2 --replication-factor 1`

### 3. Scaffold Java Project & Add Dependencies
- Use Maven or Gradle to create a new Java project.
- Add dependencies for Kafka client, Kafka Streams, and Jackson/Avro in `pom.xml` or `build.gradle`.

### 4. Implement Java Sensor Producer
- Simulate sensors (e.g., `{ "sensorId": "temp-1", "room": "living", "type": "temperature", "value": 23.5, "timestamp": 1680000000000 }`).
- Serialize as JSON (Jackson) or Avro.
- Send events to `sensor-data` topic.

### 5. Kafka Streams Processing
- Use Kafka Streams API to consume `sensor-data` topic.
- Aggregate data (e.g., average temperature per room over time windows).
- Output results to another topic or log to console.

### 6. Consume Aggregated Results
- Implement a Java consumer for the output topic or stream.
- Display or log the results.

### 7. Monitor with CLI Tools
- Use `kafka-console-consumer` to view events: `docker exec -it <kafka_container_id> kafka-console-consumer --topic sensor-data --from-beginning --bootstrap-server localhost:9092`
- Use `kafka-topics --describe` for topic info.

### 8. (Optional) Add Unit/Integration Tests
- Use JUnit/Testcontainers to test producer, processor, and consumer logic.

</details>

## Validation Steps
1. Confirm Kafka and Zookeeper are running via Docker.
2. Verify `sensor-data` topic creation.
3. Ensure Java producer sends simulated sensor data to Kafka.
4. Ensure Kafka Streams app processes and aggregates data.
5. Consume and display aggregated results.
6. Use CLI tools to inspect event flow and topic status.
7. Check application logs for errors and test results.
