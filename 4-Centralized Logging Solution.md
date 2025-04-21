# Centralized Logging Solution

## Description
Aggregate logs from multiple local applications or services into Kafka, process and visualize logs for real-time monitoring and troubleshooting. This project demonstrates Kafka’s use as a log aggregation backbone, leveraging Java, Docker, and Kafka Connect. The solution is backend-focused, leverages the Java ecosystem, and is fully testable on a local machine.

## Topics Covered
- Kafka fundamentals (brokers, topics, partitions)
- Java producer and consumer APIs
- Log data serialization (JSON)
- Kafka Connect basics (file/source connectors)
- Consumer groups and offsets
- Real-time log processing and filtering
- Monitoring with Kafka CLI tools
- Project structure and dependency management

## Tools
- Java (using the official Kafka client library: [org.apache.kafka:kafka-clients](https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients))
- Docker (for Kafka, Zookeeper, Kafka Connect)
- Logback ([ch.qos.logback:logback-classic](https://mvnrepository.com/artifact/ch.qos.logback/logback-classic)) or Log4j ([org.apache.logging.log4j:log4j-core](https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core))
- Jackson ([com.fasterxml.jackson.core:jackson-databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind))
- Maven or Gradle (for dependency management)
- Kafka Connect (Docker)
- Kafka CLI tools

## Step-by-Step Guide (General)
1. Set up Kafka, Zookeeper, and Kafka Connect using Docker.
2. Create a `logs` topic.
3. Scaffold a Java Maven/Gradle project for log producers/consumers.
4. Add Kafka, Jackson, and Logback/Log4j libraries as dependencies.
5. Configure Java applications to produce logs to Kafka (using Logback/Log4j Kafka appender or custom producer).
6. Use Kafka Connect FileSource connector to ingest logs from files.
7. Implement a Java consumer to process and visualize logs (e.g., print or filter by log level).
8. Monitor log flow and topic stats with CLI tools.
9. (Optional) Add unit/integration tests for log producer and consumer logic.

<details>
<summary>Detailed Guide (Spoiler)</summary>

### 1. Set up Kafka, Zookeeper, and Kafka Connect with Docker
- Use Docker Compose to spin up all required services.

### 2. Create the Topic
- Use CLI: `docker exec -it <kafka_container_id> kafka-topics --create --topic logs --bootstrap-server localhost:9092 --partitions 2 --replication-factor 1`

### 3. Scaffold Java Project & Add Dependencies
- Use Maven or Gradle to create a new Java project.
- Add dependencies for Kafka client, Jackson, and Logback/Log4j in `pom.xml` or `build.gradle`.

### 4. Configure Java Log Producer
- Use Logback/Log4j with a Kafka appender or write a custom Java producer.
- Serialize log events as JSON using Jackson.

### 5. Kafka Connect FileSource
- Use Kafka Connect FileSource connector to stream logs from a file into the `logs` topic.

### 6. Java Log Consumer/Visualizer
- Consume from `logs` topic.
- Filter or aggregate logs (e.g., by level, application).
- Print or visualize logs in the console or a simple dashboard.

### 7. Monitor with CLI Tools
- Use `kafka-console-consumer` to view logs: `docker exec -it <kafka_container_id> kafka-console-consumer --topic logs --from-beginning --bootstrap-server localhost:9092`
- Use `kafka-topics --describe` for topic info.

### 8. (Optional) Add Unit/Integration Tests
- Use JUnit/Testcontainers to test log producer and consumer logic.

</details>

## Validation Steps
1. Confirm all services are running via Docker.
2. Verify `logs` topic creation.
3. Ensure Java log producers send events to Kafka.
4. Use Kafka Connect to ingest file logs.
5. Ensure Java consumer processes and visualizes logs.
6. Use CLI tools to inspect log flow and topic status.
7. Check application logs for errors and test results.
