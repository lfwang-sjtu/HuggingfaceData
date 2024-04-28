package org.example.Bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.example.HiveWriter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HiveDatasetBolt extends BaseRichBolt {
    private OutputCollector outputCollector;
    private static final int BATCH_SIZE = 50;
    private List<Tuple> buffer = new ArrayList<>();
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        buffer.add(tuple);
        if (buffer.size() >= BATCH_SIZE) {
            flushBuffer();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    private void flushBuffer() {
        if (!buffer.isEmpty()) {
            HiveWriter hiveWriter = new HiveWriter();
            try {
                hiveWriter.insertDatasetList(buffer);
            } catch (SQLException e) {
                throw new RuntimeException("SQL failed");
            } finally {
                hiveWriter.closeConnection(); // 在插入完成后关闭连接
                buffer.clear(); // 清空缓冲区
            }
        }
    }
}
