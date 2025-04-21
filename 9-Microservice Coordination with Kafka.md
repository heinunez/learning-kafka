# Microservice Coordination with Kafka

## Description
Orchestrate communication and coordination between multiple backend microservices using Kafka as the event bus. This project demonstrates event-driven microservice architecture, inter-service communication, and decoupling using Java and Docker. The solution is backend-focused, leverages the Java ecosystem, and is fully testable on a local machine.

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
- (Optional) Spring Boot for microservices
- Kafka CLI tools

## Step-by-Step Guide (General)
1. Set up Kafka and Zookeeper using Docker.
2. Scaffold a Java Maven/Gradle project for the microservices.
3. Add Kafka and Jackson libraries as dependencies.
4. Create topics for inter-service communication (e.g., `orders`, `inventory`, `shipping`).
5. Implement multiple Java microservices, each producing and consuming events from different topics.
6. Simulate a workflow (e.g., order placed → inventory checked → shipping initiated).
7. Use consumer groups for scalability and reliability.
8. Monitor event flow and topic stats with CLI tools.
9. (Optional) Add unit/integration tests for microservice logic.

<details>
<summary>Detailed Guide (Spoiler)</summary>

### 1. Set up Kafka and Zookeeper with Docker
- Use Docker Compose as in previous projects.

### 2. Scaffold Java Project & Add Dependencies
- Use Maven or Gradle to create a new Java project for each microservice.
- Add dependencies for Kafka client and Jackson in `pom.xml` or `build.gradle`.

### 3. Create Topics
- Use CLI: `docker exec -it <kafka_container_id> kafka-topics --create --topic orders --bootstrap-server localhost:9092 --partitions 2 --replication-factor 1`
- Repeat for `inventory`, `shipping`, etc.

### 4. Implement Java Microservices
- Each service (order, inventory, shipping) acts as a Kafka producer and consumer.
- Serialize events as JSON using Jackson.
- Simulate a workflow by producing and consuming events across services.

### 5. Use Consumer Groups
- Run multiple instances for scalability and reliability.

### 6. Monitor with CLI Tools
- Use `kafka-console-consumer` to view events in each topic.
- Use `kafka-topics --describe` for topic info.

### 7. (Optional) Add Unit/Integration Tests
- Use JUnit/Testcontainers to test microservice logic.

</details>

## Validation Steps
1. Confirm Kafka and Zookeeper are running via Docker.
2. Verify all required topics are created.
3. Ensure each Java microservice produces and consumes events as intended.
4. Simulate a complete workflow and verify event flow.
5. Use CLI tools to inspect event flow and topic status.
6. Check application logs for errors and test results.
