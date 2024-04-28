package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.KafkaSpoutRetryExponentialBackoff;
import org.apache.storm.kafka.spout.KafkaSpoutRetryService;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.example.Bolt.*;
import org.example.Spout.KafkaJsonSpout;
import org.example.Spout.RandomSentenceSpout;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class KafkaJsonTopology {
    private static final String BOOTSTRAP_SERVERS = "slave2:9092";
    private static final String TOPIC_NAME = "json_data";
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("KafkaJsonGenerator", new KafkaSpout<>(getKafkaSpoutConfig(BOOTSTRAP_SERVERS, TOPIC_NAME)), 1);

        builder.setBolt("FieldExtractor",
                        new ExtractFieldBolt(), 1)
                .shuffleGrouping("KafkaJsonGenerator");

        builder.setBolt("HiveWriter", new HiveWriterBolt(), 4).shuffleGrouping("FieldExtractor");

        builder.setBolt("DatasetWriter", new HiveDatasetBolt(), 4).shuffleGrouping("FieldExtractor");

        try {
            StormSubmitter.submitTopology("Preprocess",  new Config(), builder.createTopology());
        } catch (AlreadyAliveException | InvalidTopologyException | AuthorizationException e) {
            e.printStackTrace();
        }
    }

    private static KafkaSpoutConfig<String, String> getKafkaSpoutConfig(String bootstrapServers, String topic) {
        return KafkaSpoutConfig.builder(bootstrapServers, topic)
                // 除了分组 ID,以下配置都是可选的。分组 ID 必须指定,否则会抛出 InvalidGroupIdException 异常
                .setProp(ConsumerConfig.GROUP_ID_CONFIG, "preprocess")
                // 定义重试策略
                .setRetry(getRetryService())
                // 定时提交偏移量的时间间隔,默认是 15s
                .setOffsetCommitPeriodMs(10_000)
                .build();
    }

    private static KafkaSpoutRetryService getRetryService() {
        return new KafkaSpoutRetryExponentialBackoff(KafkaSpoutRetryExponentialBackoff.TimeInterval.microSeconds(500),
                KafkaSpoutRetryExponentialBackoff.TimeInterval.milliSeconds(2), Integer.MAX_VALUE, KafkaSpoutRetryExponentialBackoff.TimeInterval.seconds(10));
    }
}