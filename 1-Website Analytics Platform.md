# Website Analytics Platform

## Description
Track and analyze website user activity in real time using Kafka for event ingestion and processing. This project guides backend software engineers through building a robust backend pipeline that collects clickstream/event data, streams it through Kafka, and processes it for analytics—all using Java and Docker. The design is scalable, testable, and backend-focused, leveraging the Java ecosystem.

## Topics Covered
- Kafka fundamentals (brokers, topics, partitions)
- Java producer and consumer APIs
- Message serialization (JSON/Avro)
- Real-time event ingestion and processing
- Consumer groups and offsets
- Basic stream processing (aggregation, counting)
- Monitoring with Kafka CLI tools
- Project structure and dependency management

## Tools
- Java (using the official Kafka client library: [org.apache.kafka:kafka-clients](https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients))
- Docker (for running Kafka and Zookeeper)
- Jackson ([com.fasterxml.jackson.core:jackson-databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind)) or Avro ([org.apache.avro:avro](https://mvnrepository.com/artifact/org.apache.avro/avro))
- Maven or Gradle (for dependency management)
- Kafka CLI tools (kafka-topics.sh, kafka-console-producer.sh, kafka-console-consumer.sh)
- (Optional) Spring Boot for REST endpoints

## Step-by-Step Guide (General)
1. Set up Kafka and Zookeeper using Docker.
2. Create a `user-activity` topic.
3. Scaffold a Java Maven/Gradle project for the backend pipeline.
4. Add Kafka and serialization libraries as dependencies.
5. Implement a Java producer to send simulated website events (e.g., page views, clicks) to Kafka.
6. Implement a Java consumer to process and aggregate events (e.g., count events per page/user).
7. Use consumer groups for scalable processing.
8. Monitor event flow and topic stats with CLI tools.
9. (Optional) Add unit/integration tests for producer and consumer logic.

<details>
<summary>Detailed Guide (Spoiler)</summary>

### 1. Set up Kafka and Zookeeper with Docker
- Use a Docker Compose file to spin up Kafka and Zookeeper:
  ```yaml
  version: '2'
  services:
    zookeeper:
      image: confluentinc/cp-zookeeper:latest
      environment:
        ZOOKEEPER_CLIENT_PORT: 2181
    kafka:
      image: confluentinc/cp-kafka:latest
      ports:
        - "9092:9092"
      environment:
        KAFKA_BROKER_ID: 1
        KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
        KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
        KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
  ```
- Start with: `docker-compose up -d`

### 2. Create the Topic
- Use CLI: `docker exec -it <kafka_container_id> kafka-topics --create --topic user-activity --bootstrap-server localhost:9092 --partitions 2 --replication-factor 1`

### 3. Scaffold Java Project & Add Dependencies
- Use Maven or Gradle to create a new Java project.
- Add dependencies for Kafka client and Jackson/Avro in `pom.xml` or `build.gradle`.

### 4. Implement Java Producer
- Simulate events (e.g., `{ "userId": "u1", "page": "/home", "timestamp": 1680000000000 }`).
- Serialize as JSON (Jackson) or Avro.
- Send events to `user-activity` topic.

### 5. Implement Java Consumer
- Consume from `user-activity` topic.
- Aggregate events (e.g., count per page/user).
- Print or log analytics results.

### 6. Use Consumer Groups
- Run multiple consumer instances to demonstrate partition assignment and scalability.

### 7. Monitor with CLI Tools
- Use `kafka-console-consumer` to view events: `docker exec -it <kafka_container_id> kafka-console-consumer --topic user-activity --from-beginning --bootstrap-server localhost:9092`
- Use `kafka-topics --describe` for topic info.

### 8. (Optional) Add Unit/Integration Tests
- Use JUnit/Testcontainers to test producer and consumer logic.

</details>

## Validation Steps
1. Confirm Kafka and Zookeeper are running via Docker.
2. Verify `user-activity` topic creation.
3. Ensure Java producer sends simulated events to Kafka.
4. Ensure Java consumer receives and aggregates events.
5. Run multiple consumers and observe partition assignment.
6. Validate analytics output (counts per page/user).
7. Use CLI tools to inspect event flow and topic status.
8. Check application logs for errors and test results.
