# Structured List Strategies for Kafka Topics Extraction

## Overview
To extract and organize topics from `contents.md`, we considered three possible structured list strategies. Below are the strategies, their pros and cons, and the rationale for our chosen approach.

---

### 1. Flat List of Topics
- All topics and subtopics are listed sequentially in a single-level list.
- **Pros:** Simple, easy to scan.
- **Cons:** Lacks hierarchy, harder to see relationships or dependencies.

### 2. Hierarchical (Tree) Structure
- Topics are organized into parent-child relationships, reflecting the structure in `contents.md`.
- **Pros:** Shows dependencies, logical grouping, and progression.
- **Cons:** Slightly more complex to maintain.

### 3. Thematic Clusters with Tags
- Topics are clustered by theme/use-case (e.g., “Data Ingestion”, “Stream Processing”, “Reliability & Security”), with tags for skills or requirements.
- **Pros:** Quickly maps topics to project ideas or real-world scenarios.
- **Cons:** May obscure the original learning progression; some topics may fit multiple clusters.

---

## Chosen Strategy: Hierarchical (Tree) Structure

**Rationale:**
- Closely mirrors the organization of most learning materials.
- Preserves logical groupings and dependencies, making it easier to design projects of increasing complexity.
- Facilitates comprehensive coverage and clear mapping to real-world use cases.

This structure will be used for the topic extraction step and as a reference for subsequent project design.
