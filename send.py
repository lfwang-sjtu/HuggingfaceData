from kafka import KafkaProducer
import json
import time

# Kafka server address
bootstrap_servers = 'slave2:9092'

# Create Kafka producer
producer = KafkaProducer(bootstrap_servers=bootstrap_servers)

# JSON array file path
json_file = 'hf_metadata_small.json'

# Topic to send messages to
topic = 'json_data'

# Read JSON file
with open(json_file, 'r') as file:
    data = json.load(file)

# Number of records to send each time
batch_size = 5

# Interval between each batch of messages (seconds)
interval = 5

# Loop to send messages
for i in range(0, len(data), batch_size):
    batch = data[i:i+batch_size]  # Get records for the current batch
    for item in batch:
        # Send the record to the Kafka topic
        producer.send(topic, json.dumps(item).encode('utf-8'))
        print("Sent:", item)
    # Flush and wait
    producer.flush()
    time.sleep(interval)

# Close the Kafka producer connection
producer.close()