package org.example;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.shade.com.google.common.collect.ImmutableList;
import org.apache.storm.topology.TopologyBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KafkaJsonTopology {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
//        BrokerHosts hosts = new ZkHosts("localhost:9092");
//        SpoutConfig sc = new SpoutConfig(hosts, "json_data", "/brokers/topics/json_data", UUID.randomUUID().toString());
        builder.setSpout("DataSourceSpout", new ImportJsonSpout());
        builder.setBolt("CountBolt", new CountBolt()).shuffleGrouping("DataSourceSpout");

        Config config = new Config();
        List<String> zookeeperServers = new ArrayList<>();
        zookeeperServers.add("localhost");
        config.put("storm.zookeeper.servers", zookeeperServers);
        config.put("storm.zookeeper.port", 2181);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("KafkaJsonTopology", config, builder.createTopology());
    }
}