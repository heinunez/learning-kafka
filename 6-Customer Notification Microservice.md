# Customer Notification Microservice

## Description
Implement an event-driven notification system (email/SMS/push) powered by Kafka topics and consumers. This project demonstrates how to build a backend notification microservice using Java, Docker, and Kafka, with a focus on scalable, decoupled design. The solution is backend-focused, leverages the Java ecosystem, and is fully testable on a local machine.

## Topics Covered
- Kafka fundamentals (brokers, topics, partitions)
- Java producer and consumer APIs
- Event-driven microservice architecture
- Message serialization (JSON)
- Consumer groups and offsets
- Monitoring with Kafka CLI tools
- Project structure and dependency management

## Tools
- Java (using the official Kafka client library: [org.apache.kafka:kafka-clients](https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients))
- Docker (for running Kafka and Zookeeper)
- Jackson ([com.fasterxml.jackson.core:jackson-databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind))
- Maven or Gradle (for dependency management)
- (Optional) Spring Boot for REST endpoints
- Kafka CLI tools

## Step-by-Step Guide (General)
1. Set up Kafka and Zookeeper using Docker.
2. Scaffold a Java Maven/Gradle project for the microservice.
3. Add Kafka and Jackson libraries as dependencies.
4. Create a `notifications` topic.
5. Implement a Java producer to send notification events (e.g., email, SMS, push) to Kafka.
6. Implement a Java consumer microservice to process notification events and simulate sending notifications (e.g., print to console or log).
7. Use consumer groups for scalability and reliability.
8. Monitor event flow and topic stats with CLI tools.
9. (Optional) Add unit/integration tests for producer and consumer logic.

<details>
<summary>Detailed Guide (Spoiler)</summary>

### 1. Set up Kafka and Zookeeper with Docker
- Use Docker Compose as in previous projects.

### 2. Scaffold Java Project & Add Dependencies
- Use Maven or Gradle to create a new Java project.
- Add dependencies for Kafka client and Jackson in `pom.xml` or `build.gradle`.

### 3. Create the Topic
- Use CLI: `docker exec -it <kafka_container_id> kafka-topics --create --topic notifications --bootstrap-server localhost:9092 --partitions 2 --replication-factor 1`

### 4. Implement Java Notification Producer
- Simulate notification events (e.g., `{ "userId": "u1", "type": "email", "message": "Welcome!", "timestamp": 1680000000000 }`).
- Serialize as JSON using Jackson.
- Send events to `notifications` topic.

### 5. Implement Java Notification Consumer Microservice
- Consume from `notifications` topic.
- Simulate sending notifications (e.g., print/log messages).
- Use consumer groups for reliability and scalability.

### 6. Monitor with CLI Tools
- Use `kafka-console-consumer` to view notification events.
- Use `kafka-topics --describe` for topic info.

### 7. (Optional) Add Unit/Integration Tests
- Use JUnit/Testcontainers to test producer and consumer logic.

</details>

## Validation Steps
1. Confirm Kafka and Zookeeper are running via Docker.
2. Verify `notifications` topic creation.
3. Ensure Java producer sends notification events to Kafka.
4. Ensure Java consumer microservice processes and simulates notifications.
5. Run multiple consumer instances and observe scalability.
6. Use CLI tools to inspect event flow and topic status.
7. Check application logs for errors and test results.
