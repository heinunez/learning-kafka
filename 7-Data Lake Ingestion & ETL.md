# Data Lake Ingestion & ETL

## Description
Use Kafka Connect to ingest, transform, and load data from various sources into a local data lake or database. This project demonstrates how to build a backend ETL pipeline using Kafka Connect, Java, and Docker, suitable for a backend software engineer. The solution is backend-focused, leverages the Java ecosystem, and is fully testable on a local machine.

## Topics Covered
- Kafka fundamentals (brokers, topics, partitions)
- Kafka Connect (source/sink connectors, SMTs)
- Data serialization (JSON/Avro)
- Integration with files, databases, or other systems
- Java for custom connectors or transformations
- Monitoring with Kafka CLI tools
- Project structure and dependency management

## Tools
- Java (for custom connectors or transformations)
- Docker (for Kafka, Zookeeper, Kafka Connect)
- Kafka Connect (Docker)
- Jackson ([com.fasterxml.jackson.core:jackson-databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind)) or Avro ([org.apache.avro:avro](https://mvnrepository.com/artifact/org.apache.avro/avro))
- Maven or Gradle (for dependency management)
- Local file system or embedded database (e.g., H2, SQLite)
- Kafka CLI tools

## Step-by-Step Guide (General)
1. Set up Kafka, Zookeeper, and Kafka Connect using Docker.
2. Scaffold a Java Maven/Gradle project for custom connectors/SMTs (optional).
3. Add Kafka, Kafka Connect, and serialization libraries as dependencies.
4. Configure a FileSource connector to ingest data from a local file into a Kafka topic (e.g., `raw-data`).
5. (Optional) Implement a custom Single Message Transform (SMT) in Java.
6. Configure a Sink connector to load data from Kafka into a local file or database (e.g., `data-lake`).
7. Monitor data flow and connector status using Kafka Connect REST API and CLI tools.
8. (Optional) Add unit/integration tests for custom connectors/SMTs.

<details>
<summary>Detailed Guide (Spoiler)</summary>

### 1. Set up Kafka, Zookeeper, and Kafka Connect with Docker
- Use Docker Compose to run all required services.

### 2. Scaffold Java Project & Add Dependencies (Optional)
- Use Maven or Gradle to create a new Java project for custom connectors or SMTs.
- Add dependencies for Kafka client, Kafka Connect, and Jackson/Avro in `pom.xml` or `build.gradle`.

### 3. Configure FileSource Connector
- Use Kafka Connect REST API or config file to set up a FileSource connector to ingest data from a file into the `raw-data` topic.

### 4. (Optional) Custom SMT in Java
- Write a Java class for a custom SMT (e.g., data cleaning or enrichment) and deploy it to Kafka Connect.

### 5. Configure Sink Connector
- Use Kafka Connect REST API or config file to set up a Sink connector to load data from the `raw-data` topic to a file or database (e.g., H2, SQLite).

### 6. Monitor with CLI Tools
- Use `kafka-console-consumer` to view data in topics.
- Use Kafka Connect REST API to check connector status.
- Use `kafka-topics --describe` for topic info.

### 7. (Optional) Add Unit/Integration Tests
- Use JUnit/Testcontainers to test custom connectors or SMT logic.

</details>

## Validation Steps
1. Confirm all services are running via Docker.
2. Verify FileSource and Sink connectors are configured and running.
3. Ensure data is ingested from the source file into the `raw-data` topic.
4. (Optional) Ensure custom SMT is applied.
5. Ensure data is loaded from Kafka into the data lake file or database.
6. Use CLI tools and REST API to inspect data flow and connector status.
7. Check application logs for errors and test results.
