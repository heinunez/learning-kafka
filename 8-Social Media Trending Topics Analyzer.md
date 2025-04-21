# Social Media Trending Topics Analyzer

## Description
Process and analyze social media streams to detect trending topics using windowed aggregations and stateful processing. This project demonstrates windowed analytics and real-time stream processing using Java, Kafka Streams, and Docker. The solution is backend-focused, leverages the Java ecosystem, and is fully testable on a local machine.

## Topics Covered
- Kafka fundamentals (brokers, topics, partitions)
- Java producer and consumer APIs
- Kafka Streams for windowed aggregations
- Event serialization (JSON)
- Stateful processing and windowing
- Monitoring with Kafka CLI tools
- Project structure and dependency management

## Tools
- Java (using the official Kafka client library: [org.apache.kafka:kafka-clients](https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients) and Kafka Streams: [org.apache.kafka:kafka-streams](https://mvnrepository.com/artifact/org.apache.kafka/kafka-streams))
- Docker (for Kafka and Zookeeper)
- Jackson ([com.fasterxml.jackson.core:jackson-databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind))
- Maven or Gradle (for dependency management)
- Kafka CLI tools

## Step-by-Step Guide (General)
1. Set up Kafka and Zookeeper using Docker.
2. Scaffold a Java Maven/Gradle project for the pipeline.
3. Add Kafka, Kafka Streams, and Jackson libraries as dependencies.
4. Create a `social-media` topic.
5. Implement a Java producer to simulate social media posts (e.g., tweets, messages) and send to Kafka.
6. Implement a Kafka Streams application to aggregate and detect trending topics using windowed processing.
7. Output trending topics to another topic or log to console.
8. Monitor event flow and topic stats with CLI tools.
9. (Optional) Add unit/integration tests for producer and processor logic.

<details>
<summary>Detailed Guide (Spoiler)</summary>

### 1. Set up Kafka and Zookeeper with Docker
- Use Docker Compose as in previous projects.

### 2. Scaffold Java Project & Add Dependencies
- Use Maven or Gradle to create a new Java project.
- Add dependencies for Kafka client, Kafka Streams, and Jackson in `pom.xml` or `build.gradle`.

### 3. Create the Topic
- Use CLI: `docker exec -it <kafka_container_id> kafka-topics --create --topic social-media --bootstrap-server localhost:9092 --partitions 2 --replication-factor 1`

### 4. Implement Java Social Media Producer
- Simulate posts (e.g., `{ "userId": "u1", "message": "Kafka is awesome!", "timestamp": 1680000000000 }`).
- Serialize as JSON using Jackson.
- Send events to `social-media` topic.

### 5. Kafka Streams Trending Topics Analyzer
- Use Kafka Streams API to process `social-media` topic.
- Use windowed aggregations to count keyword frequency over time windows.
- Output trending topics to another topic or log to console.

### 6. Monitor with CLI Tools
- Use `kafka-console-consumer` to view events.
- Use `kafka-topics --describe` for topic info.

### 7. (Optional) Add Unit/Integration Tests
- Use JUnit/Testcontainers to test producer and processor logic.

</details>

## Validation Steps
1. Confirm Kafka and Zookeeper are running via Docker.
2. Verify `social-media` topic creation.
3. Ensure Java producer sends simulated posts to Kafka.
4. Ensure Kafka Streams app detects and outputs trending topics.
5. Use CLI tools to inspect event flow and topic status.
6. Check application logs for errors and test results.
