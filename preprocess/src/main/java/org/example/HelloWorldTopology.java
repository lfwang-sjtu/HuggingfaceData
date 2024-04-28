package org.example;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.thrift.TException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.example.Bolt.SplitSentenceBolt;
import org.example.Bolt.WordCountBolt;
import org.example.Spout.RandomSentenceSpout;

import java.util.concurrent.TimeUnit;

public class HelloWorldTopology {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("generator",
                new RandomSentenceSpout(), 1);

        builder.setBolt("splitter",
                        new SplitSentenceBolt(), 1)
                .shuffleGrouping("generator");

        builder.setBolt("counter",
                        new WordCountBolt(), 2)
                .fieldsGrouping("splitter", new Fields("word"));

        try {
            StormSubmitter.submitTopology("ClusterModelCountApp",  new Config(), builder.createTopology());
        } catch (AlreadyAliveException | InvalidTopologyException | AuthorizationException e) {
            e.printStackTrace();
        }

//        final Config conf = new Config();
//        conf.setDebug(true);
//        LocalCluster cluster = new LocalCluster();
//        cluster.submitTopology("EmptyTopology", conf, builder.createTopology());
//        System.out.println("succeed!");
//        TimeUnit.SECONDS.sleep(30);
//        cluster.killTopology("EmptyTopology");
//        cluster.shutdown();
    }
}
