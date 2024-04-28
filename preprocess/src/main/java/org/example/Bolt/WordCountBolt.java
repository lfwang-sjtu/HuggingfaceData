package org.example.Bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordCountBolt extends BaseBasicBolt {
    private Map<String, Integer> counts = new HashMap<String, Integer>();
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String word = tuple.getStringByField("word");
        Integer count = counts.get(word);
        if( count == null ) {
            count = 0;
        }
        count++;
        counts.put(word, count);
        System.out.println("\n*** Word Count Bolt *** " + word + " = " + count + " ***\n");
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("/usr/output.txt"));
            out.write("I did run");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        basicOutputCollector.emit(new Values(word, count));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word", "count"));
    }
}
