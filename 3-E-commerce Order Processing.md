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
- Create a `docker-compose.yml` file to spin up Kafka, Zookeeper, and the Confluent Schema Registry:
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
    schema-registry:
      image: confluentinc/cp-schema-registry:latest
      ports:
        - "8081:8081"
      environment:
        SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS: PLAINTEXT://kafka:9092
        SCHEMA_REGISTRY_HOST_NAME: schema-registry
  ```
- Start services: `docker-compose up -d`

### 2. Scaffold Java Project & Add Dependencies
- Use Maven or Gradle to create a new Java project for the backend pipeline.
- Add dependencies to `pom.xml` or `build.gradle`:
  - `org.apache.kafka:kafka-clients`
  - `org.apache.avro:avro`
  - `io.confluent:kafka-schema-registry-client`

### 3. Define Avro Schemas for Order and Payment Events
- Create Avro schema files (e.g., `order.avsc`, `payment.avsc`) describing the structure of order and payment events.
- Example `order.avsc`:
  ```json
  {
    "type": "record",
    "name": "Order",
    "fields": [
      {"name": "orderId", "type": "string"},
      {"name": "userId", "type": "string"},
      {"name": "amount", "type": "double"},
      {"name": "timestamp", "type": "long"}
    ]
  }
  ```
- Example `payment.avsc`:
  ```json
  {
    "type": "record",
    "name": "Payment",
    "fields": [
      {"name": "paymentId", "type": "string"},
      {"name": "orderId", "type": "string"},
      {"name": "status", "type": "string"},
      {"name": "timestamp", "type": "long"}
    ]
  }
  ```

### 4. Register Schemas with Schema Registry
- Use the Schema Registry REST API or Java client to register the Avro schemas for both `orders` and `payments` topics.
- Example (using curl):
  ```bash
  curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data '{"schema": "<contents of order.avsc>"}' \
    http://localhost:8081/subjects/orders-value/versions
  ```
- Repeat for `payments-value`.

### 5. Create `orders` and `payments` Topics
- Use Kafka CLI or Admin API to create topics:
  ```bash
  docker exec -it <kafka_container_id> kafka-topics --create --topic orders --bootstrap-server localhost:9092 --partitions 2 --replication-factor 1
  docker exec -it <kafka_container_id> kafka-topics --create --topic payments --bootstrap-server localhost:9092 --partitions 2 --replication-factor 1
  ```
- Or use the Admin API in Java for programmatic topic creation.

### 6. Implement a Transactional Java Producer
- Configure the producer for transactions (`enable.idempotence=true`, set a `transactional.id`).
- Initialize transactions and send order and payment events atomically to their respective topics.
- Serialize events using Avro and the Schema Registry.
- Example:
  ```java
  producer.initTransactions();
  producer.beginTransaction();
  producer.send(orderRecord);
  producer.send(paymentRecord);
  producer.commitTransaction();
  ```

### 7. Implement a Java Consumer for Exactly-Once Processing
- Consume from both `orders` and `payments` topics.
- Deserialize events using Avro and validate against schemas.
- Ensure no duplicate processing (commit offsets only after successful processing).
- Process order and payment events together as a transaction if needed.

### 8. Use Admin API for Topic Management
- Use Java Admin API to create, delete, or describe topics programmatically as needed for automation and testing.

### 9. Monitor Event Flow and Topic Stats with CLI Tools
- Use `kafka-console-consumer` to view events:
  ```bash
  docker exec -it <kafka_container_id> kafka-console-consumer --topic orders --from-beginning --bootstrap-server localhost:9092
  docker exec -it <kafka_container_id> kafka-console-consumer --topic payments --from-beginning --bootstrap-server localhost:9092
  ```
- Use `kafka-topics --describe` for topic info.

### 10. (Optional) Add Unit/Integration Tests
- Use JUnit and Testcontainers to test producer and consumer logic in isolation and with a running Kafka instance.

</details>

## Validation Steps
1. Confirm all services are running via Docker.
2. Verify schema registration and topic creation.
3. Ensure Java producer sends order/payment events transactionally.
4. Ensure Java consumer processes events with exactly-once semantics.
5. Validate schema compliance and event structure.
6. Use CLI tools to inspect event flow and topic status.
7. Check application logs for errors and test results.
