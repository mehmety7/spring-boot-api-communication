https://howtodoinjava.com/wp-content/uploads/2023/06/Apache-Kafka-Architecture.png

---

Structured data: Can be stored in db

Semi-structured data: Can be stored textual in JSON, XML

Quasi-structured data: Can be formatted with effort and tools. Web page, logs

Unstructured data: Can be stored in textual format. PDF, xls, email

---

Big Data: 5 V's

Velocity: Speed of data generation

Volume: Amount of data

Variety: Different types of data

Veracity: Uncertainty (quality) of data 

Value: Worth of data

---

Big Data Analytics:

It is beneficial to take better decisions, reduce costs, and provide better services.

* Reducing decision time
* Reducing costs
* Understanding customer needs and market
* Manage online reputation
* Detecting fraud and security threats

---

Big Data Technologies:

* Hadoop
* Spark
* Kafka
* Cassandra
* HBase
* MongoDB
* Hive
* Pig
* Sqoop
* Flume

---

Messaging System:

Publish/Subscribe messaging system has three components:
* Producer: Publishes messages - sender
* Consumer: Subscribes to topics - receiver
* Broker: which publishes messages from producer to consumer

Kafka decouples Data pipelines and removes the dependency between components.
It was developed by LinkedIn that can handle user activity and system metrics in real-time.
And it is later open-sourced in 2010.
Kafka is a distributed streaming platform.

Kafka can be used for:
* Messaging system
* Activity tracking and log aggregation
* Real-time data processing

Features of Kafka:
* High throughput: It can handle millions of messages per second.
* Data Loss: Ensures no data loss. It can replicate data across multiple brokers.
* Durability: It can store data on disk for a long time.
* Scalability: Distributed system, can scale horizontally without downtime.
* Stream Processing: It can be used along with Spark, Storm, Flink, etc.
* Replication: It can replicate data across clusters. Provides fault tolerance and supports for multiple subscribers.

---

Some use cases of Kafka:
* Messaging: High throughput, low latency, built-in partitioning, replication, and fault-tolerance.
* Metrics and logging: Accumulating measurements from distributed systems.
* Commit log: It can serve as an external commit-log for distributed systems.
* Log aggregation: Collecting physical log files from servers and putting them in a central place.
* Stream processing: It operates on data in real-time, as quickly as messages are produced.
* Event sourcing: Kafka can be used as a database. It can store the state of the system as a sequence of events. It can order events and replay them.
* User activity tracking: Site activity tracking, real-time monitoring.

---

Kafka Terminology:

- Message: It is a unit of data in Kafka, key-value pair.
- Topic: It is a category of messages.
- Partition: Topics are divided into partitions. Each partition is ordered and can be replicated. It is a unit of parallelism. It is a log.
- Batch: A collection of messages which have same topic and partition.
- Producer: It publishes messages to Kafka topics.
- Consumer: It subscribes to topics and processes messages.
- Broker: It is a Single Kafka server. It stores messages in topics.
- Zookeeper: It manages and coordinates Kafka brokers.
- Cluster: It is a set of Zookeeper servers and Kafka brokers.

---

Kafka Components:

- Messages having same key will always go to the same partition.
- Messages having no key will go to a random partition. If it needs to go to a specific partition, it can be done by providing a partition number aka key.
- Brokers receive messages from producers, assign offsets, and commit messages to disk. 
- Zookeeper manages brokers and maintains the state of the Kafka cluster. Before 0.10.0 version, it was used to store offsets and metadata.
- In production, it's recommended to have multiple (odd number) zookeepers. It can tolerate the failure of one zookeeper. It will be one leader and others will be followers.
- Batch has trade-offs. It can increase throughput but can increase latency. It can be configured in producer and consumer. It can be used compression and decompression of messages. It can be used to reduce the number of requests to Kafka. 

---

Kafka Clusters:

There are 3 types of Kafka clusters:
- Single Node Single Broker Cluster: It is used for development and testing. There is no backup and fault tolerance.
- Single Node Multi Broker Cluster: It is used for development and testing. It has multiple brokers but no fault tolerance when the node (server) goes down.
- Multi Node Multi Broker Cluster: It is used for production. It has multiple servers have multiple brokers. It has fault tolerance and high availability.

---

Setup Kafka:
Kafka environment setup needs to have Java, Scala, Zookeeper, and Kafka. Kafka is written in Scala and Java. Zookeeper is used to manage Kafka brokers.

---

Producer API:
- They might have different requirements like high throughput, low latency, message delivery guarantee, etc.
- ProducerRecord has topic, value, partition (optional), key (optional), timestamp (optional).
- ProducerConfig has bootstrap.servers, key.serializer, value.serializer, acks, retries, etc.

bootstrap.servers: 
- It is a list of Kafka brokers. 
- List of host:port separated by commas. 
- No need to provide all brokers. It will discover all brokers after initial connection. 
- It is recommended to provide at least 2 brokers if one goes down.

key.serializer:
- It is used to serialize the key of the message.
- It is required if it is sent only value.
- Brokers expect messages in byte format. However, it can be string serializer, integer serializer, and byte serializer.
- It can be customized by implementing the Apache Kafka Serializer interface.

value.serializer:
- It is used to serialize the value of the message.

Optional configurations:
- These parameters may impact on memory use, performance, and reliability of the producers.

acks:
- acks=0: Producer will not wait for acknowledgment. It can lose messages.
- acks=1: Producer will wait for the leader to acknowledge. It can lose messages if the leader goes down.
- acks=all: Producer will wait for the leader and all replicas to acknowledge. It is slow but safe.

retries:
- retries: Number of retries before giving up. Default is 3 per 100 ms.

batch.size:
- batch.size: Amount of memory to use for buffering messages. Default is 16 KB.

linger.ms:
- linger.ms: Time to wait for more messages to arrive before sending the current batch. 
It can increase throughput but can increase latency. Default is 0.

buffer.memory:
- buffer.memory: Total memory to be used for buffering messages.

compression.type:
- compression.type: It can be none, gzip, snappy, lz4.

max.in.flight.requests.per.connection:
- max.in.flight.requests.per.connection: It is the number of unacknowledged requests the client will send. 
Setting it to 1 can guarantee the order of messages. 
Setting it to more than 1 can increase throughput but can lose the order of messages.

client.id:
- client.id: It is used to identify the client.

timeout.ms, request.timeout.ms, and metadata.fetch.timeout.ms:
- timeout.ms: Broker will wait for the acknowledgment from in-sync replicas if the acks=all.
- request.timeout.ms: How long to wait for a response from the broker.
- metadata.fetch.timeout.ms: How long to wait for requesting metadata.

max.block.ms:
- max.block.ms: How long the producer will block when calling send() when explicitly requesting metadata via partitionsFor().

max.request.size:
- max.request.size: Maximum size of a request.

receive.buffer.bytes and send.buffer.bytes:
- receive.buffer.bytes: Size of the TCP receive buffer. If it is set to -1, it will use the OS default.
- send.buffer.bytes: Size of the TCP send buffer. If it is set to -1, it will use the OS default.

---

Ways to send messages:
- Fire and forget: It is the fastest way. It does not wait for acknowledgment. Click tracking, log collection.
- Synchronous: It waits for acknowledgment. It can be slow. Customer orders, payment processing, user registration.
- Asynchronous: It is faster than synchronous. It can be used for high throughput. No need to wait for acknowledgment. It is used callback function to handle acknowledgment.

Common errors:
- Retriable errors: Network errors, broker not available, leader not available, etc.
- Non-retriable errors: Serialization errors, invalid topic, etc.




