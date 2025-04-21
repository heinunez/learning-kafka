# E-commerce Order Processing

## Description
Build an order pipeline for an e-commerce backend using Kafka. This project demonstrates transactional event processing, exactly-once guarantees, and schema validation. You'll implement robust Java producers and consumers, manage schemas, and ensure data consistency—all using Docker and Java. The design is backend-focused, leveraging the Java ecosystem and is fully testable on a local machine.

## Topics Covered
- Kafka fundamentals (brokers, topics, partitions)
- Java producer and consumer APIs
- Transactions and exactly-once delivery
- Schema Registry and Avro/JSON schemas
- Programmatic topic management (Admin API)
- Handling multiple event types
- Monitoring with Kafka CLI tools
- Project structure and dependency management

## Tools
- Java (using the official Kafka client library: [org.apache.kafka:kafka-clients](https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients))
- Docker (for running Kafka, Zookeeper, and Schema Registry)
- Confluent Schema Registry ([io.confluent:kafka-schema-registry-client](https://mvnrepository.com/artifact/io.confluent/kafka-schema-registry-client))
- Avro ([org.apache.avro:avro](https://mvnrepository.com/artifact/org.apache.avro/avro))
- Maven or Gradle (for dependency management)
- Kafka CLI tools

## Step-by-Step Guide (General)
1. Set up Kafka, Zookeeper, and Schema Registry using Docker.
2. Scaffold a Java Maven/Gradle project for the pipeline.
3. Add Kafka, Avro, and Schema Registry libraries as dependencies.
4. Define Avro schemas for order and payment events.
5. Register schemas with Schema Registry.
6. Create `orders` and `payments` topics.
7. Implement a transactional Java producer to send order and payment events atomically.
8. Implement a Java consumer to process and validate events, ensuring exactly-once semantics.
9. Use Admin API to manage topics programmatically.
10. Monitor event flow and topic stats with CLI tools.
11. (Optional) Add unit/integration tests for producer and consumer logic.

<details>
<summary>Detailed Guide (Spoiler)</summary>

### 1. Set up Kafka, Zookeeper, and Schema Registry with Docker
- Use a Docker Compose file to run all services.

### 2. Scaffold Java Project & Add Dependencies
- Use Maven or Gradle to create a new Java project.
- Add dependencies for Kafka client, Avro, and Schema Registry in `pom.xml` or `build.gradle`.

### 3. Define Avro Schemas
- Create Avro schemas for order and payment events (e.g., `order.avsc`, `payment.avsc`).
- Register schemas with Schema Registry.

### 4. Create Topics
- Use CLI or Admin API to create `orders` and `payments` topics.

### 5. Implement Transactional Java Producer
- Configure producer for transactions and exactly-once delivery.
- Produce order and payment events atomically.
- Serialize events using Avro and Schema Registry.

### 6. Implement Java Consumer
- Consume from both topics.
- Validate event structure and ensure no duplicates.
- Process order and payment events together.

### 7. Use Admin API
- Programmatically manage topics (create, delete, describe) from Java.

### 8. Monitor with CLI Tools
- Use `kafka-console-consumer` to view events.
- Use `kafka-topics --describe` for topic info.

### 9. (Optional) Add Unit/Integration Tests
- Use JUnit/Testcontainers to test producer and consumer logic.

</details>

## Validation Steps
1. Confirm all services are running via Docker.
2. Verify schema registration and topic creation.
3. Ensure Java producer sends order/payment events transactionally.
4. Ensure Java consumer processes events with exactly-once semantics.
5. Validate schema compliance and event structure.
6. Use CLI tools to inspect event flow and topic status.
7. Check application logs for errors and test results.
